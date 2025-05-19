package crisurenavalverde.bot_una;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;

public class UserListController {

    @FXML
    private TableView<UsersBot> userTable;

    @FXML
    private TableColumn<UsersBot, String> usernameColumn;

    @FXML
    private TableColumn<UsersBot, String> roleColumn;

    @FXML
    private TableColumn<UsersBot, String> expiredColumn;

    private DAOUtil dao;

    @FXML
    private void initialize() {
        dao = new DAOUtil();

        usernameColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getUsername()));
        roleColumn.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getRole()));
        expiredColumn.setCellValueFactory(data -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = data.getValue().getExpiredDate() != null ? sdf.format(data.getValue().getExpiredDate()) : "";
            return new ReadOnlyStringWrapper(dateStr);
        });

        loadUsers();
    }

    private void loadUsers() {
        List<UsersBot> users = dao.getUsers();
        ObservableList<UsersBot> observableUsers = FXCollections.observableArrayList(users);
        userTable.setItems(observableUsers);
    }

    @FXML
    private void handleBack() throws IOException {
        App.setRoot("primary");
    }
}
