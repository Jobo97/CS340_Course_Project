package edu.byu.cs.tweeter.view.main.following;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetStatusTask;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.cs.tweeter.view.profile.ProfileActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class StatusFragment extends Fragment implements StatusPresenter.View{
    private static final String LOG_TAG = "StatusFragment";
    private static final String USER_KEY = "UserKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String IS_STORY_KEY = "IsStoryKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;
    private Status status;
    private AuthToken authToken;
    private boolean isStory;
    private StatusPresenter presenter;

    private StatusFragment.StatusRecyclerViewAdapter statusRecyclerViewAdapter;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */

    public static StatusFragment newInstance(User user, AuthToken authToken, boolean isStory) {
        StatusFragment fragment = new StatusFragment();

        Bundle args = new Bundle(3);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);
        args.putSerializable(IS_STORY_KEY, isStory);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);
        isStory = (boolean) getArguments().getSerializable(IS_STORY_KEY);

        presenter = new StatusPresenter(this);

        RecyclerView statusRecyclerView = view.findViewById(R.id.statusRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        statusRecyclerView.setLayoutManager(layoutManager);

        statusRecyclerViewAdapter = new StatusFragment.StatusRecyclerViewAdapter();
        statusRecyclerView.setAdapter(statusRecyclerViewAdapter);

        statusRecyclerView.addOnScrollListener(new StatusFragment.StatusRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    /**
     * The ViewHolder for the RecyclerView that displays the Following data.
     */
    private class StatusHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView statusText;
        private final TextView timestamp;
        //Review these, the tweet needs to be added

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        StatusHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage);
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);
                statusText = itemView.findViewById(R.id.status);
                timestamp = itemView.findViewById(R.id.timestamp);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //pull up the new users profile.
                        //Create AsyncTask to pull up the user, see the note in FollowFragment
                        System.out.println("clicked on a tweet");
                    }
                });
            } else {
                userImage = null;
                userAlias = null;
                userName = null;
                statusText = null;
                timestamp = null;
            }
        }

        /**
         * Binds the user's data to the view.
         *
         */
        void bindStatus(Status status) {        //still binds the user data here since the users data is needed for the tweet, needs the rest
            SpannableString ss = new SpannableString(status.getTweet());
            List<String> mentions = status.getMentions();
            for(String m : mentions){
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        Intent intent = new Intent(getContext(), ProfileActivity.class);

                        intent.putExtra(ProfileActivity.CURRENT_USER_KEY, user);
                        intent.putExtra(ProfileActivity.AUTH_TOKEN_KEY, authToken);
                        intent.putExtra(ProfileActivity.VIEWED_USER, m);
                        startActivity(intent);
                    }
                };
                ss.setSpan(clickableSpan, status.getTweet().indexOf(m), status.getTweet().indexOf(m) + m.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            List<String> urls = status.getUrls();
            for(String url : urls){
                ClickableSpan clickableSpan = new ClickableSpan() {
                    @Override
                    public void onClick(View textView) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                        startActivity(browserIntent);
                    }
                };
                ss.setSpan(clickableSpan, status.getTweet().indexOf(url), status.getTweet().indexOf(url) + url.length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(status.getUser().getImageBytes()));
            userAlias.setText(status.getUser().getAlias());
            userName.setText(status.getUser().getName());
            statusText.setText(ss);
            statusText.setMovementMethod(LinkMovementMethod.getInstance());
            statusText.setHighlightColor(Color.TRANSPARENT);

            timestamp.setText(status.getTimeStamp().toString());
        }
    }

    /**
     * The adapter for the RecyclerView that displays the Following data.
     */
    private class StatusRecyclerViewAdapter extends RecyclerView.Adapter<StatusFragment.StatusHolder> implements GetStatusTask.Observer {

        private final List<Status> statuses = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.Status lastStatus;      //unsure what this is used for

        private boolean hasMorePages;
        protected boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of following data.
         */
        StatusRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new users to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newStatuses the users to add.
         */
        void addItems(List<Status> newStatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newStatuses);
            this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
        }

        /**
         * Adds a single user to the list from which the RecyclerView retrieves the users it
         * displays and notifies the RecyclerView that an item has been added.
         *
         */
        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        /**
         * Removes a user from the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         */
        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
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
        public StatusFragment.StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(StatusFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);    //change to a status row
            }

            return new StatusFragment.StatusHolder(view, viewType);
        }

        /**
         * Binds the followee at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param position the position (in the list of followees) that contains the followee to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull StatusFragment.StatusHolder statusHolder, int position) {
            if(!isLoading) {
                statusHolder.bindStatus(statuses.get(position));
            }
        }

        /**
         * Returns the current number of followees available for display.
         * @return the number of followees available for display.
         */
        @Override
        public int getItemCount() {
            return statuses.size();
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
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;      //change the view here
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more following
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetStatusTask getStatusTask = new GetStatusTask(presenter, this);
            StatusRequest request = new StatusRequest(user.getAlias(), PAGE_SIZE, isStory);
            getStatusTask.execute(request);
        }

        /**
         * A callback indicating more following data has been received. Loads the new followees
         * and removes the loading footer.
         *
         */
        @Override
        public void statusesRetrieved(StatusResponse statusResponse) {
            List<Status> statusesRetrieved = statusResponse.getStatuses();

            lastStatus = (statusesRetrieved.size() > 0) ? statusesRetrieved.get(statusesRetrieved.size() -1) : null;
            hasMorePages = statusResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            statusRecyclerViewAdapter.addItems(statusesRetrieved);
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
        private void addLoadingFooter() { //this will definitely have to change
            addItem(new Status("This is the tweet that gets replaced." +
                    " Dummy Tweet", null, new User("Dummy", "User", "")));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class StatusRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        StatusRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
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

            if (!statusRecyclerViewAdapter.isLoading && statusRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    statusRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
