package RSSFeed;

import RSSFeed.Controller.NewsListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/newsListController.fxml"));
        loader.load();
        primaryStage.setTitle("RSS Feed Aggregator");
        primaryStage.setScene(new Scene(loader.getRoot(), 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
