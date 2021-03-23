package oer57;

import java.io.*;

/**
 * @author Oguzhan Ermis
 * @project M3UGenerator
 */
public class Writer {

    private Reader _reader;
    private boolean _append;

    public Writer(Reader reader, boolean append){
        _reader = reader;
        _append = append;
    }

    public void write(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Oguzh\\OneDrive\\IpTv_Output\\iptv_fireTV.m3u",_append));
            String m3u = _reader.getM3U();
            writer.write(m3u);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
