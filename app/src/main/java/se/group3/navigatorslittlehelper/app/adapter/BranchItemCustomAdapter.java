package se.group3.navigatorslittlehelper.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import se.group3.navigatorslittlehelper.app.R;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectBranchItem;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitMessageItem;

/**
 * Created by Sam on 2014-04-10.
 */
public class BranchItemCustomAdapter extends ArrayAdapter<ObjectBranchItem> {

    Context mContext;
    int layoutResourceId;
    ArrayList<ObjectBranchItem> data = null;

    public BranchItemCustomAdapter(Context mContext, int layoutResourceId, ArrayList<ObjectBranchItem> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItem = convertView;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        TextView branchnamelabel = (TextView) listItem.findViewById(R.id.branch_name);
        TextView lastauthorlabel = (TextView) listItem.findViewById(R.id.branch_lastauthor);
        TextView lastupdatedlabel = (TextView) listItem.findViewById(R.id.branch_lastupdated);

        String branchname = data.get(position).branchname;
        String lastauthor = data.get(position).lastauthor;
        Date lastupdated = data.get(position).lastupdated;


        branchnamelabel.setText(branchname);
        lastauthorlabel.setText("Last authored by: "+lastauthor);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        lastupdatedlabel.setText("Last updated: "+sdf.format(lastupdated));

        return listItem;
    }


}
