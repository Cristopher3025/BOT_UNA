package crisurenavalverde.bot_una;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.ReadOnlyStringWrapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserListController {

    @FXML private TableView<UsersBot> userTable;
    @FXML private TableColumn<UsersBot, String> idColumn;
    @FXML private TableColumn<UsersBot, String> usernameColumn;
    @FXML private TableColumn<UsersBot, String> roleColumn;
    @FXML private TableColumn<UsersBot, String> expiredColumn;

    private DAOUtil dao;

    @FXML
    public void initialize() {
        dao = new DAOUtil();

        idColumn.setCellValueFactory(data -> 
            new ReadOnlyStringWrapper(data.getValue().getId().toString()));
        usernameColumn.setCellValueFactory(data -> 
            new ReadOnlyStringWrapper(data.getValue().getUsername()));
        roleColumn.setCellValueFactory(data -> 
            new ReadOnlyStringWrapper(data.getValue().getRole()));
        expiredColumn.setCellValueFactory(data -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = data.getValue().getExpiredDate() != null
                ? sdf.format(data.getValue().getExpiredDate())
                : "";
            return new ReadOnlyStringWrapper(dateStr);
        });

        loadUsers();
    }

    private void loadUsers() {
        List<UsersBot> users = dao.getUsers();
        ObservableList<UsersBot> obs = FXCollections.observableArrayList(users);
        userTable.setItems(obs);
    }

    @FXML
    private void handleDelete() {
        UsersBot selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dao.deleteUser(selected.getId());
            loadUsers();
        }
    }

    @FXML
    private void handleEdit() throws IOException {
        UsersBot selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
         
            PrimaryController.editingUser = selected;
            App.setRoot("primary");
        }
    }

    @FXML
    private void handleBack() throws IOException {
        PrimaryController.editingUser = null;
        App.setRoot("primary");
    }
}

