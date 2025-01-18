package demo.quickStart.DAO;

import java.util.ArrayList;
import java.util.List;
import demo.quickStart.models.*;

// import javax.print.Doc;

import org.bson.Document;
import org.bson.types.ObjectId;

// import java.sql.*;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;



public class DAOevenment implements GenericDAO<Evenments>{

    MongoCollection<Document> Event;



    public DAOevenment(){
        MongoDatabase database=Config.getbasedonnee();
        this.Event=database.getCollection("evenements");
    }
    public DAOevenment(MongoDatabase database){
         database=Config.getbasedonnee();
        this.Event=database.getCollection("evenements");
    }
    @Override
    public void insert(ClientSession session,Evenments event){
        Document doc=new Document("nom_event",event.getNom_event())
                .append("date_event",event.getDate_event())
                .append("description",event.getDescription())
                .append("id_user",event.getId_user());
       Event.insertOne(session,doc);
    }

    @Override
    public void Delete(String id,ClientSession session) {
       Event.deleteOne(session,eq("_id",new ObjectId(id)));
       
    }

    
    @Override
    public List<Evenments> findall(){

        List<Evenments> listEvents=new ArrayList<>();
        for(Document doc:Event.find()){
            Evenments event=new Evenments();
            java.util.Date recuperer_date=doc.getDate("date_event");
            event.setDate_event(new java.sql.Date(recuperer_date.getTime()));
            event.setDescription(doc.getString("description"));
            event.setNom_event(doc.getString("nom_event"));
            event.setId_user(doc.getString("id_user"));
            event.setId_event(doc.getObjectId("_id"));
            listEvents.add(event);
        }
        return listEvents;
    }

    
    @Override
     public void update(String id,Evenments event,ClientSession session){

        Document doc=new Document("nom_event",event.getNom_event())
        .append("date_event",event.getDate_event())
        .append("description",event.getDescription())
        .append("id_user",event.getId_user());

        Document docOperation=new Document("$set",doc);
        this.Event.updateOne(session,new Document("_id",new ObjectId(id)),docOperation);
    }
      @Override
     public Evenments get(String id){
    Document doc = Event.find(eq("_id", new ObjectId(id))).first(); 
    Evenments event = new Evenments(); 
    if (doc!=null){
        java.util.Date recuper_date=doc.getDate("date_event"); 
        event.setDate_event(new java.sql.Date(recuper_date.getTime()));
        event.setDescription(doc.getString("description"));
        event.setNom_event(doc.getString("nom_event"));
        event.setId_user(doc.getString("id_user"));
        event.setId_event(doc.getObjectId("_id"));
    }
    return event;

    }

    
    
}
