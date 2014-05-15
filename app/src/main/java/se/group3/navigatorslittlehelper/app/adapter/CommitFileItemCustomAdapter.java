package se.group3.navigatorslittlehelper.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import se.group3.navigatorslittlehelper.app.R;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitFileItem;

/**
 * Created by Sam on 2014-04-10.
 */
public class CommitFileItemCustomAdapter extends ArrayAdapter<ObjectCommitFileItem> {

    private static final String TAG = CommitFileItemCustomAdapter.class.getSimpleName();
    Context mContext;
    int layoutResourceId;
    ArrayList<ObjectCommitFileItem> data = null;

    public CommitFileItemCustomAdapter(Context mContext, int layoutResourceId, ArrayList<ObjectCommitFileItem> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "Get view: " + position);
        View listItem = convertView;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        TextView namelabel = (TextView) listItem.findViewById(R.id.commit_message_name);
        TextView totalChangeslabel = (TextView) listItem.findViewById(R.id.commit_message_totalChanges);
        TextView numDetetionslabel = (TextView) listItem.findViewById(R.id.commit_message_deletions);
        TextView numUpdateslabel = (TextView) listItem.findViewById(R.id.commit_message_updates);

        String name = data.get(position).name;
        int totalChanges = data.get(position).totalChanges();
        int totalDeletions = data.get(position).totalDeletions;
        int totalUpdates = data.get(position).totalUpdates;


        namelabel.setText(name);
        totalChangeslabel.setText("Total changes: "+totalChanges);
        numDetetionslabel.setText(totalDeletions + " Deletions");
        numUpdateslabel.setText(totalUpdates + "Updates");

        return listItem;
    }


}
