package com.zenika.test.cassandra.injector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class MetricCassandraRepositoryTest {

    private MetricCassandraRepository metricCassandraRepository;

    @Before
    public void setUp() throws Exception{
        PropertiesRepository propertiesRepository = new PropertiesRepository();
        propertiesRepository.initialize();
        metricCassandraRepository = new MetricCassandraRepository(propertiesRepository);
        metricCassandraRepository.initialize();
    }
    @After
    public void tearDown() throws Exception {
        metricCassandraRepository.close();
    }
    @Test
    public void testInsertMetric() throws Exception {
        Metric metric = new Metric("test","test",new Date(),123456);
        metricCassandraRepository.insertMetric(metric);
    }

    @Test
    public void testInsertMetrics() throws Exception {
        List<Metric> metrics = new ArrayList<Metric>(10);
        Date timestamp = new Date();
        for(int i=0;i<10;i++) {
            metrics.add(new Metric("test","test"+i, timestamp, i*100+123));
        }
        metricCassandraRepository.insertMetrics(metrics);

    }
}