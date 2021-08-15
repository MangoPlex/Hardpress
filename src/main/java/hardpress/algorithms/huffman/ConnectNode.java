package hardpress.algorithms.huffman;

import java.io.IOException;

import hardpress.binary.BitWriter;

public class ConnectNode extends Node {
    
    public Node off;
    public Node on;
    
    public ConnectNode(Node off, Node on) {
        this.off = off;
        this.on = on;
    }
    
    @Override
    public int nodeOccurrences() { return off.nodeOccurrences() + on.nodeOccurrences(); }

    @Override
    public void writeNodeData(BitWriter writer) throws IOException {
        writer.writeBit(true);
        off.writeNodeData(writer);
        on.writeNodeData(writer);
    }
    
    @Override
    public String toString() {
        return "[" + on + ", " + off + "]";
    }

}
