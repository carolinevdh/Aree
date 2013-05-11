/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Initiating fake Client.");
        sendXML();
    }
    
    private static void sendXML(){
        
        // SETUP CONFIGURATION //
        FileInputStream input = null;
        try {
            System.out.println();
            
            String descriptorPath = "/Users/caroline/Thesis/Code/Aree/descriptors/";
            String descriptor = "marker-plain-threed.xml"; //EDIT THIS
            input = new FileInputStream(descriptorPath + descriptor);
            System.out.println("Client: requesting new configuration");
            HttpURLConnection conn = connect(
                    new URL("http://localhost:8080/Aree/newconfiguration/post"),
                    "application/xml",
                    IOUtils.toByteArray(input), "POST");
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String jsonText = readAll(br);
            JSONObject json = JSONObject.fromObject(jsonText);
            
            System.out.println("Client: received configuration with id: " + json.get("id"));
            conn.disconnect();
            
            // USE CONFIGURATION //     
            
            
            JSONObject request = new JSONObject();
            request.accumulate("id", json.get("id"));
            request.accumulate("data", "Hello World");
            System.out.println("Client: Sending " + request.toString());
            HttpURLConnection requestConn = 
                    connect(new URL("http://localhost:8080/Aree/request/post"),
                    "application/json", request.toString().getBytes("UTF-8"), "POST");
            
            System.out.println("Client: Response from request: " + readAll(new InputStreamReader(requestConn.getInputStream())));
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
    private static HttpURLConnection connect(URL url, String contentType, byte[] bs, String requestMethod){
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", contentType);
            
            
            OutputStream os = conn.getOutputStream();            
            os.write(bs);
            os.flush();
            
            
            
            if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                System.out.println("Failed: HTTP error code: " + conn.getResponseCode());
            }
            
            //conn.disconnect();
            
            return conn;
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
    }
}
