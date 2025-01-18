package demo.quickStart;
import demo.quickStart.Controllers.formsControllers.EventformController;
import demo.quickStart.DAO.Config;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
// import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.Node;
// import java.lang.module.FindException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import demo.quickStart.DAO.DAOevenment;
import demo.quickStart.models.Evenments;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
// import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
// import javafx.scene.Scene;
// import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrimaryController implements Initializable {
    @FXML
     BorderPane mainsection;
    

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
    Button add_event;
    @FXML
    VBox main;
    @FXML
    StackPane stackpane;
    @FXML
    Label countUsers;
    @FXML 
    Label countreserva;
    @FXML
    Label countromms;
    @FXML
    Label countevents;
    @FXML
    TextField search_even;
    @FXML
    Button btn_search;
    @FXML
    Button Event;
    MongoDatabase databse=Config.getbasedonnee();
    DAOevenment daoevenement=new DAOevenment();

    // public  BorderPane getmainpane(){
    //     return  mainsection;
    // }
    
    // public void passereferencetoformevent() throws Exception{
    //     FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/forms/EventForm.fxml"));
    //     Node eventform=loader.load();
    //     EventformController eventformcontroller=loader.getController();
    //     eventformcontroller.setdashboardcontroller(this);
        

    // }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addactionbutton();

        event_name.setCellValueFactory(new PropertyValueFactory<>("nom_event"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_event"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        ObservableList<Evenments> events=FXCollections.observableArrayList(daoevenement.findall());
        table_events.setItems(events);
        add_event.setOnAction(e->showaddeventform("add",new Evenments()));
        
        MongoCollection<Document> rowsoftabbl=databse.getCollection("utilisateurs");
        long count=rowsoftabbl.countDocuments();
        countUsers.setText(String.valueOf(count));
        rowsoftabbl=databse.getCollection("evenements");
        count=rowsoftabbl.countDocuments();
        countevents.setText(String.valueOf(count));
        rowsoftabbl=databse.getCollection("salles");
        countromms.setText(String.valueOf(rowsoftabbl.countDocuments()));
        rowsoftabbl=databse.getCollection("reservations");
        countreserva.setText(String.valueOf(rowsoftabbl.countDocuments()));

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
                editbtn.setOnAction(e -> {
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

                HBox buttons=new HBox(10, editbtn, delbtn); 
                buttons.setAlignment(Pos.CENTER);
                setGraphic(buttons);
            }
        }
        });
    }

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
    static FXMLLoader loader;
    static VBox form;
    static Rectangle overlay;
    private void showaddeventform(String type,Evenments selected_event){
        try {
            loader=new FXMLLoader(getClass().getResource("/views/forms/EventForm.fxml"));
             form=loader.load();
             EventformController eventform=loader.getController();
             eventform.setEventController(this);
             if("edit".equals(type)&&selected_event!=null){
                eventform.submitBtn.setText("submit edit");
                eventform.sendIditedevent(selected_event);
             }
             overlay=new Rectangle(main.getWidth(),main.getHeight(),Color.BLACK);
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
            // Button submitbtn=(Button) form.lookup("#submitBtn");
            // submitbtn.setOnAction(e->hideformevent(overlay,form));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void hideformevent(){
        
        stackpane.getChildren().removeAll(overlay,form);

    }

    @FXML
    public  void handleEvent() throws Exception{
        System.out.println("button clicked");
        Getpage obj=new Getpage();
        Pane view=obj.getpage("EventScene");
        // passereferencetoformevent();
        mainsection.setCenter(view);
        
        
    }

    @FXML
    private void handleSalle(ActionEvent event) {
        System.out.println("button clicked");
        Getpage obj=new Getpage();
        Pane view=obj.getpage("SalleScene");
        mainsection.setCenter(view);
    }

    @FXML
    private void handleTerrain(ActionEvent event) {
        System.out.println("button clicked");
        Getpage obj=new Getpage();
        Pane view=obj.getpage("TerrainScene");
        mainsection.setCenter(view);
    }

    @FXML
    private void handleUser(ActionEvent event) {
        System.out.println("button clicked");
        Getpage obj=new Getpage();
        Pane view=obj.getpage("UserScene");
        mainsection.setCenter(view);
    }

    @FXML
    private void handleReservation(ActionEvent event) {
        System.out.println("button clicked");
        Getpage obj=new Getpage();
        Pane view=obj.getpage("ReservationScene");
        mainsection.setCenter(view);
    }
    @FXML
    private void handleDashboard(ActionEvent event) throws Exception  {
        System.out.println("button clicked");
        Parent root=FXMLLoader.load(getClass().getResource("/views/navbar.fxml"));
        Scene scene=new Scene(root);
         Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
         stage.setScene(scene);
         stage.show();
        
    }

}



