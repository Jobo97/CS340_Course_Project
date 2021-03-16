package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.LogoutServiceProxy;

import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import com.example.shared.src.main.java.edu.byu.cs.tweeter.model.service.response.Response;

public class LogoutPresenter {

    private final LogoutPresenter.View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public LogoutPresenter(LogoutPresenter.View view) {
        this.view = view;
    }

    public Response logout(LogoutRequest request) throws IOException, TweeterRemoteException {
        LogoutServiceProxy logoutServiceProxy = getLogoutService();
        return logoutServiceProxy.logout(request);
    }

    public LogoutServiceProxy getLogoutService() { return new LogoutServiceProxy(); }
}
