package demo.quickStart.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mongodb.client.ClientSession;

import demo.quickStart.Controllers.formsControllers.TerrainformController;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOterrain;
import demo.quickStart.models.Evenments;
import demo.quickStart.models.Salles;
import demo.quickStart.models.Terrains;
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

public class TerrainController implements Initializable {
    @FXML
    Button add_terrain;
    @FXML
    AnchorPane mainpane;
    @FXML
    StackPane stackpane;
    @FXML
    TableView<Terrains> table_terrain;
    @FXML
    TableColumn<Terrains,String> room_name;
    @FXML
    TableColumn<Terrains,String> room_type;
    @FXML
    TextField get_terrain;
    @FXML
    TableColumn actions;
    DAOterrain daoterrain=new DAOterrain();
    @Override
    public void initialize(URL location, ResourceBundle resources){
        addactionbutton();
        room_name.setCellValueFactory(new PropertyValueFactory<>("nom_terrain"));
        room_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        ObservableList<Terrains> data=FXCollections.observableArrayList(daoterrain.findall());
        table_terrain.setItems(data);
       add_terrain.setOnAction(e->showaddterrainform("add",new Terrains()));
    }

     @SuppressWarnings("unchecked")
	private void addactionbutton(){
        actions.setCellFactory(col->new TableCell<Terrains,Void>(){
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
                    Terrains selectedEvent=getTableView().getItems().get(getIndex());
                    showaddterrainform("edit",selectedEvent);
                });
                delbtn.setOnAction(e->{
                    try {
                        Terrains selectedreserv=getTableView().getItems().get(getIndex());
                        try (ClientSession session=Config.getClient().startSession()){
                              session.startTransaction();
                              daoterrain.Delete(String.valueOf(selectedreserv.getId_terrain()),session);
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


    public void getterrain(){
        String salle_name=get_terrain.getText();
        if(salle_name==""){
              List<Terrains> rows=daoterrain.findall();
             ObservableList<Terrains> data=FXCollections.observableArrayList(rows);
             table_terrain.setItems(data);
        }
        ArrayList<Terrains> row=new ArrayList<>();
        row.add(daoterrain.get(salle_name));
         ;     
        if(row!=null && !row.isEmpty()){
            ObservableList<Terrains> data=FXCollections.observableArrayList(row);
            table_terrain.setItems(data);
        }
    }
    FXMLLoader loader;
    VBox form;
    Rectangle overlay;
    private void showaddterrainform(String type,Terrains selectedterrain){
        try {
             loader=new FXMLLoader(getClass().getResource("/views/forms/Terrain.fxml"));
             form=loader.load();
             TerrainformController terraincontroller=loader.getController();
             terraincontroller.setTerrainController(this);
             if("edit".equals(type)&&selectedterrain!=null){
                terraincontroller.submitBtn.setText("submit edit");
                terraincontroller.sendIditedterrain(selectedterrain);
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
            overlay.setOnMouseClicked(e->hideformTerrain());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void hideformTerrain(){
        System.out.println("hid fxt c alled");
        stackpane.getChildren().removeAll(overlay,form);
    }
    
}
