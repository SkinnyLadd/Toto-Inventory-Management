package com.toto.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.springframework.context.ApplicationContext;
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

    private final ApplicationContext applicationContext;  // <-- add this

    // Constructor injection of the Spring context
    public MainLayoutController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @FXML
    public void initialize() {
        // Set the initial view to dashboard
        showDashboard();
    }

    @FXML
    public void showDashboard() {
        loadView("/com/toto/ui/fxml/Dashboard.fxml");
        updateStatus("Dashboard");
    }

    @FXML
    public void showChairs() {
        loadView("/com/toto/ui/fxml/ChairView.fxml");
        updateStatus("Chairs");
    }

    @FXML
    public void showBeds() {
        loadView("/com/toto/ui/fxml/BedView.fxml");
        updateStatus("Beds");
    }

    @FXML
    public void showSofas() {
        loadView("/com/toto/ui/fxml/SofaView.fxml");
        updateStatus("Sofas");
    }

    @FXML
    public void showTables() {
        loadView("/com/toto/ui/fxml/TableView.fxml");
        updateStatus("Tables");
    }

    @FXML
    public void showMisc() {
        loadView("/com/toto/ui/fxml/MiscView.fxml");
        updateStatus("Miscellaneous Furniture");
    }

    @FXML
    public void showSuppliers() {
        loadView("/com/toto/ui/fxml/SupplierView.fxml");
        updateStatus("Suppliers");
    }

    @FXML
    public void showCustomers() {
        loadView("/com/toto/ui/fxml/CustomerView.fxml");
        updateStatus("Customers");
    }

    @FXML
    public void showOrders() {
        loadView("/com/toto/ui/fxml/OrderView.fxml");
        updateStatus("Orders");
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setControllerFactory(applicationContext::getBean);
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
