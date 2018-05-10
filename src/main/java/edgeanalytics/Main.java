package edgeanalytics;

import edgeanalytics.analyzers.MoistureAnalyzer;
import edgeanalytics.analyzers.SunLightAnalyzer;
import edgeanalytics.analyzers.TemperatureAnalyzer;
import org.apache.edgent.connectors.iot.IotDevice;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.providers.direct.DirectTopology;
import org.apache.edgent.connectors.iotp.IotpDevice;

import java.io.File;

public class Main {
    public static void main(String[] args){

        DirectProvider dp = new DirectProvider();
        DirectTopology topology = dp.newTopology();
        String confFilePath = args.length == 1? args[0]:ClassLoader.getSystemClassLoader().getResource("device.cfg").getFile();
        System.out.println("Configuration file path: "+confFilePath);
        IotDevice device = new IotpDevice(topology, new File(confFilePath));
        //new TemperatureAnalyzer(device);
       // new MoistureAnalyzer(device);
        new SunLightAnalyzer(device);
        dp.submit(topology);

    }
}
