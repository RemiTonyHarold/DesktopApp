package RSSFeed.Controller;

import RSSFeed.Utils.HibernateHelper;
import RSSFeed.Model.Category;
import RSSFeed.Model.News;
import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NewsListController implements CategoryChooser.CategoryChoosed {
    public WebView webview;
    public Label webviewTitleLabel;
    public ListView lvNews;
    public News selectedNews;
    public Button filterButton;


    private ObservableList<News> observableList = FXCollections.observableArrayList();

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
                if (newValue.intValue() == -1) {
                    return;
                }
                selectedNews = observableList.get(newValue.intValue());
                webview.getEngine().load(selectedNews.getLink());
                webviewTitleLabel.setText("Chargement de " + selectedNews.getTitle());
            }
        });
    }

    @FXML
    void initialize() {
        assert lvNews != null : "fx:id=\"lvNews\" was not injected: check your FXML file 'CustomList.fxml'.";
        filterButton.setText("Filtrer");
        filterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/categoryChooser.fxml"));
                    loader.load();
                    CategoryChooser controller = loader.getController();
                    controller.setDelegate(NewsListController.this);
                    Stage newStage = new Stage();

                    Scene stageScene = new Scene(loader.getRoot(), 400, 400);
                    controller.setStage(newStage);
                    newStage.setScene(stageScene);
                    newStage.setTitle("Choose a category");
                    newStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loadNewses();
        cacheCategories();
        setListView();
        refreshNews();
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

    private void loadNewses() {
        Session session = HibernateHelper.getSession();
        String hql = "FROM News ORDER BY pubDate DESC";
        Query query = session.createQuery(hql);
        List results = query.getResultList();
        observableList.clear();
        observableList.setAll(results);
        lvNews.setItems(observableList);
    }

    private void refreshNews() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String result = getHTML("http://remirobert.com:4242/news");
                    Gson gson = new Gson();
                    Session session = HibernateHelper.getSession();
                    Transaction tx = null;

                    News[] newses = gson.fromJson(result, News[].class);
                    try {
                        tx = session.beginTransaction();
                        for (News news : newses) {
                            session.saveOrUpdate(news);
                        }
                        tx.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    loadNewses();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).run();
    }

    private void cacheCategories() {
        //http://remirobert.com:4242/categories
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String result = getHTML("http://remirobert.com:4242/categories");
                    Gson gson = new Gson();
                    Session session = HibernateHelper.getSession();
                    Transaction tx = null;

                    Category[] categories = gson.fromJson(result, Category[].class);
                    try {
                        tx = session.beginTransaction();
                        for (Category category : categories) {
                            session.saveOrUpdate(category);
                        }
                        tx.commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    @Override
    public void didChooseCategory(Category category) {
        Session session = HibernateHelper.getSession();
        String hql = "FROM News WHERE categoryId LIKE '" + category.getId() + "' ORDER BY pubDate DESC";
        Query query = session.createQuery(hql);
        List results = query.getResultList();
        observableList.clear();
        observableList.setAll(results);
        lvNews.setItems(observableList);

    }
}
