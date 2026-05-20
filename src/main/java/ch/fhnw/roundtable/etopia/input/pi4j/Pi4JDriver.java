package ch.fhnw.roundtable.etopia.input.pi4j;

import ch.fhnw.roundtable.etopia.input.ButtonType;
import com.pi4j.Pi4J;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalInputProvider;
import com.pi4j.plugin.gpiod.provider.gpio.digital.GpioDDigitalOutputProvider;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;

public class Pi4JDriver {

    private final Pi4JInputDriver pi4jInputDriver;
    private final Pi4jLightDriver pi4jLightDriver;

    public Pi4JDriver() {
        var pi4jContext = Pi4J.newContextBuilder()
                .add(GpioDDigitalInputProvider.newInstance())
                .add(GpioDDigitalOutputProvider.newInstance())
                .add(LinuxFsI2CProvider.newInstance())
                .build();

        Runtime.getRuntime().addShutdownHook(new Thread(pi4jContext::shutdown));

        pi4jInputDriver = new Pi4JInputDriver(pi4jContext);
        pi4jLightDriver = new Pi4jLightDriver(pi4jContext);
    }

    public Pi4JInput createInput(ButtonType type) {
        var pin = Pi4JButtonPins.getInput(type);
        return pi4jInputDriver.createInput(pin);
    }

    public Pi4JLight createLight(ButtonType type) {
        var pin = Pi4JButtonPins.getLight(type);
        return pi4jLightDriver.createLight(pin);
    }
}
