package com.example.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Repeatable;
import java.net.Socket;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private TextField userField;
    @FXML
    private TextField passField;

    @FXML
    private Button loginBtn;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String username = userField.getText();
        String password = passField.getText();
        try (
                Socket socket=new Socket("127.0.0.1",5000);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Send the login verification request to the server
            out.writeObject("VERIFY_LOGIN");
            out.writeObject(username);
            out.writeObject(password);

            // Wait for the response from the server
            boolean loginSuccessful = (boolean) in.readObject();
            System.out.println(loginSuccessful);
            if (loginSuccessful) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login successfull!", ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                alert.getDialogPane().getStyleClass().add("alert");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password", ButtonType.OK);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
                alert.getDialogPane().getStyleClass().add("alert");
                alert.showAndWait();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
