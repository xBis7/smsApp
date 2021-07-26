package xBis;

import java.util.Optional;
import java.util.Scanner;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Dialog;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * JavaFX App
 */
public class App extends Application {


    int a=0;
    int b=160;
    boolean messageSend = false;

    @Override
    public void start(Stage stage) {

        StackPane p = new StackPane();
        Scene scene = new Scene(p, 500, 500);
        
        stage.setMinHeight(350);             // window min dimensions
        stage.setMinWidth(350);

        //nodes
        Text toText = new Text("To:");

        TextField numberField = new TextField();
        numberField.setPromptText("Type numbers separated with ';'");

        Button addButton = new Button("Add...");

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Type message text.");
        
        Button sendButton = new Button("Send");
        sendButton.setMaxWidth(Double.MAX_VALUE);

        Label label = new Label(a + " / " + b);

        //panes
        BorderPane bPane1 = new BorderPane();

        GridPane gPane = new GridPane();

        //root node StackPane
        p.getChildren().addAll(gPane);

        //add nodes to BorderPane
        bPane1.setLeft(toText);
        bPane1.setCenter(numberField);
        bPane1.setRight(addButton);
        
        //add nodes to GridPane
        gPane.add(bPane1, 0, 0, 4, 1);
        gPane.add(messageArea, 0, 1, 4, 1);
        gPane.add(sendButton, 0, 2, 4, 1);
        gPane.add(label, 3, 3);

        //GridPane column constraints
        ColumnConstraints columnConstraint = new ColumnConstraints();
        columnConstraint.setHgrow(Priority.ALWAYS);
        gPane.getColumnConstraints().add(0, columnConstraint);
        
        //GridPane row constraints
        RowConstraints rowConstraint1 = new RowConstraints();
        rowConstraint1.setVgrow(Priority.NEVER);
        gPane.getRowConstraints().add(0, rowConstraint1);

        RowConstraints rowConstraint2 = new RowConstraints();
        rowConstraint2.setVgrow(Priority.ALWAYS);
        gPane.getRowConstraints().add(1, rowConstraint2);

        RowConstraints rowConstraint3 = new RowConstraints();
        rowConstraint3.setVgrow(Priority.NEVER);
        gPane.getRowConstraints().add(2, rowConstraint3);
        
        RowConstraints rowConstraint4 = new RowConstraints();
        rowConstraint4.setVgrow(Priority.NEVER);
        gPane.getRowConstraints().add(3, rowConstraint4);

        //spacing between nodes
        BorderPane.setMargin(numberField, new Insets(0, 5, 0, 5));

        StackPane.setMargin(gPane, new Insets(5, 5, 5, 5));

        gPane.setVgap(5);

        //main window actions

        addButton.setOnAction(e -> {
            
            String numberInput = Recipients.recipientsWindow(stage, numberField.getText()); 
            if(numberInput != numberField.getText()){
                numberField.setText(numberInput);
            }    
                      
        });

        //character counting
        messageArea.setOnKeyPressed(Event -> { 
            
            a = messageArea.getText().length();

            if(a == b || (a>b && a<(b+160))){
                b += 160;
            }

            label.textProperty().bind(messageArea.textProperty().length().asString(" %d/" + b));
        });
 
        //send button events
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent sendB)
            {
                if(numberField.getText().equals("")){
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(stage);
                    alert.setTitle("Recipients Error");
                    alert.setHeaderText("There are no recipients added");
                    alert.setContentText("You need to add recipients before sending a message");
                    alert.showAndWait();
                }

                if(messageArea.getText().equals("")){
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(stage);
                    alert.setTitle("Empty message warning");
                    alert.setHeaderText("Message has no body");
                    alert.setContentText("Do you want to send an empty message?");

                    ButtonType yesButtonType = new ButtonType("Yes", ButtonData.OK_DONE);
                    ButtonType noButtonType = new ButtonType("No", ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(yesButtonType, noButtonType);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == yesButtonType){
                        Alert sendInfo = new Alert(AlertType.INFORMATION);
                        sendInfo.initModality(Modality.APPLICATION_MODAL);
                        sendInfo.initOwner(stage);
                        sendInfo.setTitle("Send Confirmation");
                        sendInfo.setHeaderText(null);
                        sendInfo.setContentText("Message sent!");
                        sendInfo.showAndWait();
                    } 
                    else{
                        sendB.consume();
                    }
                }

                if(!numberField.getText().equals("") && !messageArea.getText().equals("")){
                    Alert sendInfo = new Alert(AlertType.INFORMATION);
                    sendInfo.initModality(Modality.APPLICATION_MODAL);
                    sendInfo.initOwner(stage);
                    sendInfo.setTitle("Send Confirmation");
                    sendInfo.setHeaderText(null);
                    sendInfo.setContentText("Message sent!");
                    sendInfo.showAndWait();
                    messageSend = true;
                }

            }
        };
  
        //when send button is pressed
        sendButton.setOnAction(event);

        //Exit Alert
        stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
            public void handle(WindowEvent wE) {
                if(messageArea.getText().equals("") || messageSend == true){
                    System.exit(0);
                }
                else{
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.initOwner(stage);
                    alert.setTitle("Exit Confirmation");
                    alert.setHeaderText("You have not sent the message");
                    alert.setContentText("Are you sure you want to exit?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        System.exit(0);
                    } 
                    else{
                        wE.consume();
                    }
                }
            }
        });

        stage.setScene(scene);
        stage.setTitle("SMS App");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}