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
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import edu.byu.cs.tweeter.model.service.request.GetUserRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.UserFollowRequest;
import edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.model.service.response.UserFollowResponse;
import edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.ProfilePresenter;
import edu.byu.cs.tweeter.view.asyncTasks.CheckFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.FollowCountTask;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.login.LandingActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class ProfileActivity extends AppCompatActivity implements FollowPresenter.View, CheckFollowTask.Observer,
        FollowCountTask.Observer, FollowTask.Observer, GetUserTask.Observer, ProfilePresenter.View,
        LogoutPresenter.View, LogoutTask.Observer {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String VIEWED_USER = "ViewedUser";

    private Button followButton;
    private FollowPresenter presenter;

    private User user;
    private AuthToken authToken;
    private User viewedUser;
    private String viewedUserString;
    private TextView followeeCount;
    private TextView followerCount;

    private ProfilePresenter userPresenter;
    private LogoutPresenter logoutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if (user == null) {
            throw new RuntimeException("User not passed to activity");
        }
        this.user = user;
        System.out.println(user.toString());
        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        System.out.println(authToken.toString());

        userPresenter = new ProfilePresenter(this);
        logoutPresenter = new LogoutPresenter(this);

        //Fails to cast the string int a user object. I think we need an async task here
        viewedUserString = (String) getIntent().getSerializableExtra(VIEWED_USER);
        GetUserTask task = new GetUserTask(userPresenter, this);
        task.execute(new GetUserRequest(viewedUserString));

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
                //Logout async task
                LogoutTask task = new LogoutTask(logoutPresenter, this);
                task.execute(new LogoutRequest(user.getAlias(), authToken));
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
    public void loadUser(GetUserResponse getUserResponse) {
        viewedUser = getUserResponse.getViewedUser();
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
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creates an async task to follow/unfollow that person.
                onFollowClicked(v);
            }
        });
        CheckFollowTask checkFollowTask = new CheckFollowTask(presenter,this);
        checkFollowTask.execute(new UserFollowRequest(user.getAlias(),viewedUser.getAlias()));
        //check if following or not and set the String
    }

    @Override
    public void logoutSuccessful(Response response) {
        Intent intent = new Intent(this, LandingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void logoutUnsuccessful(Response response) {
        Toast.makeText(findViewById(R.id.main_activity).getContext(),
                "Failed to logout, cannot connect to server ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        Toast.makeText(findViewById(R.id.main_activity).getContext(),
                "Failed to post tweet because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}