package com.iamshekhargh.realmexample.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.iamshekhargh.realmexample.Models.Company;
import com.iamshekhargh.realmexample.Models.Movies;
import com.iamshekhargh.realmexample.Models.Person;
import com.iamshekhargh.realmexample.Models.RealmString;
import com.iamshekhargh.realmexample.Models.SocialNetwork;
import com.iamshekhargh.realmexample.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;


public class FragmentAddDetails extends Fragment {
    @BindView(R.id.addDetails_age)
    EditText addDetailsAge;
    @BindView(R.id.addDetails_Name)
    EditText addDetailsName;
    @BindView(R.id.addDetails_button)
    Button addDetailsButton;
    @BindView(R.id.addDetails_mfToggle)
    ToggleButton addDetailsMfToggle;
    @BindView(R.id.addDetails_email)
    EditText addDetailsEmail;
    @BindView(R.id.addDetails_mobNumber)
    EditText addDetailsMobNumber;
    @BindView(R.id.addDetails_spinner_date)
    Spinner addDetailsSpinnerDate;
    @BindView(R.id.addDetails_spinner_Month)
    Spinner addDetailsSpinnerMonth;
    @BindView(R.id.addDetails_spinner_year)
    Spinner addDetailsSpinnerYear;
    @BindView(R.id.addDetails_llayout_work)
    LinearLayout addDetailsLlayoutWork;
    @BindView(R.id.addDetails_llayout_sNworks)
    LinearLayout addDetailsLlayoutSNworks;
    @BindView(R.id.addDetails_llayout_Movies)
    LinearLayout linearLayout;
    @BindView(R.id.company_name)
    EditText companyName;
    @BindView(R.id.company_title)
    EditText companyTitle;
    @BindView(R.id.company_startDate)
    EditText companyStartDate;
    @BindView(R.id.company_endDate)
    EditText companyEndDate;
    @BindView(R.id.company_salary)
    EditText companySalary;
    @BindView(R.id.sN_fullName)
    EditText sNFullName;
    @BindView(R.id.sN_socialLinks)
    EditText sNSocialLinks;
    @BindView(R.id.sN_rstatus_spinner)
    Spinner sNRstatusSpinner;
    @BindView(R.id.sN_image_url)
    EditText sNImageUrl;
    @BindView(R.id.sN_feedLayout)
    LinearLayout sNFeedLayout;
    @BindView(R.id.socialnetwork_card_layout)
    ConstraintLayout socialnetworkCardLayout;
    @BindView(R.id.movie_name)
    EditText movieName;
    @BindView(R.id.movie_releaseYear)
    EditText movieReleaseYear;
    @BindView(R.id.movie_productionHouse)
    EditText movieProductionHouse;
    @BindView(R.id.movie_directorName)
    EditText movieDirectorName;
    @BindView(R.id.movie_actor1)
    EditText movieActor1;
    @BindView(R.id.movie_actor2)
    EditText movieActor2;

    Realm realm;
    Person realmPersonObject;
    private OnFragmentInteractionListener mListener;

    public FragmentAddDetails() {
        // Required empty public constructor
    }


