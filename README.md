# RealmAndroidExample
I wanted to integrate db in the app so i decided to go with Realm . Here is just a sample app with the most basic functionality.
If I get time for this i would like to write it properly and keep it as simple as possible so that you could just go through it and understand on how to get started . 


Step I
The first step would be to add content to your gradle files.
There are two gradle files 
You have to add this to the project level build gradle
`classpath "io.realm:realm-gradle-plugin:3.0.0" `
inside dependencies.
and add 
`apply plugin: 'realm-android'`
to the application level gradle file.

Step II
You nedd to add the following lines to the Appliction class .
create a new class in to root directory.
make it extend Application class , override the onCreate function and add these lines to it.
`Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
	        Realm.setDefaultConfiguration(realmConfiguration);))))`
alsom make sure in your manifest file in the <applications 
section add a attribute name and add this class you created to it.

Step III
Now the setup is done.

you now need to create modles for the realm db.
You do that by creating modle classes and making them extend the RealmObject class.
Make a models directory in you Project and add all your models in that .
I feel you will understand better if you see it in action so check out the models folder in the repo.

Step IV

now all the models are set up and we are ready to save info to the db.
I am using recycler view to populate information and using a seperate page to add information to it.

all you need to understand is to create a entry in the db you need to do the followinf steps.
First get a instant of the realm Object, do that by writing this.
`Realm realm = Realm.getDefaultInstance();`

Now that you have the realm object reference we will add a entry to the Realm db.
You do it like this.
`realm.beginTransaction();
ModelObject model = Realm.createObject(ModelObject.class);
model.setDate(date);
realm.commitTransaction();`

Thats it you added an entry to your realm Db;

Step V

Deleting an entry from the table.
Put a Primary key for every row. you will need that to update or delete information in that perticular field.
here to delete that from the list i do the following.
`realm.beginTransaction();
RealmResult<ModelObject> list = realm.where(ModelObject.class).equalTo("id","the id that you set").findAll();
list.deleteAllFromRealm();
realm.commitTransaction();`

Thats it. you removed the field from the table;


Step VI

Reading and updating are fairly simple , 
all you have to do is get the reference by using the id and begin and commit transsaction .

`realm.beginTransaction();
ModelObject m = realm.where(ModelObject.class).findFirst();
m.setInformation();
//.. do the changes 

realm.commitTransaction();`

reading is also the same 
`List<ModelObject> list = realm.where(ModelObject.class).findAll();`


Few things to keep in mind

- you cannot store lists of the basic objects (String). you will have to create custom RealmSting or better named object which extends RealmObject adn then use it.






















