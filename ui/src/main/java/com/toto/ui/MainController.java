package com.toto.ui;

import com.toto.backend.entities.MiscFurniture;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import com.toto.backend.services.FurnitureService;
import com.toto.backend.entities.Furniture;
import com.toto.backend.entities.Chair;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainController {

    @Autowired
    private FurnitureService furnitureService;

    @FXML private TableView<Furniture> furnitureTable;
    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button refreshButton;

    @FXML
    public void initialize() {
        refreshFurnitureList();

        addButton.setOnAction(e -> addFurniture());
        deleteButton.setOnAction(e -> deleteSelectedFurniture());
        refreshButton.setOnAction(e -> refreshFurnitureList());
    }

    private void refreshFurnitureList() {
        ObservableList<Furniture> list = FXCollections.observableArrayList(furnitureService.findAll());
        furnitureTable.setItems(list);
    }

    private void addFurniture() {
        // Use MiscFurniture instead of anonymous implementation
        MiscFurniture furniture = new MiscFurniture();
        furniture.setName("Test Chair");
        furniture.setMaterial("Wood");
        furniture.setPrice(200);
        furniture.setManufacturer("Test Manufacturer");

        furnitureService.createFurniture(furniture);
        refreshFurnitureList();
    }


    private void deleteSelectedFurniture() {
        Furniture selected = furnitureTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            furnitureService.deleteById(selected.getId());
            refreshFurnitureList();
        }
    }
}
