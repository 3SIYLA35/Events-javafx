package demo.quickStart.DAO;

import static com.mongodb.client.model.Filters.eq;

// import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import demo.quickStart.models.*;


import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
public class DAOsalle implements GenericDAO<Salles>{
    MongoCollection<Document> salles;

    public DAOsalle(){
        MongoDatabase database=Config.getbasedonnee();
        salles=database.getCollection("salles");
    
    }

    @Override
    public void insert(ClientSession session,Salles salle) {
       Document doc=new Document("nom_salle",salle.getNom_salle())
       .append("capacite", salle.getCapacite());

       salles.insertOne(session,doc);
    }

    @Override
    public void Delete(String id,ClientSession session) {

        salles.deleteOne(session,eq("_id",new ObjectId(id)));
    }

    @Override
    public List<Salles> findall() {
        List<Salles> listsalles=new ArrayList<>();
        for(Document doc:salles.find()){
            Salles sale=new Salles();
            sale.setCapacite(doc.getInteger("capacite"));
            sale.setNom_salle(doc.getString("nom_salle"));
            sale.setId_salle(doc.getObjectId("_id"));
            listsalles.add(sale);
        }
        return listsalles;
    }

    @Override
    public void update(String id, Salles event,ClientSession session){
        Document doc=new Document("nom_salle",event.getNom_salle())
        .append("capacite",event.getCapacite());
        Document docOperation=new Document("$set",doc);
        this.salles.updateOne(session,new Document("_id",new ObjectId(id)),docOperation);
    };
    @Override
    public Salles get(String id){
        Document doc=salles.find(eq("_id",new ObjectId(id))).first();
        Salles salle=new Salles();
        if(doc!=null){
            salle.setNom_salle(doc.getString("nom_salle"));
            salle.setCapacite(doc.getInteger("capacite"));
        }
        return salle;
    };
    
    
}
