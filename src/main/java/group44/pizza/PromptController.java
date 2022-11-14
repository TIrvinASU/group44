package group44.pizza;

import group44.pizza.storage.AsuriteDB;
import group44.pizza.storage.Database;
import group44.pizza.storage.Order;
import group44.pizza.storage.Persist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class PromptController {
    public Button submit;
    private String asuId = "";
    @FXML
    private Text errorText;
    @FXML
    public void promptStart(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Prompt.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double x = bounds.getMinX() + (bounds.getWidth() - scene.getWidth()) * .25;
        double y = bounds.getMinY() + (bounds.getHeight() - scene.getHeight()) * .4;
        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

    private void displayCurrentState() {
        System.out.printf("\nPROMPT STATUS\n");
        System.out.printf("ORDER:\n%s", Persist.customerViewOrder);
    }

    public void setAsuIdText(KeyEvent event) {
        asuId =  ((TextField)event.getSource()).getText();
        Persist.customerViewOrder.setAsuID(asuId);
        displayCurrentState();
        return;
    }

    public void submitOrder() {
        if(AsuriteDB.isValidAsuId(this.asuId)) {
            System.out.println("SUBMIT ORDER");
            Database.addOrder(Persist.customerViewOrder);
            Stage stage = (Stage) submit.getScene().getWindow();
            stage.close();
        }
        else {
            errorText.setVisible(true);
        }
    }
}
