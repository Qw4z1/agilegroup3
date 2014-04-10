package se.group3.navigatorslittlehelper.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.Collection;

import se.group3.navigatorslittlehelper.app.adapter.CommitMessageItemCustomAdapter;

public class CommitMessageFragment extends Fragment {

    private ListView listview;
    private CommitMessageItemCustomAdapter adapter;

    public CommitMessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_commit_message, container, false);
        listview = (ListView) rootView.findViewById(R.id.commit_message_list_view);

        String[] listnames = new String[5];
        listnames[0] = new String("Commit Message 1");
        listnames[1] = new String("Commit Message 2");
        listnames[2] = new String("Commit Message 3");
        listnames[3] = new String("Commit Message 4");
        listnames[4] = new String("Commit Message 5");

        adapter = new CommitMessageItemCustomAdapter(rootView.getContext(), R.layout.commit_message_list_item, listnames);
        listview.setAdapter(adapter);

        return rootView;
    }

    public void addListItem(String s){
        adapter.add(s);
        this.updateList();;
    }
    public void addListItems(Collection <String> s){
        adapter.addAll(s);
        this.updateList();
    }
    public void removeListItem(String s){
        adapter.remove(s);
        this.updateList();
    }
    public void removeListItems(Collection <String> sc){
        for (String s : sc){
            adapter.remove(s);
        }
    }

    private void updateList(){
        adapter.clear();
        adapter.notifyDataSetChanged();
        listview.invalidateViews();
    }
}
