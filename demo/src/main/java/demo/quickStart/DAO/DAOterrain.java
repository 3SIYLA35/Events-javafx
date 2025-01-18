package demo.quickStart.DAO;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;


import demo.quickStart.models.*;


import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DAOterrain implements GenericDAO<Terrains>{
    MongoCollection<Document> terrain;

    public DAOterrain(){
        MongoDatabase database=Config.getbasedonnee();
        terrain=database.getCollection("terrains");

    }

    @Override
    public void insert(ClientSession session,Terrains terrain){
        Document doc=new Document("nom_terrain",terrain.getNom_terrain())
        .append("type",terrain.getType());
        this.terrain.insertOne(session,doc);
        
    }

    @Override
    public void Delete(String id,ClientSession session) {
        terrain.deleteOne(session,eq("_id",new ObjectId(id)));
    }

    @Override
    public List<Terrains> findall(){
            List<Terrains> listterrains=new ArrayList<>();
            for(Document doc:terrain.find()){
                Terrains terrain=new Terrains();
                terrain.setNom_terrain(doc.getString("nom_terrain"));
                terrain.setType(doc.getString("type"));
                terrain.setId_terrain(doc.getObjectId("_id"));
                listterrains.add(terrain);
            }
            return listterrains;
    }

    @Override
    public void update(String id, Terrains terrain,ClientSession session) {
        Document doc=new Document("nom_terrain",terrain.getNom_terrain())
        .append("type", terrain.getType());
        Document docOperation=new Document("$set",doc);
        this.terrain.updateOne(session,new Document("_id",new ObjectId(id)),docOperation);
    }
    @Override
    public Terrains get(String id){
        Document doc=terrain.find(eq("_id",new ObjectId(id))).first();
        Terrains terrain=new Terrains();
        if(doc!=null){
            terrain.setNom_terrain(doc.getString("nom_terrain"));
            terrain.setType(doc.getString("type"));
        }
        return terrain;
    };
    

}