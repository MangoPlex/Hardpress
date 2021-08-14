package hardpress.binary;

import java.io.IOException;
import java.io.InputStream;

public class BitReader {
    
    private InputStream stream;
    private int currentByte;
    private int bitIndex = 8;
    
    public BitReader(InputStream stream) {
        this.stream = stream;
    }
    
    public boolean readBit() throws IOException {
        if (bitIndex >= 8) {
            bitIndex = 0;
            currentByte = stream.read();
        }
        return (currentByte & (1 << (7 - (bitIndex++)))) != 0;
    }

}
