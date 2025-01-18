package demo.quickStart.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mongodb.client.ClientSession;

import demo.quickStart.Controllers.formsControllers.ReservformController;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOreservation;
import demo.quickStart.models.Evenments;
import demo.quickStart.models.Reservations;
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

public class ReservationController implements Initializable  {
    @FXML
    Button add_reservation;
    @FXML
    StackPane stackpane;
    @FXML
    AnchorPane mainPane;
    @FXML
    TextField get_reser;
    @FXML
    Button search_btn;
    @FXML
    TableView<Reservations> table_reservations;
    @FXML
    TableColumn<Reservations,String> id_user;
    @FXML
    TableColumn<Reservations,String> id_salle;
    @FXML
    TableColumn<Reservations,String> id_event;
    @FXML
    TableColumn<Reservations,String> id_terrains;
    @FXML
    TableColumn<Reservations,String> date_reservation;
    @FXML
    TableColumn<Reservations,Void> actions;
    DAOreservation daoreservation=new DAOreservation();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        addactionbutton();
        id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        id_salle.setCellValueFactory(new PropertyValueFactory<>("id_salle"));
        id_event.setCellValueFactory(new PropertyValueFactory<>("id_event"));
        id_terrains.setCellValueFactory(new PropertyValueFactory<>("id_terrain"));
        date_reservation.setCellValueFactory(new PropertyValueFactory<>("date_reservation"));
        ObservableList<Reservations> data=FXCollections.observableArrayList(daoreservation.findall());
        table_reservations.setItems(data);
        add_reservation.setOnAction(e->showaddreservationform("add",new Reservations()));
    }

     private void addactionbutton(){
        actions.setCellFactory(col->new TableCell<Reservations,Void>(){
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
                    Reservations selectedEvent=getTableView().getItems().get(getIndex());
                    showaddreservationform("edit",selectedEvent);
                });
                delbtn.setOnAction(e->{
                    try {
                        Reservations selectedreserv=getTableView().getItems().get(getIndex());
                        try (ClientSession session=Config.getClient().startSession()){
                              session.startTransaction();
                              daoreservation.Delete(String.valueOf(selectedreserv.getId_reservation()),session);
                              session.commitTransaction();
                        }
                    } catch (Exception ex){
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

    public void getReservation(){
        String salle_name=get_reser.getText();
        if(salle_name==""){
              List<Reservations> rows=daoreservation.findall();
             ObservableList<Reservations> data=FXCollections.observableArrayList(rows);
             table_reservations.setItems(data);
        }
        ArrayList<Reservations> row=new ArrayList<>();
        row.add(daoreservation.get(salle_name));
         ;     
        if(row!=null && !row.isEmpty()){
            ObservableList<Reservations> data=FXCollections.observableArrayList(row);
            table_reservations.setItems(data);
        }
    }
    FXMLLoader loader;
    VBox form;
    Rectangle overlay;
    private void showaddreservationform(String type,Reservations selectedreser){
        try {
            loader=new FXMLLoader(getClass().getResource("/views/forms/Reservation.fxml"));
            form=loader.load();
            ReservformController controller=loader.getController();
            controller.setreservController(this);
            if("edit".equals(type)&&selectedreser!=null){
                controller.submitBtn.setText("submit edit");
                controller.sendIditedreserv(selectedreser);
             }
             
            overlay=new Rectangle(mainPane.getWidth(),mainPane.getHeight(),Color.BLACK);
            overlay.setOpacity(0.5);
            form.setMaxWidth(300);
            form.setMaxHeight(450);
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
            overlay.setOnMouseClicked(e->hideformReservation());
            // Button submitBtn=(Button) form.lookup("#submitBtn");
            // submitBtn.setOnAction(e->hideformReservation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void hideformReservation(){
        System.out.println("remove called");
        stackpane.getChildren().removeAll(overlay,form);
    }

}
