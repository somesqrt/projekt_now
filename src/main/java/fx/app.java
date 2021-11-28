package fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class app extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainController controller = new MainController();
        FXMLLoader loader  = new FXMLLoader(app.class.getResource("../../resources/styling/Login.fxml"));
        loader.setController(controller);
        Parent parent = loader.load();
        Scene scene  = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
