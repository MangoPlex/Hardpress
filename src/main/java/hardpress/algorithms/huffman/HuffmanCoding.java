package hardpress.algorithms.huffman;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import hardpress.LosslessCompression;
import hardpress.binary.BinaryReader;
import hardpress.binary.BinaryWriter;
import hardpress.binary.BitReader;
import hardpress.binary.BitWriter;

public class HuffmanCoding extends LosslessCompression {

    @Override
    public String getName() { return "Huffman Coding"; }

    @Override
    public void compressData(byte[] data, OutputStream output) throws IOException {
        BinaryWriter writer = new BinaryWriter(output);
        BitWriter bitWriter = new BitWriter(output);
        writer.writeVarUint(data.length);
        if (data.length == 0) return;
        
        // Step 1: Build Huffman tree
        ArrayList<Node> nodes = new ArrayList<>();
        ByteNode[] bytes = new ByteNode[256];
        
        for (int i = 0; i < data.length; i++) {
            ByteNode node = getNode(nodes, data[i]);
            if (node == null) {
                bytes[Byte.toUnsignedInt(data[i])] = node = new ByteNode(data[i], 1);
                nodes.add(node);
            } else node.occurrences++;
        }
        
        while (nodes.size() >= 2) {
            Collections.sort(nodes);
            Node a = nodes.remove(0);
            Node b = nodes.remove(0);
            ConnectNode c = new ConnectNode(a, b);
            nodes.add(c);
            a.connector = c; b.connector = c;
            a.connectorSide = false; b.connectorSide = true;
        }
        
        if (nodes.get(0) instanceof ByteNode bn) {
            bn.writeNodeData(bitWriter);
            return;
        }
        ConnectNode tree = (ConnectNode) nodes.get(0);
        
        // Step 2: Create bits array for each byte
        for (int i = 0; i < 256; i++) {
            ByteNode bnode = bytes[i];
            if (bnode == null) continue;
            
            Node node = bnode;
            while (node.connector != null) {
                bnode.bitsList.add(0, node.connectorSide);
                node = node.connector;
            }
            
            bnode.bits = new boolean[bnode.bitsList.size()];
            for (int j = 0; j < bnode.bits.length; j++) bnode.bits[j] = bnode.bitsList.get(j); 
        }
        
        // Step 3: Compress data
        tree.writeNodeData(bitWriter);
        for (int i = 0; i < data.length; i++) bitWriter.writeBits(bytes[Byte.toUnsignedInt(data[i])].bits);
        bitWriter.flush();
    }
    
    private ByteNode getNode(ArrayList<Node> nodes, byte value) {
        for (Node node : nodes) if (node instanceof ByteNode byteNode && byteNode.value == value) return byteNode;
        return null;
    }

    @Override
    public byte[] uncompressData(InputStream input) throws IOException {
        BinaryReader reader = new BinaryReader(input);
        BitReader bitReader = new BitReader(input);
        int uncompressedSize = reader.readVarUint();
        if (uncompressedSize == 0) return new byte[0];
        
        byte[] data = new byte[uncompressedSize];
        Node nodeFromStream = Node.readFromStream(bitReader);
        
        if (nodeFromStream instanceof ByteNode bn) {
            for (int i = 0; i < uncompressedSize; i++) data[i] = bn.value;
            return data;
        }
        ConnectNode tree = (ConnectNode) nodeFromStream;
        
        for (int i = 0; i < uncompressedSize; i++) {
            Node node = tree;
            boolean bit;
            while (node instanceof ConnectNode) {
                bit = bitReader.readBit();
                node = bit? ((ConnectNode) node).on : ((ConnectNode) node).off;
            }
            data[i] = ((ByteNode) node).value;
        }
        
        return data;
    }

}
