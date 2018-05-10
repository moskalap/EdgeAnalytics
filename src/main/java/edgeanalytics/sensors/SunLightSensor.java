package edgeanalytics.sensors;

import com.pi4j.io.i2c.I2CFactory;
import org.apache.edgent.function.Supplier;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class SunLightSensor implements Supplier {
    private int intensity = 14000;
    private final int maxIntensity = 65535;
    private final int minIntensity = 1;
    private boolean random;
    private I2CDevice device;

    public SunLightSensor(){
       this.random =false;


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
            String pythonScriptPath = "/home/pi/projects/sensor.py";
            String[] cmd = new String[2];
            cmd[0] = "python"; // check version of installed python: python -V
            cmd[1] = pythonScriptPath;

// create runtime to execute external command
            Runtime rt = Runtime.getRuntime();
            Process pr = null;
            try {
                pr = rt.exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }

// retrieve output from python script
            BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            try {
                String s = bfr.readLine();
                System.out.println("RED: "+s);
                return Integer.parseInt(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
        
    
    }
}
