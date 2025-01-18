package demo.quickStart;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;





public class QuickStart extends Application {

   
    // @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        launch(args);


        //  GenericDAO<Utilisateur> daoUser;
        //  String url="mongodb+srv://rachidaarag:rachidaarag@cluster0.k7ehw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
          
        // daoUser=new DAOUser();
        
        
        // try (MongoClient mongoClient = MongoClients.create(url)) {
        //     MongoDatabase database = mongoClient.getDatabase("test");
        //     database.getCollection("utilisateurs").deleteMany(new Document());
        //     System.out.println("Connected to database: " + database.getName());
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        // TransactionManager transactionManager=new TransactionManager();
        // transactionManager.executeInTransaction(new TransactionBody(){
        //     @Override
        //     public Object execute() {
        //         ClientSession session=Config.getClient().startSession();
        //         Utilisateur user=new Utilisateur();
        //              user.setNom("kaarim");
        //              user.setPrenom("karim");
        //              user.setEmail("karim@gmail.com");
        //              user.setType("etudiant");
        //              daoUser.insert(session,user);
        //         return null;
        //     }
        // });

        // MongoDatabase database=Config.getClient().getDatabase("test");
        // MongoCollection<Document> utilisateur =database.getCollection("utilisateurs");
        // double count =utilisateur.countDocuments(new Document("email","karim@gmail.com"));
        // System.out.println(count);





        // TransactionManager transactionManager=new TransactionManager();
        // GenericDAO<Utilisateur> userdao=new DAOUser();
        // transactionManager.executeInTransaction(new TransactionBody(){
        //     @Override
        //     public Object execute() {
        //         ClientSession session=Config.getClient().startSession();
        //         Utilisateur user=new Utilisateur();
        //         user.setNom("iiiiiiiiii");
        //         user.setEmail("iiiiiii@gmail.com");
        //         user.setPrenom("bbbbbb");
        //         user.setType("etudiant");
        //         userdao.insert(session,user);
        //         return null;
        //     }
        // });
        // Utilisateur user=new Utilisateur();
        // user.setNom("rachid");
        // user.setEmail("rachidaraq@gmail.com");
        // user.setPrenom("araq");
        // user.setType("etudiant");
        // Utilisateur user1=new Utilisateur();
        // user1.setNom("ali");
        // user1.setEmail("ali124@gmail.com");
        // user1.setPrenom("aai");
        // user1.setType("etudiant");
        // userdao.insert(user);
        // userdao.insert(user1);
        // List<Utilisateur> users=userdao.findall();
        // users.forEach(u->System.out.println("ID: "+u.getId_user()+", nom:"+u.getNom()+", prenom:"+u.getPrenom()+",Email: "+u.getEmail()));


        // GenericDAO<Evenments> eventdao=new DAOevenment();
        // Evenments event=new Evenments();
        // event.setNom_event("festival");
        // event.setDescription("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // event.setId_user("676293a87a0c3d787ad57b59");
        // eventdao.insert(event);
        // List<Evenments> events=eventdao.findall();
        // events.forEach(e->System.out.println(", nom:"+e.getNom_event()+", description:"+e.getDescription()+", id_user:"+e.getId_user()));

        // GenericDAO<Salles> saledao=new DAOsalle();
        // Salles salle=new Salles();
        // salle.setNom_salle("salle TP3");
        // salle.setCapacite(80);
        // saledao.insert(salle);
        // Salles salle1=new Salles();
        // salle1.setNom_salle("salle TP6");
        // salle1.setCapacite(120);
        // saledao.insert(salle1);
        // List<Salles> salles=saledao.findall();
        // salles.forEach(e->System.out.println("nom de la salle :"+e.getNom_salle()+"la capacite de la salle "+e.getCapacite()));

        // GenericDAO<Terrains> terrainsdao=new DAOterrain();
        // Terrains terrain=new Terrains();
        // terrain.setNom_terrain("terrain football");
        // terrain.setType("football");
        // terrainsdao.insert(terrain);
        // Terrains terrain1=new Terrains();
        // terrain1.setNom_terrain("terrain basket ball");
        // terrain1.setType("basket ball");
        // terrainsdao.insert(terrain1);
        // List<Terrains> terrains=terrainsdao.findall();
        // terrains.forEach(e->System.out.println("nom terrain :"+e.getNom_terrain()+"type de terrain :"+e.getType()));

