package se.group3.navigatorslittlehelper.app;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Arrays;

import se.group3.navigatorslittlehelper.app.adapter.DrawerItemCustomAdapter;
import se.group3.navigatorslittlehelper.app.adapterobjects.ObjectDrawerItem;

public class MainActivity extends ActionBarActivity {
    //NavigationDrawer variables
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set properties for proper title display
        mTitle = mDrawerTitle = getTitle();
        //NavigationDrawer initialize properties
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //Setup object drawer items based on the items added in res/drawable-hdpi
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[5];
        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_action_share, "Branch");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_action_refresh, "Commit Message");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_action_download, "Pull Request");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_action_copy, "Issue Tracker");
        drawerItem[4] = new ObjectDrawerItem(R.drawable.ic_action_settings, "Settings");
        //Create DrawerItemCustomAdapter and pass drawer items to it
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        //Set adapter
        mDrawerList.setAdapter(adapter);
        //Set item click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        //App icon control code
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);


        /*try {
            GitHub github = GitHub.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //This is how you do network related stuff, not allowed on main thread
        /*Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(Arrays.toString(GitHubHandler.getInstance().getAllRepositories().keySet().toArray()));
            }
        };
        thread.start();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Make the app icon a toggle of nav drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Needed for changing titles
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    //Change the up caret icon before the app icon
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    //-----------------Navigation Drawer Listener------------------
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new BranchFragment();
                break;
            case 1:
                fragment = new CommitMessageFragment();
                break;
            case 2:
                fragment = new PullRequestFragment();
                break;
            case 3:
                fragment = new IssueTrackerFragment();
                break;
            case 4:
                fragment = new SettingsFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}


