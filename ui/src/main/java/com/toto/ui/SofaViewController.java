package com.toto.ui;

import com.toto.backend.entities.Sofa;
import com.toto.backend.services.interfaces.ISofaService;
import javafx.beans.property.SimpleBooleanProperty;
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
public class SofaViewController {

    @Autowired
    private ISofaService sofaService;

    @FXML private TableView<Sofa> sofasTable;
    @FXML private TableColumn<Sofa, Long> idColumn;
    @FXML private TableColumn<Sofa, String> nameColumn;
    @FXML private TableColumn<Sofa, Double> priceColumn;
    @FXML private TableColumn<Sofa, String> materialColumn;
    @FXML private TableColumn<Sofa, String> manufacturerColumn;
    @FXML private TableColumn<Sofa, Integer> seatingCapacityColumn;
    @FXML private TableColumn<Sofa, Boolean> isConvertibleColumn;
    @FXML private TableColumn<Sofa, String> upholsteryTypeColumn;
    @FXML private TableColumn<Sofa, Integer> numberOfCushionsColumn;
    @FXML private TableColumn<Sofa, Boolean> hasReclinersColumn;
    @FXML private TableColumn<Sofa, Void> actionsColumn;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private Button addSofaButton;
    @FXML private Button searchButton;
    @FXML private Button refreshButton;
    @FXML private Label totalItemsLabel;
    @FXML private Pagination pagination;

    private ObservableList<Sofa> sofasList = FXCollections.observableArrayList();
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

