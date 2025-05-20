package crisurenavalverde.bot_una;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.ZoneId;
import java.util.Date;

public class PrimaryController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField roleField;
    @FXML private DatePicker expiredDatePicker;
    @FXML private Label statusLabel;
    @FXML private Button registerButton;

    private DAOUtil dao;


    public static UsersBot editingUser = null;

    @FXML
    private void initialize() {
        dao = new DAOUtil();

       
        if (editingUser != null) {
            usernameField.setText(editingUser.getUsername());
            passwordField.setText(editingUser.getPassword());
            roleField.setText(editingUser.getRole());
            if (editingUser.getExpiredDate() != null) {
                expiredDatePicker.setValue(
                    editingUser.getExpiredDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                );
            }
            registerButton.setText("Actualizar");
        }
    }

    @FXML
    private void handleViewUsers() throws Exception {
        
        editingUser = null;
        App.setRoot("UserList");
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
            if (editingUser != null) {
                
                user.setId(editingUser.getId());
                dao.updateUser(user);
                statusLabel.setText("Usuario actualizado correctamente.");
                registerButton.setText("Registrar");
                editingUser = null;
            } else {
               
                dao.addUser(user);
                statusLabel.setText("Usuario registrado exitosamente.");
            }

          
            usernameField.clear();
            passwordField.clear();
            roleField.clear();
            expiredDatePicker.setValue(null);

        } catch (Exception e) {
            statusLabel.setText("Error al procesar usuario.");
            e.printStackTrace();
        }
    }
}

