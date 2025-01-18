package demo.quickStart.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mongodb.client.ClientSession;

import demo.quickStart.Controllers.formsControllers.SalleformController;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOsalle;
import demo.quickStart.models.Evenments;
import demo.quickStart.models.Reservations;
import demo.quickStart.models.Salles;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class SalleController implements Initializable {
    @FXML
    Button add_salle;
    @FXML
    AnchorPane mainpane;
    @FXML
    StackPane stackpane;
    @FXML 
    TableColumn<Salles,String> nom_salle;
    @FXML
    TableColumn<Salles,Integer>  capacite;
    @FXML
    TableView<Salles> table_salles;
    @FXML
    TextField get_salle;
    @FXML
    TableColumn<Salles,Void> actions;
    DAOsalle daosalle=new DAOsalle();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        addactionbutton();
        nom_salle.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));
        capacite.setCellValueFactory(new PropertyValueFactory<>("capacite"));
        ObservableList<Salles> salles= FXCollections.observableArrayList(daosalle.findall());
        table_salles.setItems(salles);

        add_salle.setOnAction(e->showaddsalleform("add",new Salles()));
    }

     private void addactionbutton(){
        actions.setCellFactory(col->new TableCell<Salles,Void>(){
        Button editbtn=new Button("Edit");
        Button delbtn=new Button("Delete");
       
        @Override
        protected void updateItem(Void item, boolean empty){

            super.updateItem(item, empty);
            if(empty){
                setGraphic(null);
            }else{
                editbtn.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                delbtn.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-border-radius: 5px; -fx-background-radius: 5px;");
                editbtn.setOnAction(e ->{
                    Salles selectedEvent=getTableView().getItems().get(getIndex());
                    showaddsalleform("edit",selectedEvent);
                });
                 delbtn.setOnAction(e->{
                    try {
                        Salles selectedreserv=getTableView().getItems().get(getIndex());
                        try (ClientSession session=Config.getClient().startSession()){
                              session.startTransaction();
                              daosalle.Delete(String.valueOf(selectedreserv.getId_salle()),session);
                              session.commitTransaction();
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    
                });
                HBox buttons = new HBox(10, editbtn, delbtn); 
                buttons.setAlignment(Pos.CENTER);
                setGraphic(buttons);
            }
        }
        });
    }

    public void getsalle(){
        String salle_name=get_salle.getText();
        if(salle_name==""){
              List<Salles> rows=daosalle.findall();
             ObservableList<Salles> data=FXCollections.observableArrayList(rows);
             table_salles.setItems(data);
        }
        ArrayList<Salles> row=new ArrayList<>();
        row.add(daosalle.get(salle_name));
         ;     
        if(row!=null && !row.isEmpty()){
            ObservableList<Salles> data=FXCollections.observableArrayList(row);
            table_salles.setItems(data);
        }
    }   
    FXMLLoader loader;
    VBox form;
    Rectangle overlay;

     private void showaddsalleform(String type,Salles selectedsalle){
        try {
            loader=new FXMLLoader(getClass().getResource("/views/forms/Salle.fxml"));
            form=loader.load();
            SalleformController sallecontroller=loader.getController();
            sallecontroller.setsalleController(this);
            if("edit".equals(type)&&selectedsalle!=null){
                sallecontroller.submitBtn.setText("submit edit");
                sallecontroller.sendIditedsalle(selectedsalle);
             }
            overlay=new Rectangle(mainpane.getWidth(),mainpane.getHeight(),Color.BLACK);
            overlay.setOpacity(0.5);
            form.setMaxWidth(300);
            form.setMaxHeight(290);
            // stackpane.setAlignment(form,Pos.CENTER);
            stackpane.getChildren().addAll(overlay,form);
            TranslateTransition transitionform=new TranslateTransition(Duration.seconds(0.5),form);
            transitionform.setFromY(300);
            transitionform.setToY(0);
            transitionform.play();
            FadeTransition smoothform=new FadeTransition(Duration.seconds(0.5),form);
            smoothform.setFromValue(0);
            smoothform.setToValue(1);
            smoothform.play();
            FadeTransition smoothoverlay=new FadeTransition(Duration.seconds(0.5),overlay);
            smoothoverlay.setFromValue(0);
            smoothoverlay.setToValue(0.5);
            smoothoverlay.play();
            overlay.setOnMouseClicked(e->hideformSalle());
            // Button submitBtn=(Button) form.lookup("#submitBtn");
            // submitBtn.setOnAction(e->hideformSalle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void hideformSalle(){
        System.out.println("hide fct called ");
        stackpane.getChildren().removeAll(overlay,form);
    }
    
}
