package com.iamshekhargh.realmexample.Models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by <<-- iamShekharGH -->>
 * on 08/03/17.
 */

public class Person extends RealmObject {

    String name;
    Integer age;
    boolean gender;
    String email;
    String imageURL;
    Integer mobNo;
    String dob;
    RealmList<Companey> companeys;
    RealmList<SocialNetwork> socialNetworks;
    RealmList<Movies> favMovies;

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

    public Integer getMobNo() {
        return mobNo;
    }

    public void setMobNo(Integer mobNo) {
        this.mobNo = mobNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public RealmList<Companey> getCompaneys() {
        return companeys;
    }

    public void setCompaneys(RealmList<Companey> companeys) {
        this.companeys = companeys;
    }

    public RealmList<SocialNetwork> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(RealmList<SocialNetwork> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    public RealmList<Movies> getFavMovies() {
        return favMovies;
    }

    public void setFavMovies(RealmList<Movies> favMovies) {
        this.favMovies = favMovies;
    }
}
