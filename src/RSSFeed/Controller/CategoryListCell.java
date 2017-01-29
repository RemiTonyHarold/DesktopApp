package RSSFeed.Controller;

import RSSFeed.CategoryAdapter;
import RSSFeed.Data;
import RSSFeed.Model.Category;
import RSSFeed.Model.News;
import javafx.scene.control.ListCell;

/**
 * Created by fritsc_h on 29/01/2017.
 */
public class CategoryListCell extends ListCell<Category> {
        @Override
        public void updateItem(Category category, boolean empty)
        {
            super.updateItem(category, empty);
            if(category != null)
            {
                CategoryAdapter data = new CategoryAdapter();
                data.setInfo(category);
                setGraphic(data.getBox());
            }
    }
}
