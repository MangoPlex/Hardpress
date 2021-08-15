package hardpress.algorithms.huffman;

import java.io.IOException;
import java.util.ArrayList;

import hardpress.binary.BitWriter;

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
    public void writeNodeData(BitWriter writer) throws IOException {
        writer.writeBit(false);
        writer.writeByte(Byte.toUnsignedInt(value));
    }
    
    @Override
    public String toString() {
        return "(" + Byte.toUnsignedInt(value) + ": " + occurrences + ")";
    }

}
