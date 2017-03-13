package com.iamshekhargh.realmexample.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.iamshekhargh.realmexample.Models.Company;
import com.iamshekhargh.realmexample.Models.Movies;
import com.iamshekhargh.realmexample.Models.Person;
import com.iamshekhargh.realmexample.Models.RealmString;
import com.iamshekhargh.realmexample.Models.SocialNetwork;
import com.iamshekhargh.realmexample.R;
import com.iamshekhargh.realmexample.StaticClasses.StaticFunctions;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;


public class FragmentAddDetails extends Fragment {

    private static final String TAG = "FragmentAddDetails";

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
    LinearLayout addDetailsLlayoutMovies;
    @BindView(R.id.addDetails_addCompaney)
    ImageView addDetailsAddCompaney;
    @BindView(R.id.addDetails_addSocialNetwork)
    ImageView addDetailsAddSocialNetwork;
    @BindView(R.id.addDetails_addMovie)
    ImageView addDetailsAddMovie;


    private OnFragmentInteractionListener mListener;
    Realm realm;
    Person realmPersonObject;
    RealmList<Company> companyRealmList = new RealmList<>();
    RealmList<SocialNetwork> socialNetworkRealmList = new RealmList<>();
    RealmList<Movies> moviesRealmList = new RealmList<>();

    List<View> companyViewList = new ArrayList<>();
    List<View> socialViewNList = new ArrayList<>();
    List<View> movieViewList = new ArrayList<>();

    String id;

    private enum listType {
        COMPANY,
        SOCIALN,
        MOVIE
    }

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

        setupElements();

        initialiseValues();

        addDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (formCheckValues()) {
                    return;
                }
                addRealmObjectToDb();
                mListener.refreshViews();
                popFragment();
            }
        });
    }

    private void initialiseValues() {
        if (mListener.getPerson() != null) {
            Person person = mListener.getPerson();

            id = person.getId();

            injectText(addDetailsName, person.getName());
            injectText(addDetailsAge, person.getAge() + "");
            injectText(addDetailsEmail, person.getEmail());
            injectText(addDetailsMobNumber, person.getMobNo());

//            addDetailsSpinnerDate.setSelection(10);

            //TODO initialise date.

            StaticFunctions.logDotI(TAG, person.getDob());


            if (person.getCompanies() != null && person.getCompanies().size() > 0) {
                RealmList<Company> company = person.getCompanies();
                for (Company c : company) {
                    View view = inflateView(R.layout.card_companey_details, addDetailsLlayoutWork);
                    ((EditText) view.findViewById(R.id.company_name)).setText(c.getName());
                    ((EditText) view.findViewById(R.id.company_title)).setText(c.getTitle());
                    ((EditText) view.findViewById(R.id.company_salary)).setText(c.getSalary() + "");
                    ((EditText) view.findViewById(R.id.company_startDate)).setText(c.getStartingDate());
                    ((EditText) view.findViewById(R.id.company_endDate)).setText(c.getEndDate());

                    addDetailsLlayoutWork.addView(view);
                }
            }

            if (person.getSocialNetworks() != null && person.getSocialNetworks().size() > 0) {
                RealmList<SocialNetwork> socialN = person.getSocialNetworks();
                for (SocialNetwork s : socialN) {
                    View view = inflateView(R.layout.card_social_network_details, addDetailsLlayoutSNworks);
                    ((EditText) view.findViewById(R.id.sN_fullName)).setText(s.getName());
                    ((EditText) view.findViewById(R.id.sN_socialLinks)).setText(s.getUrl());
                    ((EditText) view.findViewById(R.id.sN_image_url)).setText(s.getImageUrl());
                    //TODO add feed and relationship status.

                    addDetailsLlayoutSNworks.addView(view);
                }

            }
            if (person.getFavMovies() != null && person.getFavMovies().size() > 0) {
                RealmList<Movies> movies = person.getFavMovies();
                for (Movies m : movies) {
                    View view = inflateView(R.layout.card_movie, addDetailsLlayoutMovies);
                    ((EditText) view.findViewById(R.id.movie_name)).setText(m.getName());
                    ((EditText) view.findViewById(R.id.movie_directorName)).setText(m.getDirectorName());
                    ((EditText) view.findViewById(R.id.movie_productionHouse)).setText(m.getProductionHouse());
                    ((EditText) view.findViewById(R.id.movie_releaseYear)).setText(m.getStringReleaseDate());
                    if (m.getActorNames() != null && m.getActorNames().size() > 0) {
                        ((EditText) view.findViewById(R.id.movie_actor1)).setText(m.getActorNames().get(0).getString());
                        StaticFunctions.logDotI(TAG, "Actor Name : " + m.getActorNames().get(0).getString());
                        if (m.getActorNames().size() > 1)
                            ((EditText) view.findViewById(R.id.movie_actor2)).setText(m.getActorNames().get(1).getString());
                    }

                    addDetailsLlayoutMovies.addView(view);
                }
            }
        }
    }

    private View inflateView(int layoutId, ViewGroup layout) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, layout, false);
        return view;
    }

    private void setupElements() {

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

//        List<String> relStatus = new ArrayList<>();
//        relStatus.add("Single");
//        relStatus.add("in a relationship.");
//        relStatus.add("looking to hookup");
//        relStatus.add("married");
//        relStatus.add("married,looking to hookup");
//        relStatus.add("call me");
//        relStatus.add("non of your business");
//        ArrayAdapter<String> relStatusAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, relStatus);
//        relStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sNRstatusSpinner.setAdapter(relStatusAdapter);

        realm = Realm.getDefaultInstance();


    }

    private void addRealmObjectToDb() {

        realm.beginTransaction();

        Calendar calendar = Calendar.getInstance();
        if (id == null)
            realmPersonObject = realm.createObject(Person.class, calendar.getTime().toString());
        else {
//            realmPersonObject = realm.createObject(Person.class, id);
            realmPersonObject = realm.where(Person.class).equalTo("id", id).findFirst();
        }

        realmPersonObject.setName(extractString(addDetailsName));
        realmPersonObject.setMobNo(extractString(addDetailsMobNumber));
        realmPersonObject.setAge(convertStingToInt(extractString(addDetailsAge)));
        realmPersonObject.setEmail(extractString(addDetailsEmail));
        realmPersonObject.setGender(addDetailsMfToggle.getText().toString().toLowerCase().equals("male"));
        realmPersonObject.setDob("" + addDetailsSpinnerDate.getSelectedItem().toString()
                + " " + addDetailsSpinnerMonth.getSelectedItem().toString()
                + " " + addDetailsSpinnerYear.getSelectedItem().toString());

        if (companyViewList.size() > 0) {
            for (View company : companyViewList) {
                Company tempCompany = realm.createObject(Company.class);

                EditText name = (EditText) company.findViewById(R.id.company_name);
                EditText title = (EditText) company.findViewById(R.id.company_title);
                EditText startingDate = (EditText) company.findViewById(R.id.company_startDate);
                EditText endDate = (EditText) company.findViewById(R.id.company_endDate);
                EditText salary = (EditText) company.findViewById(R.id.company_salary);

                tempCompany.setName(extractString(name));
                tempCompany.setTitle(extractString(title));
                tempCompany.setStartingDate(extractString(startingDate));
                tempCompany.setEndDate(extractString(endDate));
                tempCompany.setSalary(convertStingToInt(extractString(salary)));
                companyRealmList.add(tempCompany);
            }
            realmPersonObject.setCompanies(companyRealmList);
        }

        if (socialViewNList.size() > 0) {
            for (View sN : socialViewNList) {
                SocialNetwork socialNetwork = realm.createObject(SocialNetwork.class);

                EditText name = (EditText) sN.findViewById(R.id.sN_fullName);
                EditText url = (EditText) sN.findViewById(R.id.sN_socialLinks);
                EditText imageUrl = (EditText) sN.findViewById(R.id.sN_image_url);
//                Spinner relStatus = (Spinner) sN.findViewById(R.id.sN_rstatus_spinner);

                socialNetwork.setName(extractString(name));
                socialNetwork.setUrl(extractString(url));
                socialNetwork.setImageUrl(extractString(imageUrl));

                //TODO add relationship status to the realm object
//                socialNetwork.setRelationshipStatusString(relStatus.getSelectedItem().toString());
//                if (relStatus.getSelectedItem().toString().toLowerCase().contains("single")) {
//                    socialNetwork.setRelationshipStatus(false);
//                } else socialNetwork.setRelationshipStatus(true);
                socialNetworkRealmList.add(socialNetwork);
            }
            realmPersonObject.setSocialNetworks(socialNetworkRealmList);
        }

        if (movieViewList.size() > 0) {
            for (View movie : movieViewList) {
                Movies m = realm.createObject(Movies.class);

                EditText name = (EditText) movie.findViewById(R.id.movie_name);
                EditText releaseYr = (EditText) movie.findViewById(R.id.movie_releaseYear);
                EditText productionHouse = (EditText) movie.findViewById(R.id.movie_productionHouse);
                EditText directorName = (EditText) movie.findViewById(R.id.movie_directorName);
                EditText actor1 = (EditText) movie.findViewById(R.id.movie_actor1);
                EditText actor2 = (EditText) movie.findViewById(R.id.movie_actor2);

                m.setName(extractString(name));
                m.setStringReleaseDate(extractString(releaseYr));
                m.setProductionHouse(extractString(productionHouse));
                m.setDirectorName(extractString(directorName));
                RealmList<RealmString> actorList = new RealmList<>();
                RealmString string = new RealmString();
                string.setString(extractString(actor1));
                actorList.add(string);
                string.setString(extractString(actor2));
                actorList.add(string);
                m.setActorNames(actorList);

                StaticFunctions.logDotI(TAG, "actor names\t" + actorList.get(0).getString() + "\nactor 2 : " + actorList.get(1).getString());

                moviesRealmList.add(m);
            }
            realmPersonObject.setFavMovies(moviesRealmList);
        }


//        //Company Object
//        Company company = realm.createObject(Company.class);
//        company.setName(extractString(companyName));
//        company.setEndDate(extractString(companyEndDate));
//        company.setStartingDate(extractString(companyStartDate));
//        company.setSalary(convertStingToInt(extractString(companySalary)));
//        company.setTitle(extractString(companyTitle));
//        RealmList<Company> companyList = new RealmList<>();
//        companyList.add(company);
//        realmPersonObject.setCompanies(companyList);
//
//        //SocialNetwork Object
//        SocialNetwork socialNetwork = realm.createObject(SocialNetwork.class);
//        socialNetwork.setName(extractString(sNFullName));
//        socialNetwork.setUrl(extractString(sNSocialLinks));
//        socialNetwork.setImageUrl(extractString(sNImageUrl));
//        if (sNRstatusSpinner.getSelectedItem().toString().toLowerCase().contains("single")) {
//            socialNetwork.setRelationshipStatus(false);
//        } else {
//            socialNetwork.setRelationshipStatus(true);
//        }
//        RealmList<SocialNetwork> socialNetworkRealmList = new RealmList<>();
//        socialNetworkRealmList.add(socialNetwork);
//        realmPersonObject.setSocialNetworks(socialNetworkRealmList);
//
//        //Movies Object
//        Movies movies = realm.createObject(Movies.class);
//        movies.setName(extractString(movieName));
//        movies.setDirectorName(extractString(movieDirectorName));
//        movies.setProductionHouse(extractString(movieProductionHouse));
//        movies.setStringReleaseDate(extractString(movieReleaseYear));
//        RealmString s1 = realm.createObject(RealmString.class);
//        RealmString s2 = realm.createObject(RealmString.class);
//        s1.setString(extractString(movieActor1));
//        s2.setString(extractString(movieActor2));
//        RealmList<RealmString> actorList = new RealmList<>();
//        actorList.add(s1);
//        actorList.add(s2);
//        movies.setActorNames(actorList);
//        RealmList<Movies> moviesRealmList = new RealmList<>();
//        moviesRealmList.add(movies);
//        realmPersonObject.setFavMovies(moviesRealmList);
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
        addDetailsAddCompaney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View addCompaney = LayoutInflater.from(getContext()).inflate(R.layout.card_companey_details, addDetailsLlayoutWork, false);
                addCompaney.findViewById(R.id.companey_remove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        companyViewList.remove(addCompaney);
                        addDetailsLlayoutWork.removeView(addCompaney);
                    }
                });
                companyViewList.add(addCompaney);
                addDetailsLlayoutWork.addView(addCompaney);
            }
        });

        addDetailsAddSocialNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View addSNetwork = LayoutInflater.from(getContext()).inflate(R.layout.card_social_network_details, addDetailsLlayoutSNworks, false);
                addSNetwork.findViewById(R.id.sN_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        socialViewNList.remove(addSNetwork);
                        addDetailsLlayoutSNworks.removeView(addSNetwork);
                    }
                });
                socialViewNList.add(addSNetwork);
                addDetailsLlayoutSNworks.addView(addSNetwork);
            }
        });

        addDetailsAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View movie = LayoutInflater.from(getContext()).inflate(R.layout.card_movie, addDetailsLlayoutMovies, false);
                movie.findViewById(R.id.movie_close).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        movieViewList.remove(movie);
                        addDetailsLlayoutMovies.removeView(movie);
                    }
                });
                movieViewList.add(movie);
                addDetailsLlayoutMovies.addView(movie);
            }
        });


