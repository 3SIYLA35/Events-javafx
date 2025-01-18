package demo.quickStart.Controllers.formsControllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.mongodb.client.ClientSession;

import demo.quickStart.Controllers.SalleController;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOsalle;
import demo.quickStart.models.Reservations;
import demo.quickStart.models.Salles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SalleformController implements Initializable {
    @FXML
    TextField roomName;
    @FXML
    TextField roomCapacity;
    @FXML
    public Button submitBtn;
    DAOsalle daosalle=new DAOsalle();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        submitBtn.setOnAction(e->{
            if(submitBtn.getText()=="Submit Salle"){
                try{
                    addSalle();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }else if(submitBtn.getText()=="submit edit"){
                try {
                    editsalle();
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }
          });
    }
    SalleController sallecontroller;
    public void setsalleController(SalleController controller){
        this.sallecontroller=controller;
    }
    public void addSalle() throws IOException{
        System.out.println("btn add clicked");
        Salles newSalle=new Salles();

        newSalle.setNom_salle(roomName.getText());
        newSalle.setCapacite(Integer.parseInt(roomCapacity.getText())); ;
       
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daosalle.insert(session,newSalle);
            session.commitTransaction();
        }
        // FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/ReservationScene.fxml"));
        // Pane view=loader.load();
        if(sallecontroller!=null){
          hideForm();
        }
    }

    Salles salle=new Salles();
    public void sendIditedsalle(Salles selectedsalle){
            salle.setId_salle(selectedsalle.getId_salle());
    }

    public void editsalle() throws Exception{
        System.out.println("btn edit clicked");
        Salles editedSalle=new Salles();

        editedSalle.setNom_salle(roomName.getText());
        editedSalle.setCapacite(Integer.parseInt(roomCapacity.getText())); ;
       
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daosalle.update(String.valueOf(salle.getId_salle()),editedSalle,session);
            session.commitTransaction();
        }
        
        if(sallecontroller!=null){
          hideForm();
        }
    }

    private void hideForm(){
        System.out.println("call hid form fct");
        sallecontroller.hideformSalle(); 
        }

    
}
