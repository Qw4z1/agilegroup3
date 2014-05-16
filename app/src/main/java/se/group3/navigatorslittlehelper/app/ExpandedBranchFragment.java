package se.group3.navigatorslittlehelper.app;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.kohsuke.github.GHCommitStatus;

import java.io.IOException;
import java.util.ArrayList;

import se.group3.navigatorslittlehelper.app.adapter.ExpandedBranchItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectExpandedBranchItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;

public class ExpandedBranchFragment extends Fragment {
    private ListView listview;
    private ExpandedBranchItemCustomAdapter adapter;
    private final ArrayList<ObjectExpandedBranchItem> expandedbranchitemlist = new ArrayList<ObjectExpandedBranchItem>();

    public ExpandedBranchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_expanded_branch, container, false);
        listview = (ListView) rootView.findViewById(R.id.expanded_branch_list_view);
        adapter = new ExpandedBranchItemCustomAdapter(getActivity(), R.layout.expanded_branch_list_item, new ArrayList<ObjectExpandedBranchItem>());
        listview.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                expandedbranchitemlist.clear();
                try {
                    for (GHCommitStatus c : GitHubHandler.getInstance().getRepository().listCommitStatuses(GitHubHandler.getInstance().getBranch().getSHA1())) {
                         expandedbranchitemlist.add(new ObjectExpandedBranchItem(c.getDescription(),c.getCreator().getName(),c.getCreatedAt()));
                    }
                }catch (IOException e){
                    Log.e("ExpandedBRanchFragment", "IOException when getting commitstatus");
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addListItems(expandedbranchitemlist);
                    }
                });
            }
        };
        thread.start();
        return rootView;
    }

    private void addListItems(ArrayList<ObjectExpandedBranchItem> s){
        adapter.addAll(s);
        this.updateList();
    }
    private void removeListItems(ArrayList<ObjectExpandedBranchItem> s){
        for (ObjectExpandedBranchItem m : s){
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
