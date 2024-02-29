package com.ame.artemis;

import java.io.Serializable;
import java.util.Map;

public class ReceiveObject implements Serializable {

    private long ts;

    private int devicestatus;

    private Map<String,Object> values;

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public int getDevicestatus() {
        return devicestatus;
    }

    public void setDevicestatus(int devicestatus) {
        this.devicestatus = devicestatus;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
