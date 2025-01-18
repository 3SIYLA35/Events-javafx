package demo.quickStart;
import demo.quickStart.DAO.*;
import demo.quickStart.models.*;

import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



import com.mongodb.client.ClientSession;

public class DAOTerrainTest {
     private DAOterrain  daoterrain;
    static final String url="mongodb+srv://rachidaarag:rachidaarag@cluster0.k7ehw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    static final String database_name="test";
        static  MongoClient client;

    @BeforeEach
    public void setUp(){
        client=MongoClients.create(url);
        MongoDatabase database=client.getDatabase("test");
        database.getCollection("terrains").deleteMany(new Document());
        daoterrain=new DAOterrain();
    }
    @Test
    public void testInsert(){
        Terrains terrain=new Terrains();
        terrain.setNom_terrain("test");
        terrain.setType("terrain");
        try (ClientSession session = Config.getClient().startSession()){
            session.startTransaction();
            daoterrain.insert(session,terrain);
            session.commitTransaction();
        }
        MongoDatabase database=Config.getbasedonnee();
        MongoCollection<Document> terrains=database.getCollection("terrains");
        Document doc=terrains.find(new Document("nom_terrain","test")).first();
        assertEquals("test",doc.getString("nom_terrain"));
    }
    @Test
    public void testDelete(){
        Terrains terrain=new Terrains();
        terrain.setNom_terrain("test");
        terrain.setType("terrain");
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoterrain.insert(session,terrain);
            session.commitTransaction();
        }
        List<Terrains> deleted_terrain=daoterrain.findall();
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoterrain.Delete(deleted_terrain.get(0).getId_terrain().toString(),session);
            session.commitTransaction();
        }
        MongoDatabase database=Config.getbasedonnee();
        MongoCollection<Document> terrains=database.getCollection("terrains");
        Document doc=terrains.find(new Document("nom_terrain","test")).first();
        assertEquals(null,doc);

    }
    @Test
    public void testUpdate(){
        Terrains terrain=new Terrains();
        terrain.setNom_terrain("test");
        terrain.setType("terrain");
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoterrain.insert(session,terrain);
            session.commitTransaction();
        }
        terrain.setNom_terrain("test2");
        List<Terrains> updated_terrain=daoterrain.findall();
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoterrain.update(updated_terrain.get(0).getId_terrain().toString(),terrain,session);
            session.commitTransaction();
        }
        MongoDatabase database=Config.getbasedonnee();
        MongoCollection<Document> terrains=database.getCollection("terrains");
        Document doc=terrains.find(new Document("nom_terrain","test2")).first();
        assertEquals("test2",doc.getString("nom_terrain"));
    }
}
