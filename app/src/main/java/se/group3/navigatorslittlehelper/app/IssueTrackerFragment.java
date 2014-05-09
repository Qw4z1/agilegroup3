package se.group3.navigatorslittlehelper.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import se.group3.navigatorslittlehelper.app.adapter.CommitMessageItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapter.IssueTrackerItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitMessageItem;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectIssueTrackerItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;


public class IssueTrackerFragment extends Fragment {

    private ListView listview;
    private IssueTrackerItemCustomAdapter adapter;
    final ArrayList<ObjectIssueTrackerItem> issueitemlist = new ArrayList<ObjectIssueTrackerItem>();


    public IssueTrackerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_issue_tracker, container, false);
        listview = (ListView) rootView.findViewById(R.id.issue_message_list_view);

      /*  listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               // GitHubHandler.getInstance().getRepository().getCommit()listIssues(GHIssueState.CLOSED)
                Intent intent;
                intent = new Intent(this,issuedetails.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        */

        adapter = new IssueTrackerItemCustomAdapter(getActivity(), R.layout.issue_message_list_item, new ArrayList<ObjectIssueTrackerItem>());
        listview.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                issueitemlist.clear();
                //I think, but I'm not 100%, that all information is already stored in the GHRepository in GitHubHandler, so to update that has to be updated
                for (GHIssue c : GitHubHandler.getInstance().getRepository().listIssues(GHIssueState.OPEN).asList()) {
                    issueitemlist.add(new ObjectIssueTrackerItem(c.getTitle(),c.getBody(),c.getUpdatedAt()));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addListItems(issueitemlist);
                    }
                });
            }
        };
        thread.start();

        return rootView;
    }


    private void addListItems(ArrayList<ObjectIssueTrackerItem> s){
        adapter.addAll(s);
        this.updateList();
    }
    private void removeListItems(ArrayList<ObjectIssueTrackerItem> s){
        for (ObjectIssueTrackerItem m : s){
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
