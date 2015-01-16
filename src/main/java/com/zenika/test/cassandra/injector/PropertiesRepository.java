package com.zenika.test.cassandra.injector;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;

import java.util.Properties;

public class PropertiesRepository {
    private Properties properties = new Properties();
    private ConsistencyLevel writeConsistencyLevel;
    public void initialize() throws Exception {
        properties.load(getClass().getResourceAsStream("/application.properties"));
        properties.putAll(System.getProperties());
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    public <T> T getProperty(String key, Class<T> type) {
        String sVal=getProperty(key);
        Object val;
        if (sVal==null || type.equals(String.class)) {
            val = sVal;
        } else if (type.equals(Integer.class)) {
            val = Integer.valueOf(sVal);
        } else if (type.equals(Long.class)) {
            val = Long.valueOf(sVal);
        } else if (Enum.class.isAssignableFrom(type)) {
            Class<? extends Enum> enumType= (Class<? extends Enum>) type;
            val = Enum.valueOf(enumType, sVal.toUpperCase());
        } else {
            val = null;
        }
        return type.cast(val);
    }
}
