package demo.quickStart.DAO;


import static com.mongodb.client.model.Filters.eq;

// import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import demo.quickStart.models.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.ClientSession;
public class DAOUser implements GenericDAO<Utilisateur>{
    MongoCollection<Document> user;


    public DAOUser(MongoDatabase database){
         database=Config.getbasedonnee();
        this.user=database.getCollection("utilisateurs");
    }
    public DAOUser( ){
        MongoDatabase database=Config.getbasedonnee();
       this.user=database.getCollection("utilisateurs");
   }
   public MongoCollection<Document> getcollection(){
    return this.user;
   }

    @Override
    public void insert(ClientSession session, Utilisateur user){
        Document document = new Document("nom", user.getNom())
        .append("prenom", user.getPrenom())
        .append("email", user.getEmail())
        .append("type", user.getType())
        .append("password", user.getpassword());

        this.user.insertOne(session,document);
    }

    @Override
    public void Delete(String id,ClientSession session){
        this.user.deleteOne(session,eq("_id",new ObjectId(id)));
    }

    @Override
    public List<Utilisateur> findall(){
        List<Utilisateur> listusers=new ArrayList<>();
            for(Document Doc:user.find()){
                Utilisateur user=new Utilisateur();
                user.setNom(Doc.getString("nom"));
                user.setPrenom(Doc.getString("prenom"));
                user.setEmail(Doc.getString("email"));
                user.setId_user(Doc.getObjectId("_id"));
                user.setType(Doc.getString("type"));
                user.setpassword(Doc.getString("password"));
                listusers.add(user);
            }
            return listusers;
    }
    @Override 
    public Utilisateur get(String id){
        Document doc=user.find(eq("_id",new ObjectId(id))).first();
        Utilisateur user=new Utilisateur();
        if(doc!=null){
            user.setNom(doc.getString("nom"));
            user.setPrenom(doc.getString("prenom"));
            user.setEmail(doc.getString("email"));
            user.setType(doc.getString("type"));
            user.setpassword(doc.getString("password"));
        }
        return user;

    }
    @Override
    public void update(String id, Utilisateur utilisateur,ClientSession session){
        Document updatedDoc = new Document();
        
        if (utilisateur.getNom()!=null) {
            updatedDoc.append("nom", utilisateur.getNom());
        }
        if (utilisateur.getPrenom()!=null) {
            updatedDoc.append("prenom", utilisateur.getPrenom());
        }
        if (utilisateur.getEmail()!=null) {
            updatedDoc.append("email", utilisateur.getEmail());
        }
        if (utilisateur.getType()!=null) {
            updatedDoc.append("type", utilisateur.getType());
        }
        if (utilisateur.getpassword()!=null) {
            updatedDoc.append("password", utilisateur.getpassword());
        }
        Document docOperation=new Document("$set",updatedDoc);
        this.user.updateOne(session,new Document("_id",new ObjectId(id)),docOperation);
    }

    
    
}
