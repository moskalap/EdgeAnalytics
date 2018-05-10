package edgeanalytics.sensors;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import org.apache.edgent.function.Supplier;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.io.w1.W1Master;
import com.pi4j.temperature.TemperatureScale;

public class MyTemperatureSensor implements Supplier {
    W1Master master = new W1Master();
    Random rand = new Random();
    public double temp = 20.0;
    public MyTemperatureSensor(){
        W1Master master = new W1Master();
        List<W1Device> w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
        System.out.println("devices: "+w1Devices.size());
        for (W1Device device : w1Devices) {
            //this line is enought if you want to read the temperature
            System.out.println("Temperature: " + ((TemperatureSensor) device).getTemperature());
            //returns the temperature as double rounded to one decimal place after the point

            try {
                System.out.println("1-Wire ID: " + device.getId() +  " value: " + device.getValue());
                //returns the ID of the Sensor and the  full text of the virtual file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
