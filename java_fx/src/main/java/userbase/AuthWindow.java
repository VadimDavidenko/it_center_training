package userbase;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by v.davidenko on 03.02.2016.
 */
public class AuthWindow extends Application {
    @FXML
    public TableView tableUsers;
    @FXML
    public TableColumn<User, Integer> idColumn;
    @FXML
    public TableColumn<User, String> nameColumn;
    @FXML
    public TableColumn<User, String> passwordColumn;
    @FXML
    public TableColumn<User, Date> dateColumn;
    @FXML
    public PasswordField password;
    @FXML
    public TextField userName;
    @FXML
    public Label message;

    final static String LOGIN_ERR_MSG = "Wrong name or password!";
    final static String EMPTY_FIELDS_MSG = "Please, fill in all fields!";

    @FXML
    private void initTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<User, Date>("date"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/userbase/login_form.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("Authentication form");
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onClickLogin() {
        if (userName.getText().isEmpty() || password.getText().isEmpty()) {
            message.setText(EMPTY_FIELDS_MSG);
            return;
        }
        message.setText("");
        UserJDBCManager userMan = new UserJDBCManager();
        User loggedUser = null;
        try {
            loggedUser = userMan.readByNamePass(userName.getText().trim(), password.getText().trim());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (loggedUser == null || loggedUser.getId() == null) {
            message.setText(LOGIN_ERR_MSG);
            return;
        }
        List<User> users = new ArrayList<User>();
        try {
            users = userMan.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (users != null && !users.isEmpty()) {
            ObservableList<User> usersList = FXCollections.observableArrayList();
            for (User user : users) {
                usersList.add(user);
            }
            initTableView();
            tableUsers.setItems(usersList);
        }
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}
