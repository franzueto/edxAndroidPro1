package edu.galileo.mvp;

import android.text.TextUtils;

public class LoginPresenterImpl implements LoginPresenter, LoginModel.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void validateCredentials(String username, String password) {
        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            loginView.setPasswordError(R.string.error_invalid_password);
            return;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            loginView.setUsernameError(R.string.error_field_required);
            return;
        } else if (!isEmailValid(username)) {
            loginView.setUsernameError(R.string.error_invalid_email);
            return;
        }

        loginView.showProgress(true);
        loginModel.login(username, password, this);
    }

    @Override
    public void onCanceled() {
        loginView.showProgress(false);
    }

    @Override
    public void onPasswordError() {
        loginView.showProgress(false);
        loginView.setPasswordError(R.string.error_incorrect_password);
    }

    @Override
    public void onSuccess() {
        loginView.showProgress(false);
        loginView.successAction();
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