        // GenericDAO<Reservations> reservationdao=new DAOreservation();
        // Reservations reservation=new Reservations();
        // reservation.setId_event("676295bb071d1015d6c83351");
        // reservation.setId_salle("6762965c687d20632b36a23d");
        // reservation.setId_terrain("676296ee3b80c97669018046");
        // reservation.setId_user("676293a87a0c3d787ad57b59");
        // reservation.setDate_reservation(new Date());
        // reservationdao.insert(reservation);
        // Reservations reservations1=new Reservations();
        // reservations1.setId_event("676295bb071d1015d6c83351");
        // reservations1.setId_salle("6762965c687d20632b36a23d");
        // reservations1.setId_terrain("676296ee3b80c97669018046");
        // reservations1.setId_user("676293a57a0c3d787ad57b58");
        // reservations1.setDate_reservation(new Date());
        // reservationdao.insert(reservations1);
        // List<Reservations> reservations=reservationdao.findall();
        // reservations.forEach(e->System.out.println("id_user :"+e.getId_user()+"id_event= "+e.getId_event()+"id_terrain= "+e.getId_terrain()+"date: "+e.getDate_reservation()+"id_salle"+e.getId_salle()));

        //test users
        // GenericType<Utilisateur> userDAO=new DAOUser();

        // Utilisateur user=new Utilisateur();
        // user.setNom("aaaaaa");
        // user.setEmail("aaaaaaaaaa11@gmail.com");
        // user.setPrenom("ccccc");
        // user.setType("etudiant");
        // userDAO.insert(user);

        // List<Utilisateur> users=userDAO.findall();
        // users.forEach(u->System.out.println("ID: "+u.getId_user()+", nom:"+u.getNom()+", prenom:"+u.getPrenom()+",Email: "+u.getEmail()));
        
        // userDAO.Delete("6751ddea7336e429bdffbfa2");



        // GenericDAO<Evenments> eventdao=new DAOevenment();
        // Evenments event=new Evenments();
        
        // event.setDescription("aaaaaaaaaaaaaaaaaaaaaaaaa");
        
        // List<Evenments> events=eventdao.findall();
        // events.forEach(e->System.out.println("Id: "+e.getId_user()+" event name: "+e.getNom_event()+" description: "+e.getDescription()+" date: "+e.getDate_event()));

        // GenericDAO<Salles> saledao=new DAOsalle();
        // Salles salle=new Salles();
        // salle.setCapacite(250);
        // salle.setNom_salle("salle 20");
        // saledao.insert(salle);
        // List<Salles> salles=saledao.findall();
        // salles.forEach(e->System.out.println("nom de la salle :"+e.getNom_salle()+"la capacite de la salle "+e.getCapacite()));


        // GenericDAO<Terrains> terrainsdao=new DAOterrain();
        // Terrains terrain=new Terrains();
        // terrain.setNom_terrain("terrain est ");
        // terrain.setType("football");
        // terrainsdao.insert(terrain);
        // List<Terrains> listterrains=terrainsdao.findall();
        // listterrains.forEach(e->System.out.println("nom terrain :"+e.getNom_terrain()+"type de terrain :"+e.getType()));

        // DAOreservation reservationdao=new DAOreservation();
        // Reservations reservation=new Reservations();
        // reservation.setDate_reservation(new Date());
        // reservation.setId_event("6751fc839062fa693051a5d4");
        // reservation.setId_terrain("67521bc844de6a67bc3b0084");
        // reservation.setId_salle("675217254fe65c4998cef537");
        // reservation.setId_user("6751b8df2140221b1b8306ff");
        // reservationdao.insert(reservation);


        // Reservations reservation=new Reservations();
        // reservation.setId_event("6751fc839062fa693051a5d4");
        //         reservation.setId_terrain("67521bc844de6a67bc3b0084");
        //         reservation.setId_salle("675217254fe65c4998cef537");
        // reservation.setDate_reservation(new Date());
        // reservation.setId_user("6751b8df2140221b1b830700");
        // reservationdao.insert(reservation);
        // if(reservationdao.disponibilite("6751fc839062fa693051a5d4","salle 20")!=1){
        //     System.out.println("sold out");
        // }
        // else{
        //     System.out.println("reserver votre ticket");
        // }

        // List<Reservations> listreservation=reservationdao.findall();
        // listreservation.forEach(e->System.out.println("id_user :"+e.getId_user()+"id_event= "+e.getId_event()+"id_terrain= "+e.getId_terrain()+"date: "+e.getDate_reservation()+"id_salle"+e.getId_salle()));
        

        // String uri="mongodb+srv://rachidaarag:rachidaarag@cluster0.k7ehw.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

        // try (MongoClient mongoClient=MongoClients.create(uri)) {
        //     MongoDatabase database=mongoClient.getDatabase("mini-project-java");
        //     MongoCollection<Document> utilisateurs=database.getCollection("utilisateurs");
        //     MongoCollection<Document> evenements=database.getCollection("evenements");
        //     MongoCollection<Document> salles=database.getCollection("salles");
        //     MongoCollection<Document> terrains=database.getCollection("terrains");
        //     MongoCollection<Document> reservations=database.getCollection("reservations");


