package edgeanalytics.sensors;

import org.apache.edgent.function.Supplier;

public class MoistureSensor implements Supplier {
    private final int minMoisture = 0;
    private final int maxMoisture = 65536;
    private int outputMoisture = 800;

    @Override
    public Object get() {
        if(Math.random() > 0.9) {
            outputMoisture += 1000; //water plant XD
        } else {
            int toDecrease = (int)(Math.random() * 100);
            outputMoisture -= toDecrease;
        }
        if(outputMoisture < minMoisture) outputMoisture = minMoisture;
        if(outputMoisture > maxMoisture) outputMoisture = maxMoisture;

        System.out.println("Current moisture: " + outputMoisture);
        return outputMoisture;
    }
}
