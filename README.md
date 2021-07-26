Personality Test
===========================================================

Worked on “Personality Test” project: https://github.com/sparknetworks/coding_exercises_options/blob/master/personality_test/README.md
Mostly concentrated on the Android based front end development.

Introduction
-------------

### Functionality
The app is composed of 2 main screens.

#### CategoryListFragment
This fragment display the categories fetched from network (for app first launch) and later it will be fetched from the local DB.

#### QuestionsFragment
This fragment display the Personality test questions for a selected category in CategoryListFragment.
Questions will be fetched from network for first time and later it will be displayed from the local DB. 
User can interact with the Personality test questions in offline/online mode. Once they are comfortable with the selection, answers can be submitted to backend in one shot per category.

**NOTE** This note is for Personality questions which changes frequently from backend. If it’s static, no issues.
Currently there is no expiration to local DB content, once loaded from network via rest api. Hence on subsequent app launches, questions will be read from local DB.  
Probably we can extend this project, to have DB content valid for 24 hours and re-fetch from network once expired.

### Server:
For Server interactions, I have used open source **JSON Server** which helps to fully fake Rest API and DB there in. 
Installation steps:
1. npm install -g json-server
2. From terminal, go to project folder /PersonalityTest/fake-json-server (cd /PersonalityTest/fake-json-server)
3. Run command: json-server --watch personality_test.json
4. Please make sure you are able to load http://localhost:3000 in browser. Server Setup is done.
5. More info: https://www.npmjs.com/package/json-server

### Building
You can open the project in Android studio and press run. All ready.
Turn on **JSON Server** server before execution, else app won't work and will show "Try again" screen.
As **JSON Server** provides fake json DB, user submitted answers persist even after app is uninstalled.
So on app re-install, submitted answers will be queried back and shown on app again :) 

### Testing
The project uses both instrumentation tests that run on the device/emulator and local unit tests that run on your computer

#### Device Tests
##### Integration Tests
The project uses Espresso for integration testing. 
Integration test added for CategoryListFragment and QuestionsFragment
Path: /PersonalityTest/app/src/androidTest/java/com/darshan/personalitytest

##### Database Tests
The project creates an in memory database for each database table test but still runs them on the device.
Path: /PersonalityTest/lib-database/src/androidTest/java/com/darshan/database/room/dao

For device/emulator testing, project uses **MockWebServer** project to mock Rest APIs.

#### Local Unit Tests
##### ViewModel Tests
Each ViewModel is tested using local unit tests with mock Usecase implementations.
##### Usecase Tests
Each Usecase is tested using local unit tests with mock Repository implementations.
##### Repository Tests
Each Repository is tested using local unit tests with mocked Network layer and mock database.

Path: /PersonalityTest/app/src/test/java/com/darshan/personalitytest/

### Security:
As MockWebserver and Json-server support requests in **http** format only, have made some temporary security hacks in XML. 
Have added a security configuration XML inside **debug only** folder app/src/debug/res/xml/network_security_config.xml.
This XML configuration can be removed once actual backend endpoint is enabled.

### Libraries

* [Android Architecture Components][arch]
* [View Binding][view-binding] for view binding
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [espresso][espresso] for UI tests
* [mockito][mockito] for mocking in tests for mocking actual remote server for device testing
* [MockWebServer][mockwebserver]
* [RxJava/RxAndroid][rxandroidjava] for all threading abstraction (network calls, DBs, etc)
* [Kluent][kluent] for infix kind of assertion in test cases.


[arch]: https://developer.android.com/arch
[view-binding]: https://developer.android.com/topic/libraries/view-binding
[dagger2]: https://google.github.io/dagger
[retrofit]: http://square.github.io/retrofit
[espresso]: https://google.github.io/android-testing-support-library/docs/espresso/
[mockito]: http://site.mockito.org
[mockwebserver]: https://github.com/square/okhttp/tree/master/mockwebserver
[rxandroidjava]: https://github.com/ReactiveX/RxJava
[kluent]: https://github.com/MarkusAmshove/Kluent