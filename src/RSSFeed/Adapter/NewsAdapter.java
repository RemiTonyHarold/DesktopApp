package RSSFeed.Adapter;

import RSSFeed.Model.News;
import RSSFeed.Utils.TimeAgo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Date;

/**
 * Created by fritsc_h on 28/01/2017.
 */
public class NewsAdapter {
    @FXML
    private VBox vBox;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label description;

    public NewsAdapter()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/newsListCell.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
            vBox.setPadding(new Insets(10));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(News news)
    {
        label1.setText(news.getTitle());
        label2.setText(TimeAgo.toRelative(news.getPubDate(), new Date(), 1));
        description.setText(news.getDescription());

    }

    public VBox getBox() {
        return vBox;
    }
}
