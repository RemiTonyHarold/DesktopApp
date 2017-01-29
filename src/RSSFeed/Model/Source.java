package RSSFeed.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fritsc_h on 29/01/2017.
 */
@Entity
public class Source {
    private String categoryId;
    @Id
    private String id;
    private String name;
    private String url;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
