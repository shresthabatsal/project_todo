package controller;

import model.LoginModel;

public class LoginController {

    private LoginModel userModel;

    public LoginController() {
        userModel = new LoginModel();
    }

    public boolean login(String email, String password) {
        return userModel.authenticate(email, password);
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
