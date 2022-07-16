package web.uni.hr.meli.model;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class EntityType {

    @Id
    private long id;
    private String EntityType;

    public EntityType() {
    }

    public EntityType(long id, String entityType) {
        this.id = id;
        EntityType = entityType;
    }

    //    BT,KFT,NYRT,ZRT;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEntityType() {
        return EntityType;
    }

    public void setEntityType(String entityType) {
        EntityType = entityType;
    }
}
