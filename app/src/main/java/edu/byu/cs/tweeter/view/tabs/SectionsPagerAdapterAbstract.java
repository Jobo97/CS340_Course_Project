package edu.byu.cs.tweeter.view.tabs;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;

public abstract class SectionsPagerAdapterAbstract extends FragmentPagerAdapter {

    @StringRes
    protected static int[] TAB_TITLES = null;
    private final Context mContext;
    private final User user;
    private final AuthToken authToken;

    public SectionsPagerAdapterAbstract(FragmentManager fm, Context mContext, User user, AuthToken authToken) {
        super(fm);
        this.mContext = mContext;
        this.user = user;
        this.authToken = authToken;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }
}
