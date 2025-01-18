package demo.quickStart.DAO;

import static com.mongodb.client.model.Filters.eq;
import demo.quickStart.models.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

// import javax.print.Doc;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DAOreservation  implements GenericDAO<Reservations>{
    MongoCollection<Document> reservation;
    public DAOreservation(){
        MongoDatabase database=Config.getbasedonnee();
        reservation=database.getCollection("reservations");
    }

    @Override
    public void insert(ClientSession session,Reservations reservation) {
        Document doc = new Document("id_user", reservation.getId_user())
            .append("id_event", reservation.getId_event())
            .append("id_terrain", reservation.getId_terrain())
            .append("id_salle", reservation.getId_salle())
       .append("date_reservation",reservation.getDate_reservation());
       this.reservation.insertOne(session,doc);
    }

  
    @Override
    public void Delete(String id,ClientSession session) {
        reservation.deleteOne(session,eq("_id",new ObjectId(id)));
    }

    @Override
    public List<Reservations> findall(){
       List<Reservations> listreservation=new ArrayList<>();
       for(Document doc:reservation.find()){
        Reservations res=new Reservations();
        java.util.Date date=doc.getDate("date_reservation");
        res.setDate_reservation(new java.sql.Date(date.getTime()));
        res.setId_event(doc.getString("id_event"));
        res.setId_user(doc.getString("id_user"));
        res.setId_terrain(doc.getString("id_terrain"));
        res.setId_salle(doc.getString("id_salle"));
        res.setId_reservation(doc.getObjectId("_id"));

        listreservation.add(res);
       }
       return listreservation;
    }

   
    @Override
    public void update(String id,Reservations reservation,ClientSession session){
        Document doc=new Document("id_user",reservation.getId_user())
        .append("id_event",reservation.getId_event())
        .append("id_terrain",reservation.getId_terrain())
        .append("id_salle", reservation.getId_salle())
        .append("date_reservation",reservation.getDate_reservation());

        Document docOperation=new Document("$set",doc);

        this.reservation.updateOne(session,new Document("_id",new ObjectId(id)),docOperation);
    }

    public int disponibilite(String nom_salle,Date date_reservation){
        MongoDatabase datebase=Config.getbasedonnee();
        MongoCollection<Document> Reservations=datebase.getCollection("reservations");
        Document dispo=Reservations.find(new Document("nom_salle",nom_salle).append("date_reservation",date_reservation)).first();
        if(dispo!=null){
            return 1;
        }
        return 0;
    }
    @Override
    public Reservations get(String id){
        MongoCollection<Document> Evenments=Config.getbasedonnee().getCollection("reservations");
         Document doc=Evenments.find(eq("_id", new ObjectId(id))).first();
         Reservations reservation = new Reservations();
         if (doc!=null) { 
            reservation.setId_event(doc.getString("id_event"));
            reservation.setId_user(doc.getString("id_user"));
            reservation.setId_terrain(doc.getString("id_terrain"));
            reservation.setId_salle(doc.getString("id_salle"));
            java.util.Date date=doc.getDate("date_reservation");
            reservation.setDate_reservation(new java.sql.Date(date.getTime()));
        }
        return reservation;
    }
}
