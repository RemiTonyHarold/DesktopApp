package RSSFeed;

import javafx.scene.control.ListCell;

/**
 * Created by fritsc_h on 28/01/2017.
 */
public class NewsListCell extends ListCell<String> {
    @Override
    public void updateItem(String string, boolean empty)
    {
        super.updateItem(string,empty);
        if(string != null)
        {
            Data data = new Data();
            data.setInfo(string);
            setGraphic(data.getBox());
        }
    }
}

