package se.group3.navigatorslittlehelper.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.kohsuke.github.GHRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import se.group3.navigatorslittlehelper.app.adapter.ChooseRepositoryCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectChooseRepositoryItem;
import se.group3.navigatorslittlehelper.app.handler.GitHubHandler;


public class ChooseRepositoryActivity extends Activity {
    private ChooseRepositoryCustomAdapter adapter;
    private int[] colors = new int[] { 0xAAf6ffc8, 0xAA538d00 };
    private LayoutInflater mInflater;
    private ListView listview;
    private ArrayList<String> repostringlist = new ArrayList<String>();
    private ArrayList<GHRepository> repoobjectlist = new ArrayList<GHRepository>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ProgressDialog dialog = ProgressDialog.show(ChooseRepositoryActivity.this, "Please Wait", "Loading..", true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_repository);

        listview = (ListView) findViewById(R.id.choose_repository_list_view);
        adapter = new ChooseRepositoryCustomAdapter(this, R.layout.choose_repository_list_item, new ArrayList<ObjectChooseRepositoryItem>());
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                System.out.println("String at "+i+": "+repostringlist.get(i));
                System.out.println("Object at "+i+": "+repoobjectlist.get(i));
                GitHubHandler.getInstance().setRepository(repoobjectlist.get(i));
                Intent intent = new Intent(ChooseRepositoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Thread thread = new Thread() {
            @Override
            public void run() {
                repostringlist.clear();
                repoobjectlist.clear();
                Map<String,GHRepository> repomap = GitHubHandler.getInstance().getAllRepositories();
                repostringlist.addAll(repomap.keySet());
                repoobjectlist.addAll(repomap.values());

                final ArrayList<ObjectChooseRepositoryItem> repoitems = new ArrayList<ObjectChooseRepositoryItem>();
                for (String s : repostringlist){
                    repoitems.add(new ObjectChooseRepositoryItem(s));
                }
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        addListItems(repoitems);
                    }
                });
                dialog.dismiss();
            }
        };
        thread.start();

        }


    public void addListItems(ArrayList<ObjectChooseRepositoryItem> a){
        adapter.addAll(a);
        this.updateList();
    }
    public void removeListItems(ArrayList<ObjectChooseRepositoryItem> a){
        for (ObjectChooseRepositoryItem i : a){
            adapter.remove(i);
        }
        this.updateList();
    }
    private void updateList(){
        //adapter.clear();
        adapter.notifyDataSetChanged();
        //listview.invalidateViews();
    }
}
