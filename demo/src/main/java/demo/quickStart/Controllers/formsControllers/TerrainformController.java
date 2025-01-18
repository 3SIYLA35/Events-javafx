package demo.quickStart.Controllers.formsControllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.mongodb.client.ClientSession;

import demo.quickStart.Controllers.TerrainController;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOterrain;
import demo.quickStart.models.Salles;
import demo.quickStart.models.Terrains;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TerrainformController implements Initializable {
    @FXML
    TextField TerrainName;
    @FXML 
    TextField TerrainType;
    @FXML
    public Button submitBtn;
    DAOterrain daoterrain=new DAOterrain();;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    TerrainController terrainController;
    public void setTerrainController(TerrainController controller){
        this.terrainController=controller;
    }

    public void addTerrain(){
         System.out.println("btn add clicked");
        Terrains newTerrain=new Terrains();

        newTerrain.setNom_terrain(TerrainName.getText());
        newTerrain.setType(TerrainType.getText()); ;
       
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoterrain.insert(session,newTerrain);
            session.commitTransaction();
        }
        // FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/ReservationScene.fxml"));
        // Pane view=loader.load();
        if(terrainController!=null){
          hideForm();
        }
    }

    Terrains terrain=new Terrains();
    public void sendIditedterrain(Terrains selectedterrain){
            terrain.setId_terrain(selectedterrain.getId_terrain());
    }

    public void editTerrain()throws Exception{

        System.out.println("btn add clicked");
        Terrains editedTerrain=new Terrains();

        editedTerrain.setNom_terrain(TerrainName.getText());
        editedTerrain.setType(TerrainType.getText()); ;
       
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoterrain.update(String.valueOf(terrain.getId_terrain()),editedTerrain,session);
            session.commitTransaction();
        }
        // FXMLLoader loader=new FXMLLoader(getClass().getResource("/views/ReservationScene.fxml"));
        // Pane view=loader.load();
        if(terrainController!=null){
          hideForm();
        }
    }
    private void hideForm(){
        System.out.println("call hid form fct");
        terrainController.hideformTerrain(); 
        }

    
}
