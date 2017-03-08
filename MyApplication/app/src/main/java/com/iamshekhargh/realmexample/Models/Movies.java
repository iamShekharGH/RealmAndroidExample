package com.iamshekhargh.realmexample.Models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by <<-- iamShekharGH -->>
 * on 08/03/17.
 */

public class Movies extends RealmObject {
    String name;
    String stringReleaseDate;
    Date dateReleaseDate;
    String productionHouse;
    String directorName;
    RealmList<RealmString> actorNames;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStringReleaseDate() {
        return stringReleaseDate;
    }

    public void setStringReleaseDate(String stringReleaseDate) {
        this.stringReleaseDate = stringReleaseDate;
    }

    public Date getDateReleaseDate() {
        return dateReleaseDate;
    }

    public void setDateReleaseDate(Date dateReleaseDate) {
        this.dateReleaseDate = dateReleaseDate;
    }

    public String getProductionHouse() {
        return productionHouse;
    }

    public void setProductionHouse(String productionHouse) {
        this.productionHouse = productionHouse;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public RealmList<RealmString> getActorNames() {
        return actorNames;
    }

    public void setActorNames(RealmList<RealmString> actorNames) {
        this.actorNames = actorNames;
    }
}
