package demo.quickStart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.util.List;
// import demo.quickStart.DAO.*;
import demo.quickStart.models.*;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOevenment;

import com.mongodb.client.ClientSession;

public class DAOEvenementTest {

    private DAOevenment daoEvenement;
    static final String url="mongodb+srv://rachidaarag:rachidaarag@cluster0.k7ehw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    static final String database_name="test";
        static  MongoClient client;

    @BeforeEach
    public void setUp(){
        client=MongoClients.create(url);
        MongoDatabase database=client.getDatabase("test");
        database.getCollection("evenements").deleteMany(new Document());
        daoEvenement=new DAOevenment(database);
    }
    @Test
    public void testInsert(){
        Evenments event=new Evenments();
        event.setNom_event("test");
        event.setDate_event(new Date(System.currentTimeMillis()));
        event.setDescription("test");
        try (ClientSession session = Config.getClient().startSession()){
            session.startTransaction();
            daoEvenement.insert(session,event);
            session.commitTransaction();
        }
        MongoDatabase database=Config.getClient().getDatabase("test");
        MongoCollection<Document> evenements=database.getCollection("evenements");
        Document check_insert=evenements.find(new Document("nom_event","test")).first();
        assertNotNull(check_insert);
        assertEquals("test",check_insert.getString("description"));
        assertEquals(event.getDate_event(),check_insert.getDate("date_event"));
    }

    @Test
    public void testUpdate(){
        Evenments event=new Evenments();
        event.setNom_event("test");
        event.setDate_event(new java.sql.Date(new java.util.Date().getTime()));
        event.setDescription("test");
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoEvenement.insert(session,event);
            session.commitTransaction();
        }
        List<Evenments> events=daoEvenement.findall();
        events.get(0).setDescription("test2");
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoEvenement.update(events.get(0).getId_event().toString(),events.get(0),session);
            session.commitTransaction();
        }
        
            Evenments event_update=daoEvenement.get(events.get(0).getId_event().toString());
            assertEquals("test2",event_update.getDescription());
        
    }

    @Test
    public void testDelete(){
        Evenments event=new Evenments();
        event.setNom_event("test");
        event.setDate_event(new java.sql.Date(new java.util.Date().getTime()));
        event.setDescription("test");
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoEvenement.insert(session,event);
            session.commitTransaction();
        }
        List<Evenments> events=daoEvenement.findall();
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoEvenement.Delete(events.get(0).getId_event().toString(),session);
            session.commitTransaction();
        }
        MongoDatabase database=Config.getClient().getDatabase("test");
        MongoCollection<Document> evenements=database.getCollection("evenements");
        double count=evenements.countDocuments();
        assertEquals(0,count);



    }

}
