package hardpress.binary;

import java.io.IOException;
import java.io.OutputStream;

public class BitWriter {
    
    private OutputStream stream;
    private int currentByte = 0;
    private int bitIndex = 0; // 0 = 0b10000000
    
    public BitWriter(OutputStream stream) {
        this.stream = stream;
    }
    
    public void writeBit(boolean bit) throws IOException {
        if (bit) currentByte |= 1 << (7 - (bitIndex++));
        else bitIndex++;
        
        if (bitIndex == 8) flush();
    }
    
    public void writeBits(boolean... bs) throws IOException {
        for (int i = 0; i < bs.length; i++) writeBit(bs[i]);
    }
    
    public void flush() throws IOException {
        stream.write(currentByte);
        bitIndex = 0;
        currentByte = 0;
    }
    
}
