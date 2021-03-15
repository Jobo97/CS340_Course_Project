package edu.byu.cs.tweeter.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.main.following.FollowFragment;
import edu.byu.cs.tweeter.view.main.following.StatusFragment;
import edu.byu.cs.tweeter.view.tabs.PlaceholderFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to one of the sections/tabs/pages
 * of the Main Activity.
 */
class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final int FEED_FRAGMENT_POSITION = 0;
    private static final int STORY_FRAGMENT_POSITION = 1;
    private static final int FOLLOWING_FRAGMENT_POSITION = 2;
    private static final int FOLLOWER_FRAGMENT_POSITION = 3;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.feedTabTitle, R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;
    private final User user;
    private final AuthToken authToken;

    public SectionsPagerAdapter(Context context, FragmentManager fm, User user, AuthToken authToken) {
        super(fm);
        System.out.println(context.toString());
        System.out.println(user.toString());
        System.out.println(authToken.toString());
        mContext = context;
        this.user = user;
        this.authToken = authToken;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == FEED_FRAGMENT_POSITION){
            return StatusFragment.newInstance(user, authToken, false);
        }
        else if (position == STORY_FRAGMENT_POSITION){
            return StatusFragment.newInstance(user, authToken, true);
        }
        else if (position == FOLLOWING_FRAGMENT_POSITION) {
            return FollowFragment.newInstance(user, authToken, false);
        }
        else if (position == FOLLOWER_FRAGMENT_POSITION) {
            return FollowFragment.newInstance(user, authToken, true);
        }
        else {
            return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
}