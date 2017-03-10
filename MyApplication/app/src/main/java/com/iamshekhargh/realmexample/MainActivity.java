package com.iamshekhargh.realmexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.iamshekhargh.realmexample.Fragments.FragmentAddDetails;
import com.iamshekhargh.realmexample.Fragments.Fragment_mainActivity;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        FragmentAddDetails.OnFragmentInteractionListener,
        Fragment_mainActivity.OnFragmentInteractionListener {
    //Defining it here to refresh views when data changes.
    Fragment_mainActivity fragment_mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragment_mainActivity = Fragment_mainActivity.newInstance();

    }

    @Override
    protected void onResume() {
        super.onResume();
        addFragment(fragment_mainActivity);
    }

    @Override
    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void refreshViews() {
        fragment_mainActivity.refreshViews();
    }
}
