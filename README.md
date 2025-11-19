# 📱 AnimeKeep

**AnimeKeep** is a native Android application developed for tracking anime watching habits. The app is built using **Jetpack Compose** and follows the **MVVM architecture**. Anime data is fetched from the **Jikan API (MyAnimeList)**, while user data is securely stored and synchronized using **Firebase**.

This project was developed as part of a **Mobile Application Development** course.

---

## 🏗️ Project Structure

The project is organized according to **MVVM** and **Clean Architecture** principles:

```text
com.asude.animekeep/
│
├── data/        # API services and repository layer
├── model/       # Data classes (Anime, User, etc.)
└── ui/          # Jetpack Compose screens and ViewModels
    ├── home/
    ├── detail/
    ├── login/
    ├── mylist/
    └── profile/

🛠️ Tech Stack
Language: Kotlin

UI: Jetpack Compose (Material Design 3)

Architecture: MVVM

Backend: Firebase Authentication & Cloud Firestore

Networking: Retrofit + Gson (Jikan API v4)

Image Loading: Coil

Concurrency: Coroutines & Flow

Navigation: Navigation Compose

✨ Features
Discover Anime: Search and browse anime using real-time data from the Jikan API.

List Management:

Watching

Plan to Watch

Completed

Cloud Sync: User data is stored and synced with Firebase Firestore.

Authentication: User registration and login with Firebase Auth.

Profile & Statistics: Track progress and view your “Otaku Level” based on completed anime.

Dark Mode: Full dark theme support.

▶️ How to Run
Clone the repository:

Bash

git clone https://github.com/asudeaslan/AnimeKeep.git
Open the project in Android Studio.

Sync Gradle files.

Run the app on an emulator or a physical device.

Note: To use Firebase features, you need to add your own google-services.json file to the app directory.

👩‍🎓 Developer
Asude Aslan

West Pomeranian University of Technology

📄 License
This project was developed for educational purposes only.
