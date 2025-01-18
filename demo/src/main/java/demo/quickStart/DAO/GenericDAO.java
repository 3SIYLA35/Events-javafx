package demo.quickStart.DAO;

import java.util.List;

// import org.bson.Document;

import com.mongodb.client.ClientSession;
// import com.mongodb.session.ClientSession;


interface GenericDAO<T>{
   public  void insert(ClientSession session,T user1);
    public void Delete(String id,ClientSession session);
   public  List<T> findall();
    public  void update(String id,T entity,ClientSession session);
    public T get(String id);
}