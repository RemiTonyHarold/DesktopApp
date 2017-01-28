package RSSFeed;

import RSSFeed.Model.News;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.io.IOException;

/**
 * Created by fritsc_h on 28/01/2017.
 */
public class Data {
    @FXML
    private VBox vBox;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label description;

    public Data()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("newsListCell.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(News news)
    {
        label1.setText(news.getTitle());
        label2.setText(news.getPubDate().toString());
        description.setText(news.getDescription());

    }

    public VBox getBox() {
        return vBox;
    }
}
