package com.iamshekhargh.realmexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.iamshekhargh.realmexample.Models.Person;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    List<Person> persons;


    PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        generateRandomData();

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PersonAdapter(persons);
        recyclerview.setAdapter(adapter);


    }

    private void generateRandomData() {
        persons = new ArrayList<>();

        boolean temp = true;

        for (int i = 0; i < 5; i++) {
            Person person = new Person();
            person.setName("" + i);
            person.setAge(i);
            person.setEmail("Email No . " + i);
            person.setMobNo(i * 100000 - 1);
            person.setDob("0" + i + "st Jan 2017");
            if (temp) {
                person.setGender(temp);
                temp = false;
            } else {
                person.setGender(temp);
                temp = true;
            }
            persons.add(person);
        }

    }
}
