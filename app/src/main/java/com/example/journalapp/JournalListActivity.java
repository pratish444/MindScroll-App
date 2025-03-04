package com.example.journalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class JournalListActivity extends AppCompatActivity {

    // Firebase Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    // Firestore Database
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Journal");

    // Firebase Storage
    private StorageReference storageReference;

    // List to hold Journals
    private List<Journal> journalList;

    // RecyclerView
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    // Floating Action Button
    private ExtendedFloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal_list);

        // Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        // Firebase Storage
        storageReference = FirebaseStorage.getInstance().getReference();

        // Initialize Widgets
        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fab);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Journal List
        journalList = new ArrayList<>();

        // Adapter Initialization
        myAdapter = new MyAdapter(JournalListActivity.this, journalList);
        recyclerView.setAdapter(myAdapter);

        // Floating Button Click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(JournalListActivity.this, AddJournalActivity.class);
                startActivity(i);
            }
        });
    }

    // Consolidated method to fetch journals
    private void fetchJournals() {
        if (user != null) {
            // Clear list before fetching to prevent duplicates
            journalList.clear();

            collectionReference
                    .whereEqualTo("userId", user.getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                for (QueryDocumentSnapshot journal : queryDocumentSnapshots) {
                                    Journal j = journal.toObject(Journal.class);
                                    journalList.add(j);
                                }

                                // Notify Adapter
                                myAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(JournalListActivity.this,
                                        "No Journals Found. Add your first journal!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(JournalListActivity.this,
                                    "Failed to Load Journals: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Menu Options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_add) {
            if (user != null) {
                Intent i = new Intent(JournalListActivity.this, AddJournalActivity.class);
                startActivity(i);
            }
        } else if (itemId == R.id.action_signout) {
            if (user != null) {
                firebaseAuth.signOut();
                startActivity(new Intent(JournalListActivity.this, MainActivity.class));
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is logged in
        if (user == null) {
            startActivity(new Intent(JournalListActivity.this, MainActivity.class));
            finish();
            return;
        }

        // Fetch journals
        fetchJournals();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Fetch journals again to ensure latest data
        fetchJournals();
    }
}