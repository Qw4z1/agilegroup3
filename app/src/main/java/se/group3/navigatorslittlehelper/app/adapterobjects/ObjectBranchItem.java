package se.group3.navigatorslittlehelper.app.adapterobjects;

import java.util.Date;

/**
 * Created by Pro9 on 2014-04-30.
 */
public class ObjectBranchItem {
    public String branchname;
    public String lastauthor;
    public Date lastupdated;

    public ObjectBranchItem(String branchname, String lastauthor, Date lastupdated) {
        this.branchname = branchname;
        this.lastauthor = lastauthor;
        this.lastupdated = lastupdated;
    }
}
