package com.zenika.test.cassandra.injector;

import org.hyperic.sigar.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MetricSigarRepository {
    private Sigar sigar;
    private Cpu cpu;
    private Mem mem;
    private String host;
    private String[] netInterfaces;
    public void initialize() throws SigarException {
        sigar = new Sigar();
        cpu = sigar.getCpu();
        mem = sigar.getMem();
        host = sigar.getNetInfo().getHostName();
        netInterfaces = sigar.getNetInterfaceList();
    }
    public List<Metric> getMetrics() {
        Date timestamp = new Date();
        List<Metric> metrics = new ArrayList<Metric>(Arrays.asList(
                new Metric(host, "cpu.sys", timestamp, cpu.getSys()),
                new Metric(host, "cpu.user", timestamp, cpu.getSys()),
                new Metric(host, "cpu.total", timestamp, cpu.getTotal()),
                new Metric(host, "mem.used", timestamp, mem.getUsed()),
                new Metric(host, "mem.total", timestamp, mem.getTotal())
        ));
        for(String netInterface:netInterfaces) {
            try {
                NetInterfaceStat netInterfaceStat = sigar.getNetInterfaceStat(netInterface);
                metrics.add(new Metric(host, "net."+netInterface+".rxBytes", timestamp, netInterfaceStat.getRxBytes()));
                metrics.add(new Metric(host, "net."+netInterface+".txBytes", timestamp, netInterfaceStat.getTxBytes()));
            } catch (SigarException e) {
            }
        }
        return metrics;
    }
    public void close() {
        cpu = null;
        mem = null;
        sigar.close();
        sigar = null;
    }

}
