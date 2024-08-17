package controller;

import model.SignupModel;

import java.sql.Connection;
import java.sql.SQLException;

public class SignupController {
    private SignupModel userModel;

    public SignupController(Connection connection) {
        this.userModel = new SignupModel(connection);
    }

    public boolean handleSignup(String fullName, String email, String dob, String gender, String password, String confirmPassword) throws SQLException {
        if (password.equals(confirmPassword)) {
            return userModel.insertUser(fullName, email, dob, gender, password);
        }
        return false;
    }
}
