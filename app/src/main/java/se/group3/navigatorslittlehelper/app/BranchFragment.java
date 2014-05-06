package se.group3.navigatorslittlehelper.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import se.group3.navigatorslittlehelper.app.adapter.BranchItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapter.CommitMessageItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectBranchItem;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitMessageItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;

public class BranchFragment extends Fragment {

    private ListView listview;
    private BranchItemCustomAdapter adapter;
    final ArrayList<ObjectBranchItem> branchitemlist = new ArrayList<ObjectBranchItem>();
    Map<String, GHBranch> map = new HashMap<String, GHBranch>();
    List<String> branchkey;
    List<GHBranch> branchobject;

    public BranchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_branch, container, false);
        listview = (ListView) rootView.findViewById(R.id.branch_list_view);

        adapter = new BranchItemCustomAdapter(getActivity(), R.layout.branch_list_item, new ArrayList<ObjectBranchItem>());
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("String at "+i+": "+branchkey.get(i));
                System.out.println("Object at "+i+": "+branchobject.get(i));

                //Bundle would probably be a better approach, but for now our GitHubHandler
                //is storing the choosen Branch due to GHBranch not being parcelable
                /*
                GitHubHandler.getInstance().setBranch(branchobject.get(i));
                Fragment fragment = new ExpandedBranchFragment();
                FragmentManager fragmentmanager = getFragmentManager();
                fragmentmanager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                */
            }
        });




        Thread thread = new Thread() {
            @Override
            public void run() {
                branchitemlist.clear();
                //I think, but I'm not 100%, that all information is already stored in the GHRepository in GitHubHandler, so to update that has to be updated
                Map<String, GHBranch> map = GitHubHandler.getInstance().getBranches();
                branchkey = new ArrayList<String>(map.keySet());
                branchobject = new ArrayList<GHBranch>(map.values());
                for (GHBranch b :branchobject) {
                    GHCommit c = getLastCommit(b);
                    branchitemlist.add(new ObjectBranchItem(b.getName(),c.getCommitShortInfo().getAuthor().getName(), c.getCommitShortInfo().getCommitter().getDate()));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addListItems(branchitemlist);
                    }
                });
            }
        };
        thread.start();

        return rootView;
    }

    private GHCommit getLastCommit(GHBranch branch){
        return GitHubHandler.getInstance().getCommitBySHA1(branch.getSHA1());
    }


    private void addListItems(ArrayList<ObjectBranchItem> b){
        adapter.addAll(b);
        this.updateList();
    }
    private void removeListItems(ArrayList<ObjectBranchItem> b){
        for (ObjectBranchItem m : b){
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
