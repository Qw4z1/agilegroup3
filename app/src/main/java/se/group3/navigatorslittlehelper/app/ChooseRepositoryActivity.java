package se.group3.navigatorslittlehelper.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.kohsuke.github.GHRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import se.group3.navigatorslittlehelper.app.adapter.ChooseRepositoryCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectChooseRepositoryItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;


public class ChooseRepositoryActivity extends Activity {
    private ChooseRepositoryCustomAdapter adapter;
    private ListView listview;
    private Map<String,GHRepository> repomap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_repository);

        ArrayList<ObjectChooseRepositoryItem> testrepolist = new ArrayList<ObjectChooseRepositoryItem>();
        testrepolist.add(new ObjectChooseRepositoryItem("Repository 1"));
        testrepolist.add(new ObjectChooseRepositoryItem("Repository 2"));

        listview = (ListView) findViewById(R.id.choose_repository_list_view);
        adapter = new ChooseRepositoryCustomAdapter(this, R.layout.choose_repository_list_item, testrepolist);
        listview.setAdapter(adapter);

        Thread thread = new Thread() {
            @Override
            public void run() {
                repomap = GitHubHandler.getInstance().getAllRepositories();
                final ArrayList<ObjectChooseRepositoryItem> repolist = new ArrayList<ObjectChooseRepositoryItem>();
                for (String s : repomap.keySet()){
                    repolist.add(new ObjectChooseRepositoryItem(s));
                }
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        addListItems(repolist);
                    }
                });
            }
        };
        thread.start();
    }

    public void addListItems(ArrayList<ObjectChooseRepositoryItem> s){
        adapter.addAll(s);
        this.updateList();
    }
    public void removeListItems(ArrayList<ObjectChooseRepositoryItem> s){
        for (ObjectChooseRepositoryItem i : s){
            adapter.remove(i);
        }
        this.updateList();
    }
    private void updateList(){
        adapter.clear();
        adapter.notifyDataSetChanged();
        listview.invalidateViews();
    }
}
