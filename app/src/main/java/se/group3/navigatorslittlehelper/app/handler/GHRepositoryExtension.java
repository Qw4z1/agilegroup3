package se.group3.navigatorslittlehelper.app.handler;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.PagedIterable;
import org.kohsuke.github.PagedIterator;

/**
 * Created by Pro9 on 2014-05-12.
 */
public class GHRepositoryExtension extends GHRepository{


    /**
     * Lists all the commits from one branch.
     */
    /*
    public PagedIterable<GHCommit> listBranchCommits(final String SHA1) {
        return new PagedIterable<GHCommit>() {
            public PagedIterator<GHCommit> iterator() {
                return new PagedIterator<GHCommit>(root.retrieve().asIterator(String.format("/repos/%s/%s/commits?per_page100&sha=%s", owner.login, name, SHA1), GHCommit[].class)) {
                    protected void wrapUp(GHCommit[] page) {
                        for (GHCommit c : page)
                            c.wrapUp(GHRepository.this);
                    }
                };
            }
        };
    }

    */
}
