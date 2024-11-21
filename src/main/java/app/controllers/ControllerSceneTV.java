package app.controllers;

import app.CustomEditingCell;
import app.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerSceneTV implements Initializable {
    @Getter
    public TableView<Position> tableInfo;
    public ObservableList<Position> dataTable;
    public TableColumn<Position, String> column1;
    public TableColumn<Position, String> column2;
    public TableColumn<Position, String> column3;
    public TableView<Position> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableInfo = tableView;
        initializeCols();
        loadData();
        tableInfo.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    private void initializeCols() {

        column1.setCellValueFactory(new PropertyValueFactory<Position, String>("tableColumnName"));
        column1.impl_setReorderable(false);

        column2.setCellValueFactory(new PropertyValueFactory<>("tableColumnUnit"));
        column2.impl_setReorderable(false);

        column3.setCellValueFactory(new PropertyValueFactory<>("nds"));
        column3.impl_setReorderable(false);

        editableCols();

    }

    private void editableCols() {

        column1.setCellFactory(new Callback<TableColumn<Position, String>, TableCell<Position, String>>() {
            @Override
            public TableCell<Position, String> call(TableColumn<Position, String> orderItemStringTableColumn) {
                return new CustomEditingCell();
            }
        });


        column2.setCellFactory(new Callback<TableColumn<Position, String>, TableCell<Position, String>>() {
            @Override
            public TableCell<Position, String> call(TableColumn<Position, String> orderItemStringTableColumn) {
                return new CustomEditingCell();
            }
        });

        column3.setCellFactory(new Callback<TableColumn<Position, String>, TableCell<Position, String>>() {
            @Override
            public TableCell<Position, String> call(TableColumn<Position, String> orderItemStringTableColumn) {
                return new CustomEditingCell();
            }
        });

        tableView.setEditable(true);
    }

    private void loadData() {
        dataTable = FXCollections.observableArrayList();
        // FOR TEST
        for (int i = 0; i < 5; i++) {
            dataTable.add(Position.builder()
                    .column1("")
                    .column2("")
                    .column3("")
                    .build());
        }
        dataTable.add(new Position());
        tableView.setItems(dataTable);
    }
}
