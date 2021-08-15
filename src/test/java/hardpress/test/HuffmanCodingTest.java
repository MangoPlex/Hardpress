package hardpress.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hardpress.algorithms.huffman.HuffmanCoding;

class HuffmanCodingTest {
    
    byte[] getRawData(String text) {
        return text.getBytes(StandardCharsets.UTF_8);
    }

    @Test
    void testRegular() throws IOException {
        byte[] rawData = getRawData("Hello World! aaaaaaaaaaaaaooooooooooobbbbbbbbbb");
        HuffmanCoding huffman = new HuffmanCoding();
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        huffman.compressData(rawData, out);
        byte[] compressedData = out.toByteArray();
        
        ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
        byte[] uncompressedData = huffman.uncompressData(in);
        assertArrayEquals(rawData, uncompressedData);
    }

    @Test
    void testEmptyString() throws IOException {
        byte[] rawData = getRawData("");
        HuffmanCoding huffman = new HuffmanCoding();
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        huffman.compressData(rawData, out);
        byte[] compressedData = out.toByteArray();
        
        ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
        byte[] uncompressedData = huffman.uncompressData(in);
        assertArrayEquals(rawData, uncompressedData);
    }

    @Test
    void testSingleCharacter() throws IOException {
        byte[] rawData = getRawData("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        HuffmanCoding huffman = new HuffmanCoding();
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        huffman.compressData(rawData, out);
        byte[] compressedData = out.toByteArray();
        
        ByteArrayInputStream in = new ByteArrayInputStream(compressedData);
        byte[] uncompressedData = huffman.uncompressData(in);
        assertArrayEquals(rawData, uncompressedData);
    }

}
