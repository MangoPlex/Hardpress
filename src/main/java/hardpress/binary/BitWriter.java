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
    
    public void writeByte(int bv) throws IOException {
        writeBits(
            (bv & 0b10000000) != 0,
            (bv & 0b01000000) != 0,
            (bv & 0b00100000) != 0,
            (bv & 0b00010000) != 0,
            (bv & 0b00001000) != 0,
            (bv & 0b00000100) != 0,
            (bv & 0b00000010) != 0,
            (bv & 0b00000001) != 0
        );
    }
    
    public void flush() throws IOException {
        stream.write(currentByte);
        bitIndex = 0;
        currentByte = 0;
    }
    
}
