package edgeanalytics.analyzers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import edgeanalytics.sensors.SunLightSensor;
import org.apache.edgent.analytics.math3.json.JsonAnalytics;
import org.apache.edgent.connectors.iot.IotDevice;
import org.apache.edgent.connectors.iot.QoS;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.TWindow;

import java.util.concurrent.TimeUnit;

import static org.apache.edgent.analytics.math3.stat.Statistic.*;;

public class SunLightAnalyzer {
    private int lightIntensity = 1;

    public SunLightAnalyzer(IotDevice device) {

        TStream<Integer> distanceReadings = device.topology().poll(new SunLightSensor(), 2000, TimeUnit.MILLISECONDS);

        distanceReadings = distanceReadings
                .filter(j -> Math.abs(j - this.lightIntensity) > 5)
                .peek(j -> this.lightIntensity = j);

        TStream<JsonObject> sensorJSON = distanceReadings.map(i -> {
            JsonObject j = new JsonObject();
            j.addProperty("name", "lightSensor");
            j.addProperty("intensity", i);
            return j;
        });

        TWindow<JsonObject, JsonElement> sensorWindow = sensorJSON.last(5, TimeUnit.MINUTES, j -> j.get("name"));
        sensorJSON = JsonAnalytics.aggregate(sensorWindow, "name", "intensity", MIN, MAX, MEAN, STDDEV);
        sensorJSON.print();
        sensorJSON.peek(j->j.addProperty("current", this.lightIntensity));
        device.events(sensorJSON, "sensors", QoS.FIRE_AND_FORGET);

    }
}
