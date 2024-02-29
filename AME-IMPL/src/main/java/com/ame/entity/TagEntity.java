package com.ame.entity;

import com.ame.annotation.Description;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;

@Description("菜单维护表")
@Entity
@Table(name = "SYS_TAG_VALUE")
@Cacheable
public class TagEntity extends BaseEntity {

    public static final String TAG_ADDRESS = "tagAddress";
    public static final String TAG_NAME = "tagName";

    public static final String TAG_VALUE = "tagValue";

    @Column(name = "TAG_ADDRESS")
    private String tagAddress;

    @Column(name = "TAG_NAME")
    private String tagName;

    @Column(name = "TAG_VALUE")
    private String tagValue;

    public String getTagAddress() {
        return tagAddress;
    }

    public void setTagAddress(String tagAddress) {
        this.tagAddress = tagAddress;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }
}
