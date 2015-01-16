package com.zenika.test.cassandra.injector;

public class Main {

    private PropertiesRepository propertiesRepository;
    private MetricSigarRepository metricSigarRepository;
    private MetricCassandraRepository metricCassandraRepository;
    private boolean running=false;
    public void initialize() throws Exception{
        propertiesRepository = new PropertiesRepository();
        propertiesRepository.initialize();
        metricSigarRepository = new MetricSigarRepository();
        metricSigarRepository.initialize();
        metricCassandraRepository = new MetricCassandraRepository(propertiesRepository);
        metricCassandraRepository.initialize();
    }
    public void close() {
        if (metricCassandraRepository != null) {
            metricCassandraRepository.close();
        }
    }
    public void readWriteMetrics() {
        metricCassandraRepository.insertMetrics(metricSigarRepository.getMetrics());
    }
    public void run() throws InterruptedException {
        try {
            running = true;
            while (running) {
                readWriteMetrics();
                Thread.currentThread().sleep(propertiesRepository.getProperty("period", Long.class));
            }
        } finally {
            running = false;
        }
    }
    public static void main(String ... args) throws Exception {
        Main main = new Main();
        try {
            main.initialize();
            main.run();
        } finally {
            main.close();
        }
    }
}
