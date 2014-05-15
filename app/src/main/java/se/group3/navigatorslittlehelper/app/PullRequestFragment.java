package se.group3.navigatorslittlehelper.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHPullRequest;

import java.util.ArrayList;

import se.group3.navigatorslittlehelper.app.adapter.CommitMessageItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapter.PullRequestCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitMessageItem;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectPullRequestItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;

public class PullRequestFragment extends Fragment {

    private ListView listview;
    private PullRequestCustomAdapter adapter;
    final ArrayList<ObjectPullRequestItem> pullrequestitemlist = new ArrayList<ObjectPullRequestItem>();

    public PullRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pull_request, container, false);
        listview = (ListView) rootView.findViewById(R.id.pull_request_list_view);

        adapter = new PullRequestCustomAdapter(getActivity(), R.layout.pull_request_list_item, new ArrayList<ObjectPullRequestItem>());
        listview.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                pullrequestitemlist.clear();
                //I think, but I'm not 100%, that all information is already stored in the GHRepository in GitHubHandler, so to update that has to be updated
                for (GHPullRequest p : GitHubHandler.getInstance().getPullRequest()) {
                    System.out.println("User tostring: "+p.getUser().toString());
                    pullrequestitemlist.add(new ObjectPullRequestItem(p.getTitle(), p.getUser().toString(), p.getCreatedAt()));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addListItems(pullrequestitemlist);
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
        adapter.notifyDataSetChanged();
    }

}
