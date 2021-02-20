package edu.byu.cs.tweeter.view.profile;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.PostStatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.CheckFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.FollowCountTask;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.login.LandingActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class ProfileActivity extends AppCompatActivity implements FollowPresenter.View, CheckFollowTask.Observer,
        FollowCountTask.Observer, FollowTask.Observer{

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String VIEWED_USER = "ViewedUser";

    private Button followButton;
    private FollowPresenter presenter;

    private User user;
    private User viewedUser;
    private TextView followeeCount;
    private TextView followerCount;

    private PostStatusPresenter postStatusPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if (user == null) {
            throw new RuntimeException("User not passed to activity");
        }
        this.user = user;
        System.out.println(user.toString());
        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        System.out.println(authToken.toString());
        viewedUser = (User) getIntent().getSerializableExtra(VIEWED_USER);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), viewedUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        presenter = new FollowPresenter(this);

        TextView userName = findViewById(R.id.userName);
        userName.setText(viewedUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(viewedUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(viewedUser.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        followerCount = findViewById(R.id.followerCount);

        FollowCountTask followCountTask = new FollowCountTask(presenter, this);
        followCountTask.execute(new FollowCountRequest(viewedUser.getAlias()));

        followButton = findViewById(R.id.follow_button);
        CheckFollowTask checkFollowTask = new CheckFollowTask(presenter,this);
        checkFollowTask.execute(new UserFollowRequest(user.getAlias(),viewedUser.getAlias()));
        //check if following or not and set the String
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creates async task to follow/unfollow that person.
                onFollowClicked(v);
            }
        });
    }

    private void onFollowClicked(View v) {
        try{
            FollowTask task = new FollowTask(presenter, this);
            task.execute(new UserFollowRequest(user.getAlias(), viewedUser.getAlias()));
        } catch (IllegalArgumentException e) {
            Log.e("MA exception", e.getMessage(), e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logoutMenu:
                Intent intent = new Intent(this, LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void followInformation(UserFollowResponse userFollowResponse) {
        if(userFollowResponse.isFollower()) {
            followButton.setText("Unfollow");
            followButton.setBackgroundColor(getResources().getColor(R.color.unfollow));
        }
        else{
            followButton.setText("Follow");
            followButton.setBackgroundColor(getResources().getColor(R.color.follow));
        }
    }

    @Override
    public void followCount(FollowCountResponse followCountResponse) {
        followeeCount.setText(getString(R.string.followeeCount, followCountResponse.getFolloweeCount()));
        followerCount.setText(getString(R.string.followerCount, followCountResponse.getFollowerCount()));
    }

    @Override
    public void followStatus(UserFollowResponse userFollowResponse) {
        CheckFollowTask checkFollowTask = new CheckFollowTask(presenter,this);
        checkFollowTask.execute(new UserFollowRequest(user.getAlias(),viewedUser.getAlias()));

        FollowCountTask followCountTask = new FollowCountTask(presenter, this);
        followCountTask.execute(new FollowCountRequest(viewedUser.getAlias()));
    }

    @Override
    public void handleException(Exception exception) {

    }
}