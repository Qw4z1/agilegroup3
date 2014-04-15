package se.group3.navigatorslittlehelper.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import se.group3.navigatorslittlehelper.app.R;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectChooseRepositoryItem;

public class ChooseRepositoryCustomAdapter extends ArrayAdapter<ObjectChooseRepositoryItem>{
    Context mContext;
    int layoutResourceId;
    ArrayList<ObjectChooseRepositoryItem> data;

    public ChooseRepositoryCustomAdapter(Context mContext, int layoutResourceId, ArrayList<ObjectChooseRepositoryItem> data){
        super(mContext, layoutResourceId, data);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItem = convertView;
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);

        TextView repolabel = (TextView) listItem.findViewById(R.id.choose_repository_reponame);
        String reponame = data.get(position).reponame;

        repolabel.setText(reponame);
        return listItem;
    }

}
