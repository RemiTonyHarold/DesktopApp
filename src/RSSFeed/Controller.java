package RSSFeed;

import RSSFeed.Model.News;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
        loadNewses();
        setListView();
    }

    private void loadNewses() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String result = getHTML("http://remirobert.com:4242/news?timestamp=1485603935000");
                    Gson gson = new Gson();
                    News[] fetchedNewses = gson.fromJson(result, News[].class);
                    System.out.println("fetched :" + fetchedNewses.length);
                    newses.clear();
                    observableList.clear();
                    observableList.setAll(fetchedNewses);
                    lvNews.setItems(observableList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        runnable.run();
    }

    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }
}
