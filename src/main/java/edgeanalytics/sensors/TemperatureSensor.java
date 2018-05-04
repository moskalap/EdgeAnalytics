package edgeanalytics.sensors;
import org.apache.edgent.function.Supplier;

import java.util.Random;

public class TemperatureSensor implements Supplier {

    Random rand = new Random();
    public double temp = 20.0;

    @Override
    public Object get() {
        if(rand.nextDouble() > 0.9){
            if(rand.nextBoolean()){
                temp += rand.nextDouble();
            }else{
                temp -= rand.nextDouble();
            }

        }
        System.out.print("sensor" + temp);
        return temp;
    }
}
