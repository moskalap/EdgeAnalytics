package edgeanalytics;

import edgeanalytics.sensors.TemeratureSensor;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.providers.direct.DirectTopology;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.connectors.iotp.;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){

        DirectProvider dp = new DirectProvider();
        DirectTopology topology = dp.newTopology();
        TemeratureSensor sensor = new TemeratureSensor();
        String deviceCfg = "/home/przmek/agh/6/iot/edge/src/res/device.cfg";
        IotDevice device = new IotpDevice(topology, new File(deviceCfg));
        TStream<Double> distanceReadings = device.topology().poll(sensor, 1, TimeUnit.SECONDS);
        distanceReadings.print();
        dp.submit(topology);


        System.out.print("gel");
    }
}
