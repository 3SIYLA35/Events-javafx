package demo.quickStart.models;                

import org.bson.types.ObjectId;

public class Terrains {
    String nom_terrain,type;
    ObjectId id_terrain;
    public ObjectId getId_terrain(){
        return id_terrain;
    }
    public void setId_terrain(ObjectId id_terrain){
        this.id_terrain=id_terrain;
    }
    public String getNom_terrain(){
        return nom_terrain;
    }
    public String getType(){
        return type;
    }
    public void setNom_terrain(String nom_terrain){
        this.nom_terrain=nom_terrain;
    }
    public void setType(String type){
        this.type=type;
    }

    
}
