package demo.quickStart.DAO;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Config {
    static final String url="mongodb+srv://rachidaarag:rachidaarag@cluster0.k7ehw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
    static final String database_name="mini-project-java";


    static  MongoClient client;
    static{
        client=MongoClients.create(url);
    }
    public static MongoClient getClient(){
        return client;
    }

    public static MongoDatabase getbasedonnee(){
        if(client==null){
            client=MongoClients.create(url);
            return client.getDatabase(database_name);
        }
        return client.getDatabase(database_name);
    }
}
