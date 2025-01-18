package demo.quickStart;

import java.sql.Date;
import java.util.List;

import org.bson.Document;
// import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
// import com.mongodb.internal.connection.Time;
// import demo.quickStart.DAO.*;
import demo.quickStart.models.*;
import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOreservation;

public class DAOReservationTest {
    private DAOreservation daoreservation;
    static final String url="mongodb+srv://rachidaarag:rachidaarag@cluster0.k7ehw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    static final String database_name="test";
        static  MongoClient client;

    @BeforeEach
    public void setUp(){
        client=MongoClients.create(url);
        MongoDatabase database=client.getDatabase("test");
        database.getCollection("reservations").deleteMany(new Document());
        daoreservation=new DAOreservation();
    }
    @Test
    public void testInsert(){
        Reservations reservation=new Reservations();
        reservation.setDate_reservation(new Date(System.currentTimeMillis()));
        reservation.setId_event("666666666666666666666666");
        reservation.setId_user("666666666666666666666666");
        reservation.setId_terrain("666666666666666666666666");
        reservation.setId_salle("666666666666666666666666");
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoreservation.insert(session,reservation);
            session.commitTransaction();
        }
        MongoCollection<Document> reservations=Config.getbasedonnee().getCollection("reservations");
        Document doc=reservations.find(new Document("id_user","666666666666666666666666")).first();
        assertEquals("666666666666666666666666",doc.getString("id_user"));
    }
    @Test
    public void testDelete(){
      Reservations reservation=new Reservations();
      reservation.setId_user("666666666666666666666666");
      reservation.setId_event("666666666666666666666666");
      reservation.setId_terrain("666666666666666666666666");
      reservation.setId_salle("666666666666666666666666");
      reservation.setDate_reservation(new Date(System.currentTimeMillis()));
      try (ClientSession session=Config.getClient().startSession()){
        session.startTransaction();
        daoreservation.insert(session,reservation);
        session.commitTransaction();}
        List<Reservations> deleted_reservation=daoreservation.findall();
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoreservation.Delete(deleted_reservation.get(0).getId_reservation().toString(),session);
            session.commitTransaction();
        }
        MongoCollection<Document> reservations=Config.getbasedonnee().getCollection("reservations");
        double count=reservations.countDocuments(new Document("id_user","666666666666666666666666"));
        assertEquals(0,count);

    }

    @Test
    public void testUpdate(){
        Reservations reservation=new Reservations();
        reservation.setId_user("666666666666666666666666");
        reservation.setId_event("666666666666666666666666");
        reservation.setId_terrain("666666666666666666666666");
        reservation.setId_salle("666666666666666666666666");
        reservation.setDate_reservation(new Date(System.currentTimeMillis()));
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoreservation.insert(session,reservation);
            session.commitTransaction();
        }
        reservation.setId_user("555555555555555555555555");
        List<Reservations> list=daoreservation.findall();
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoreservation.update(list.get(0).getId_reservation().toString(),reservation,session);
            session.commitTransaction();
        }
        MongoCollection<Document> reservations=Config.getbasedonnee().getCollection("reservations");
        Document doc=reservations.find(new Document("id_user","555555555555555555555555")).first();
        assertNotNull(doc,"reservation matbeltx ");
        assertEquals("555555555555555555555555",doc.getString("id_user"));
    }
}


