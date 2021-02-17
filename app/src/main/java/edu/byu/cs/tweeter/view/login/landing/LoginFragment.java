package edu.byu.cs.tweeter.view.login.landing;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import android.widget.Toast;
import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.LoginActivity;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.login.LandingActivity;
import edu.byu.cs.tweeter.view.main.MainActivity;

public class LoginFragment extends Fragment implements LoginPresenter.View, LoginTask.Observer {

    private static final String LOG_TAG = "LoginActivity";

    private Toast loginInToast;

    private EditText username;
    private EditText password;

    private Button loginButton;

    private LoginPresenter presenter;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_OK = 0;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.login_username);
        password = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginInToast = Toast.makeText(getContext(), "Logging In", Toast.LENGTH_LONG);
                loginInToast.show();
                onLoginClicked(v);
            }
        });

        presenter = new LoginPresenter(this);

        username.addTextChangedListener(mTextWatcher);
        password.addTextChangedListener(mTextWatcher);
        updateButton();

        return view;
    }

    public void onLoginClicked(View view){
        try {
            LoginTask task = new LoginTask(presenter, this);
            task.execute(new LoginRequest(username.getText().toString(), password.getText().toString()));
        } catch (IllegalArgumentException e) {
            Log.e("MA exception", e.getMessage(), e);
        }
    }


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            updateButton();
        }
    };

    private void updateButton() {
        String s1 = username.getText().toString();
        String s2 = password.getText().toString();

        if(s1.equals("") || s2.equals("")){
            loginButton.setEnabled(false);
        }
        else{
            loginButton.setEnabled(true);
        }

    }

    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(this.getContext(), MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, loginResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, loginResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(this.getContext(), "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this.getContext(), "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    /*public void onProfileClicked(View view){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
    }*/
}
