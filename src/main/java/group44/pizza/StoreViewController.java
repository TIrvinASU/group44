package group44.pizza;

import group44.pizza.storage.Order;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import group44.pizza.storage.Database;

public class StoreViewController implements Initializable {
    @FXML
    public ListView<Order> orders;
    @FXML
    private Label asuid;
    @FXML
    private Label toppings;
    @FXML
    private Label pizzaType;

    Order currentOrder = null;


    @FXML
    public void storeViewStart(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StoreView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) * .55;
        double y = bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) * .4;
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database.loadData();
        ArrayList<Order> order = Database.orders;
        orders.getItems().addAll(order);

        orders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
            @Override
            public void changed(ObservableValue<? extends Order> observableValue, Order order, Order t1) {
                currentOrder = t1;
                asuid.setText(t1.getAsuID());
                pizzaType.setText(t1.getPizzaType());
                toppings.setText(String.join("\n", t1.getToppings()));


            }
        });

        orders.setCellFactory(orderListView -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty) {
                    String text = "test";
                    setText(null);
                } else {
                    setText("Order: " + String.valueOf(order.getId() ));
                }
            }
        });
    }

    public void refresh(ActionEvent actionEvent) {
        Database.loadData();
        ArrayList<Order> order = Database.orders;
        orders.getItems().clear();
        orders.getItems().addAll(order);
        orders.setCellFactory(orderListView -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText("Order: " + String.valueOf(order.getId()));
                }
            }
        });
    }

    public void setOrderReadyToCook() {
        if (currentOrder != null){
            Database.setOrderReadyToCook(currentOrder.getId());
        }
    }
    
    public void readyToCook(ActionEvent actionEvent) {
        setOrderReadyToCook();
    }
    
    public void setOrderReady() {
        if (currentOrder != null && currentOrder.getStatus().equals("READY TO COOK")){
            Database.setOrderReady(currentOrder.getId());
        }
    }

    public void ready(ActionEvent actionEvent) {setOrderReady();}
}
