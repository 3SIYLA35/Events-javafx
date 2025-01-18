package demo.quickStart.models;                

import org.bson.types.ObjectId;

public class Salles{
    String nom_salle;
    int capacite;
    ObjectId id_salle;
    public ObjectId getId_salle(){
        return id_salle;
    }
    public void setId_salle(ObjectId id_salle){
        this.id_salle=id_salle;
    }
    public int getCapacite(){
        return capacite;
    }
    public String getNom_salle(){
        return nom_salle;
    }
    public void setCapacite(int capacite){
        this.capacite=capacite;
    }
    public void setNom_salle(String nom_salle){
        this.nom_salle=nom_salle;
    }
    
}
