package se.group3.navigatorslittlehelper.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import se.group3.navigatorslittlehelper.app.ObjectDrawerItem;
import se.group3.navigatorslittlehelper.app.R;

/**
 * Created by Sam on 2014-04-10.
 */
public class CommitMessageItemCustomAdapter extends ArrayAdapter<String> {

    Context mContext;
    int layoutResourceId;
    String data[] = null;

    public CommitMessageItemCustomAdapter(Context mContext, int layoutResourceId, String[] data) {

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

        TextView textView = (TextView) listItem.findViewById(R.id.commit_message_list_item);

        String text = data[position];
        textView.setText(text);

        return listItem;
    }


}
