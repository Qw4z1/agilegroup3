package se.group3.navigatorslittlehelper.app.adapterobjects;

import java.util.Date;

public class ObjectPullRequestItem {

    public String description;
    public String author;
    public Date time;

    // Constructor.
    public ObjectPullRequestItem(String description, String author, Date createdAt) {

        this.description = description;
        this.author = author;
        this.time = createdAt;
    }
}
