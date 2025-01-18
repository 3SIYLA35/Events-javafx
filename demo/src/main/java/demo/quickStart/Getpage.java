package demo.quickStart;

import java.net.URL;

// import de.jensd.fx.glyphs.testapps.App;
import javafx.fxml.FXMLLoader;
// import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class Getpage {

    Pane view;

    @SuppressWarnings("static-access")
    public Pane getpage(String filename) {
        try {
            // URL url=getClass().getResource("/views/"+filename + ".fxml");
            // if (url==null) {
            //     throw new java.io.FileNotFoundException("FXML file not found");
            // }
            view=new FXMLLoader().load(getClass().getResource("/views/"+filename + ".fxml"));
           
            return view;
        } catch (Exception e) {
            System.out.println("no page"+filename+"found");
            e.printStackTrace();
        }

        return null;

    }
}
