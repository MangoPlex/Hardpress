package hardpress.algorithms.huffman;

import java.io.IOException;

import hardpress.binary.BitReader;
import hardpress.binary.BitWriter;

public abstract class Node implements Comparable<Node> {
    
    public ConnectNode connector;
    public boolean connectorSide;
    
    public abstract int nodeOccurrences();
    public abstract void writeNodeData(BitWriter writer) throws IOException;
    
    @Override
    public int compareTo(Node o) {
        return nodeOccurrences() - o.nodeOccurrences();
    }
    
    public static Node readFromStream(BitReader reader) throws IOException {
        boolean isConnectNode = reader.readBit();
        if (!isConnectNode) return new ByteNode((byte) reader.readByte(), 0);
        else {
            Node a = readFromStream(reader);
            Node b = readFromStream(reader);
            return new ConnectNode(a, b);
        }
    }
    
}
