package com.toto.ui;

import com.toto.backend.entities.Bed;
import com.toto.backend.services.BedService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BedViewController {

    @Autowired
    private BedService bedService;

    @FXML private TableView<Bed> bedsTable;
    @FXML private TableColumn<Bed, Long> idColumn;
    @FXML private TableColumn<Bed, String> nameColumn;
    @FXML private TableColumn<Bed, Double> priceColumn;
    @FXML private TableColumn<Bed, String> materialColumn;
    @FXML private TableColumn<Bed, String> manufacturerColumn;
    @FXML private TableColumn<Bed, String> sizeColumn;
    @FXML private TableColumn<Bed, Boolean> hasHeadboardColumn;
    @FXML private TableColumn<Bed, Boolean> hasFootboardColumn;
    @FXML private TableColumn<Bed, Boolean> hasStorageDrawersColumn;
    @FXML private TableColumn<Bed, String> mattressTypeColumn;
    @FXML private TableColumn<Bed, Boolean> isAdjustableColumn;
    @FXML private TableColumn<Bed, Void> actionsColumn;
    
    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private Button addBedButton;
    @FXML private Button searchButton;
    @FXML private Button refreshButton;
    @FXML private Label totalItemsLabel;
    @FXML private Pagination pagination;

    private ObservableList<Bed> bedsList = FXCollections.observableArrayList();
    private static final int ITEMS_PER_PAGE = 10;

    @FXML
    public void initialize() {
        setupTableColumns();
        setupFilterComboBox();
        refreshData();
        
        // Setup pagination
        pagination.setPageFactory(this::createPage);
    }
    
    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        
        // Bed-specific columns
        sizeColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getSize()));
        
        hasHeadboardColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isHasHeadboard()));
        
        hasFootboardColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isHasFootboard()));
        
        hasStorageDrawersColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isHasStorageDrawers()));
        
        mattressTypeColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getMattressType()));
        
        isAdjustableColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isAdjustable()));
        
        // Setup actions column with edit and delete buttons
        setupActionsColumn();
    }
    
    private void setupActionsColumn() {
        Callback<TableColumn<Bed, Void>, TableCell<Bed, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Bed, Void> call(final TableColumn<Bed, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox hbox = new HBox(5, editButton, deleteButton);
                    
                    {
                        editButton.getStyleClass().add("small-button");
                        deleteButton.getStyleClass().add("small-button");
                        
                        editButton.setOnAction(event -> {
                            Bed bed = getTableView().getItems().get(getIndex());
                            handleEditBed(bed);
                        });
                        
                        deleteButton.setOnAction(event -> {
                            Bed bed = getTableView().getItems().get(getIndex());
                            handleDeleteBed(bed);
                        });
                    }
                    
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(hbox);
                        }
                    }
                };
            }
        };
        
        actionsColumn.setCellFactory(cellFactory);
    }
    
    private void setupFilterComboBox() {
        filterComboBox.getItems().addAll(
            "All",
            "Single",
            "Double",
            "Queen",
            "King",
            "Has Headboard",
            "Has Footboard",
            "Has Storage",
            "Adjustable"
        );
        filterComboBox.setValue("All");
        
        filterComboBox.setOnAction(e -> applyFilter());
    }
    
    private TableView<Bed> createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, bedsList.size());
        
        bedsTable.setItems(FXCollections.observableArrayList(
            bedsList.subList(fromIndex, toIndex)));
        
        return bedsTable;
    }
    
    @FXML
    public void handleAddBed() {
        // This would typically open a dialog to add a new bed
        // For now, we'll just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Bed");
        alert.setHeaderText("Add Bed Feature");
        alert.setContentText("This feature would open a dialog to add a new bed.");
        alert.showAndWait();
    }
    
    @FXML
    public void handleRefresh() {
        refreshData();
    }
    
    @FXML
    public void handleSearch() {
        String searchTerm = searchField.getText().toLowerCase().trim();
        if (searchTerm.isEmpty()) {
            refreshData();
            return;
        }
        
        List<Bed> allBeds = bedService.findAll();
        ObservableList<Bed> filteredList = FXCollections.observableArrayList();
        
        for (Bed bed : allBeds) {
            if (bed.getName().toLowerCase().contains(searchTerm) ||
                bed.getManufacturer().toLowerCase().contains(searchTerm) ||
                bed.getMaterial().toLowerCase().contains(searchTerm) ||
                bed.getSize().toLowerCase().contains(searchTerm) ||
                bed.getMattressType().toLowerCase().contains(searchTerm)) {
                filteredList.add(bed);
            }
        }
        
        updateTableWithData(filteredList);
    }
    
    private void handleEditBed(Bed bed) {
        // This would typically open a dialog to edit the bed
        // For now, we'll just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Bed");
        alert.setHeaderText("Edit Bed Feature");
        alert.setContentText("This feature would open a dialog to edit bed: " + bed.getName());
        alert.showAndWait();
    }
    
    private void handleDeleteBed(Bed bed) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Bed");
        confirmDialog.setContentText("Are you sure you want to delete bed: " + bed.getName() + "?");
        
        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                bedService.deleteById(bed.getId());
                refreshData();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Delete Error");
                errorAlert.setContentText("An error occurred while deleting the bed: " + e.getMessage());
                errorAlert.showAndWait();
            }
        }
    }
    
    private void applyFilter() {
        String filter = filterComboBox.getValue();
        if (filter == null || filter.equals("All")) {
            refreshData();
            return;
        }
        
        List<Bed> allBeds = bedService.findAll();
        ObservableList<Bed> filteredList = FXCollections.observableArrayList();
        
        for (Bed bed : allBeds) {
            boolean include = switch (filter) {
                case "Single", "Double", "Queen", "King" -> bed.getSize().equalsIgnoreCase(filter);
                case "Has Headboard" -> bed.isHasHeadboard();
                case "Has Footboard" -> bed.isHasFootboard();
                case "Has Storage" -> bed.isHasStorageDrawers();
                case "Adjustable" -> bed.isAdjustable();
                default -> true;
            };
            
            if (include) {
                filteredList.add(bed);
            }
        }
        
        updateTableWithData(filteredList);
    }
    
    private void refreshData() {
        try {
            List<Bed> beds = bedService.findAll();
            updateTableWithData(FXCollections.observableArrayList(beds));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data Refresh Error");
            alert.setContentText("An error occurred while refreshing data: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    private void updateTableWithData(ObservableList<Bed> beds) {
        this.bedsList = beds;
        
        int pageCount = (beds.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        createPage(0);
        
        totalItemsLabel.setText("Total Items: " + beds.size());
    }
}