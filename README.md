# RealmAndroidExample
I wanted to integrate DB in the app so I decided to go with Realm. Here is just a sample app with the most basic functionality.
If I get time for this I would like to write it properly and keep it as simple as possible so that you could just go through it and understand how to get started. 


##Step I
The first step would be to add content to your gradle files.
There are two gradle files 
You have to add this to the project level build gradle
```
classpath "io.realm:realm-gradle-plugin:3.0.0" 
```
inside dependencies.
and add 
```
apply plugin: 'realm-android'
```
to the application level gradle file.

##Step II
You need to add the following lines to the Application class.
create a new class into the root directory.
make it extend Application class, override the onCreate function and add these lines to it.
```
Realm.init(this);
RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
Realm.setDefaultConfiguration(realmConfiguration);
```
also, make sure in your manifest file in the applications section add an attribute name and add this class you created to it.

##Step III
Now the setup is done.

you now need to create models for the realm DB.
You do that by creating model classes and making them extend the RealmObject class.
Make a models directory in you Project and add all your models in that.
I feel you will understand better if you see it in action so check out the models folder in the repo.

##Step IV

now all the models are set up and we are ready to save info to the DB.
I am using recycler view to populate information and using a separate page to add information to it.

all you need to understand is to create an entry in the DB you need to do the following steps.
First get an instant of the realm Object, do that by writing this.
```
Realm realm = Realm.getDefaultInstance();
```

Now that you have the realm object reference we will add an entry to the Realm DB.
You do it like this.
```
realm.beginTransaction();
ModelObject model = Realm.createObject(ModelObject.class);
model.setDate(date);
realm.commitTransaction();
```

That's it you added an entry to your realm Db;

##Step V

Deleting an entry from the table.
Put a Primary key for every row. you will need that to update or delete information in that perticular field.
here to delete that from the list i do the following.
```
realm.beginTransaction();
RealmResult<ModelObject> list = realm.where(ModelObject.class).equalTo("id","the id that you set").findAll();
list.deleteAllFromRealm();
realm.commitTransaction();
```

That's it. you removed the field from the table;


##Step VI

Reading and updating are fairly simple, 
all you have to do is get the reference by using the id and begin and commit the transaction.

```
realm.beginTransaction();
ModelObject m = realm.where(ModelObject.class).findFirst();
m.setInformation();
//.. do the changes 

realm.commitTransaction();
```

reading is also the same 
```
List<ModelObject> list = realm.where(ModelObject.class).findAll();
```


Few things to keep in mind

* You cannot store lists of the basic objects (String). you will have to create custom RealmSting or better-named object which extends RealmObject and then use it.






















