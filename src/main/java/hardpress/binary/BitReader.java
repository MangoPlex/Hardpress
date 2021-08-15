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
    
    public int readBitAsInt() throws IOException {
        if (bitIndex >= 8) {
            bitIndex = 0;
            currentByte = stream.read();
        }
        return (currentByte & (1 << (7 - (bitIndex++)))) != 0? 1 : 0;
    }
    
    public int readByte() throws IOException {
        return 
            (readBitAsInt() << 7) | (readBitAsInt() << 6) | (readBitAsInt() << 5) | (readBitAsInt() << 4)
          | (readBitAsInt() << 3) | (readBitAsInt() << 2) | (readBitAsInt() << 1) | readBitAsInt();
    }

}
