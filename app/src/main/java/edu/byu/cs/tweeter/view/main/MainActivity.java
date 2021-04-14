package edu.byu.cs.tweeter.view.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.byu.cs.tweeter.R;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowCountRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowCountResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.FollowCountTask;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.PostStatusTask;
import edu.byu.cs.tweeter.view.login.LandingActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements MainPresenter.View, PostStatusTask.Observer,
        FollowPresenter.View, FollowCountTask.Observer, Serializable, LogoutPresenter.View, LogoutTask.Observer{

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String LOG_TAG = "MainActivity";

    private User user;
    private AuthToken authToken;

    private TextView followeeCount;
    private TextView followerCount;

    private FollowPresenter followPresenter;
    private MainPresenter mainPresenter;
    private LogoutPresenter logoutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }
        this.user = user;
        System.out.println(user.toString());
        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        this.authToken = authToken;
        System.out.println(authToken.toString());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        followPresenter = new FollowPresenter(this);
        mainPresenter = new MainPresenter(this);
        logoutPresenter = new LogoutPresenter(this);

        FloatingActionButton fab = findViewById(R.id.fab);

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder tweetBuilder = new AlertDialog.Builder(view.getContext());

                tweetBuilder.setTitle("Tweet");

                final EditText tweet = new EditText(view.getContext());
                tweetBuilder.setView(tweet);

                tweetBuilder.setPositiveButton("Post Tweet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postStatus(tweet.getText().toString());
                    }
                });

                tweetBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                tweetBuilder.show();
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        followerCount = findViewById(R.id.followerCount);

        FollowCountTask followCountTask = new FollowCountTask(followPresenter, this);
        followCountTask.execute(new FollowCountRequest(user.getAlias(), user.getAlias()));
    }

    public void postStatus(String tweet) {
        PostStatusTask postStatusTask = new PostStatusTask(mainPresenter, this);
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        Date date = new Date(System.currentTimeMillis());
        try{
            date = dateFormat.parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        PostStatusRequest postStatusRequest = new PostStatusRequest(tweet,
                this.user.getAlias(), timestamp.toString());
        postStatusTask.execute(postStatusRequest);
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
    public void postSuccessful(Response response) {
        Toast.makeText(findViewById(R.id.main_activity).getContext(), "Great tweet! ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void postUnsuccessful(Response response) {
        Toast.makeText(findViewById(R.id.main_activity).getContext(), "Terrible tweet. " + response.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void followCount(FollowCountResponse followCountResponse) {
        followeeCount.setText(getString(R.string.followeeCount, followCountResponse.getFolloweeCount()));
        followerCount.setText(getString(R.string.followerCount, followCountResponse.getFollowerCount()));
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
    public void handleException(Exception ex) {
        Log.e("Main Activity", ex.getMessage(), ex);

        if(ex instanceof TweeterRemoteException) {
            TweeterRemoteException remoteException = (TweeterRemoteException) ex;
            Log.e(LOG_TAG, "Remote Exception Type: " + remoteException.getRemoteExceptionType());

            Log.e(LOG_TAG, "Remote Stack Trace:");
            if(remoteException.getRemoteStackTrace() != null) {
                for(String stackTraceLine : remoteException.getRemoteStackTrace()) {
                    Log.e(LOG_TAG, "\t\t" + stackTraceLine);
                }
            }
        }
        Toast.makeText(findViewById(R.id.main_activity).getContext(),
                "Failed to post tweet because of exception: " + ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}