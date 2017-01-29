package RSSFeed.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fritsc_h on 29/01/2017.
 */
@Entity
public class Category {
    @Id
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
