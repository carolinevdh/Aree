/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            URL url = new URL("http://localhost:8080/Aree/newconfiguration");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/xml");
            
            String descriptorPath = "/Users/caroline/Thesis/Code/Aree/descriptors/";
            String descriptor = "marker-plain-threed.xml"; //EDIT THIS
            FileInputStream input = new FileInputStream(descriptorPath + descriptor);
            //String input = "<descriptor><input>be.cvandenhauwe.aree.input.MarkerInput</input></descriptor>";

            OutputStream os = conn.getOutputStream();            
            os.write(IOUtils.toByteArray(input));
            os.flush();
            
            if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String output;
            System.out.println("--Output from Server:--");
            while((output = br.readLine()) != null){
                System.out.println(output);
            }
            System.out.println("---------end-----------");
            
            conn.disconnect();
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
