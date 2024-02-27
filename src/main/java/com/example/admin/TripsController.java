package com.example.admin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class TripsController {
    @FXML
    Button viewTripsButton;

    public void AddTripModal(ActionEvent actionEvent) {
    }

    public void onViewTripsBtn(ActionEvent actionEvent) {
        try (
                Socket socket=new Socket("127.0.0.1",5000);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
        ) {
            // Send the login verification request to the server
            out.writeObject("VIEW_TRIPS");

           //get response
            List<Trips2> trips= (List<Trips2>) in.readObject();
            if (trips != null) {
                // Add schedules to the table view
                updateTripTable(trips);
            } else {
                // Show error message
                System.out.println("cannot find data");
            }
    } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    private TableView<Trips2> tripsTable;

    @FXML
    private TableColumn<Trips2,String> sourceColumn;
    @FXML
    private TableColumn<Trips2,String> destinationColumn;
    @FXML
    private TableColumn<Trips2,String> timingColumn;
    @FXML
    private TableColumn<Trips2,Double> priceColumn;


    public void updateTripTable(List<Trips2> trips) {
        ObservableList<Trips2> data = FXCollections.observableArrayList(trips);
        tripsTable.setItems(data);
        sourceColumn.setCellValueFactory(new PropertyValueFactory<>("source"));
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        timingColumn.setCellValueFactory(new PropertyValueFactory<>("timing"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    @FXML
    private Button deleteTripButton;

    @FXML
    private void initialize() {
        deleteTripButton.setDisable(true); // Disable delete button initially

        tripsTable.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                deleteTripButton.setDisable(true); // Disable delete button when no row is selected
            } else {
                deleteTripButton.setDisable(false); // Enable delete button when a row is selected
            }
        });
    }

    @FXML
    private void deleteSelectedRow() {
        Trips2 selectedTrip = tripsTable.getSelectionModel().getSelectedItem();
        if (selectedTrip != null) {
            try (
                    Socket socket=new Socket("127.0.0.1",5000);
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
            ) {
                // Send the login verification request to the server
                out.writeObject("DELETE_TRIP");
                out.writeObject(selectedTrip);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Delete selected trip from table
            tripsTable.getItems().remove(selectedTrip);
            System.out.println(selectedTrip);
        }
    }


}
