package edgeanalytics.analyzers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edgeanalytics.sensors.MyTemperatureSensor;
import org.apache.edgent.analytics.math3.json.JsonAnalytics;
import org.apache.edgent.connectors.iot.IotDevice;
import org.apache.edgent.connectors.iot.QoS;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.TWindow;

import java.util.concurrent.TimeUnit;

import static org.apache.edgent.analytics.math3.stat.Statistic.*;

public class TemperatureAnalyzer {

    private double lastTemp =0;

    public TemperatureAnalyzer(IotDevice device){
        TStream<Double> distanceReadings = device.topology().poll(new MyTemperatureSensor(), 100, TimeUnit.MILLISECONDS);
        distanceReadings = distanceReadings.
                filter(j -> Math.abs(j-this.lastTemp) > 0.5)
                .peek(j -> this.lastTemp = j)
                .peek(j->  System.out.println("\t\tCHANGE "+j));


        TStream<JsonObject> sensorJSON = distanceReadings.map(v -> {
            JsonObject j = new JsonObject();
            j.addProperty("name", "tempSensor");
            j.addProperty("temperature", v);
            return j;
        });
        TWindow<JsonObject,JsonElement> sensorWindow = sensorJSON.last(5,TimeUnit.MINUTES, j -> j.get("name"));
        sensorJSON = JsonAnalytics.aggregate(sensorWindow, "name", "temperature", MIN, MAX, MEAN, STDDEV);
        sensorJSON.print();
        sensorJSON.peek(j->j.addProperty("actual", this.lastTemp));

        device.events(sensorJSON, "sensors", QoS.FIRE_AND_FORGET);



    }

}
