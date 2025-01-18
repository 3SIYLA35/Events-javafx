package demo.quickStart;

import java.net.URL;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.client.ClientSession;

import javafx.scene.control.CheckBox;
// import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import  javafx.scene.control.Label;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOUser;
import demo.quickStart.models.Utilisateur;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AuthController implements Initializable  {

     @FXML
    private VBox loginForm;
    @FXML
    private VBox registerForm;
    @FXML
    private TextField loginemail;
    @FXML
    private PasswordField loginpassword;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField registeremail;

    @FXML
    private PasswordField registerpassword;
    @FXML
    Label status;
    @FXML 
    CheckBox checkbox_Admin;
    @FXML
    CheckBox checkbox_etudiant;
    @FXML
    CheckBox checkbox_Professeur;
    // @FXML
    // FontAwesomeIconView spinner;
    DAOUser user=new DAOUser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     
    }
    public void switchToRegister() {
        loginForm.setVisible(false);
        registerForm.setVisible(true);
        TranslateTransition transitionregister=new TranslateTransition(Duration.seconds(0.5),registerForm);
        transitionregister.setFromX(400);
        transitionregister.setToX(0);
        transitionregister.play();
        FadeTransition smoothregister=new FadeTransition(Duration.seconds(0.5),registerForm);
            smoothregister.setFromValue(0.2);
            smoothregister.setToValue(1);
            smoothregister.play();
        // registerForm.setLayoutY(0);
    }
    
    public void switchToLogin() {
        registerForm.setVisible(false);
        loginForm.setVisible(true);
        TranslateTransition transitionlogin=new TranslateTransition(Duration.seconds(0.5),loginForm);
        transitionlogin.setFromX(-400);
        transitionlogin.setToX(0);
        transitionlogin.play();
        FadeTransition smoothlogin=new FadeTransition(Duration.seconds(0.5),loginForm);
        smoothlogin.setFromValue(0.2);
        smoothlogin.setToValue(1);
        smoothlogin.play();
    }

    @FXML
    private void handleLogin()throws Exception{
        
        
        String email=loginemail.getText();
        String password=loginpassword.getText();
        Document check_user=user.getcollection().find(new Document("email",email)).first();
        if(check_user!=null){
            if(check_user.get("password").equals(password)){
                System.out.println("Email:"+email+" password: "+password);
                status.setText("Connexion réussie !");
                status.setStyle("-fx-text-fill:green;");
                status.setVisible(true);
                PauseTransition pause=new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e->{
                    try{
                        Parent root=FXMLLoader.load(getClass().getResource("/views/navbar.fxml"));
                        Scene scene=new Scene(root);
                        Stage stage=(Stage) loginemail.getScene().getWindow();
                        stage.setScene(scene);
                        
                    }catch(Exception ex) {
                        ex.printStackTrace();
                    }
                });
                pause.play();
                // status.setVisible(false);  
            }else{
                System.out.println("password incorecct :"+check_user.get("password"));
                status.setStyle("-fx-text-fill:red;");
                status.setVisible(true);
                status.setText("passsword incoreccet");
                
            }
        }
        else{
            status.setText("email non valid ");
            status.setStyle("-fx-text-fill:red;");
            status.setVisible(true);
        }
    }
    
    @FXML
    private void handleRegister() {
        String noM=nom.getText();
        String prenoM=prenom.getText();
        String email=registeremail.getText();
        String password=registerpassword.getText();
        Utilisateur user=new Utilisateur();
        user.setEmail(email);
        user.setNom(noM);
        user.setPrenom(prenoM);
        user.setpassword(password);
        String type="";
        if(checkbox_etudiant.isSelected()){
            type=checkbox_etudiant.getText();
        }
        else if(checkbox_Professeur.isSelected()){
            type=checkbox_Professeur.getText();
        }
        else if(checkbox_Admin.isSelected()){
            type=checkbox_Admin.getText();
        }
        user.setType(type);
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            this.user.insert( session,user); 
            session.commitTransaction();
        }catch(Exception e) {
            System.err.println(" error transaction : " + e.getMessage());
        }
        switchToLogin();
        

        
    }
    
}
