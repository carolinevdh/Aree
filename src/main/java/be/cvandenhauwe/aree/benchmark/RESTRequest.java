/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.benchmark;

import be.cvandenhauwe.aree.SetupChain;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class RESTRequest {
    
    public static HttpURLConnection connect(URL url, String contentType, byte[] bs, String requestMethod){
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", contentType);
            
            
            OutputStream os = conn.getOutputStream();            
            os.write(bs);
            os.flush();
            
            //System.out.println("HTTP response code: " + conn.getResponseCode());           
            
            return conn;
            
        } catch (IOException ex) {
            Logger.getLogger(SetupChain.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
    }
}
