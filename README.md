# RealmAndroidExample
I wanted to integrate db in the app so i decided to go with Realm . Here is just a sample app with the most basic functionality.
If I get time for this i would like to write it properly and keep it as simple as possible so that you could just go through it and understand on how to get started . 


Step I
The first step would be to add content to your gradle files.
There are two gradle files 
You have to add this to the project level build gradle
classpath "io.realm:realm-gradle-plugin:3.0.0" 
inside dependencies.
and add 
apply plugin: 'realm-android'
to the application level gradle file.