        //     Scanner scanner=new Scanner(System.in);
        //     System.out.println("donner le id de l'utilisateur ");
        //     int id_user=scanner.nextInt();

        //     System.out.println("donner le nom de l'utilisateur ");
        //     String nom=scanner.next();

        //     System.out.println("donner le prenom de l'utilisateur ");
        //     String prenom=scanner.next();

        //     System.out.println("donner l'email de l'utilisateur ");
        //     String email=scanner.next();


        //     System.out.println("donner le type de l'utilisateur ");
        //     String type=scanner.next();

        //     System.out.println("Insertion Utilisateur");
        //             Document utilisateur1=new Document("id_user",id_user)
        //             .append("nom", nom)
        //             .append("prenom",prenom)
        //             .append("email",email)
        //             .append("type",type);

                



            

                // System.out.println("Insertion Utilisateur");
                //     Document utilisateur1=new Document("id_user", 1)
                //     .append("nom", "rachid")
                //     .append("prenom","aarag")
                //     .append("email","rachidaraq108@gmail.com")
                //     .append("type","etudiant");

                // Document utilisateur2=new Document("id_user", 2)
                //     .append("nom", "karim")
                //     .append("prenom","aarag")
                //     .append("email","karimmmm108@gmail.com")
                //     .append("type","etudiant");

                // Document utilisateur3=new Document("id_user", 2)
                //     .append("nom", "ali")
                //     .append("prenom","aarag")
                //     .append("email","aliiii@gmail.com")
                //     .append("type","etudiant");

              
                //     utilisateurs.insertOne(utilisateur1);
                //     utilisateurs.insertOne(utilisateur2);
                //     utilisateurs.insertOne(utilisateur3);
                
                // System.out.println("successfully for all  utilisateurs ");




                // System.out.println("Insertion evenments");
                //     Document evenement1=new Document("id_event", 1)
                //     .append("nom_event", "concert")
                //     .append("date_event","2024-12-31")
                //     .append("description","nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn")
                //     .append("id_user","2");

                // Document evenement2=new Document("id_event", 2)
                //     .append("nom_event", "conference")
                //     .append("date_event","2024-01-15")
                //     .append("description","ccccccccccccccccccccccccccc")
                //     .append("id_user","3");

                // Document evenement3=new Document("id_event", 3)
                //     .append("nom_event", "festival")
                //     .append("date_event","2024-01-25")
                //     .append("description","vvvvvvvvvvvvvvvvvvvv")
                //     .append("id_user","2");

              
                //     evenements.insertOne(evenement1);
                //     evenements.insertOne(evenement2);
                //     evenements.insertOne(evenement3);
                
                // System.out.println("successfully for all evenements ");





                // System.out.println("Insertion salles");
                //     Document salle1=new Document("id_salle", 1)
                //     .append("nom_salle", "salle 2")
                //     .append("capacite","120");
                    

                // Document salle2=new Document("id_salle", 2)
                //     .append("nom_salle", "salle 5")
                //     .append("capacite","64");
                    

                // Document salle3=new Document("id_salle", 3)
                //     .append("nom_salle", "salle 19")
                //     .append("capacite","70");
                    

              
                //     salles.insertOne(salle1);
                //     salles.insertOne(salle2);
                //     salles.insertOne(salle3);
                
                // System.out.println("successfully for all salles ");





                // System.out.println("Insertion terrain");
                //     Document terrain1=new Document("id_terrain", 1)
                //     .append("nom_salle", "salle 2")
                //     .append("capacite","120");
                    

                // Document terrain2=new Document("id_terrain", 2)
                //     .append("nom_terrain", "terrain football")
                //     .append("type","64");
                    

                    

              
                //     terrains.insertOne(terrain1);
                //     terrains.insertOne(terrain2);
                
                // System.out.println("successfully for all terrains ");




                // System.out.println("Insertion reservation");
                //     Document reservation1=new Document("id_reservation", 1)
                //     .append("id_user", 1)
                //     .append("id_event",3)
                //     .append("id_terrain",1)
                //     .append("date_reservation",new Date());
                    

                // Document reservation2=new Document("id_reservation", 2)
                //     .append("id_user", 1)
                //     .append("id_event",3)
                //     .append("id_terrain",1)
                //     .append("date_reservation",new Date());
                    

                    

              
                //     reservations.insertOne(reservation1);
                //     reservations.insertOne(reservation2);
                
                // System.out.println("successfully for all reservations ");





        }

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root=FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene=new Scene(root);
        // Button Event_btn=new Button();
        // Event_btn.getStyleClass().add("Event_btn");
        // String css_file=getClass().getResource("/style/style.css").toExternalForm();
        // scene.getStylesheets().add(css_file);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
        // stage.initStyle(StageStyle.UNDECORATED);
        stage.show();   
         }

    }
