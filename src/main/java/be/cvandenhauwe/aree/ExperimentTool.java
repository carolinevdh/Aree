/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree;

import be.cvandenhauwe.aree.experiments.RESTRequest;
import static be.cvandenhauwe.aree.experiments.RESTRequest.connect;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

public class ExperimentTool {
   
    private static final int START_THREADS = 10;
    private static final int STEP_THREADS = 10;
    private static final int STOP_THREADS = 100;
    
    private static final int TIMES = 30;
    private static final int WARMUP = 5;
    
    public static void main(String[] args) throws InterruptedException, IOException {
        final HashMap<Integer,ArrayList> alltimings = new HashMap();
                
        final Stack stack = new Stack();
        for(int i = 20000; i >= 0; i--) stack.add(i);
        
        int threads = START_THREADS;        
        while(threads <= STOP_THREADS){ //varieer aantal uit te voeren threads
            System.out.println("==== GOING FOR " + threads + " threads. ====");
            final ArrayList<String> timings = new ArrayList();
            int counter = 0;
            while(counter < (TIMES + WARMUP)){
                System.out.println("=> " + threads + "thr, #" + (counter + 1));
                ExecutorService threadPool = Executors.newFixedThreadPool(threads);

                for (int i = 0; i < threads; i++) {
                    threadPool.submit(new Runnable() {
                        @Override
                        public void run() {                            
                            //runExperimentOne(timings);                            
                            //runExperimentTwo(timings);
                            //runExperimentThree(timings, stack.pop().toString());
                            runExperimentFour(timings);
                        }
                    });
                }
                
                threadPool.shutdown();
                Thread.sleep(1000);
                counter++;
            }
            alltimings.put(threads, timings);
            threads += STEP_THREADS;
            Thread.sleep(1000);
        }
        
        write("/Users/caroline/Desktop/exp2nano_from" + START_THREADS + "to" + STOP_THREADS + "in" + STEP_THREADS + ".csv",
                "threads,time (ns)",
                alltimings);
        
    }
    
      
    private static void runExperimentOne(ArrayList<String> timings){
        long start = System.currentTimeMillis();
        
        FileInputStream input;
        try {        
            
            String descriptorPath = "/Users/caroline/Thesis/Code/Aree/descriptors/";
            String descriptor = "areedesc-stringtopdb.xml"; //EDIT THIS
            input = new FileInputStream(descriptorPath + descriptor);
            byte[] bytes = IOUtils.toByteArray(input);
            HttpURLConnection conn = connect(
                    new URL("http://localhost:8080/Aree/newconfiguration/post"),
                    "application/xml",
                    bytes, "POST");
            conn.getInputStream();
            conn.disconnect();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExperimentTool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ExperimentTool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExperimentTool.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        long end = System.currentTimeMillis();
        timings.add((end-start) + "");                        
        System.out.println(end-start);
    }
    
    private static void runExperimentTwo(ArrayList<String> timings){
        try {
            JSONObject json = new JSONObject();
            json.accumulate("key",0);
            json.accumulate("data", "select identifier from molecules");
            JSONObject args = new JSONObject();
            args.accumulate("dbname", "Chemistry");
            json.accumulate("args", args);
            String request = json.toString();                       

            byte[] bytes = IOUtils.toByteArray(request);
            HttpURLConnection conn = connect(
                    new URL("http://localhost:8080/Aree/exptwo/post"),
                    "application/json",
                    bytes, "POST");
            String output = RESTRequest.readAll(new InputStreamReader(conn.getInputStream()));
            System.out.println(output);
            timings.add(output);
            conn.disconnect();

        } catch (IOException ex) {
            Logger.getLogger(ExperimentTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void runExperimentThree(ArrayList<String> timings, String key) {
        
        try {        
            JSONObject json = new JSONObject();
            json.accumulate("key",key);
            json.accumulate("data", "select identifier from molecules");
            //JSONObject args = new JSONObject();
            //args.accumulate("dbname", "Chemistry");
            //json.accumulate("args", args);
            String request = json.toString();                       

            HttpURLConnection connrequest = connect(
                    new URL("http://localhost:8080/Aree/expthree/post"),
                    "application/json",
                    IOUtils.toByteArray(request), "POST");
            
            
            String output = RESTRequest.readAll(new InputStreamReader(connrequest.getInputStream()));
            System.out.println(output);
            timings.add(output);
            connrequest.disconnect();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExperimentTool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ExperimentTool.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExperimentTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }            
            
    private static void runExperimentFour(ArrayList<String> timings){
    
        
    
    }

    private static void write(String path, String header, HashMap<Integer, ArrayList> data) throws IOException{
        FileWriter writer = new FileWriter(path);
        writer.append(header + "\n");
        
        for(int j = START_THREADS; j < STOP_THREADS + 1; j += STEP_THREADS){
            ArrayList<Long> times2write = data.get(j);
            int size = times2write.size();
            int skip = WARMUP * j;
            for(int i = skip; i < size; i++){
                writer.append(String.valueOf(j));
                writer.append(',');
                writer.append(String.valueOf(times2write.get(i)));
                writer.append('\n');
            }  
        }         
        
        writer.flush();
	writer.close();
    }
}
