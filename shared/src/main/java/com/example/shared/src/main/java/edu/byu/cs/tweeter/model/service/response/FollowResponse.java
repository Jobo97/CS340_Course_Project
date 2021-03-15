package com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response;

import java.util.List;
import java.util.Objects;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.domain.User;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.FollowRequest;

/**
 * A paged response for a {@link FollowRequest}.
 */
public class FollowResponse extends PagedResponse {

    private List<User> follows;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowResponse(String message) {
        super(false, message, false);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param follows the follows to be included in the result.
     * @param hasMorePages an indicator of whether more data is available for the request.
     */
    public FollowResponse(List<User> follows, boolean hasMorePages) {
        super(true, hasMorePages);
        this.follows = follows;
    }

    /**
     * Returns the follows for the corresponding request.
     *
     * @return the follows.
     */
    public List<User> getFollows() {
        return follows;
    }

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowResponse that = (FollowResponse) param;

        return (Objects.equals(follows, that.follows) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(follows);
    }
}
