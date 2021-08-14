package hardpress.algorithms.huffman;

import java.io.IOException;
import java.io.OutputStream;

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
    public void writeNodeData(OutputStream stream) throws IOException {
        stream.write(0x01);
        off.writeNodeData(stream);
        on.writeNodeData(stream);
    }
    
    @Override
    public String toString() {
        return "[" + on + ", " + off + "]";
    }

}
