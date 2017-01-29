package RSSFeed;

import RSSFeed.Model.Category;
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
public class CategoryAdapter {
    @FXML
    private VBox vBox;
    @FXML
    private Label label1;

    public CategoryAdapter()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("View/categoryListCell.fxml"));
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

    public void setInfo(Category category)
    {
        label1.setText(category.getName());
    }

    public VBox getBox() {
        return vBox;
    }
}
