package demo.quickStart.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mongodb.client.ClientSession;

import demo.quickStart.PrimaryController;
import demo.quickStart.Controllers.formsControllers.EventformController;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOevenment;
import demo.quickStart.models.Evenments;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class EventController implements Initializable {
    @FXML
    Button add_event;
    @FXML 
     StackPane stackpane;
    @FXML
    AnchorPane mainPane;
    @FXML
    TableView<Evenments> table_events;
    @FXML
    TableColumn<Evenments,String> event_name;
    @FXML
    TableColumn<Evenments,Date> date;
    @FXML
    TableColumn<Evenments,Integer> status;
    @FXML
    TableColumn<Evenments,String> description;
    @FXML
    TableColumn<Evenments, Void> actions;
    @FXML
    TextField search_even;
   
    DAOevenment daoevenement=new DAOevenment();
    @Override
    public void initialize(URL location, ResourceBundle resources){
        addactionbutton();
        event_name.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_event"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        ObservableList<Evenments> events=FXCollections.observableArrayList(daoevenement.findall());
        table_events.setItems(events);

        add_event.setOnAction(e->showaddeventform("add",new Evenments()));
    }

     private void addactionbutton(){
        actions.setCellFactory(col->new TableCell<Evenments,Void>(){
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
                    Evenments selectedEvent=getTableView().getItems().get(getIndex());
                    showaddeventform("edit",selectedEvent);
                });
                delbtn.setOnAction(e->{
                    try {
                        Evenments selectedEvent=getTableView().getItems().get(getIndex());
                        try (ClientSession session=Config.getClient().startSession()){
                              session.startTransaction();
                              daoevenement.Delete(String.valueOf(selectedEvent.getId_event()),session);
                              session.commitTransaction();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    
                });
                HBox buttons=new HBox(10,editbtn,delbtn); 
                buttons.setAlignment(Pos.CENTER);
                setGraphic(buttons);
            }
        }
        });
    }
    // PrimaryController controller=new PrimaryController();
    //     BorderPane mainsection=controller.getmainpane();

    //     public BorderPane getmainpane(){
    //         return mainsection;
    //     }
    public void getevent(){
        String event_name=search_even.getText();
        if(event_name==""){
              List<Evenments> rows=daoevenement.findall();
             ObservableList<Evenments> data=FXCollections.observableArrayList(rows);
             table_events.setItems(data);
        }
        ArrayList<Evenments> row=new ArrayList<>();
        row.add(daoevenement.get(event_name));
         ;     
        if(row!=null && !row.isEmpty()){
            ObservableList<Evenments> data=FXCollections.observableArrayList(row);
            table_events.setItems(data);
        }
    }
    
     static FXMLLoader   loader;
     static VBox   form;
     static Rectangle overlay;
    private void showaddeventform(String type,Evenments selected_event){
        try {
             loader=new FXMLLoader(getClass().getResource("/views/forms/EventForm.fxml"));
              form=loader.load();

            EventformController formController=loader.getController();
            formController.setEventController(this);    
            if("edit".equals(type)&&selected_event!=null){
                formController.submitBtn.setText("submit edit");
                formController.sendIditedevent(selected_event);
             }
              overlay=new Rectangle(mainPane.getWidth(), mainPane.getHeight(), Color.BLACK);
            overlay.setOpacity(0.5);
            form.setMaxWidth(300);
            form.setMaxHeight(450);
            stackpane.setAlignment(form,Pos.CENTER);
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
            overlay.setOnMouseClicked(e->hideformevent());
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void hideformevent(){
        System.out.println("called hide");
        stackpane.getChildren().removeAll(overlay,form);

    }
    
    
}
