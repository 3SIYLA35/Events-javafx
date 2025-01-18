package demo.quickStart.Controllers.formsControllers;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.mongodb.client.ClientSession;

import demo.quickStart.PrimaryController;
import demo.quickStart.Controllers.EventController;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOevenment;
import demo.quickStart.models.Evenments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class EventformController implements Initializable{
     @FXML
      DatePicker reservationDate;
    @FXML
      TextField  Event_Name;
    @FXML
     TextArea description_field;
    @FXML
     TextField Id_user;
    @FXML
    public Button submitBtn;
     DAOevenment daoevenement=new DAOevenment();
    @Override
    public void initialize(URL location, ResourceBundle resources){
      submitBtn.setOnAction(e->{
        if(submitBtn.getText()=="Submit Reservation"){
            try{
                addevent();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else if(submitBtn.getText()=="submit edit"){
            try {
                editevent();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
      });
    }
    
     EventController eventController;  
    public void setEventController(EventController controller) {
        this.eventController=controller;
    }
    PrimaryController primarycontroller;
    public void setEventController(PrimaryController controller) {
        this.primarycontroller=controller;
    }
    public  void addevent() throws Exception{

        System.out.println("btn add clicked");
        Evenments newEvent=new Evenments();

        newEvent.setNom_event(Event_Name.getText());
        newEvent.setDescription(description_field.getText()); ;
        LocalDate recuper_date=reservationDate.getValue();
        Date new_date=java.sql.Date.valueOf(recuper_date);
        newEvent.setDate_event(new_date);
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoevenement.insert(session,newEvent);
            session.commitTransaction();
        }
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/EventScene.fxml"));
        Pane view=loader.load();
        if(primarycontroller==null){
        hideForm1();
        }else{
            hideForm2();
        }
        }
        Evenments event=new Evenments();
        public void sendIditedevent(Evenments selectedevent){
            event.setId_event(selectedevent.getId_event());
            
        }

        public void editevent() throws Exception{
            System.out.println("btn edit clicked");
            Evenments editedEvent=new Evenments();
            editedEvent.setNom_event(Event_Name.getText());
            editedEvent.setDescription(description_field.getText()); ;
            LocalDate recuper_date=reservationDate.getValue();
            Date new_date=java.sql.Date.valueOf(recuper_date);
            editedEvent.setDate_event(new_date);
            try (ClientSession session=Config.getClient().startSession()){
                session.startTransaction();
                daoevenement.update(String.valueOf(event.getId_event()),editedEvent,session);
                session.commitTransaction();
            }
              FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/EventScene.fxml"));
              Pane view=loader.load();
              if(primarycontroller==null){
              hideForm1();
              }else{
                  hideForm2();
              }
        }


    private void hideForm1(){
            eventController.hideformevent();   
    }
    private void hideForm2() {
        
            
            primarycontroller.hideformevent();  
        
    }
    
}
