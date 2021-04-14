package edu.byu.cs.tweeter.view.main.following;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.AuthToken;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.FollowResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.GetUserResponse;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.ProfilePresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowingTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.login.LandingActivity;
import edu.byu.cs.tweeter.view.profile.ProfileActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class FollowFragment extends Fragment implements FollowPresenter.View, Serializable, ProfilePresenter.View, LogoutPresenter.View {

    private static final String LOG_TAG = "FollowFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String IS_FOLLOWER_KEY = "IsFollowerKey";
    private static final String L_USER_KEY = "LUserKey";


    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private String serializedUser;
    private AuthToken authToken;
    private Serializable serializedAuthToken;
    private boolean isFollower;
    private FollowPresenter presenter;
    private ProfilePresenter userPresenter;
    private LogoutPresenter logoutPresenter;
    private String loggedInUser;


    private FollowFragment.FollowingRecyclerViewAdapter followingRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */

    public static FollowFragment newInstance(User user, AuthToken authToken, boolean isFollower, String loggedInUser) {
        FollowFragment fragment = new FollowFragment();

        Bundle args = new Bundle(3);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);
        args.putSerializable(IS_FOLLOWER_KEY, isFollower);
        args.putSerializable(L_USER_KEY, loggedInUser);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follow, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);
        isFollower = (boolean) getArguments().getSerializable(IS_FOLLOWER_KEY);
        loggedInUser = (String) getArguments().getSerializable(L_USER_KEY);

        presenter = new FollowPresenter(this);
        userPresenter = new ProfilePresenter(this);
        logoutPresenter = new LogoutPresenter(this);

        RecyclerView followingRecyclerView = view.findViewById(R.id.followRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followingRecyclerView.setLayoutManager(layoutManager);

        followingRecyclerViewAdapter = new FollowFragment.FollowingRecyclerViewAdapter();
        followingRecyclerView.setAdapter(followingRecyclerViewAdapter);

        followingRecyclerView.addOnScrollListener(new FollowFragment.FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    /**
     * The ViewHolder for the RecyclerView that displays the Following data.
     */
    private class FollowingHolder extends RecyclerView.ViewHolder implements GetUserTask.Observer {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private User viewedUser;

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        FollowingHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage);
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);
                //Check this next line for the FollowingHolder.this
                //GetUserTask task = new GetUserTask(userPresenter, FollowingHolder.this);
                //task.execute(new GetUserRequest(userAlias.getText().toString()));

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ProfileActivity.class);

                        intent.putExtra(ProfileActivity.CURRENT_USER_KEY, user);
                        intent.putExtra(ProfileActivity.AUTH_TOKEN_KEY, authToken);
                        //would the async task wait at all for it to load viewedUser?
                        intent.putExtra(ProfileActivity.VIEWED_USER, viewedUser.getAlias());
                        intent.putExtra(ProfileActivity.L_USER_KEY, loggedInUser);
                        startActivity(intent);
                    }
                });
            } else {
                userImage = null;
                userAlias = null;
                userName = null;
                viewedUser = null;
            }
        }

        /**
         * Binds the user's data to the view.
         *
         * @param user the user.
         */
        void bindUser(User user) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
            viewedUser = user;
        }

        @Override
        public void loadUser(GetUserResponse getUserResponse) {
            viewedUser = getUserResponse.getViewedUser();
        }

        @Override
        public void handleException(Exception exception) {
            System.out.println(exception);

        }
    }

    /**
     * The adapter for the RecyclerView that displays the Following data.
     */
    private class FollowingRecyclerViewAdapter extends RecyclerView.Adapter<FollowFragment.FollowingHolder> implements GetFollowingTask.Observer, LogoutTask.Observer {

        private final List<User> users = new ArrayList<>();

        private User lastFollowee;

        private boolean hasMorePages;
        protected boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of following data.
         */
        FollowingRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new users to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newUsers the users to add.
         */
        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        /**
         * Adds a single user to the list from which the RecyclerView retrieves the users it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param user the user to add.
         */
        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        /**
         * Removes a user from the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param user the user to remove.
         */
        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a followee to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public FollowFragment.FollowingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowFragment.FollowingHolder(view, viewType);
        }

        /**
         * Binds the followee at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param followingHolder the ViewHolder to which the followee should be bound.
         * @param position the position (in the list of followees) that contains the followee to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull FollowFragment.FollowingHolder followingHolder, int position) {
            if(!isLoading) {
                if (users.get(position) != null) {
                    followingHolder.bindUser(users.get(position));
                }
            }
        }

        /**
         * Returns the current number of followees available for display.
         * @return the number of followees available for display.
         */
        @Override
        public int getItemCount() {
            return users.size();
        }

        /**
         * Returns the type of the view that should be displayed for the item currently at the
         * specified position.
         *
         * @param position the position of the items whose view type is to be returned.
         * @return the view type.
         */
        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more following
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();
            System.out.println("scroll was registered");

            GetFollowingTask getFollowingTask = new GetFollowingTask(presenter, this);
            FollowRequest request = new FollowRequest(user.getAlias(), PAGE_SIZE, (lastFollowee == null ? null : lastFollowee.getAlias()), isFollower, loggedInUser);
            getFollowingTask.execute(request);
        }

        /**
         * A callback indicating more following data has been received. Loads the new followees
         * and removes the loading footer.
         *
         * @param followResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void followsRetrieved(FollowResponse followResponse) {
            List<User> follows = followResponse.getFollows();
            if (followResponse.getMessage() != null) {
                if (followResponse.getMessage().equals("User-session timeout")) {
                    //logout task
                    System.out.println("timeout logout task was called");
                    LogoutTask task = new LogoutTask(logoutPresenter, this);
                    task.execute(new LogoutRequest(loggedInUser, authToken));
                }
            }

            lastFollowee = (follows.size() > 0) ? follows.get(follows.size() -1) : null;
            hasMorePages = followResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followingRecyclerViewAdapter.addItems(follows);
        }

        @Override
        public void logoutSuccessful(Response response) {
            Intent intent;
            if (getActivity() == null) {
                intent = new Intent(getContext(), LandingActivity.class);
            }
            else {
                intent = new Intent(getActivity(), LandingActivity.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        @Override
        public void logoutUnsuccessful(Response response) {
            System.out.println("Session timeout failed.");
        }

        /**
         * A callback indicating that an exception was thrown by the presenter.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * Adds a dummy user to the list of users so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            addItem(new User("Dummy", "User", ""));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!followingRecyclerViewAdapter.isLoading && followingRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followingRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
