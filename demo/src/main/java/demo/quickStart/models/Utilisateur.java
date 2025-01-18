package demo.quickStart.models;                

import org.bson.types.ObjectId;

public class Utilisateur {
    ObjectId id_user;
    String nom,prenom,email,type,password;

    public String getpassword(){
            return password;
    }
    public void setpassword(String password){
        this.password=password;

    }

    public ObjectId getId_user(){
        return id_user;
    }
    public String getEmail(){
        return email;
    }
    public String getNom(){
        return nom;
    }
    public String getPrenom(){
        return prenom;
    }
    public String getType(){
        return type;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setId_user(ObjectId id_user){
        this.id_user=id_user;
    }
    public void setNom(String nom){
        this.nom=nom;
    }
    public void setPrenom(String prenom){
        this.prenom=prenom;
    }
    public void setType(String type){
        this.type=type;
    }


    
}

