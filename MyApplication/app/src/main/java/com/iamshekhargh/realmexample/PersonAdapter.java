package com.iamshekhargh.realmexample;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iamshekhargh.realmexample.Models.Person;
import com.iamshekhargh.realmexample.StaticClasses.StaticFunctions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by <<-- iamShekharGH -->>
 * on 08/03/17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private static final String TAG = "PersonAdapter";

    List<Person> persons;
    Realm realm;
    Context context;

    Helper helper;


    public PersonAdapter(Context context, Helper helper) {
        this.context = context;
        this.helper = helper;
        try {
            realm = Realm.getDefaultInstance();
        } catch (Exception e) {
            //Adding this to delete the entire db if there is any change in models.
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
//            realm = Realm.getDefaultInstance();
            realm = Realm.getInstance(config);
        }

        Log.d(TAG, "path: " + realm.getPath());
        //You 'could' pass the list in the constructor also.
        this.persons = realm.where(Person.class).findAll();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_person_basic_info, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_empty, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (persons.size() > 0) {
            final Person person = persons.get(position);

            setupTextView(holder.textViewName, person.getName());
            setupTextView(holder.textViewAge, "Age " + person.getAge());
            setupTextView(holder.textViewDob, person.getDob());
            setupTextView(holder.textViewEmail, person.getEmail());
            setupTextView(holder.textViewMobileNo, "+91 " + person.getMobNo());
            setupTextView(holder.addDetailsId, person.getId());
            if (person.getGender()) {
                holder.imageView.setBackgroundResource(R.drawable.male);
            } else holder.imageView.setBackgroundResource(R.drawable.female);

            holder.basicInfoCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    helper.launchDetailsFragment(person.getId());
                }
            });


            View.OnLongClickListener longPress = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    StaticFunctions.alertDialogTwoButtons(context, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Delete Item.
                            realm.beginTransaction();
                            RealmResults<Person> result = realm.where(Person.class).equalTo("id", person.getId()).findAll();
                            result.deleteAllFromRealm();
                            realm.commitTransaction();

                            notifyDataSetChanged();


                        }
                    }, "Hell Yeah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    }, "Nope, i need more time.", "Delete", "Are you Sure ?");
                    return true;
                }
            };

            View.OnClickListener press = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Long Press to delete", Toast.LENGTH_LONG).show();
                }
            };
            holder.delete.setOnClickListener(press);
            holder.delete.setOnLongClickListener(longPress);

        } else {
            setupTextView(holder.emptyLayoutText, "Nothing To Show.");
        }

    }

    @Override
    public int getItemViewType(int position) {
        //For multiple layouts
        if (persons.size() == 0)
            return 0;
        else
            return 1;

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    private void setupTextView(TextView textView, String string) {
        if (textView != null) {
            textView.setText(string);
        }
    }

    public interface Helper {
        void launchDetailsFragment(String id);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        @BindView(R.id.textView_name)
        TextView textViewName;
        @Nullable
        @BindView(R.id.imageView)
        ImageView imageView;
        @Nullable
        @BindView(R.id.textView_mobileNo)
        TextView textViewMobileNo;
        @Nullable
        @BindView(R.id.textView_age)
        TextView textViewAge;
        @Nullable
        @BindView(R.id.textView_email)
        TextView textViewEmail;
        @Nullable
        @BindView(R.id.textView_dob)
        TextView textViewDob;
        @Nullable
        @BindView(R.id.addDetails_Id)
        TextView addDetailsId;
        @Nullable
        @BindView(R.id.delete)
        ImageView delete;
        @Nullable
        @BindView(R.id.basicInfoCard)
        CardView basicInfoCard;
        @Nullable
        @BindView(R.id.emptyLayout_text)
        TextView emptyLayoutText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

//
//            if (delete != null)
//                delete.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        StaticFunctions.alertDialogTwoButtons(context, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Person person = persons.get(getAdapterPosition());
//                                //Delete Item.
//                                realm.beginTransaction();
//                                RealmResults<Person> result = realm.where(Person.class).equalTo("id", person.getId()).findAll();
//                                result.deleteAllFromRealm();
//                                realm.commitTransaction();
//
//                                notifyDataSetChanged();
//
//
//                            }
//                        }, "Hell Yeah", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                            }
//                        }, "Nope, i need more time.", "Delete", "Are you Sure ?");
//                        return true;
//                    }
//                });
//            if (delete != null)
//                delete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(context, "Long Press to delete", Toast.LENGTH_LONG).show();
//
//                    }
//                });
//            Log.i(TAG, "position\t:" + getLayoutPosition());
//            if (persons != null && persons.size() > 0 && getAdapterPosition() >= 0) {
//
//                setupTextView(textViewName, persons.get(getAdapterPosition()).getName());
//                setupTextView(textViewAge, String.format(Locale.getDefault(), "Age : %d", persons.get(getAdapterPosition()).getAge()));
//                setupTextView(textViewMobileNo, String.format(Locale.getDefault(), "%s", persons.get(getAdapterPosition()).getMobNo()));
//                setupTextView(textViewEmail, persons.get(getAdapterPosition()).getEmail());
//                setupTextView(textViewDob, persons.get(getAdapterPosition()).getDob());
//                setupTextView(addDetailsId, persons.get(getAdapterPosition()).getId());
//
//                if (imageView != null)
//                    if (persons.get(getAdapterPosition()).getGender())
//                        imageView.setBackgroundResource(R.drawable.male);
//                    else
//                        imageView.setBackgroundResource(R.drawable.female);
//            } else {
//                setupTextView(emptyLayoutText, "Nothing to show");
//            }

        }

        private void setupTextView(TextView textView, String string) {
            if (textView != null) {
                textView.setText(string);
            }
        }
    }
}
