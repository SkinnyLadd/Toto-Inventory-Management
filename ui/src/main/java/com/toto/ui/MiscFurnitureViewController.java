package com.toto.ui;

import com.toto.backend.entities.MiscFurniture;
import com.toto.backend.services.interfaces.IMiscFurnitureService;
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
public class MiscFurnitureViewController {

    @Autowired
    private IMiscFurnitureService miscFurnitureService;

    @FXML private TableView<MiscFurniture> miscTable;
    @FXML private TableColumn<MiscFurniture, Long> idColumn;
    @FXML private TableColumn<MiscFurniture, String> nameColumn;
    @FXML private TableColumn<MiscFurniture, Double> priceColumn;
    @FXML private TableColumn<MiscFurniture, String> materialColumn;
    @FXML private TableColumn<MiscFurniture, String> manufacturerColumn;
    @FXML private TableColumn<MiscFurniture, String> categoryColumn;
    @FXML private TableColumn<MiscFurniture, String> descriptionColumn;
    @FXML private TableColumn<MiscFurniture, Void> actionsColumn;

    @FXML private TextField searchField;
    @FXML private ComboBox<String> filterComboBox;
    @FXML private Button addMiscButton;
    @FXML private Button searchButton;
    @FXML private Button refreshButton;
    @FXML private Label totalItemsLabel;
    @FXML private Pagination pagination;

    private ObservableList<MiscFurniture> miscList = FXCollections.observableArrayList();
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

