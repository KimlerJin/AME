package com.ame.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class Entity implements ISetDataId, Serializable {

    public static final String ID = "id";
    /**
     *
     */
    private static final long serialVersionUID = -3463142496273773620L;
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "com/ame/uidgenerator")
    @GenericGenerator(name = "com/ame/uidgenerator",
        strategy = "com.ame.hibernate.CommonsDataUidGenerator")

    private long id;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Entity entity = (Entity)o;
        if (id == entity.id && id == 0) {
            return super.equals(o);
        }
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return id > 0 ? Objects.hash(id) : super.hashCode();
    }
}
