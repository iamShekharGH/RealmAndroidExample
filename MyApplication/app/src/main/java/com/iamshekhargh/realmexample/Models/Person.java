package com.iamshekhargh.realmexample.Models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by <<-- iamShekharGH -->>
 * on 08/03/17.
 */

public class Person extends RealmObject {


    @PrimaryKey
    String id;

    String name;
    Integer age;
    boolean gender;
    String email;
    String imageURL;
    String mobNo;
    String dob;
    RealmList<Company> companies;
    RealmList<SocialNetwork> socialNetworks;
    RealmList<Movies> favMovies;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public RealmList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(RealmList<Company> companies) {
        if (companies == null || companies.size() == 0) {
            this.companies = new RealmList<>();
        } else
            this.companies = companies;
    }

    public RealmList<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(RealmList<SocialNetwork> socialNetworks) {
        if (socialNetworks == null || socialNetworks.size() == 0) {
            this.socialNetworks = new RealmList<>();
        } else
            this.socialNetworks = socialNetworks;
    }

    public RealmList<Movies> getFavMovies() {
        return favMovies;
    }

    public void setFavMovies(RealmList<Movies> favMovies) {
        if (favMovies == null || favMovies.size() == 0) {
            this.favMovies = new RealmList<>();
        } else
            this.favMovies = favMovies;
    }
}
