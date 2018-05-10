package edgeanalytics.sensors;

import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import org.apache.edgent.function.Supplier;

import java.util.List;
import java.util.Random;
import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;

public class MyTemperatureSensor implements Supplier {

    private final boolean random;
    private TemperatureSensor sensor;
    Random rand = new Random();
    public double temp = 20.0;
    public MyTemperatureSensor(){
        W1Master    master = new W1Master();
        List<W1Device> w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
        if(w1Devices.size() == 0){
            this.random = true;
        }else{
            this.random = false;
            sensor = (TemperatureSensor) w1Devices.get(0);
        }
    }
    @Override
    public Object get() {
        if(this.random){
            if(rand.nextDouble() > 0.2){
                if(rand.nextBoolean()){
                    temp += rand.nextDouble()*0.5d;
                }else{
                    temp -= rand.nextDouble()*0.5d;
                }

            }
            System.out.println("\tsensor: " + temp);
            return temp;
        }else{
            return sensor.getTemperature();
        }

    }
}
