package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.PostStatusService;
import edu.byu.cs.tweeter.model.service.StatusService;
import edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.service.request.StatusRequest;
import edu.byu.cs.tweeter.model.service.response.Response;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public class PostStatusPresenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }


    public PostStatusPresenter(View view) {
        this.view = view;
    }


    public Response postStatus(PostStatusRequest request) throws IOException {
        PostStatusService postStatusService = getPostStatusService();
        return postStatusService.postStatus(request);
    }

    PostStatusService getPostStatusService() {
        return new PostStatusService();
    }

}
