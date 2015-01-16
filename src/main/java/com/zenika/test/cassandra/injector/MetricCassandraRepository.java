package com.zenika.test.cassandra.injector;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.LoadBalancingPolicy;

import java.util.List;

public class MetricCassandraRepository {
    private Cluster cluster;
    private Session session;
    private PreparedStatement insertStatement;
    private final PropertiesRepository propertiesRepository;
    private ConsistencyLevel writeConsistencyLevel;

    public MetricCassandraRepository(PropertiesRepository propertiesRepository) {
        this.propertiesRepository = propertiesRepository;
    }

    public void initialize() throws Exception {
        Cluster.Builder builder = new Cluster.Builder();
        String[] hosts = propertiesRepository.getProperty("cassandra.hosts").split("\\s*[,;]\\s*");
        for(String host:hosts) {
            builder.addContactPoint(host);
        }
        Integer port = propertiesRepository.getProperty("cassandra.port", Integer.class);
        if (port!=null) {
            builder.withPort(port);
        }
        builder.withClusterName(propertiesRepository.getProperty("cassandra.clusterName"));
        String localDc = propertiesRepository.getProperty("cassandra.localDc");
        if (localDc == null) {
            DCAwareRoundRobinPolicy loadBalancingPolicy = new DCAwareRoundRobinPolicy(localDc);
            builder.withLoadBalancingPolicy(loadBalancingPolicy);
        }
        cluster = builder.build();
        session = cluster.connect(propertiesRepository.getProperty("cassandra.keyspace"));
        writeConsistencyLevel = propertiesRepository.getProperty("cassandra.consistencyLevel.write", ConsistencyLevel.class);
        insertStatement= session.prepare("insert into metric(host,name,date,value) values(?,?,?,?)");
        if (writeConsistencyLevel != null) {
            insertStatement.setConsistencyLevel(writeConsistencyLevel);
        }
    }
    public void close() {
        if (session!=null) {
            session.close();
        }
        if (cluster!=null) {
            cluster.close();
        }
    }
    public void insertMetric(Metric metric) {
        session.execute(insertStatement.bind(metric.getHost(), metric.getName(), metric.getDate(), metric.getValue()));
    }
    public void insertMetrics(List<Metric> metrics) {
        for(Metric metric:metrics) {
            insertMetric(metric);
        }
    }
}
