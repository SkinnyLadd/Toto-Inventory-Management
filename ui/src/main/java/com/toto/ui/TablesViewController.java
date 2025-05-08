package com.toto.ui;

import com.toto.backend.entities.Tables;
import com.toto.backend.services.interfaces.ITablesService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TablesViewController {

    @Autowired
    private ITablesService tablesService;

    @FXML private TableView<Tables> tablesTable;
    @FXML private TableColumn<Tables, Long> idColumn;
    @FXML private TableColumn<Tables, String> nameColumn;
    @FXML private TableColumn<Tables, Double> priceColumn;
    @FXML private TableColumn<Tables, String> materialColumn;
    @FXML private TableColumn<Tables, String> manufacturerColumn;
    @FXML private TableColumn<Tables, String> shapeColumn;
    @FXML private TableColumn<Tables, Integer> seatingCapacityColumn;
    @FXML private TableColumn<Tables, Boolean> isExtendableColumn;
    @FXML private TableColumn<Tables, Double> lengthColumn;
    @FXML private TableColumn<Tables, Double> widthColumn;
    @FXML private TableColumn<Tables, Double> heightColumn;
    @FXML private TableColumn<Tables, Boolean> hasGlassTopColumn;
    @FXML private TableColumn<Tables, Void> actionsColumn;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private Button addTableButton;
    @FXML private Button searchButton;
    @FXML private Button refreshButton;
    @FXML private Label totalItemsLabel;
    @FXML private Pagination pagination;

    private ObservableList<Tables> tablesList = FXCollections.observableArrayList();
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

        // Tables-specific columns
        shapeColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getShape()));

        seatingCapacityColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getSeatingCapacity()).asObject());

        isExtendableColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isExtendable()));

        lengthColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getLength()).asObject());

        widthColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getWidth()).asObject());

        heightColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getHeight()).asObject());

        hasGlassTopColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isHasGlassTop()));

        // Setup actions column with edit and delete buttons
        setupActionsColumn();
    }

    private void setupActionsColumn() {
        Callback<TableColumn<Tables, Void>, TableCell<Tables, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Tables, Void> call(final TableColumn<Tables, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox hbox = new HBox(5, editButton, deleteButton);

                    {
                        editButton.getStyleClass().add("small-button");
                        deleteButton.getStyleClass().add("small-button");

                        editButton.setOnAction(event -> {
                            Tables table = getTableView().getItems().get(getIndex());
                            handleEditTable(table);
                        });

                        deleteButton.setOnAction(event -> {
                            Tables table = getTableView().getItems().get(getIndex());
                            handleDeleteTable(table);
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
            "Round",
            "Rectangular",
            "Square",
            "Extendable",
            "Non-Extendable",
            "Glass Top",
            "No Glass Top"
        );
        filterComboBox.setValue("All");

        filterComboBox.setOnAction(e -> applyFilter());
    }

    private TableView<Tables> createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, tablesList.size());

        tablesTable.setItems(FXCollections.observableArrayList(
            tablesList.subList(fromIndex, toIndex)));

        return tablesTable;
    }

    @FXML
    public void handleAddTable() {
        try {
            // Load the dialog FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/toto/ui/fxml/AddTableDialog.fxml"));
            DialogPane dialogPane = loader.load();

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add New Table");

            // Get references to the form fields
            TextField nameField = (TextField) dialogPane.lookup("#nameField");
            TextField priceField = (TextField) dialogPane.lookup("#priceField");
            TextField materialField = (TextField) dialogPane.lookup("#materialField");
            TextField manufacturerField = (TextField) dialogPane.lookup("#manufacturerField");
            ComboBox<String> woodTypeComboBox = (ComboBox<String>) dialogPane.lookup("#woodTypeComboBox");
            ComboBox<String> shapeComboBox = (ComboBox<String>) dialogPane.lookup("#shapeComboBox");
            Spinner<Integer> seatingCapacitySpinner = (Spinner<Integer>) dialogPane.lookup("#seatingCapacitySpinner");
            CheckBox isExtendableCheckBox = (CheckBox) dialogPane.lookup("#isExtendableCheckBox");
            TextField lengthField = (TextField) dialogPane.lookup("#lengthField");
            TextField widthField = (TextField) dialogPane.lookup("#widthField");
            TextField heightField = (TextField) dialogPane.lookup("#heightField");
            CheckBox hasGlassTopCheckBox = (CheckBox) dialogPane.lookup("#hasGlassTopCheckBox");

            // Configure the spinner
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 4);
            seatingCapacitySpinner.setValueFactory(valueFactory);

            // Populate the wood type combo box with enum values
            woodTypeComboBox.getItems().addAll(
                    "SHEESHAM",
                    "DEODAR",
                    "MANGO",
                    "ACACIA",
                    "MULBERRY",
                    "ROSEWOOD",
                    "WALNUT",
                    "TEAK",
                    "MDF",
                    "LAMINATE",
                    "OAK",
                    "OTHER"
            );
            woodTypeComboBox.setValue("OAK");

            // Populate the shape combo box
            shapeComboBox.getItems().addAll("Round", "Rectangular", "Square", "Oval");
            shapeComboBox.setValue("Rectangular");

            // Show the dialog and wait for the user response
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Create a new Tables object with the form data
                Tables newTable = new Tables();
                newTable.setName(nameField.getText());
                newTable.setPrice(Double.parseDouble(priceField.getText()));
                newTable.setMaterial(materialField.getText());
                newTable.setManufacturer(manufacturerField.getText());

                // Set the wood type enum
                newTable.setWoodType(com.toto.backend.entities.enums.WoodType.valueOf(woodTypeComboBox.getValue()));

                // Set table-specific properties
                newTable.setShape(shapeComboBox.getValue());
                newTable.setSeatingCapacity(seatingCapacitySpinner.getValue());
                newTable.setExtendable(isExtendableCheckBox.isSelected());
                newTable.setLength(Double.parseDouble(lengthField.getText()));
                newTable.setWidth(Double.parseDouble(widthField.getText()));
                newTable.setHeight(Double.parseDouble(heightField.getText()));
                newTable.setHasGlassTop(hasGlassTopCheckBox.isSelected());

                // Save the new table to the database
                tablesService.save(newTable);

                // Refresh the table to show the new table
                refreshData();

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Table Added");
                alert.setContentText("The table has been added successfully.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Add Table Error");
            alert.setContentText("An error occurred while adding the table: " + e.getMessage());
            alert.showAndWait();
        }
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

        List<Tables> allTables = tablesService.findAll();
        ObservableList<Tables> filteredList = FXCollections.observableArrayList();

        for (Tables table : allTables) {
            if (table.getName().toLowerCase().contains(searchTerm) ||
                table.getManufacturer().toLowerCase().contains(searchTerm) ||
                table.getMaterial().toLowerCase().contains(searchTerm) ||
                table.getShape().toLowerCase().contains(searchTerm)) {
                filteredList.add(table);
            }
        }

        updateTableWithData(filteredList);
    }

    private void handleEditTable(Tables table) {
        // This will be implemented later
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Table");
        alert.setHeaderText("Edit Table Feature");
        alert.setContentText("This feature would open a dialog to edit table: " + table.getName());
        alert.showAndWait();
    }

    private void handleDeleteTable(Tables table) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Table");
        confirmDialog.setContentText("Are you sure you want to delete table: " + table.getName() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                tablesService.deleteById(table.getId());
                refreshData();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Delete Error");
                errorAlert.setContentText("An error occurred while deleting the table: " + e.getMessage());
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

        List<Tables> allTables = tablesService.findAll();
        ObservableList<Tables> filteredList = FXCollections.observableArrayList();

        for (Tables table : allTables) {
            boolean include = switch (filter) {
                case "Round", "Rectangular", "Square" -> table.getShape().equalsIgnoreCase(filter);
                case "Extendable" -> table.isExtendable();
                case "Non-Extendable" -> !table.isExtendable();
                case "Glass Top" -> table.isHasGlassTop();
                case "No Glass Top" -> !table.isHasGlassTop();
                default -> true;
            };

            if (include) {
                filteredList.add(table);
            }
        }

        updateTableWithData(filteredList);
    }

    private void refreshData() {
        try {
            List<Tables> tables = tablesService.findAll();
            updateTableWithData(FXCollections.observableArrayList(tables));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data Refresh Error");
            alert.setContentText("An error occurred while refreshing data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateTableWithData(ObservableList<Tables> tables) {
        this.tablesList = tables;

        int pageCount = (tables.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        createPage(0);

        totalItemsLabel.setText("Total Items: " + tables.size());
    }
}