//        removeFromView(companyViewList, R.id.companey_remove, listType.COMPANY);
//        removeFromView(socialViewNList, R.id.sN_close, listType.SOCIALN);
//        removeFromView(movieViewList, R.id.movie_close, listType.MOVIE);
    }

    private void addViews(int layoutId, listType type, ViewGroup layout) {
        View view = LayoutInflater.from(getContext()).inflate(layoutId, layout, false);
        layout.addView(view);
        if (type == listType.COMPANY) {
            companyViewList.add(view);

        }

    }

    private void removeFromView(final List<View> viewList, int buttonId, final listType type) {
        Log.i(TAG, "enum Type : " + type.toString());
        for (int i = 0; i < viewList.size(); i++) {
            final int tempI = i;
            viewList.get(i).findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type == listType.COMPANY) {
                        addDetailsLlayoutWork.removeView(viewList.get(tempI));
                    } else if (type == listType.SOCIALN) {
                        addDetailsLlayoutSNworks.removeView(viewList.get(tempI));
                    } else if (type == listType.MOVIE) {
                        addDetailsLlayoutMovies.removeView(viewList.get(tempI));
                    }
                    viewList.remove(tempI);


                }
            });
        }

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
//
//        //Company Object
//        Company company = new Company();
//        company.setName(extractString(companyName));
//        company.setEndDate(extractString(companyEndDate));
//        company.setStartingDate(extractString(companyStartDate));
//        company.setSalary(convertStingToInt(extractString(companySalary)));
//        company.setTitle(extractString(companyTitle));
//        RealmList<Company> companyList = new RealmList<>();
//        companyList.add(company);
//        person.setCompanies(companyList);
//
//        //SocialNetwork Object
//        SocialNetwork socialNetwork = new SocialNetwork();
//        socialNetwork.setName(extractString(sNFullName));
//        socialNetwork.setUrl(extractString(sNSocialLinks));
//        socialNetwork.setImageUrl(extractString(sNImageUrl));
//        if (sNRstatusSpinner.getSelectedItem().toString().toLowerCase().contains("single")) {
//            socialNetwork.setRelationshipStatus(false);
//        } else {
//            socialNetwork.setRelationshipStatus(true);
//        }
//        RealmList<SocialNetwork> socialNetworkRealmList = new RealmList<>();
//        socialNetworkRealmList.add(socialNetwork);
//        person.setSocialNetworks(socialNetworkRealmList);
//
//        //Movies Object
//        Movies movies = new Movies();
//        movies.setName(extractString(movieName));
//        movies.setDirectorName(extractString(movieDirectorName));
//        movies.setProductionHouse(extractString(movieProductionHouse));
//        movies.setStringReleaseDate(extractString(movieReleaseYear));
//        RealmString s1 = new RealmString(), s2 = new RealmString();
//        s1.setString(extractString(movieActor1));
//        s2.setString(extractString(movieActor2));
//        RealmList<RealmString> actorList = new RealmList<>();
//        actorList.add(s1);
//        actorList.add(s2);
//        movies.setActorNames(actorList);
//        RealmList<Movies> moviesRealmList = new RealmList<>();
//        moviesRealmList.add(movies);
//        person.setFavMovies(moviesRealmList);

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

    private void injectText(EditText editText, String string) {
        editText.setText(string);
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

        Person getPerson();
    }

}
