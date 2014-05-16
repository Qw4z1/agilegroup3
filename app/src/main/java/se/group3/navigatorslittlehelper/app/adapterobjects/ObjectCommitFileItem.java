package se.group3.navigatorslittlehelper.app.adapterobjects;

import java.util.Date;

/**
 * Created by Abel on 2014-05-8.
 */
public class ObjectCommitFileItem {

    public String name;
    public int totalDeletions;
    public int totalUpdates;

    public int totalChanges(){
        return   totalDeletions+totalUpdates;
    }

    // Constructor.
    public ObjectCommitFileItem(String name, int totalDeletions, int totalUpdates) {
        this.name = name;
        this.totalDeletions = totalDeletions;
        this.totalUpdates = totalUpdates;
    }

}
