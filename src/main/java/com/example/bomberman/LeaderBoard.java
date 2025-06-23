package com.example.bomberman;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LeaderBoard implements Initializable {
    @FXML
    Button back;
    @FXML
    TabPane tabPane;
    @FXML
    TableView<UserScore> tableView;
    @FXML
    TableColumn<UserScore, String> usernameCol;
    @FXML
    TableColumn<UserScore, String> scoreCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameCol.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        scoreCol.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asString());

        tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            loadLeaderboard(newVal.intValue() + 1);
        });
        loadLeaderboard(1);
    }

    private void loadLeaderboard(int level) {
        String column = "score" + level;
        ObservableList<UserScore> scores = FXCollections.observableArrayList();

        String query = "SELECT username, " + column + " AS score FROM Users ORDER BY " + column + " DESC LIMIT 10";

        try {
            Statement statement = HelloApplication.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                scores.add(new UserScore(resultSet.getString("username"), resultSet.getInt("score")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(scores);
    }


    @FXML
    private void switchToStartScreen(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


}
