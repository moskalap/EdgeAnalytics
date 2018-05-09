package edgeanalytics.analyzers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edgeanalytics.sensors.MoistureSensor;
import org.apache.edgent.analytics.math3.json.JsonAnalytics;
import org.apache.edgent.connectors.iot.IotDevice;
import org.apache.edgent.connectors.iot.QoS;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.TWindow;

import java.util.concurrent.TimeUnit;

import static org.apache.edgent.analytics.math3.stat.Statistic.*;

public class MoistureAnalyzer {
    private int moisture = 800;

    public MoistureAnalyzer(IotDevice device) {
        TStream<Integer> moistureReadings = device.topology().poll(new MoistureSensor(), 100, TimeUnit.MILLISECONDS);

        moistureReadings = moistureReadings
                .filter(x -> x < 100)
                .peek(x -> this.moisture = x);

        TStream<JsonObject> sensorJSON = moistureReadings.map(v -> {
                    JsonObject j = new JsonObject();
                    j.addProperty("name", "moistureSensor");
                    j.addProperty("reading", v);
                    return j;
                });
        TWindow<JsonObject, JsonElement> sensorWindow = sensorJSON.last(10, j -> j.get("name"));
        sensorJSON = JsonAnalytics.aggregate(sensorWindow, "name", "reading", MIN, MAX, MEAN, STDDEV);
        //sensorJSON = sensorJSON.filter(j -> j.get("reading").getAsInt() < 100);
        sensorJSON.print();
        device.events(sensorJSON, "sensors", QoS.FIRE_AND_FORGET);
    }
}
