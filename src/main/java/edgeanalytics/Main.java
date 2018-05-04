package edgeanalytics;

import edgeanalytics.analyzers.MoistureAnalyzer;
import edgeanalytics.analyzers.SunLightAnalyzer;
import edgeanalytics.analyzers.TemperatureAnalyzer;
import edgeanalytics.sensors.TemperatureSensor;
import org.apache.edgent.connectors.iot.IotDevice;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.providers.direct.DirectTopology;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.connectors.iotp.IotpDevice;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args){

        DirectProvider dp = new DirectProvider();
        DirectTopology topology = dp.newTopology();
        IotDevice device = new IotpDevice(topology, new File(ClassLoader.getSystemClassLoader().getResource("device.cfg").getFile()));
        new TemperatureAnalyzer(device);
        new MoistureAnalyzer(device);
        new SunLightAnalyzer(device);
        dp.submit(topology);
    }
}
