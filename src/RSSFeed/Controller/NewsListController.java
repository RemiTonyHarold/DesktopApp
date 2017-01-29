package RSSFeed.Controller;

import RSSFeed.Model.News;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsListController {
    public WebView webview;
    public Label webviewTitleLabel;
    public ListView lvNews;
    public News selectedNews;

    private News[] newses = {};
    private ObservableList observableList = FXCollections.observableArrayList();

    public void setListView(){
        lvNews.setCellFactory(
                new Callback<ListView<News>, ListCell<News>>() {
                    @Override
                    public ListCell<News> call(ListView<News> listView) {
                        return new NewsListCell();
                    }
                });
        lvNews.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                selectedNews = newses[newValue.intValue()];
                webview.getEngine().load(selectedNews.getLink());
                webviewTitleLabel.setText("Chargement de " + selectedNews.getTitle());
            }
        });
        webview.getEngine().getLoadWorker().stateProperty().addListener(
                (ObservableValue<? extends Worker.State> observable,
                 Worker.State oldValue,
                 Worker.State newValue) -> {
                    if( newValue != Worker.State.SUCCEEDED ) {
                        return;
                    }
                    webviewTitleLabel.setText(selectedNews.getTitle());
                    // Your logic here
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
                    newses = gson.fromJson(result, News[].class);
                    observableList.clear();
                    observableList.setAll(newses);
                    lvNews.setItems(observableList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).run();
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
