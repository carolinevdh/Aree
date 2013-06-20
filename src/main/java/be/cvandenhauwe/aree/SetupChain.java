/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree;

import be.cvandenhauwe.aree.experiments.DescriptorBenchmark;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

public class SetupChain {
    
   
    
    public static void main(String[] args) {
        DescriptorBenchmark descriptorbm = new DescriptorBenchmark();  
        descriptorbm.run(1);
        //System.out.println(descriptorbm.getAverage(10));
    }
       
 //   private static void sendXML(){
        
        // SETUP CONFIGURATION //
//        FileInputStream input = null;
//        try {
//            
//            
//            // USE CONFIGURATION //     
//            
//            
//            JSONObject request = new JSONObject();
//            request.accumulate("key", json.get("key"));
//            request.accumulate("data", "select identifier from molecules");
//            JSONObject args = new JSONObject();
//            JSONObject arg = new JSONObject();
//            arg.accumulate("type", "getfilepath");
//            arg.accumulate("value", "Chemistry");
//            args.accumulate("dbname", arg);
//            request.accumulate("args", args);
//            System.out.println("Client: Sending " + request.toString());
//            HttpURLConnection requestConn = 
//                    connect(new URL("http://localhost:8080/Aree/request/post"),
//                    "application/json", request.toString().getBytes("UTF-8"), "POST");
//            
//            System.out.println("Client: Response from request: " + readAll(new InputStreamReader(requestConn.getInputStream())));
//            
//            
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(SetupChain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(SetupChain.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(SetupChain.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                input.close();
//            } catch (IOException ex) {
//                Logger.getLogger(SetupChain.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//            
//    }
    
    
}
