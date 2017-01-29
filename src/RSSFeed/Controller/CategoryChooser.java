package RSSFeed.Controller;/**
 * Created by fritsc_h on 29/01/2017.
 */

import RSSFeed.HibernateHelper;
import RSSFeed.Model.Category;
import RSSFeed.Model.News;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryChooser extends Application {

    public ListView lvCategories;
    private CategoryChoosed delegate = null;
    private ObservableList<Category> observableList = FXCollections.observableArrayList();

    private Stage stage;

    interface CategoryChoosed {
        public void didChooseCategory(Category category);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
    }

    private void loadCategories() {
        Session session = HibernateHelper.getSession();
        String hql = "FROM Category ORDER BY name DESC";
        Query query = session.createQuery(hql);
        List results = query.getResultList();
        observableList.clear();
        observableList.setAll(results);
        lvCategories.setItems(observableList);
    }

    @FXML
    void initialize() {
        lvCategories.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new CategoryListCell();
            }
        });
        lvCategories.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (delegate != null) {
                    delegate.didChooseCategory(observableList.get(newValue.intValue()));
                }
                CategoryChooser.this.stage.close();
            }
        });
        loadCategories();
    }

        public CategoryChoosed getDelegate() {
        return delegate;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDelegate(CategoryChoosed delegate) {
        this.delegate = delegate;
    }
}
