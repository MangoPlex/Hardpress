package hardpress.algorithms.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Node implements Comparable<Node> {
    
    public ConnectNode connector;
    public boolean connectorSide;
    
    public abstract int nodeOccurrences();
    public abstract void writeNodeData(OutputStream stream) throws IOException;
    
    @Override
    public int compareTo(Node o) {
        return nodeOccurrences() - o.nodeOccurrences();
    }
    
    public static Node readFromStream(InputStream stream) throws IOException {
        int id = stream.read();
        if (id == 0) return new ByteNode((byte) stream.read(), 0);
        if (id == 1) {
            Node a = readFromStream(stream);
            Node b = readFromStream(stream);
            return new ConnectNode(a, b);
        }
        return null;
    }
    
}
