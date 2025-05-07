package com.toto.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MainLayoutController {

    @FXML private StackPane contentArea;
    @FXML private Button dashboardButton;
    @FXML private Button chairsButton;
    @FXML private Button bedsButton;
    @FXML private Button sofasButton;
    @FXML private Button tablesButton;
    @FXML private Button miscButton;
    @FXML private Button suppliersButton;
    @FXML private Button customersButton;
    @FXML private Button ordersButton;
    @FXML private Button settingsButton;
    @FXML private Label statusLabel;
    @FXML private Label versionLabel;

    @FXML
    public void initialize() {
        // Set the initial view to dashboard
        showDashboard();
    }

    @FXML
    public void showDashboard() {
        loadView("/fxml/Dashboard.fxml");
        updateStatus("Dashboard");
    }

    @FXML
    public void showChairs() {
        loadView("/fxml/ChairView.fxml");
        updateStatus("Chairs");
    }

    @FXML
    public void showBeds() {
        loadView("/fxml/BedView.fxml");
        updateStatus("Beds");
    }

    @FXML
    public void showSofas() {
        loadView("/fxml/SofaView.fxml");
        updateStatus("Sofas");
    }

    @FXML
    public void showTables() {
        loadView("/fxml/TableView.fxml");
        updateStatus("Tables");
    }

    @FXML
    public void showMisc() {
        loadView("/fxml/MiscView.fxml");
        updateStatus("Miscellaneous Furniture");
    }

    @FXML
    public void showSuppliers() {
        loadView("/fxml/SupplierView.fxml");
        updateStatus("Suppliers");
    }

    @FXML
    public void showCustomers() {
        loadView("/fxml/CustomerView.fxml");
        updateStatus("Customers");
    }

    @FXML
    public void showOrders() {
        loadView("/fxml/OrderView.fxml");
        updateStatus("Orders");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading view: " + e.getMessage());
        }
    }

    private void updateStatus(String status) {
        statusLabel.setText("Viewing: " + status);
    }
}