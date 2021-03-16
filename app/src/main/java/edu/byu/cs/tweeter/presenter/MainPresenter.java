package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.MainService;
import edu.byu.cs.tweeter.model.service.MainServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.PostStatusRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class MainPresenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }


    public MainPresenter(View view) {
        this.view = view;
    }


    public Response postStatus(PostStatusRequest request) throws IOException, TweeterRemoteException {
        MainServiceProxy mainServiceProxy = getPostStatusServiceProxy();
        return mainServiceProxy.postStatus(request);
    }

    MainServiceProxy getPostStatusServiceProxy() {
        return new MainServiceProxy();
    }

}
