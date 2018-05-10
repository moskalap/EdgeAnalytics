package edgeanalytics;

import edgeanalytics.analyzers.MoistureAnalyzer;
import edgeanalytics.analyzers.SunLightAnalyzer;
import edgeanalytics.analyzers.TemperatureAnalyzer;
import edgeanalytics.sensors.MyTemperatureSensor;
import org.apache.edgent.connectors.iot.IotDevice;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.providers.direct.DirectTopology;
import org.apache.edgent.connectors.iotp.IotpDevice;

import java.io.File;

public class Main {
    public static void main(String[] args){

        MyTemperatureSensor t = new MyTemperatureSensor();

    }
}
