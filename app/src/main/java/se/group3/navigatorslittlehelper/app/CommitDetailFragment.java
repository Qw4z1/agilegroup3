package se.group3.navigatorslittlehelper.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.kohsuke.github.GHCommit;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import se.group3.navigatorslittlehelper.app.adapter.CommitFileItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitFileItem;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectCommitMessageItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;

public class CommitDetailFragment extends Fragment {
    private static final String AUTHOR_ARGUMENT = "Author argument";
    private static final String TIME_ARGUMENT = "Time argument";
    private static final String MESSAGE_ARGUMENT = "Message argument";
    private static final String SHA1_ARGUMENT = "SHA1 argument";
    private static final String TAG = CommitDetailFragment.class.getSimpleName();
    private static GHCommit GHcommit;

    private ListView listview;
    private CommitFileItemCustomAdapter adapter;
    final ArrayList<ObjectCommitFileItem> commitfileitemlist = new ArrayList<ObjectCommitFileItem>();

    public ObjectCommitMessageItem objectCommitMessageItem;
    TextView author, time, message;
    public String description;
    public String author1;
    public String time1;
    public String SHA1;
    private View mHEaderVIew;

    public CommitDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        author1 = args.getString(AUTHOR_ARGUMENT);
        time1 = args.getString(TIME_ARGUMENT);
        description = args.getString(MESSAGE_ARGUMENT);
        SHA1 = args.getString(SHA1_ARGUMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_commit_detail, container, false);
        mHEaderVIew = inflater.inflate(R.layout.commit_detail_header, null, false);
        author = (TextView) mHEaderVIew.findViewById(R.id.commit_message_author);
        time = (TextView) mHEaderVIew.findViewById(R.id.commit_message_time);
        message = (TextView) mHEaderVIew.findViewById(R.id.commit_message_description);
        author.setText(author1);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:MM");
        time.setText(sdf.format(stringToDate(time1, "EEE MMM d HH:mm:ss zz yyyy")));
        message.setText(description);


        listview = (ListView) rootView.findViewById(R.id.commit_message_files);

        adapter = new CommitFileItemCustomAdapter(getActivity(), R.layout.commit_file_list_item, new ArrayList<ObjectCommitFileItem>());
        listview.addHeaderView(mHEaderVIew);
        listview.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {

                GHcommit=GitHubHandler.getInstance().getCommitBySHA1(SHA1);

                for (GHCommit.File F : GHcommit.getFiles()) {
                    Log.d(TAG, "File:  " + F.getFileName());
                    commitfileitemlist.add(new ObjectCommitFileItem(F.getFileName(), F.getLinesDeleted(), F.getLinesAdded()));

                }
                Log.d(TAG, " List size: " + commitfileitemlist.size() );
                if(getActivity()!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(TAG, "Add list items List size: " + commitfileitemlist.size());
                            addListItems(commitfileitemlist);

                            commitfileitemlist.clear();
                        }
                    });
                }
            }
        };
        thread.start();

        return rootView;
    }

    public static CommitDetailFragment newInstance(ObjectCommitMessageItem commitMessageItem) {
        Log.d(TAG, "Commit Message Item SHA1: " + commitMessageItem.SHA1);

        Bundle bundle = new Bundle();
        bundle.putString(AUTHOR_ARGUMENT, commitMessageItem.author);
        bundle.putString(TIME_ARGUMENT, commitMessageItem.time.toString());
        bundle.putString(MESSAGE_ARGUMENT, commitMessageItem.description);
        bundle.putString(SHA1_ARGUMENT,commitMessageItem.SHA1);

        CommitDetailFragment fragment = new CommitDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    private void addListItems(ArrayList<ObjectCommitFileItem> s) {
        adapter.addAll(s);
        adapter.notifyDataSetChanged();
    }

    private Date stringToDate(String aDate, String aFormat) {
        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;
    }

}
