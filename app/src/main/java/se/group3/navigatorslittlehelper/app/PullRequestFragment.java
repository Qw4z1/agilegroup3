package se.group3.navigatorslittlehelper.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PullRequestFragment extends Fragment {

    public PullRequestFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

              View rootView = inflater.inflate(R.layout.fragment_pull_request, container, false);

        return rootView;
    }

}
