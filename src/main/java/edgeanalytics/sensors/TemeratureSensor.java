package edgeanalytics.sensors;
import org.apache.edgent.function.Supplier;

import java.util.Random;

public class TemeratureSensor implements Supplier {

    Random rand = new Random();

    @Override
    public Object get() {
        Double temperature = rand.nextDouble()*50; ;
        return temperature;
    }
}
