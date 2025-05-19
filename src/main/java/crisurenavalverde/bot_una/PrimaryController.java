package crisurenavalverde.bot_una;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.ZoneId;
import java.util.Date;

public class PrimaryController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField roleField;

    @FXML
    private DatePicker expiredDatePicker;

    @FXML
    private Label statusLabel;

    private DAOUtil dao;
    
    @FXML
    private void handleViewUsers() throws IOException {
    App.setRoot("UserList");
    }


    @FXML
    private void initialize() {
        dao = new DAOUtil();
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleField.getText();
        Date expiredDate = expiredDatePicker.getValue() != null
                ? Date.from(expiredDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant())
                : null;

        UsersBot user = new UsersBot();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setExpiredDate(expiredDate);

        try {
            dao.addUser(user);
            statusLabel.setText("Usuario registrado exitosamente.");
            usernameField.clear();
            passwordField.clear();
            roleField.clear();
            expiredDatePicker.setValue(null);
        } catch (Exception e) {
            statusLabel.setText("Error al registrar usuario.");
            e.printStackTrace();
        }
    }
}
