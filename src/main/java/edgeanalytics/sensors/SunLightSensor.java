package edgeanalytics.sensors;

import org.apache.edgent.function.Supplier;

public class SunLightSensor implements Supplier {
    private int intensity = 14000;
    private final int maxIntensity = 65535;
    private final int minIntensity = 1;

    @Override
    public Object get() {
        if (Math.random() > 0.8) {
            int change = (int) (Math.random() * 10);
            if (Math.random() > 0.5)
                change *= -1;
            intensity += change;
        }

        if (intensity < minIntensity)
            intensity = minIntensity;
        else if (intensity > maxIntensity)
            intensity = maxIntensity;

        System.out.println("light:\t" + intensity);
        return intensity;
    }
}
