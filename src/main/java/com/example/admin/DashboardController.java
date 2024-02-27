package com.example.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    @FXML
    private StackPane contentPane;
    @FXML private Button reservBtn;
    @FXML private Button tripBtn;
    @FXML private Button schedButton;
    @FXML
    private AnchorPane reservContent;
    @FXML
    private AnchorPane tripContent;

    @FXML
    private AnchorPane schedContent;

    public void initialize() {
        // Load and display the initial content
        showSchedules();
        schedButton.setOnAction(event -> {
            if (schedContent == null) {
                try {
                    schedContent = FXMLLoader.load(getClass().getResource("schedules-view.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            contentPane.getChildren().setAll(schedContent);
        });
        reservBtn.setOnAction(event -> {
            if (reservContent == null) {
                try {
                    reservContent = FXMLLoader.load(getClass().getResource("reservations-view.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            contentPane.getChildren().setAll(reservContent);
        });

        tripBtn.setOnAction(event -> {
            if (tripContent == null) {
                try {
                    tripContent = FXMLLoader.load(getClass().getResource("trips-view.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            contentPane.getChildren().setAll(tripContent);
        });
    }

    public void showSchedules(){
        if (schedContent== null) {
            try {
                schedContent = FXMLLoader.load(getClass().getResource("schedules-view.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        contentPane.getChildren().setAll(schedContent);
    }
}
