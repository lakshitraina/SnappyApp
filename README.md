SnappyApp
Snappy is a real-time video calling app built using WebRTC, Firebase, and PeerJS. It allows users to connect with friends and make seamless video calls directly from their devices. The app includes user authentication, profile management, and audio/video call controls.

Features
Real-time Video Call: Seamless audio and video communication using WebRTC.
User Authentication: Sign in with Firebase Authentication for secure login.
User Profiles: Display and update user profile information.
Audio/Video Controls: Mute/unmute audio and enable/disable video during calls.
Cross-Platform Support: Compatible with Android and Web, ensuring accessibility across different platforms.
Prerequisites
Firebase account to set up the backend.
Firebase credentials (API keys) for integration.
Android Studio for running the Android app.
Web browser for testing the web version.
Installation
1. Clone the repository
git clone https://github.com/lakshitraina/SnappyApp.git
2. Firebase Setup
Go to Firebase Console.
Create a new project.
Set up Firebase Authentication (Email/Password sign-in).
Set up Firebase Database and Firestore.
Add your Firebase configuration to the firebase directory in the app.
3. Set up Android app
Open the project in Android Studio.
Add your Firebase configuration in the google-services.json file.
4. Web Setup
For the Web version, navigate to the web directory in the project and follow the steps in the WebRTC setup to configure the app.
Usage
For Android: Install the app on your Android device, sign in, and start making calls by adding friends.
For Web: Open the call.html file in a browser to start video calling with friends.
Contributions
Feel free to fork the project, make improvements, and submit a pull request. Contributions are always welcome!

License
This project is licensed under the MIT License
