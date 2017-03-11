package com.iamshekhargh.realmexample.Models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by <<-- iamShekharGH -->>
 * on 08/03/17.
 */

public class SocialNetwork extends RealmObject {
    String Name;
    String url;
    RealmList<RealmString> feeds;
    boolean relationshipStatus;
    String imageUrl;

    public String getRelationshipStatusString() {
        return relationshipStatusString;
    }

    public void setRelationshipStatusString(String relationshipStatusString) {
        this.relationshipStatusString = relationshipStatusString;
    }

    String relationshipStatusString;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RealmList<RealmString> getFeeds() {
        return feeds;
    }

    public void setFeeds(RealmList<RealmString> feeds) {
        this.feeds = feeds;
    }

    public boolean isRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(boolean relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
