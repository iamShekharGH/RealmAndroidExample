package com.iamshekhargh.realmexample.Models;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by <<-- iamShekharGH -->>
 * on 08/03/17.
 */

public class Companey extends RealmObject {
    String name;
    String title;
    String startingDate;
    String endDate;
    Integer salary;
    Date startingDateDateObj;
    Date endDateDateObj;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Date getStartingDateDateObj() {
        return startingDateDateObj;
    }

    public void setStartingDateDateObj(Date startingDateDateObj) {
        this.startingDateDateObj = startingDateDateObj;
    }

    public Date getEndDateDateObj() {
        return endDateDateObj;
    }

    public void setEndDateDateObj(Date endDateDateObj) {
        this.endDateDateObj = endDateDateObj;
    }
}
