package ch.fhnw.roundtable.etopia.input.pi4j;

import com.pi4j.context.Context;
import com.pi4j.io.OnOffWrite;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pi4jLightDriver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Pi4jLightDriver.class);

    private static final String PROVIDER = "linuxfs-i2c";
    private static final String ID = "PCF8574-Expander";

    private Pcf8574OutputDriver driver;

    public Pi4jLightDriver(Context pi4jContext) {

        I2CProvider i2CProvider = pi4jContext.provider(PROVIDER);
        I2CConfig i2CConfiguration = I2C.newConfigBuilder(pi4jContext)
                .id(ID)
                .bus(1)
                .device(Pcf8574OutputDriver.PCF8574_ADDRESS_BASE)
                .build();

        try (var i2C = i2CProvider.create(i2CConfiguration)) {
            driver = new Pcf8574OutputDriver(i2C);
        } catch (Exception e) {
            LOGGER.warn("Could not initialize Pi4jLightDriver: {}", e.getMessage());
        }
    }

    public Pi4JLight createLight(int pin) {
        OnOffWrite<?> control = null;

        if (driver != null) {
            control = driver.getOutput(pin);
        }

        return new Pi4JLight(control);
    }
}