    public static FragmentAddDetails newInstance() {
        return new FragmentAddDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addCustomViews();

        initialiseValues();

        addDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formCheckValues()) {
                    return;
                }
                addToRealmObjectToDb();
                mListener.refreshViews();
                popFragment();
            }
        });
    }

    private void initialiseValues() {

        //dateSpinner
        ArrayAdapter<CharSequence> dateAdapter = ArrayAdapter.createFromResource(getContext(), R.array.date, android.R.layout.simple_spinner_item);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addDetailsSpinnerDate.setAdapter(dateAdapter);

        //monthSpinner
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(getContext(), R.array.months, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addDetailsSpinnerMonth.setAdapter(monthAdapter);

        //yearSpinner
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(getContext(), R.array.years, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addDetailsSpinnerYear.setAdapter(yearAdapter);

        List<String> relStatus = new ArrayList<>();
        relStatus.add("Single");
        relStatus.add("in a relationship.");
        relStatus.add("looking to hookup");
        relStatus.add("married");
        relStatus.add("married,looking to hookup");
        relStatus.add("call me");
        relStatus.add("non of your business");
        ArrayAdapter<String> relStatusAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, relStatus);
        relStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sNRstatusSpinner.setAdapter(relStatusAdapter);

        realm = Realm.getDefaultInstance();

    }

    private void addToRealmObjectToDb() {

        realm.beginTransaction();
        Calendar calendar = Calendar.getInstance();
        realmPersonObject = realm.createObject(Person.class, calendar.getTime().toString());

        realmPersonObject.setName(extractString(addDetailsName));
        realmPersonObject.setMobNo(extractString(addDetailsMobNumber));
        realmPersonObject.setAge(convertStingToInt(extractString(addDetailsAge)));
        realmPersonObject.setEmail(extractString(addDetailsEmail));
        realmPersonObject.setGender(addDetailsMfToggle.getText().toString().toLowerCase().equals("male"));
        realmPersonObject.setDob("" + addDetailsSpinnerDate.getSelectedItem().toString()
                + " " + addDetailsSpinnerMonth.getSelectedItem().toString()
                + " " + addDetailsSpinnerYear.getSelectedItem().toString());

        //Company Object
        Company company = realm.createObject(Company.class);
        company.setName(extractString(companyName));
        company.setEndDate(extractString(companyEndDate));
        company.setStartingDate(extractString(companyStartDate));
        company.setSalary(convertStingToInt(extractString(companySalary)));
        company.setTitle(extractString(companyTitle));
        RealmList<Company> companyList = new RealmList<>();
        companyList.add(company);
        realmPersonObject.setCompanies(companyList);

        //SocialNetwork Object
        SocialNetwork socialNetwork = realm.createObject(SocialNetwork.class);
        socialNetwork.setName(extractString(sNFullName));
        socialNetwork.setUrl(extractString(sNSocialLinks));
        socialNetwork.setImageUrl(extractString(sNImageUrl));
        if (sNRstatusSpinner.getSelectedItem().toString().toLowerCase().contains("single")) {
            socialNetwork.setRelationshipStatus(false);
        } else {
            socialNetwork.setRelationshipStatus(true);
        }
        RealmList<SocialNetwork> socialNetworkRealmList = new RealmList<>();
        socialNetworkRealmList.add(socialNetwork);
        realmPersonObject.setSocialNetworks(socialNetworkRealmList);

        //Movies Object
        Movies movies = realm.createObject(Movies.class);
        movies.setName(extractString(movieName));
        movies.setDirectorName(extractString(movieDirectorName));
        movies.setProductionHouse(extractString(movieProductionHouse));
        movies.setStringReleaseDate(extractString(movieReleaseYear));
        RealmString s1 = realm.createObject(RealmString.class);
        RealmString s2 = realm.createObject(RealmString.class);
        s1.setString(extractString(movieActor1));
        s2.setString(extractString(movieActor2));
        RealmList<RealmString> actorList = new RealmList<>();
        actorList.add(s1);
        actorList.add(s2);
        movies.setActorNames(actorList);
        RealmList<Movies> moviesRealmList = new RealmList<>();
        moviesRealmList.add(movies);
        realmPersonObject.setFavMovies(moviesRealmList);
        realm.commitTransaction();

        emailDb();

    }

    private void emailDb() {
        File exportRealmFile = null;
        try {
            // get or create an "export.realm" file
            exportRealmFile = new File(getActivity().getExternalCacheDir(), "export.realm");

            // if "export.realm" already exists, delete
            exportRealmFile.delete();

            // copy current realm to "export.realm"
            realm.writeCopyTo(exportRealmFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "shekhar@housingman.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "RealmDB");
        intent.putExtra(Intent.EXTRA_TEXT, "NJOy");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        startActivity(Intent.createChooser(intent, "YMAil That Shit"));

    }

    private void addCustomViews() {

    }

    private Person getAllValues(Person person) {

        person.setName(extractString(addDetailsName));
        person.setMobNo(extractString(addDetailsMobNumber));
        person.setAge(convertStingToInt(extractString(addDetailsAge)));
        person.setEmail(extractString(addDetailsEmail));
        person.setGender(addDetailsMfToggle.getText().toString().toLowerCase().equals("male"));
        person.setDob("" + addDetailsSpinnerDate.getSelectedItem().toString()
                + " " + addDetailsSpinnerMonth.getSelectedItem().toString()
                + " " + addDetailsSpinnerYear.getSelectedItem().toString());

        //Company Object
        Company company = new Company();
        company.setName(extractString(companyName));
        company.setEndDate(extractString(companyEndDate));
        company.setStartingDate(extractString(companyStartDate));
        company.setSalary(convertStingToInt(extractString(companySalary)));
        company.setTitle(extractString(companyTitle));
        RealmList<Company> companyList = new RealmList<>();
        companyList.add(company);
        person.setCompanies(companyList);

        //SocialNetwork Object
        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.setName(extractString(sNFullName));
        socialNetwork.setUrl(extractString(sNSocialLinks));
        socialNetwork.setImageUrl(extractString(sNImageUrl));
        if (sNRstatusSpinner.getSelectedItem().toString().toLowerCase().contains("single")) {
            socialNetwork.setRelationshipStatus(false);
        } else {
            socialNetwork.setRelationshipStatus(true);
        }
        RealmList<SocialNetwork> socialNetworkRealmList = new RealmList<>();
        socialNetworkRealmList.add(socialNetwork);
        person.setSocialNetworks(socialNetworkRealmList);

        //Movies Object
        Movies movies = new Movies();
        movies.setName(extractString(movieName));
        movies.setDirectorName(extractString(movieDirectorName));
        movies.setProductionHouse(extractString(movieProductionHouse));
        movies.setStringReleaseDate(extractString(movieReleaseYear));
        RealmString s1 = new RealmString(), s2 = new RealmString();
        s1.setString(extractString(movieActor1));
        s2.setString(extractString(movieActor2));
        RealmList<RealmString> actorList = new RealmList<>();
        actorList.add(s1);
        actorList.add(s2);
        movies.setActorNames(actorList);
        RealmList<Movies> moviesRealmList = new RealmList<>();
        moviesRealmList.add(movies);
        person.setFavMovies(moviesRealmList);

        return person;


    }

    private boolean formCheckValues() {
        //Define all checks here and if it fails return true.
        //TODO add a animation to show which field is wrongly written.
        if (addDetailsName.getText().toString().isEmpty() || addDetailsName.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
//        realm.close();

    }

    private String extractString(EditText editText) {
        if (editText.getText().toString().isEmpty() || editText.getText().toString().equals("")) {
            return "";
        } else {
            return editText.getText().toString();
        }

    }

    private int convertStingToInt(String string) {
        if (string == null || string.isEmpty() || string.equals("")) {
            return 0;
        } else {
            int i;
            try {
                i = Integer.valueOf(string);
            } catch (Exception e) {
                i = 0;
            }
            return i;
        }

    }

    private void popFragment() {
        getFragmentManager().popBackStack();
    }


    public interface OnFragmentInteractionListener {
        void refreshViews();
    }

}
