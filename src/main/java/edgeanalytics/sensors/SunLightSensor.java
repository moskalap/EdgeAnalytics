package edgeanalytics.sensors;

import com.pi4j.io.i2c.I2CFactory;
import org.apache.edgent.function.Supplier;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;

import java.io.IOException;
import java.math.BigDecimal;

public class SunLightSensor implements Supplier {
    private int intensity = 14000;
    private final int maxIntensity = 65535;
    private final int minIntensity = 1;
    private boolean random;
    private I2CDevice device;

    public SunLightSensor(){
        I2CBus bus = null;
        try {
            System.out.println("1");
            bus = I2CFactory.getInstance(I2CBus.BUS_1);

            System.out.println("2");
            device = bus.getDevice(0x23);
            System.out.println("3");
            if(device!= null){
                System.out.println(device.getAddress());
                this.random=false;
                System.out.println("4");
            }else{
                this.random=true;
                System.out.println("5");
            }
        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
            this.random = true;
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Object get() {
        if(this.random){
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

            System.out.println("random light:\t" + intensity);
            return intensity;
        }else{
            byte[] p = new byte[2];

            int r;
            try {

                r = device.read(0x23,p, 0, 2);
                System.out.println("read: "+r);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            if (r != 2) {
                throw new IllegalStateException("Read Error; r=" + r);
            }
            intensity = new BigDecimal((p[0] << 8) | p[1]).intValue();
            System.out.println("light:\t" + intensity);
            return intensity;
        }
        
    
    }
}
