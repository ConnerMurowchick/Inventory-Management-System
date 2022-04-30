import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *  FUTURE ENHANCEMENT
 *      Creating another tableview on the main form screen that displays recently added/modified parts.
 *      This would come in handy if this system had dozens or hundreds of parts in it
 *
 * class MainForm
 */
public class MainForm extends Application {

    /**
     * Opens the Main form on start up of the program
     * @param primaryStage the main form.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Main form.fxml"));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }

    /**
     * Launches the application
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}