        // MiscFurniture-specific columns
        categoryColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCategory()));

        descriptionColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDescription()));

        // Setup actions column with edit and delete buttons
        setupActionsColumn();
    }

    private void setupActionsColumn() {
        Callback<TableColumn<MiscFurniture, Void>, TableCell<MiscFurniture, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<MiscFurniture, Void> call(final TableColumn<MiscFurniture, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");
                    private final Button deleteButton = new Button("Delete");
                    private final HBox hbox = new HBox(5, editButton, deleteButton);

                    {
                        editButton.getStyleClass().add("small-button");
                        deleteButton.getStyleClass().add("small-button");

                        editButton.setOnAction(event -> {
                            MiscFurniture misc = getTableView().getItems().get(getIndex());
                            handleEditMisc(misc);
                        });

                        deleteButton.setOnAction(event -> {
                            MiscFurniture misc = getTableView().getItems().get(getIndex());
                            handleDeleteMisc(misc);
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
        // Get unique categories from the database
        List<MiscFurniture> allItems = miscFurnitureService.findAll();
        ObservableList<String> categories = FXCollections.observableArrayList();
        categories.add("All");

        for (MiscFurniture item : allItems) {
            if (!categories.contains(item.getCategory())) {
                categories.add(item.getCategory());
            }
        }

        filterComboBox.setItems(categories);
        filterComboBox.setValue("All");

        filterComboBox.setOnAction(e -> applyFilter());
    }

    private TableView<MiscFurniture> createPage(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, miscList.size());

        miscTable.setItems(FXCollections.observableArrayList(
            miscList.subList(fromIndex, toIndex)));

        return miscTable;
    }

    @FXML
    public void handleAddMisc() {
        try {
            // Load the dialog FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/toto/ui/fxml/AddMiscDialog.fxml"));
            DialogPane dialogPane = loader.load();

            // Create the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add New Miscellaneous Item");

            // Get references to the form fields
            TextField nameField = (TextField) dialogPane.lookup("#nameField");
            TextField priceField = (TextField) dialogPane.lookup("#priceField");
            TextField materialField = (TextField) dialogPane.lookup("#materialField");
            TextField manufacturerField = (TextField) dialogPane.lookup("#manufacturerField");
            ComboBox<String> woodTypeComboBox = (ComboBox<String>) dialogPane.lookup("#woodTypeComboBox");
            ComboBox<String> categoryComboBox = (ComboBox<String>) dialogPane.lookup("#categoryComboBox");
            TextArea descriptionTextArea = (TextArea) dialogPane.lookup("#descriptionTextArea");

            // Custom attributes fields
            TextField attributeNameField = (TextField) dialogPane.lookup("#attributeNameField");
            TextField attributeValueField = (TextField) dialogPane.lookup("#attributeValueField");
            Button addAttributeButton = (Button) dialogPane.lookup("#addAttributeButton");
            ListView<String> attributesListView = (ListView<String>) dialogPane.lookup("#attributesListView");

            // Price modifiers fields
            TextField modifierNameField = (TextField) dialogPane.lookup("#modifierNameField");
            TextField modifierValueField = (TextField) dialogPane.lookup("#modifierValueField");
            Button addModifierButton = (Button) dialogPane.lookup("#addModifierButton");
            ListView<String> modifiersListView = (ListView<String>) dialogPane.lookup("#modifiersListView");

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

            // Populate the category combo box with existing categories
            List<MiscFurniture> allItems = miscFurnitureService.findAll();
            ObservableList<String> categories = FXCollections.observableArrayList();

            for (MiscFurniture item : allItems) {
                if (!categories.contains(item.getCategory())) {
                    categories.add(item.getCategory());
                }
            }

            if (categories.isEmpty()) {
                categories.add("General");
            }

            categoryComboBox.setItems(categories);
            categoryComboBox.setValue(categories.get(0));

            // Setup attribute and modifier lists
            ObservableList<String> attributes = FXCollections.observableArrayList();
            attributesListView.setItems(attributes);

            ObservableList<String> modifiers = FXCollections.observableArrayList();
            modifiersListView.setItems(modifiers);

            // Add attribute button action
            addAttributeButton.setOnAction(e -> {
                String name = attributeNameField.getText().trim();
                String value = attributeValueField.getText().trim();
                if (!name.isEmpty() && !value.isEmpty()) {
                    attributes.add(name + ": " + value);
                    attributeNameField.clear();
                    attributeValueField.clear();
                }
            });

            // Add modifier button action
            addModifierButton.setOnAction(e -> {
                String name = modifierNameField.getText().trim();
                String value = modifierValueField.getText().trim();
                if (!name.isEmpty() && !value.isEmpty()) {
                    try {
                        Double.parseDouble(value); // Validate that value is a number
                        modifiers.add(name + ": " + value);
                        modifierNameField.clear();
                        modifierValueField.clear();
                    } catch (NumberFormatException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText("Invalid Modifier Value");
                        alert.setContentText("Modifier value must be a number.");
                        alert.showAndWait();
                    }
                }
            });

            // Show the dialog and wait for the user response
            Optional<ButtonType> result = dialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Create a new MiscFurniture object with the form data
                MiscFurniture newItem = new MiscFurniture();
                newItem.setName(nameField.getText());
                newItem.setPrice(Double.parseDouble(priceField.getText()));
                newItem.setMaterial(materialField.getText());
                newItem.setManufacturer(manufacturerField.getText());

                // Set the wood type enum
                newItem.setWoodType(com.toto.backend.entities.enums.WoodType.valueOf(woodTypeComboBox.getValue()));

                // Set misc-specific properties
                newItem.setCategory(categoryComboBox.getValue());
                newItem.setDescription(descriptionTextArea.getText());

                // Add custom attributes
                for (String attribute : attributes) {
                    String[] parts = attribute.split(":", 2);
                    if (parts.length == 2) {
                        newItem.addCustomAttribute(parts[0].trim(), parts[1].trim());
                    }
                }

                // Add price modifiers
                for (String modifier : modifiers) {
                    String[] parts = modifier.split(":", 2);
                    if (parts.length == 2) {
                        try {
                            double value = Double.parseDouble(parts[1].trim());
                            newItem.addPriceModifier(parts[0].trim(), value);
                        } catch (NumberFormatException e) {
                            // Skip invalid modifiers
                        }
                    }
                }

                // Save the new item to the database
                miscFurnitureService.save(newItem);

                // Refresh the table to show the new item
                refreshData();

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Item Added");
                alert.setContentText("The miscellaneous furniture item has been added successfully.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Add Item Error");
            alert.setContentText("An error occurred while adding the item: " + e.getMessage());
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

        List<MiscFurniture> allItems = miscFurnitureService.findAll();
        ObservableList<MiscFurniture> filteredList = FXCollections.observableArrayList();

        for (MiscFurniture item : allItems) {
            if (item.getName().toLowerCase().contains(searchTerm) ||
                item.getManufacturer().toLowerCase().contains(searchTerm) ||
                item.getMaterial().toLowerCase().contains(searchTerm) ||
                item.getCategory().toLowerCase().contains(searchTerm) ||
                item.getDescription().toLowerCase().contains(searchTerm)) {
                filteredList.add(item);
            }
        }

        updateTableWithData(filteredList);
    }

    private void handleEditMisc(MiscFurniture misc) {
        // This will be implemented later
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Edit Item");
        alert.setHeaderText("Edit Item Feature");
        alert.setContentText("This feature would open a dialog to edit item: " + misc.getName());
        alert.showAndWait();
    }

    private void handleDeleteMisc(MiscFurniture misc) {
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirm Delete");
        confirmDialog.setHeaderText("Delete Item");
        confirmDialog.setContentText("Are you sure you want to delete item: " + misc.getName() + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                miscFurnitureService.deleteById(misc.getId());
                refreshData();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Delete Error");
                errorAlert.setContentText("An error occurred while deleting the item: " + e.getMessage());
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

        List<MiscFurniture> allItems = miscFurnitureService.findAll();
        ObservableList<MiscFurniture> filteredList = FXCollections.observableArrayList();

        for (MiscFurniture item : allItems) {
            if (item.getCategory().equals(filter)) {
                filteredList.add(item);
            }
        }

        updateTableWithData(filteredList);
    }

    private void refreshData() {
        try {
            List<MiscFurniture> items = miscFurnitureService.findAll();
            updateTableWithData(FXCollections.observableArrayList(items));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data Refresh Error");
            alert.setContentText("An error occurred while refreshing data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void updateTableWithData(ObservableList<MiscFurniture> items) {
        this.miscList = items;

        int pageCount = (items.size() + ITEMS_PER_PAGE - 1) / ITEMS_PER_PAGE;
        pagination.setPageCount(pageCount == 0 ? 1 : pageCount);
        pagination.setCurrentPageIndex(0);
        createPage(0);

        totalItemsLabel.setText("Total Items: " + items.size());
    }
}
