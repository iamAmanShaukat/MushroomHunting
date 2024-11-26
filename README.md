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
 ####
		git clone https://github.com/yourusername/MushroomHuntingApp.git
####

	2.	Open the project in Android Studio.
	3.	Build and run the app on an Android emulator or physical device.

 ## Project structure
 ```plaintext
│  ├─ .gitignore
│  ├─ .name
│  ├─ appInsightsSettings.xml
│  ├─ compiler.xml
│  ├─ deploymentTargetSelector.xml
│  ├─ easycode.ignore
│  ├─ gradle.xml
│  ├─ migrations.xml
│  ├─ misc.xml
│  ├─ render.experimental.xml
│  ├─ runConfigurations.xml
│  └─ vcs.xml
├─ README.md
├─ Screenshot_20241126_133409.png
├─ app
│  ├─ .gitignore
│  ├─ build.gradle.kts
│  ├─ google-services.json
│  ├─ proguard-rules.pro
│  └─ src
│     ├─ androidTest
│     │  └─ java
│     │     └─ com
│     │        └─ example
│     │           └─ mushroomhunting
│     │              └─ ExampleInstrumentedTest.java
│     ├─ main
│     │  ├─ AndroidManifest.xml
│     │  ├─ ic_launcher-playstore.png
│     │  ├─ java
│     │  │  └─ com
│     │  │     └─ example
│     │  │        └─ mushroomhunting
│     │  │           ├─ activity
│     │  │           │  ├─ AddMushroomDetailsActivity.java
│     │  │           │  ├─ AddTripActivity.java
│     │  │           │  ├─ ClearActivity.java
│     │  │           │  ├─ EditTripActivity.java
│     │  │           │  ├─ HelpActivity.java
│     │  │           │  ├─ MainActivity.java
│     │  │           │  ├─ PrivacyActivity.java
│     │  │           │  ├─ RecentActivityFragment.java
│     │  │           │  ├─ SettingsActivity.java
│     │  │           │  ├─ SyncActivity.java
│     │  │           │  ├─ TripDetailsActivity.java
│     │  │           │  ├─ ViewMushroomDetailsActivity.java
│     │  │           │  └─ ViewTripsActivity.java
│     │  │           ├─ adapter
│     │  │           │  ├─ MushroomAdapter.java
│     │  │           │  └─ TripAdapter.java
│     │  │           ├─ constant
│     │  │           │  └─ AppConstants.java
│     │  │           ├─ db
│     │  │           │  ├─ CacheHelper.java
│     │  │           │  ├─ CloudHelper.java
│     │  │           │  ├─ DatabaseHelper.java
│     │  │           │  └─ DatabaseManager.java
│     │  │           ├─ dto
│     │  │           │  ├─ MushroomDto.java
│     │  │           │  ├─ TripDto.java
│     │  │           │  └─ ViewTripDTO.java
│     │  │           ├─ util
│     │  │           │  ├─ AppContextUtil.java
│     │  │           │  ├─ LocationUtils.java
│     │  │           │  └─ TripDiffCallback.java
│     │  │           └─ validate
│     │  │              └─ Validation.java
│     │  └─ res
│     │     ├─ drawable
│     │     │  ├─ add_icon.xml
│     │     │  ├─ background_image.png
│     │     │  ├─ bg_1.png
│     │     │  ├─ bg_2.png
│     │     │  ├─ bg_3.png
│     │     │  ├─ calendar.xml
│     │     │  ├─ custom_dialog_background.xml
│     │     │  ├─ delete_icon.xml
│     │     │  ├─ details_background.xml
│     │     │  ├─ edit_icon.xml
│     │     │  ├─ favorite_icon.xml
│     │     │  ├─ gradient_scrim.xml
│     │     │  ├─ hello.xml
│     │     │  ├─ help_icon.xml
│     │     │  ├─ home_icon.xml
│     │     │  ├─ ic_add.xml
│     │     │  ├─ ic_calendar.xml
│     │     │  ├─ ic_camera.xml
│     │     │  ├─ ic_category.xml
│     │     │  ├─ ic_clear.xml
│     │     │  ├─ ic_comment.xml
│     │     │  ├─ ic_date.xml
│     │     │  ├─ ic_delete_all.xml
│     │     │  ├─ ic_description.xml
│     │     │  ├─ ic_details.xml
│     │     │  ├─ ic_duration.xml
│     │     │  ├─ ic_empty_trips.xml
│     │     │  ├─ ic_launcher_background.xml
│     │     │  ├─ ic_launcher_foreground.xml
│     │     │  ├─ ic_location.xml
│     │     │  ├─ ic_mushroom.xml
│     │     │  ├─ ic_quantity.xml
│     │     │  ├─ ic_save.xml
│     │     │  ├─ ic_time.xml
│     │     │  ├─ ic_travel.xml
│     │     │  ├─ ic_trip.xml
│     │     │  ├─ info_background.xml
│     │     │  ├─ list_background_1.xml
│     │     │  ├─ list_background_2.xml
│     │     │  ├─ list_background_4.xml
│     │     │  ├─ menu_icon.xml
│     │     │  ├─ mushroom_app_icon.xml
│     │     │  ├─ mushroom_icon.xml
│     │     │  ├─ mushroom_jungle.jpg
│     │     │  ├─ privacy_icon.xml
│     │     │  ├─ recent_icon.xml
│     │     │  ├─ scrim_gradient.xml
│     │     │  ├─ settings_icon.xml
│     │     │  ├─ status_background.xml
│     │     │  ├─ strings.xml
│     │     │  ├─ sync_icon.xml
│     │     │  ├─ tab_background_selector.xml
│     │     │  ├─ tab_default_background.xml
│     │     │  ├─ tab_selected_background.xml
│     │     │  ├─ travel_icon.xml
│     │     │  └─ view_eye_icon.xml
│     │     ├─ layout
│     │     │  ├─ activity_add_mushroom_details.xml
│     │     │  ├─ activity_add_trip.xml
│     │     │  ├─ activity_clear.xml
│     │     │  ├─ activity_edit_trip.xml
│     │     │  ├─ activity_help.xml
│     │     │  ├─ activity_main.xml
│     │     │  ├─ activity_privacy.xml
│     │     │  ├─ activity_settings.xml
│     │     │  ├─ activity_trip_details.xml
│     │     │  ├─ activity_view_mushroom_details.xml
│     │     │  ├─ activity_view_trips.xml
│     │     │  ├─ dialog_confirm_delete.xml
│     │     │  ├─ fragment_recent_activity.xml
│     │     │  ├─ mushroom_item.xml
│     │     │  └─ trip_item.xml
│     │     ├─ menu
│     │     │  └─ main_menu.xml
│     │     ├─ mipmap-anydpi-v26
│     │     │  ├─ ic_launcher.xml
│     │     │  └─ ic_launcher_round.xml
│     │     ├─ mipmap-hdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  ├─ ic_launcher_background.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ mipmap-mdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  ├─ ic_launcher_background.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ mipmap-xhdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  ├─ ic_launcher_background.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ mipmap-xxhdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  ├─ ic_launcher_background.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ mipmap-xxxhdpi
│     │     │  ├─ ic_launcher.webp
│     │     │  ├─ ic_launcher_background.webp
│     │     │  └─ ic_launcher_round.webp
│     │     ├─ raw
│     │     │  └─ privacy_policy.html
│     │     ├─ values-night
│     │     │  ├─ colors.xml
│     │     │  ├─ strings.xml
│     │     │  ├─ styles.xml
│     │     │  └─ themes.xml
│     │     ├─ values
│     │     │  ├─ colors.xml
│     │     │  ├─ strings.xml
│     │     │  ├─ style.xml
│     │     │  ├─ styles.xml
│     │     │  └─ themes.xml
│     │     └─ xml
│     │        ├─ backup_rules.xml
│     │        └─ data_extraction_rules.xml
│     └─ test
│        └─ java
│           └─ com
│              └─ example
│                 └─ mushroomhunting
│                    └─ ExampleUnitTest.java
├─ build.gradle.kts
├─ gradle.properties
├─ gradle
│  ├─ libs.versions.toml
│  └─ wrapper
│     ├─ gradle-wrapper.jar
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
└─ settings.gradle.kts
