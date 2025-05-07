package com.toto.ui;

import com.toto.backend.entities.Chair;
import com.toto.backend.services.ChairService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ChairViewController {

    @Autowired
    private ChairService chairService;

    @FXML private TableView<Chair> chairsTable;
    @FXML private TableColumn<Chair, Long> idColumn;
    @FXML private TableColumn<Chair, String> nameColumn;
    @FXML private TableColumn<Chair, Double> priceColumn;
    @FXML private TableColumn<Chair, String> materialColumn;
    @FXML private TableColumn<Chair, String> manufacturerColumn;
    @FXML private TableColumn<Chair, Integer> seatingCapacityColumn;
    @FXML private TableColumn<Chair, Boolean> hasArmrestsColumn;
    @FXML private TableColumn<Chair, String> chairStyleColumn;
    @FXML private TableColumn<Chair, Boolean> isAdjustableColumn;
    @FXML private TableColumn<Chair, Boolean> hasWheelsColumn;
    @FXML private TableColumn<Chair, Void> actionsColumn;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private Button addChairButton;
    @FXML private Button searchButton;
    @FXML private Button refreshButton;
    @FXML private Label totalItemsLabel;
    @FXML private Pagination pagination;

    private ObservableList<Chair> chairsList = FXCollections.observableArrayList();
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

        // Chair-specific columns
        seatingCapacityColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getSeatingCapacity()).asObject());

        hasArmrestsColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isHasArmrests()));

        chairStyleColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getChairStyle()));

        isAdjustableColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isAdjustable()));

        hasWheelsColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isHasWheels()));

        // Setup actions column with edit and delete buttons
        setupActionsColumn();
    }

    private void setupActionsColumn() {
        Callback<TableColumn<Chair, Void>, TableCell<Chair, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Chair, Void> call(final TableColumn<Chair, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox hbox = new HBox(5, editButton, deleteButton);

                    {
                        editButton.getStyleClass().add("small-button");
                        deleteButton.getStyleClass().add("small-button");

                        editButton.setOnAction(event -> {
                            Chair chair = getTableView().getItems().get(getIndex());
                            handleEditChair(chair);
                        });

                        deleteButton.setOnAction(event -> {
                            Chair chair = getTableView().getItems().get(getIndex());
                            handleDeleteChair(chair);
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
            "Has Armrests",
            "No Armrests",
            "Adjustable",
            "Non-Adjustable",
            "With Wheels",
            "Without Wheels"
        );
        filterComboBox.setValue("All");

        filterComboBox.setOnAction(e -> applyFilter());
    }

    private TableView<Chair> createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, chairsList.size());

        chairsTable.setItems(FXCollections.observableArrayList(
            chairsList.subList(fromIndex, toIndex)));

        return chairsTable;
    }

    @FXML
    public void handleAddChair() {
        // This would typically open a dialog to add a new chair
        // For now, we'll just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Chair");
        alert.setHeaderText("Add Chair Feature");
        alert.setContentText("This feature would open a dialog to add a new chair.");
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

        List<Chair> allChairs = chairService.findAll();
        ObservableList<Chair> filteredList = FXCollections.observableArrayList();

        for (Chair chair : allChairs) {
            if (chair.getName().toLowerCase().contains(searchTerm) ||
                chair.getManufacturer().toLowerCase().contains(searchTerm) ||
                chair.getMaterial().toLowerCase().contains(searchTerm) ||
                chair.getChairStyle().toLowerCase().contains(searchTerm)) {
                filteredList.add(chair);
            }
        }

        updateTableWithData(filteredList);
    }

    private void handleEditChair(Chair chair) {
        // This would typically open a dialog to edit the chair
        // For now, we'll just show an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Chair");
        alert.setHeaderText("Edit Chair Feature");
        alert.setContentText("This feature would open a dialog to edit chair: " + chair.getName());
        alert.showAndWait();
    }

    private void handleDeleteChair(Chair chair) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Chair");
        confirmDialog.setContentText("Are you sure you want to delete chair: " + chair.getName() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                chairService.deleteById(chair.getId());
                refreshData();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Delete Error");
                errorAlert.setContentText("An error occurred while deleting the chair: " + e.getMessage());
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

        List<Chair> allChairs = chairService.findAll();
        ObservableList<Chair> filteredList = FXCollections.observableArrayList();

        for (Chair chair : allChairs) {
            boolean include = switch (filter) {
                case "Has Armrests" -> chair.isHasArmrests();
                case "No Armrests" -> !chair.isHasArmrests();
                case "Adjustable" -> chair.isAdjustable();
                case "Non-Adjustable" -> !chair.isAdjustable();
                case "With Wheels" -> chair.isHasWheels();
                case "Without Wheels" -> !chair.isHasWheels();
                default -> true;
            };

            if (include) {
                filteredList.add(chair);
            }
        }

        updateTableWithData(filteredList);
    }

    private void refreshData() {
        try {
            List<Chair> chairs = chairService.findAll();
            updateTableWithData(FXCollections.observableArrayList(chairs));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data Refresh Error");
            alert.setContentText("An error occurred while refreshing data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateTableWithData(ObservableList<Chair> chairs) {
        this.chairsList = chairs;

        int pageCount = (chairs.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        createPage(0);

        totalItemsLabel.setText("Total Items: " + chairs.size());
    }
}
