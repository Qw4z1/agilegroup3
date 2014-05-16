package se.group3.navigatorslittlehelper.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.kohsuke.github.GHCommit;

import java.util.ArrayList;

import se.group3.navigatorslittlehelper.app.adapter.CommitMessageItemCustomAdapter;
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



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommitDetailFragment fragment=CommitDetailFragment.newInstance((ObjectCommitMessageItem) adapterView.getAdapter().getItem(i));
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                ft.replace(R.id.content_frame, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        adapter = new CommitMessageItemCustomAdapter(getActivity(), R.layout.commit_message_list_item, new ArrayList<ObjectCommitMessageItem>());
        listview.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                //I think, but I'm not 100%, that all information is already stored in the GHRepository in GitHubHandler, so to update that has to be updated
                for (GHCommit c : GitHubHandler.getInstance().getRepository().listCommits().asList()) {
                        commitmessageitemlist.add(new ObjectCommitMessageItem(c.getCommitShortInfo().getMessage(), c.getCommitShortInfo().getCommitter().getName(), c.getCommitShortInfo().getCommitter().getDate(),c.getSHA1()));

                }
                if(getActivity()!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addListItems(commitmessageitemlist);
                        }
                    });
                }

                commitmessageitemlist.clear();
                GHCommit c1= GitHubHandler.getInstance().getRepository().listCommits().asList().get(1);

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
        adapter.notifyDataSetChanged();
    }
}
