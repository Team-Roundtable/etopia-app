package ch.fhnw.roundtable.etopia.input.pi4j;

import com.pi4j.io.OnOffWrite;
import com.pi4j.io.i2c.I2C;

/// Inspired by [Pi4J Drivers](https://github.com/Pi4J/pi4j-drivers)
public class Pcf8574OutputDriver {

    public static final int PCF8574_ADDRESS_BASE = 0x20;

    private static final int TRIGGER_MASK = -1;

    private final I2C i2c;
    private final OnOffWrite<?>[] onOffWriteArray = new OnOffWrite[8];

    private int outputBits = -1;

    public Pcf8574OutputDriver(I2C i2c) {
        this.i2c = i2c;
        for (int i = 0; i < 8; i++) {
            onOffWriteArray[i] = new OnOffWriteImpl(i);
        }
    }

    public OnOffWrite<?> getOutput(int index) {
        return onOffWriteArray[index];
    }

    public void setOutputState(int index, boolean state) {
        int mask = 1 << index;
        if (state) {
            setOutputState(outputBits | mask);
        } else {
            setOutputState(outputBits & ~mask);
        }
    }

    public void setOutputState(int bits) {
        int changedBits = outputBits ^ bits;
        outputBits = bits;
        if ((changedBits & TRIGGER_MASK) != 0) {
            this.i2c.write(outputBits);
        }
    }

    private class OnOffWriteImpl implements OnOffWrite<OnOffWriteImpl> {
        final int index;

        OnOffWriteImpl(int index) {
            this.index = index;
        }

        @Override
        public OnOffWriteImpl on() {
            Pcf8574OutputDriver.this.setOutputState(index, false);
            return this;
        }

        @Override
        public OnOffWriteImpl off() {
            Pcf8574OutputDriver.this.setOutputState(index, true);
            return this;
        }
    }
}
