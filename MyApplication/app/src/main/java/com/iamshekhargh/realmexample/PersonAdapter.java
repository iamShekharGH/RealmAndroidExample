package com.iamshekhargh.realmexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iamshekhargh.realmexample.Models.Person;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by <<-- iamShekharGH -->>
 * on 08/03/17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    List<Person> persons;

    PersonAdapter(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_person_basic_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Person person = persons.get(position);

        holder.textViewName.setText(person.getName());
        holder.textViewAge.setText(person.getAge() + "");
        holder.textViewMobileNo.setText(person.getMobNo() + "");
        holder.textViewEmail.setText(person.getEmail());
        holder.textViewDob.setText(person.getDob());
        if (person.getGender()) {
            holder.imageView.setBackgroundResource(R.drawable.male);
        } else {
            holder.imageView.setBackgroundResource(R.drawable.female);
        }

//        holder.imageView.

    }

    @Override
    public int getItemCount() {
        if (persons.size() == 0)
            return 1;
        else return persons.size();
    }

//    static class ViewHolder extends RecyclerView.ViewHolder {
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView_name)
        TextView textViewName;
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.textView_mobileNo)
        TextView textViewMobileNo;
        @BindView(R.id.textView_age)
        TextView textViewAge;
        @BindView(R.id.textView_email)
        TextView textViewEmail;
        @BindView(R.id.textView_dob)
        TextView textViewDob;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
