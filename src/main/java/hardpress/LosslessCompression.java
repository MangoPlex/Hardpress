package hardpress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class LosslessCompression {
    
    public abstract String getName();
    
    public abstract void compressData(byte[] data, OutputStream output) throws IOException;
    public abstract byte[] uncompressData(InputStream input) throws IOException;
    
}
