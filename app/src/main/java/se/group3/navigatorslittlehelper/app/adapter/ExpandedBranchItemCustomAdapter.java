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
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitMessageItem;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectExpandedBranchItem;

/**
 * Created by Pro9 on 2014-05-06.
 */
public class ExpandedBranchItemCustomAdapter extends ArrayAdapter<ObjectExpandedBranchItem> {

    Context mContext;
    int layoutResourceId;
    ArrayList<ObjectExpandedBranchItem> data = null;

    public ExpandedBranchItemCustomAdapter(Context mContext, int layoutResourceId, ArrayList<ObjectExpandedBranchItem> data) {

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

        TextView descriptionlabel = (TextView) listItem.findViewById(R.id.expanded_branch_description);
        TextView authorlabel = (TextView) listItem.findViewById(R.id.expanded_branch_author);
        TextView timelabel = (TextView) listItem.findViewById(R.id.expanded_branch_time);

        String description = data.get(position).description;
        String author = data.get(position).author;
        Date time = data.get(position).time;


        descriptionlabel.setText(description);
        authorlabel.setText("Author: "+author);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        timelabel.setText(sdf.format(time));

        return listItem;
    }










}
