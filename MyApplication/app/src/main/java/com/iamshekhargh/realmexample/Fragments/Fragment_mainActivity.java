package com.iamshekhargh.realmexample.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamshekhargh.realmexample.PersonAdapter;
import com.iamshekhargh.realmexample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Fragment_mainActivity extends Fragment implements PersonAdapter.Helper {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    PersonAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public Fragment_mainActivity() {
        // Required empty public constructor
    }

    public static Fragment_mainActivity newInstance() {
        return new Fragment_mainActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview.setLayoutManager(new GridLayoutManager(getContext(), 1));
        adapter = new PersonAdapter(getContext(), this);
        recyclerview.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.createEmptyForm();
                mListener.addFragment(FragmentAddDetails.newInstance());
            }
        });
    }

    public void refreshViews() {
        adapter.notifyDataSetChanged();
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
    }

    @Override
    public void launchDetailsFragment(String id) {
        mListener.launchThisFragment(id);
    }

    public interface OnFragmentInteractionListener {

        void createEmptyForm();

        void addFragment(Fragment fragment);

        void launchThisFragment(String id);
    }
}
