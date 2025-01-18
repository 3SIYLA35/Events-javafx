package demo.quickStart.models;                

import java.sql.Date;

import org.bson.types.ObjectId;

public class Reservations {
    String id_user,id_event,id_terrain,id_salle;
    Date date_reservation;
    ObjectId id_reservation;

    public ObjectId getId_reservation(){
        return id_reservation;
    }

    public void setId_reservation(ObjectId id_reservation) {
        this.id_reservation = id_reservation;
    }

    public String getId_salle() {
        return id_salle;
    }
    public void setId_salle(String id_salle) {
        this.id_salle=id_salle;
    }
    public Date getDate_reservation(){
        return date_reservation;
    }
    public String getId_event(){
        return id_event;
    }
    public String getId_terrain(){
        return id_terrain;
    }
    public String getId_user(){
        return id_user;
    }
    public void setDate_reservation(Date date_reservation){
        this.date_reservation=date_reservation;
    }
    public void setId_event(String id_event){
        this.id_event=id_event;
    }
    public void setId_terrain(String id_terrain){
        this.id_terrain=id_terrain;
    }
    public void setId_user(String id_user){
        this.id_user=id_user;
    }
    
}
