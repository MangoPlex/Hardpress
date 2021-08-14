package hardpress.algorithms.huffman;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class ByteNode extends Node {
    
    public final byte value;
    public int occurrences;
    
    public final ArrayList<Boolean> bitsList = new ArrayList<>();
    public boolean[] bits;
    
    public ByteNode(byte value, int occurrences) {
        this.value = value;
        this.occurrences = occurrences;
    }
    
    @Override
    public int nodeOccurrences() { return occurrences; } 

    @Override
    public void writeNodeData(OutputStream stream) throws IOException {
        stream.write(0x00);
        stream.write(Byte.toUnsignedInt(value));
    }
    
    @Override
    public String toString() {
        return "(" + Byte.toUnsignedInt(value) + ": " + occurrences + ")";
    }

}
