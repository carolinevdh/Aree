/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree;

import be.cvandenhauwe.aree.benchmark.RESTRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class UseChain {
    public static void main(String[] mainargs) throws IOException {
                 
        JSONObject json = new JSONObject();
        json.accumulate("key",2);
        json.accumulate("data", "select identifier from molecules");
                
        JSONObject args = new JSONObject();
        args.accumulate("dbname", "Chemistry");
        
        json.accumulate("args", args);
        
        String request = json.toString();

        byte[] bytes = request.getBytes();
        HttpURLConnection conn = RESTRequest.connect(
               new URL("http://localhost:8080/Aree/request/post"),
               "application/xml",
               bytes, "POST");
        System.out.println(RESTRequest.readAll(new InputStreamReader(conn.getInputStream())));
        conn.disconnect();
    }
}
