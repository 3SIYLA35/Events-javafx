package demo.quickStart;

import org.bson.Document;
// import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import demo.quickStart.DAO.Config;
import demo.quickStart.DAO.DAOUser;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
// import org.bson.Document;
// import org.bson.types.ObjectId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
// import demo.quickStart.DAO.*;
import demo.quickStart.models.*;

import java.util.List;
public class DAOUserTest {
    private DAOUser daoUser;
    static final String url="mongodb+srv://rachidaarag:rachidaarag@cluster0.k7ehw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    static final String database_name="test";
        static  MongoClient client;

     @BeforeEach
     public void setUp(){
        client=MongoClients.create(url);
        MongoDatabase database=client.getDatabase("test");
        database.getCollection("utilisateurs").deleteMany(new Document());
         daoUser=new DAOUser(database);
    }


    @Test
    public void testInsert(){
        Utilisateur user=new Utilisateur();
        user.setNom("ali");
        user.setPrenom("ali");
        user.setEmail("ali@gmail.com");
        user.setType("Professeur");
        user.setpassword("aaaa");
        try (ClientSession session=Config.getClient().startSession()){
            session.startTransaction();
            daoUser.insert( session,user);
            session.commitTransaction();
        }catch(Exception e) {
            System.err.println(" error transaction : " + e.getMessage());
        }

        MongoDatabase database=Config.getClient().getDatabase("test");
        MongoCollection<Document> utilisateur =database.getCollection("utilisateurs");
        Document check_insert=utilisateur.find(new Document("email","ali@gmail.com")).first();
        assertNotNull(check_insert,"error f l'insertion dyal objet");
        assertEquals("ali@gmail.com",check_insert.get("email"));
    }


    @Test
    public void test_update(){
        Utilisateur user=new Utilisateur();
        user.setNom("kaarim");
        user.setPrenom("karim");
        user.setEmail("karim@gmail.com");
        user.setType("etudiant");
        user.setpassword("aaaa");
        try (ClientSession session = Config.getClient().startSession()) {
            session.startTransaction();
            daoUser.insert( session,user); 
            session.commitTransaction();
        }catch(Exception e) {
            System.err.println(" error transaction : " + e.getMessage());
        }
        List<Utilisateur> users=daoUser.findall();
        users.forEach(u->System.out.println("ID: "+u.getId_user()+", nom:"+u.getNom()+", prenom:"+u.getPrenom()+",Email: "+u.getEmail()));
        String userID=users.get(0).getId_user().toHexString();

        Utilisateur updateduser=new Utilisateur();
        updateduser.setNom("karima");
        updateduser.setPrenom("karima");
        updateduser.setpassword("bbbb");
        try (ClientSession session=Config.getClient().startSession()) {
            session.startTransaction();
            daoUser.update(userID,updateduser,session);
            session.commitTransaction();
        }catch(Exception e) {
            System.err.println(" error transaction : " + e.getMessage());
        }
        MongoDatabase database=Config.getClient().getDatabase("test");
        MongoCollection<Document> utilisateur=database.getCollection("utilisateurs");
        Document check_update=utilisateur.find(new Document("nom","karima")).first();
        assertNotNull(check_update,"error f la mise a jour dyal objet");
        assertEquals("karima",check_update.get("nom"));
    }


    @Test
    public void test_delete(){
        Utilisateur user=new Utilisateur();
        user.setNom("yoyo");
        user.setPrenom("yoyo");
        user.setEmail("yoyo@gmail.com");
        user.setType("Professeur");
        user.setpassword("aaaa");
        try (ClientSession session=Config.getClient().startSession()) {
            session.startTransaction();
            daoUser.insert(session,user);
            session.commitTransaction();
        }catch(Exception e) {
            System.err.println("error transaction : " + e.getMessage());
        }
        List<Utilisateur> users=daoUser.findall();
        users.forEach(u->System.out.println("ID: "+u.getId_user()+", nom:"+u.getNom()+", prenom:"+u.getPrenom()+",Email: "+u.getEmail()));
        String userID=users.get(0).getId_user().toHexString();
        try (ClientSession session=Config.getClient().startSession()) {
            session.startTransaction();
            daoUser.Delete(userID,session);
            session.commitTransaction();
        }catch(Exception e) {
            System.err.println("error transaction:" + e.getMessage());
        }
        MongoDatabase database=Config.getClient().getDatabase("test");
        MongoCollection<Document> utilisateur=database.getCollection("utilisateurs");
        double check_delete=utilisateur.countDocuments(new Document("nom","ali"));
        assertNotNull(check_delete,"error f la suppression dyal objet");
        assertEquals(0,check_delete);
    }



}
