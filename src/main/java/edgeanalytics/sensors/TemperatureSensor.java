package edgeanalytics.sensors;
import org.apache.edgent.function.Supplier;

import java.util.Random;

public class TemperatureSensor implements Supplier {

    Random rand = new Random();
    public double temp = 20.0;

    @Override
    public Object get() {
        if(rand.nextDouble() > 0.2){
            if(rand.nextBoolean()){
                temp += rand.nextDouble()*0.5d;
            }else{
                temp -= rand.nextDouble()*0.5d;
            }

        }
        System.out.println("\tsensor: " + temp);
        return temp;
    }
}
