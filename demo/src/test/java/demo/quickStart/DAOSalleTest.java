package demo.quickStart;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOsalle;
// import demo.quickStart.DAO.*;
import demo.quickStart.models.*;

import com.mongodb.client.ClientSession;

public class DAOSalleTest {
    
    private DAOsalle daoSalle;
    static final String url="mongodb+srv://rachidaarag:rachidaarag@cluster0.k7ehw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    static final String database_name="test";
        static  MongoClient client;

    @BeforeEach
    public void setUp(){
        client=MongoClients.create(url);
        MongoDatabase database=client.getDatabase("test");
        database.getCollection("salles").deleteMany(new Document());
        daoSalle=new DAOsalle();
    }
    @Test
    public void testInsert(){
        Salles salle=new Salles();
        salle.setNom_salle("test");
        salle.setCapacite(100);
        try (ClientSession session = Config.getClient().startSession()){
            session.startTransaction();
            daoSalle.insert(session,salle);
            session.commitTransaction();
        }
        MongoDatabase database=Config.getbasedonnee();
        MongoCollection<Document> salles=database.getCollection("salles");
        Document doc=salles.find(new Document("nom_salle","test")).first();
        assertEquals("test",doc.getString("nom_salle"));
    }
    @Test
    public void testUpdate(){
        Salles salle=new Salles();
        salle.setNom_salle("test");
        salle.setCapacite(100);
        try (ClientSession session = Config.getClient().startSession()){
            session.startTransaction();
            daoSalle.insert(session,salle);
            session.commitTransaction();
        }
        salle.setNom_salle("test2");
        List<Salles> updated_salle=daoSalle.findall();
        try (ClientSession session = Config.getClient().startSession()){
            session.startTransaction();
            daoSalle.update(updated_salle.get(0).getId_salle().toString(),salle,session);
            session.commitTransaction();
        }
        MongoDatabase database=Config.getbasedonnee();
        MongoCollection<Document> salles=database.getCollection("salles");
        Document doc=salles.find(new Document("nom_salle","test2")).first();
        assertEquals("test2",doc.getString("nom_salle"));
    }
    @Test
    public void testDelete(){
        Salles salle=new Salles();
        salle.setNom_salle("test");
        salle.setCapacite(100);
        try (ClientSession session = Config.getClient().startSession()){
            session.startTransaction();
            daoSalle.insert(session,salle);
            session.commitTransaction();
        }
        List<Salles> updated_salle=daoSalle.findall();
        try (ClientSession session = Config.getClient().startSession()){
            session.startTransaction();
            daoSalle.Delete(updated_salle.get(0).getId_salle().toString(),session);
            session.commitTransaction();
        }
        MongoDatabase database=Config.getbasedonnee();
        MongoCollection<Document> salles=database.getCollection("salles");
        double count=salles.countDocuments(new Document("nom_salle","test"));
        assertEquals(0,count);
    }
}
