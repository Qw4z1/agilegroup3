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
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectIssueTrackerItem;

/**
 * Created by Sam on 2014-04-10.
 */
public class IssueTrackerItemCustomAdapter extends ArrayAdapter<ObjectIssueTrackerItem> {

    Context mContext;
    int layoutResourceId;
    ArrayList<ObjectIssueTrackerItem> data = null;

    public IssueTrackerItemCustomAdapter(Context mContext, int layoutResourceId, ArrayList<ObjectIssueTrackerItem> data) {

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

        TextView descriptionlabel = (TextView) listItem.findViewById(R.id.issue_message_description);
        //TextView authorlabel = (TextView) listItem.findViewById(R.id.issue_message_author);
        TextView timelabel = (TextView) listItem.findViewById(R.id.issue_message_time);

        String description = data.get(position).description;
        String author = data.get(position).author;
        Date time = data.get(position).time;


        descriptionlabel.setText(description);
       // authorlabel.setText("Author: "+author);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        timelabel.setText(sdf.format(time));

        return listItem;
    }

}
