package se.group3.navigatorslittlehelper.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHPullRequestCommitDetail;

import java.io.IOException;
import java.util.ArrayList;

import se.group3.navigatorslittlehelper.app.adapter.IssueTrackerItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapter.PullRequestItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectIssueTrackerItem;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectPullRequestItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;


public class PullRequestFragment extends Fragment {

    private ListView listview;
    private PullRequestItemCustomAdapter adapter;
    final ArrayList<ObjectPullRequestItem> pullitemlist = new ArrayList<ObjectPullRequestItem>();


    public PullRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pull_request, container, false);
        listview = (ListView) rootView.findViewById(R.id.pull_message_list_view);

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

        adapter = new PullRequestItemCustomAdapter(getActivity(), R.layout.pull_message_list_item, new ArrayList<ObjectPullRequestItem>());
        listview.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                pullitemlist.clear();
                //I think, but I'm not 100%, that all information is already stored in the GHRepository in GitHubHandler, so to update that has to be updated
                for (GHPullRequest c : GitHubHandler.getInstance().getRepository().listPullRequests(GHIssueState.CLOSED).asList()) {
                    try {
                        pullitemlist.add(new ObjectPullRequestItem(c.getTitle(),c.getUser().getName() ,c.getUpdatedAt()));
                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addListItems(pullitemlist);
                    }
                });
            }
        };
        thread.start();

        return rootView;
    }


    private void addListItems(ArrayList<ObjectPullRequestItem> s){
        adapter.addAll(s);
        this.updateList();
    }
    private void removeListItems(ArrayList<ObjectPullRequestItem> s){
        for (ObjectPullRequestItem m : s){
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
