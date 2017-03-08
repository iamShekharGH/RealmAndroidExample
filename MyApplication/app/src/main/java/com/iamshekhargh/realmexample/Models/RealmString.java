package com.iamshekhargh.realmexample.Models;

import io.realm.RealmObject;

/**
 * Created by <<-- iamShekharGH -->>
 * on 08/03/17.
 */

public class RealmString extends RealmObject {
    String string;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
