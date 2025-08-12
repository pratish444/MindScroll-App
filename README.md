# 📓 MindScroll App

**Tech Stack:** XML, Java, MVVM, Firebase Authentication, Cloud Firestore, GitHub

---

## 📌 Overview
MindScroll is a native Android journaling application that enables users to securely create, manage, and store personal journal entries in real-time.  
Built using **Firebase backend services** and the **MVVM architecture pattern**, the app ensures **data persistence**, **responsive layouts**, and a **smooth user experience** across devices.

---

## ✨ Features
- 🔐 **Secure User Authentication**  
  Email/Password-based authentication using **Firebase Authentication**.
  
- ☁ **Real-Time Cloud Storage**  
  Store and sync journal entries instantly using **Cloud Firestore**.
  
- ✏ **CRUD Operations**  
  Create, Read, Update, and Delete journal entries seamlessly.
  
- 🎨 **Material Design UI**  
  Responsive and intuitive layout for a pleasant user experience.
  
- 📱 **Device Compatibility**  
  Works smoothly across various Android devices and screen sizes.

---

## 🛠 Tech Stack
- **Frontend:** XML (UI Layouts), Java (App Logic)  
- **Architecture:** MVVM (Model-View-ViewModel)  
- **Backend:** Firebase Authentication, Cloud Firestore  
- **Version Control:** GitHub

---

## 📦 Dependencies & Plugins

### **Module-level `build.gradle` (`app/build.gradle`):**
```gradle
plugins {
    id 'com.android.application'
    id 'com.google.gms.google-services' // Firebase plugin
}

android {
    namespace 'com.example.mindscroll'
    compileSdk 34

    defaultConfig {
        applicationId "com.example.mindscroll"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding true
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    // AndroidX
    implementation 'androidx.core:core-ktx:1.12.0'
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0'

    // Firebase
    implementation platform('com.google.firebase:firebase-bom:32.7.0')
    implementation 'com.google.firebase:firebase-auth'
    implementation 'com.google.firebase:firebase-firestore'

    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

Project-level build.gradle (Root folder):
    ```gradle

    buildscript {
    dependencies {
        classpath 'com.google.gms:google-services:4.4.0'
       }
     }

 ## Clone the repository:



     git clone https://github.com/pratish444/MindScroll-App.git



            
