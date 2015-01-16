package com.zenika.test.cassandra.injector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MetricSigarRepositoryTest {
    private MetricSigarRepository metricRepository = new MetricSigarRepository();
    @Before
    public void setUp() throws Exception{
        metricRepository.initialize();
    }
    @After
     public void tearDown() throws Exception{
        metricRepository.close();
    }
    @Test
    public void testGetMetrics() {
        List<Metric> metrics = metricRepository.getMetrics();
        assertTrue(metrics.size()>5);
    }
}