        // Sofa-specific columns
        seatingCapacityColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getSeatingCapacity()).asObject());

        isConvertibleColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isConvertible()));

        upholsteryTypeColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getUpholsteryType()));

        numberOfCushionsColumn.setCellValueFactory(cellData -> 
            new SimpleIntegerProperty(cellData.getValue().getNumberOfCushions()).asObject());

        hasReclinersColumn.setCellValueFactory(cellData -> 
            new SimpleBooleanProperty(cellData.getValue().isHasRecliners()));

        // Setup actions column with edit and delete buttons
        setupActionsColumn();
    }

    private void setupActionsColumn() {
        Callback<TableColumn<Sofa, Void>, TableCell<Sofa, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Sofa, Void> call(final TableColumn<Sofa, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox hbox = new HBox(5, editButton, deleteButton);

                    {
                        editButton.getStyleClass().add("small-button");
                        deleteButton.getStyleClass().add("small-button");

                        editButton.setOnAction(event -> {
                            Sofa sofa = getTableView().getItems().get(getIndex());
                            handleEditSofa(sofa);
                        });

                        deleteButton.setOnAction(event -> {
                            Sofa sofa = getTableView().getItems().get(getIndex());
                            handleDeleteSofa(sofa);
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
            "Convertible",
            "Non-Convertible",
            "Has Recliners",
            "No Recliners"
        );
        filterComboBox.setValue("All");

        filterComboBox.setOnAction(e -> applyFilter());
    }

    private TableView<Sofa> createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, sofasList.size());

        sofasTable.setItems(FXCollections.observableArrayList(
            sofasList.subList(fromIndex, toIndex)));

        return sofasTable;
    }

    @FXML
    public void handleAddSofa() {
        try {
            // Load the dialog FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/toto/ui/fxml/AddSofaDialog.fxml"));
            DialogPane dialogPane = loader.load();

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add New Sofa");

            // Get references to the form fields
            TextField nameField = (TextField) dialogPane.lookup("#nameField");
            TextField priceField = (TextField) dialogPane.lookup("#priceField");
            TextField materialField = (TextField) dialogPane.lookup("#materialField");
            TextField manufacturerField = (TextField) dialogPane.lookup("#manufacturerField");
            ComboBox<String> woodTypeComboBox = (ComboBox<String>) dialogPane.lookup("#woodTypeComboBox");
            Spinner<Integer> seatingCapacitySpinner = (Spinner<Integer>) dialogPane.lookup("#seatingCapacitySpinner");
            CheckBox isConvertibleCheckBox = (CheckBox) dialogPane.lookup("#isConvertibleCheckBox");
            ComboBox<String> upholsteryTypeComboBox = (ComboBox<String>) dialogPane.lookup("#upholsteryTypeComboBox");
            Spinner<Integer> numberOfCushionsSpinner = (Spinner<Integer>) dialogPane.lookup("#numberOfCushionsSpinner");
            CheckBox hasReclinersCheckBox = (CheckBox) dialogPane.lookup("#hasReclinersCheckBox");

            // Configure the spinners
            SpinnerValueFactory<Integer> seatingValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 3);
            seatingCapacitySpinner.setValueFactory(seatingValueFactory);

            SpinnerValueFactory<Integer> cushionsValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 4);
            numberOfCushionsSpinner.setValueFactory(cushionsValueFactory);

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

            // Populate the upholstery type combo box
            upholsteryTypeComboBox.getItems().addAll(
                "Leather", "Fabric", "Microfiber", "Velvet", "Linen", "Cotton", "Other"
            );
            upholsteryTypeComboBox.setValue("Fabric");

            // Show the dialog and wait for the user response
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Create a new Sofa object with the form data
                Sofa newSofa = new Sofa();
                newSofa.setName(nameField.getText());
                newSofa.setPrice(Double.parseDouble(priceField.getText()));
                newSofa.setMaterial(materialField.getText());
                newSofa.setManufacturer(manufacturerField.getText());

                // Set the wood type enum
                newSofa.setWoodType(com.toto.backend.entities.enums.WoodType.valueOf(woodTypeComboBox.getValue()));

                // Set sofa-specific properties
                newSofa.setSeatingCapacity(seatingCapacitySpinner.getValue());
                newSofa.setConvertible(isConvertibleCheckBox.isSelected());
                newSofa.setUpholsteryType(upholsteryTypeComboBox.getValue());
                newSofa.setNumberOfCushions(numberOfCushionsSpinner.getValue());
                newSofa.setHasRecliners(hasReclinersCheckBox.isSelected());

                // Save the new sofa to the database
                sofaService.save(newSofa);

                // Refresh the table to show the new sofa
                refreshData();

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Sofa Added");
                alert.setContentText("The sofa has been added successfully.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Add Sofa Error");
            alert.setContentText("An error occurred while adding the sofa: " + e.getMessage());
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

        List<Sofa> allSofas = sofaService.findAll();
        ObservableList<Sofa> filteredList = FXCollections.observableArrayList();

        for (Sofa sofa : allSofas) {
            if (sofa.getName().toLowerCase().contains(searchTerm) ||
                sofa.getManufacturer().toLowerCase().contains(searchTerm) ||
                sofa.getMaterial().toLowerCase().contains(searchTerm) ||
                sofa.getUpholsteryType().toLowerCase().contains(searchTerm)) {
                filteredList.add(sofa);
            }
        }

        updateTableWithData(filteredList);
    }

    private void handleEditSofa(Sofa sofa) {
        // This will be implemented later
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Sofa");
        alert.setHeaderText("Edit Sofa Feature");
        alert.setContentText("This feature would open a dialog to edit sofa: " + sofa.getName());
        alert.showAndWait();
    }

    private void handleDeleteSofa(Sofa sofa) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Sofa");
        confirmDialog.setContentText("Are you sure you want to delete sofa: " + sofa.getName() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                sofaService.deleteById(sofa.getId());
                refreshData();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Delete Error");
                errorAlert.setContentText("An error occurred while deleting the sofa: " + e.getMessage());
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

        List<Sofa> allSofas = sofaService.findAll();
        ObservableList<Sofa> filteredList = FXCollections.observableArrayList();

        for (Sofa sofa : allSofas) {
            boolean include = switch (filter) {
                case "Convertible" -> sofa.isConvertible();
                case "Non-Convertible" -> !sofa.isConvertible();
                case "Has Recliners" -> sofa.isHasRecliners();
                case "No Recliners" -> !sofa.isHasRecliners();
                default -> true;
            };

            if (include) {
                filteredList.add(sofa);
            }
        }

        updateTableWithData(filteredList);
    }

    private void refreshData() {
        try {
            List<Sofa> sofas = sofaService.findAll();
            updateTableWithData(FXCollections.observableArrayList(sofas));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data Refresh Error");
            alert.setContentText("An error occurred while refreshing data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateTableWithData(ObservableList<Sofa> sofas) {
        this.sofasList = sofas;

        int pageCount = (sofas.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        createPage(0);

        totalItemsLabel.setText("Total Items: " + sofas.size());
    }
}
