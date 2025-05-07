package com.toto.ui;

import com.toto.backend.entities.Furniture;
import com.toto.backend.services.FurnitureService;
import com.toto.backend.services.ChairService;
import com.toto.backend.services.BedService;
import com.toto.backend.services.SofaService;
import com.toto.backend.services.TablesService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DashboardController {

    @Autowired
    private FurnitureService furnitureService;
    
    @Autowired
    private ChairService chairService;
    
    @Autowired
    private BedService bedService;
    
    @Autowired
    private SofaService sofaService;
    
    @Autowired
    private TablesService tablesService;

    @FXML private Label totalFurnitureLabel;
    @FXML private Label chairsCountLabel;
    @FXML private Label bedsCountLabel;
    @FXML private Label sofasCountLabel;
    
    @FXML private TableView<Furniture> recentItemsTable;
    @FXML private TableColumn<Furniture, Long> idColumn;
    @FXML private TableColumn<Furniture, String> nameColumn;
    @FXML private TableColumn<Furniture, String> typeColumn;
    @FXML private TableColumn<Furniture, Double> priceColumn;
    @FXML private TableColumn<Furniture, String> materialColumn;
    @FXML private TableColumn<Furniture, String> manufacturerColumn;
    
    @FXML private Button addFurnitureButton;
    @FXML private Button generateReportButton;
    @FXML private Button refreshButton;

    @FXML
    public void initialize() {
        setupTableColumns();
        refreshData();
    }
    
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(cellData -> {
            Furniture furniture = cellData.getValue();
            String type = furniture.getClass().getSimpleName();
            return new SimpleStringProperty(type);
        });
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    }
    
    @FXML
    public void handleAddFurniture() {
        // This would typically open a dialog to add new furniture
        // For now, we'll just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Furniture");
        alert.setHeaderText("Add Furniture Feature");
        alert.setContentText("This feature would open a dialog to add new furniture.");
        alert.showAndWait();
    }
    
    @FXML
    public void handleGenerateReport() {
        // This would typically generate a report
        // For now, we'll just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Generate Report");
        alert.setHeaderText("Generate Report Feature");
        alert.setContentText("This feature would generate a report of the furniture inventory.");
        alert.showAndWait();
    }
    
    @FXML
    public void handleRefresh() {
        refreshData();
    }
    
    private void refreshData() {
        try {
            // Update counts
            List<Furniture> allFurniture = furnitureService.findAll();
            totalFurnitureLabel.setText(String.valueOf(allFurniture.size()));
            
            chairsCountLabel.setText(String.valueOf(chairService.findAll().size()));
            bedsCountLabel.setText(String.valueOf(bedService.findAll().size()));
            sofasCountLabel.setText(String.valueOf(sofaService.findAll().size()));
            
            // Get recent items (limited to 10)
            List<Furniture> recentItems = allFurniture.stream()
                    .limit(10)
                    .collect(Collectors.toList());
            
            ObservableList<Furniture> items = FXCollections.observableArrayList(recentItems);
            recentItemsTable.setItems(items);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data Refresh Error");
            alert.setContentText("An error occurred while refreshing data: " + e.getMessage());
            alert.showAndWait();
        }
    }
}