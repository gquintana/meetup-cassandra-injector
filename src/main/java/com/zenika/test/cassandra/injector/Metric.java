package com.zenika.test.cassandra.injector;

import java.util.Date;

/**
 * Created by gquintana on 16/12/14.
 */
public class Metric {
    private final String host;
    private final String name;
    private final Date date;
    private final long value;

    public Metric(String host, String name, Date date, long value) {
        this.host = host;
        this.name = name;
        this.date = date;
        this.value = value;
    }

    public String getHost() {
        return host;
    }

    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public long getValue() {
        return value;
    }
}
