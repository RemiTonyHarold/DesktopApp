package RSSFeed.Controller;

import RSSFeed.Data;
import RSSFeed.Model.News;
import javafx.scene.control.ListCell;

/**
 * Created by fritsc_h on 28/01/2017.
 */
public class NewsListCell extends ListCell<News> {
    @Override
    public void updateItem(News news, boolean empty)
    {
        super.updateItem(news,empty);
        if(news != null)
        {
            Data data = new Data();
            data.setInfo(news);
            setGraphic(data.getBox());
        }
    }
}

