package demo.quickStart.Controllers.formsControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.Action;

import com.mongodb.client.ClientSession;

import demo.quickStart.Controllers.ReservationController;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOreservation;
import demo.quickStart.models.Evenments;
import demo.quickStart.models.Reservations;
import demo.quickStart.models.Reservations;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ReservformController implements Initializable {
    @FXML
    DatePicker reservationDate;
    @FXML
    TextField EventName;
    @FXML
    TextField iduser;
    @FXML
    TextField idsalle;
    @FXML
    TextField idterrain;
    @FXML
    public Button submitBtn;
    DAOreservation daoreservation=new DAOreservation();
    @Override
    public void initialize(URL location, ResourceBundle resources){
        submitBtn.setOnAction(e->{
            if(submitBtn.getText()=="Submit Reservation"){
                try{
                    addReservation();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }else if(submitBtn.getText()=="submit edit"){
                try {
                    editreservation();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
          });
    }
    ReservationController reservationController;
    public void setreservController(ReservationController controller){
        this.reservationController=controller;
    }


    public void addReservation() throws IOException{
        System.out.println("btn add clicked");
        Reservations newReservation=new Reservations();

        newReservation.setId_event(EventName.getText());
        newReservation.setId_user(iduser.getText()); ;
        LocalDate recuper_date=reservationDate.getValue();
        Date new_date=java.sql.Date.valueOf(recuper_date);
        newReservation.setDate_reservation(new_date);
        newReservation.setId_terrain(idterrain.getText());
        newReservation.setId_salle(idsalle.getText());
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoreservation.insert(session,newReservation);
            session.commitTransaction();
        }
        
        if(reservationController!=null){
          hideForm();
        }
    }
    Reservations Reservation=new Reservations();
    public void sendIditedreserv(Reservations selectedreser){
            Reservation.setId_reservation(selectedreser.getId_reservation());
    }

    public void editreservation() throws Exception{
            System.out.println("btn edit clicked");
            Reservations editedReservation=new Reservations();
    
            editedReservation.setId_event(EventName.getText());
            editedReservation.setId_user(iduser.getText()); ;
            LocalDate recuper_date=reservationDate.getValue();
            Date new_date=java.sql.Date.valueOf(recuper_date);
            editedReservation.setDate_reservation(new_date);
            editedReservation.setId_terrain(idterrain.getText());
             editedReservation.setId_salle(idsalle.getText());
            try(ClientSession session=Config.getClient().startSession()){
                session.startTransaction();
                daoreservation.update(String.valueOf(Reservation.getId_reservation()),editedReservation,session);
                session.commitTransaction();
            }
            
            if(reservationController!=null){
              hideForm();
            }
        }


    private void hideForm(){
        System.out.println("call hid form fct");
        reservationController.hideformReservation(); 
        }

    
}
