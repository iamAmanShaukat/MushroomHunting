# Mushroom-App

**Overview**

The Mushroom Hunting Tracker is an Android application designed for mushroom enthusiasts to document their hunting trips, keep track of their findings, and manage detailed information about various mushroom species. This user-friendly app allows users to enter trip details, log mushrooms found, and view past trips, all while providing an intuitive interface and seamless navigation.

**Features**

	•	Trip Management: Easily add new trips with essential details such as trip name, date, time, location, and expected duration.
	•	Mushroom Logging: Record findings during each trip, including mushroom type, location, quantity, and additional comments about edibility.
	•	View Trips: Access a list of all recorded trips, with the ability to edit or delete trip information.
	•	User-Friendly Interface: Clean and attractive UI designed for a smooth user experience.
	•	SQLite Database: All data is stored locally using an SQLite database for quick access and efficient storage.

**Technologies Used**

	•	Programming Language: Java
	•	Development Environment: Android Studio
	•	Database: SQLite
	•	UI Components: Android Jetpack libraries

**Installation**

To run this application locally:

	1.	Clone the repository:

git clone https://github.com/yourusername/MushroomHuntingApp.git


	2.	Open the project in Android Studio.
	3.	Build and run the app on an Android emulator or physical device.

 ## Project structure
 ```plaintext
my-app/
 ├── app/
 │   ├── src/
 │   │   ├── main/
 │   │   │   ├── java/com/example/myapp/
 │   │   │   │   ├── data/                <-- Folder for database-related components
 │   │   │   │   │   ├── db/              <-- Subfolder for database configuration
 │   │   │   │   │   │   └── AppDatabase.java  <-- SQLite/Room database class
 │   │   │   │   │   ├── dao/             <-- Subfolder for DAO (Data Access Object) classes
 │   │   │   │   │   │   └── TripDao.java  <-- Interface for Trip-related database operations
 │   │   │   │   │   ├── models/          <-- Subfolder for entity/data model classes
 │   │   │   │   │   │   └── Trip.java    <-- Data model class for Trip
 │   │   │   │   │   ├── repository/      <-- Subfolder for Repository classes (optional)
 │   │   │   │   │   │   └── TripRepository.java  <-- Class to abstract data operations
 │   │   │   │   ├── ui/                  <-- Folder for UI components (Activities, Fragments)
 │   │   │   │   │   ├── MainActivity.java <-- Main Activity of the app
 │   │   │   │   │   ├── AddTripActivity.java  <-- Activity for adding a new trip
 │   │   │   │   │   ├── TripDetailsActivity.java  <-- Activity to view/edit details
 │   │   │   │   ├── utils/               <-- Utility classes (e.g., constants, helpers)
 │   │   │   │   │   └── DatabaseHelper.java  <-- Utility for SQLite database (optional if using SQLite)
 │   │   │   │   └── AndroidManifest.xml  <-- Application manifest
 │   │   ├── res/                         <-- Resources (layout, images, strings, etc.)
 │   │   │   ├── drawable/                <-- Icons and images
 │   │   │   │   └── app_logo.png         <-- App logo image file
 │   │   │   ├── layout/                  <-- XML layouts for activities and UI components
 │   │   │   │   ├── activity_main.xml    <-- Layout for MainActivity
 │   │   │   │   ├── activity_add_trip.xml <-- Layout for adding a new trip
 │   │   │   │   ├── activity_trip_details.xml  <-- Layout for trip details
 │   │   │   ├── values/                  <-- Resource values (strings, colors, etc.)
 │   │   │   │   ├── strings.xml          <-- String resources (app texts)
 │   │   │   │   ├── colors.xml           <-- App color scheme
 │   │   │   │   └── themes.xml           <-- Application themes
 │   │   ├── AndroidManifest.xml          <-- Main Android manifest file
 │   ├── build.gradle                     <-- Project build script
 │   └── proguard-rules.pro               <-- ProGuard rules for obfuscation
