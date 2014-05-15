package se.group3.navigatorslittlehelper.app.adapterobjects;

import java.util.Date;

/**
 * Created by Sam on 2014-04-11.
 */
public class ObjectCommitMessageItem {

    public String description;
    public String author;
    public Date time;
    public String SHA1;

    // Constructor.
    public ObjectCommitMessageItem(String description, String author, Date time, String SHA1) {

        this.description = description;
        this.author = author;
        this.time = time;
        this.SHA1 = SHA1;
    }
}
