package RSSFeed;

import RSSFeed.Model.News;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    public ListView lvNews;

    private List<News> newses = new ArrayList<>(5);
    private ObservableList observableList = FXCollections.observableArrayList();

    public void setListView(){
        newses.add(new News("description1", new Date(), "title1"));
        newses.add(new News("description2", new Date(), "title2"));
        newses.add(new News("description3", new Date(), "title3"));
        newses.add(new News("description4", new Date(), "title4"));
        newses.add(new News("description5", new Date(), "title5"));

        observableList.setAll(newses);

        lvNews.setItems(observableList);

        lvNews.setCellFactory(
                new Callback<ListView<News>, ListCell<News>>() {
                    @Override
                    public ListCell<News> call(ListView<News> listView) {
                        return new NewsListCell();
                    }
                });
    }

    @FXML
    void initialize() {
        assert lvNews != null : "fx:id=\"lvNews\" was not injected: check your FXML file 'CustomList.fxml'.";

        setListView();
    }

}
