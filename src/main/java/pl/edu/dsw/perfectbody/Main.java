package pl.edu.dsw.perfectbody;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage MainStage) throws Exception{

        /* --replaced by loading FXML on root level--
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/MainMenu.fxml"));
        Pane mainPane = loader.load();*/
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        MainStage.setTitle("Perfect Body - Your personal calculator");
        MainStage.setScene(new Scene(root, 800, 600));
        MainStage.initStyle(StageStyle.UNDECORATED);
        MainStage.setResizable(false); //Non-resizable

        //Drag and drop feature to make UNDECORATED window movable -> credits to @Eeliya on https://stackoverflow.com/a/18177792
        //Lambda mouse event handler -press
        root.setOnMousePressed(event -> {
            xOffset = MainStage.getX() - event.getScreenX();
            yOffset = MainStage.getY() - event.getScreenY();
        });
        //Lambda mouse event handler -drag
        root.setOnMouseDragged(event -> {
            MainStage.setX(event.getScreenX() + xOffset);
            MainStage.setY(event.getScreenY() + yOffset);
        });

        MainStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
