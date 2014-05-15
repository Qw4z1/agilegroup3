package se.group3.navigatorslittlehelper.app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import se.group3.navigatorslittlehelper.app.adapter.CommitMessageItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectChooseRepositoryItem;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitMessageItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;

public class CommitMessageFragment extends Fragment {

    private ListView listview;
    private CommitMessageItemCustomAdapter adapter;
    final ArrayList<ObjectCommitMessageItem> commitmessageitemlist = new ArrayList<ObjectCommitMessageItem>();

    public CommitMessageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //ProgressDialog dialog = ProgressDialog.show(CommitMessageFragment.this, "Please Wait", "Loading..", true);
        final View rootView = inflater.inflate(R.layout.fragment_commit_message, container, false);
        listview = (ListView) rootView.findViewById(R.id.commit_message_list_view);


        /*
        ArrayList<ObjectCommitMessageItem> commitlist = new ArrayList<ObjectCommitMessageItem>();
        commitlist.add(new ObjectCommitMessageItem("Commit message 1","Author 1",new Date(114, 3, 11, 8, 20, 20)));
        commitlist.add(new ObjectCommitMessageItem("Commit message 2","Author 2",new Date(114, 3, 11, 6, 20, 20)));
        commitlist.add(new ObjectCommitMessageItem("Commit message 3","Author 3",new Date(114, 3, 11, 4, 20, 20)));
        commitlist.add(new ObjectCommitMessageItem("Commit message 4","Author 4",new Date(114, 3, 11, 9, 20, 20)));
        */

        adapter = new CommitMessageItemCustomAdapter(getActivity(), R.layout.commit_message_list_item, new ArrayList<ObjectCommitMessageItem>());
        listview.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                commitmessageitemlist.clear();
                //I think, but I'm not 100%, that all information is already stored in the GHRepository in GitHubHandler, so to update that has to be updated
                for (GHCommit c : GitHubHandler.getInstance().getRepository().listCommits().asList()) {
                        commitmessageitemlist.add(new ObjectCommitMessageItem(c.getCommitShortInfo().getMessage(), c.getCommitShortInfo().getCommitter().getName(), c.getCommitShortInfo().getCommitter().getDate()));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addListItems(commitmessageitemlist);
                    }
                });
            }
        };
        thread.start();

        return rootView;
    }


    private void addListItems(ArrayList<ObjectCommitMessageItem> s){
        adapter.addAll(s);
        this.updateList();
    }
    private void removeListItems(ArrayList<ObjectCommitMessageItem> s){
        for (ObjectCommitMessageItem m : s){
            adapter.remove(m);
        }
        this.updateList();
    }

    private void updateList(){
        //adapter.clear();
        adapter.notifyDataSetChanged();
        //listview.invalidateViews();
    }
}
