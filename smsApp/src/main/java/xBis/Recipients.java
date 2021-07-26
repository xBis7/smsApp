package xBis;

import java.io.ObjectInputStream.GetField;

import javafx.application.Application;

import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javafx.geometry.Pos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.Optional;


public class Recipients extends Dialog<String> {

    public static ObservableList<String> data2 = FXCollections.observableArrayList();
    
    public static ListView<String> list2 = new ListView<String>(data2);

    public static String recipientsWindow(Stage stage, String inputData){
        Dialog dialog = new Dialog();

        dialog.initModality(Modality.APPLICATION_MODAL);

        dialog.initOwner(stage);
        
        dialog.setTitle("Add recipients");

        ButtonType okButtonType = new ButtonType("OK", ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

        dialog.getDialogPane().getButtonTypes().addAll(cancelButtonType, okButtonType);

        VBox vBox = new VBox();

        HBox hBox = new HBox();

        Button addButton = new Button("Add");
        addButton.setMinWidth(60.0);

        Button removeButton = new Button("Remove");
        removeButton.setMinWidth(60.0);

        ObservableList<String> data1 = FXCollections.observableArrayList(
        "6912345678","6923456789","6934567891","6945678910","6956789101","6978910111",
        "6989101112","6991011121","6910111213","6901112131","6911121314","6911213141");
    
        ListView<String> list1 = new ListView<String>(data1);

        //enable multiple selection 
        list1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        list2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        dialog.getDialogPane().setContent(hBox);

        vBox.getChildren().addAll(addButton, removeButton);

        hBox.getChildren().addAll(list1, vBox, list2);

        vBox.setAlignment(Pos.CENTER);

        vBox.setSpacing(10);
        
        hBox.setSpacing(10);

        HBox.setHgrow(list1, Priority.ALWAYS);
        HBox.setHgrow(vBox, Priority.ALWAYS);
        HBox.setHgrow(list2, Priority.ALWAYS);
        
        dialog.getDialogPane().setPrefSize(400, 300);


        //phone number add
        addButton.setOnAction(event ->  {                    
               
            if(list1.getSelectionModel().getSelectedItems() != null){
                data2.addAll(list1.getSelectionModel().getSelectedItems());
                list2.setItems(data2); 
                data1.removeAll(list1.getSelectionModel().getSelectedItems());                                        
            }
            
        });

        //phone number remove
        removeButton.setOnAction(event ->  {                     
            
            if(list2.getSelectionModel().getSelectedItems() != null){
                data1.addAll(list2.getSelectionModel().getSelectedItems());
                list1.setItems(data1); 
                data2.removeAll(list2.getSelectionModel().getSelectedItems()); 
            }
            
        });

        //scanner delimiting string
        if(inputData != null){
            Scanner s = new Scanner(inputData);
            s.useDelimiter(";");     
            
            while(s.hasNext()){
                String str = s.next();
                if(!data2.contains(str)){
                    data2.add(str);
                }       
            }
            s.close();
        }

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == okButtonType){
            if(list2 != null){
                return String.join(";", data2);
            }
            else{
                return inputData;
            }  
        }
        else{
            return inputData;
        }

    }

}
