package demo.quickStart;

import com.mongodb.client.MongoClient;
import com.mongodb.client.ClientSession;
import com.mongodb.client.TransactionBody;

import demo.quickStart.DAO.Config;

public class TransactionManager {
    private final MongoClient client;

    public TransactionManager(){
        this.client=Config.getClient();
    }
    
    public <T> T  executeInTransaction(TransactionBody<T> operation){
        try(ClientSession session=client.startSession()){
            session.startTransaction();
            try{
                T result=(T) operation.execute();
                session.commitTransaction();
                System.out.println("transaction committed successfully");
                return result;
            }catch(Exception e){
                session.abortTransaction();
                System.err.println("error ");

            }
        }
                return null;
}
}
