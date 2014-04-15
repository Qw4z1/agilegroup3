package se.group3.navigatorslittlehelper.app.handler;


import android.util.Log;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Map;

public class GitHubHandler {
    private static GitHubHandler instance = null;
    private static GitHub github = null;
    private static GHRepository repo = null;

    private GitHubHandler() { }

    public static synchronized GitHubHandler getInstance() {
        if (instance == null) {
            instance = new GitHubHandler();
        }
        return instance;
    }

    public Boolean connectUsingPassword(String username, String password){
        boolean returnValue;
        try {
            github = GitHub.connectUsingPassword(username, password);
            returnValue = github.isCredentialValid();
        }catch (IOException e){
            Log.e("GitHubHandler", "IOException when connecting with username/password");
            return false;
        }
        return returnValue;
    }

    public Map<String,GHRepository> getAllRepositories(){
        if(github == null){
            Log.e("GitHubHandler", "github == null, did you forget to call connectUsingPassword?");
            return null;
        }else{
            Map<String,GHRepository> map;
            try {
                map = github.getMyself().getAllRepositories();
            }catch (IOException e){
                Log.e("GitHubHandler", "IOException when getting all repositories");
                return null;
            }
            return map;
        }
    }

    public void setRepository(GHRepository repo){
        this.repo = repo;
    }

}

