package com.ame.filter.query;

import org.hibernate.cache.spi.support.SimpleTimestamper;

import java.io.Serializable;

/**
 * @author tam_tang
 * @date 2022/6/17 15:01
 */
public class QueryCacheItem implements Serializable {

    private  long timestamp;
    private  Object results;

    public QueryCacheItem() {
        this.timestamp = timestamp;
        this.results = results;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }

    public static QueryCacheItem build(Object results){
        QueryCacheItem queryCacheItem = new QueryCacheItem();
        queryCacheItem.setResults(results);
        queryCacheItem.setTimestamp(SimpleTimestamper.next());
        return queryCacheItem;
    }
}
