package se.group3.navigatorslittlehelper.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import se.group3.navigatorslittlehelper.app.adapter.CommitMessageItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitMessageItem;

public class CommitMessageFragment extends Fragment {

    private ListView listview;
    private CommitMessageItemCustomAdapter adapter;

    public CommitMessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commit_message, container, false);
        listview = (ListView) rootView.findViewById(R.id.commit_message_list_view);

        ArrayList<ObjectCommitMessageItem> commitlist = new ArrayList<ObjectCommitMessageItem>();
        commitlist.add(new ObjectCommitMessageItem("Commit message 1","Author 1",new Date(114, 3, 11, 8, 20, 20)));
        commitlist.add(new ObjectCommitMessageItem("Commit message 2","Author 2",new Date(114, 3, 11, 6, 20, 20)));
        commitlist.add(new ObjectCommitMessageItem("Commit message 3","Author 3",new Date(114, 3, 11, 4, 20, 20)));
        commitlist.add(new ObjectCommitMessageItem("Commit message 4","Author 4",new Date(114, 3, 11, 9, 20, 20)));

        adapter = new CommitMessageItemCustomAdapter(getActivity(), R.layout.commit_message_list_item, commitlist);
        listview.setAdapter(adapter);

        return rootView;
    }

    public void addListItem(ObjectCommitMessageItem s){
        adapter.add(s);
        this.updateList();
    }
    public void addListItems(ArrayList<ObjectCommitMessageItem> s){
        adapter.addAll(s);
        this.updateList();
    }
    public void removeListItem(ObjectCommitMessageItem s){
        adapter.remove(s);
        this.updateList();
    }
    public void removeListItems(ArrayList<ObjectCommitMessageItem> s){
        for (ObjectCommitMessageItem m : s){
            adapter.remove(m);
        }
    }

    private void updateList(){
        adapter.clear();
        adapter.notifyDataSetChanged();
        listview.invalidateViews();
    }
}
