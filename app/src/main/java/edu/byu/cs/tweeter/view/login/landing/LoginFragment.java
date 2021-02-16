package edu.byu.cs.tweeter.view.login.landing;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;

public class LoginFragment extends Fragment {

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
                onLoginClicked(v);
            }
        });

        presenter = new LoginPresenter((LoginPresenter.View) this);

        username.addTextChangedListener(mTextWatcher);
        password.addTextChangedListener(mTextWatcher);
        updateButton();

        return view;
    }

    public void onLoginClicked(View view){
        try {
            LoginTask task = new LoginTask(presenter, (LoginTask.Observer) this);
            task.execute(new LoginRequest(username.getText().toString(), password.getText().toString()));
        } catch (IllegalArgumentException e) {
            Log.e("MA exception", e.getMessage(), e);
        }
    }

    public void onRegisterClicked(View view){

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

        if(s1 == "" || s2 == ""){
            loginButton.setEnabled(false);
        }
        else{
            loginButton.setEnabled(true);
        }

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
