/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree;

import be.cvandenhauwe.aree.experiments.DescriptorBenchmark;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */

public class SetupChain {
   
    private static final int START_THREADS = 10;
    private static final int STEP_THREADS = 10;
    private static final int STOP_THREADS = 100;
    
    private static final int TIMES = 30;
    private static final int WARMUP = 5;
    
    public static void main(String[] args) throws InterruptedException, IOException {
        final HashMap<Integer,ArrayList> alltimings = new HashMap();
        
        int threads = START_THREADS;        
        while(threads <= STOP_THREADS){ //varieer aantal uit te voeren threads
            System.out.println("==== GOING FOR " + threads + " threads. ====");
            final ArrayList<Long> timings = new ArrayList();
            int counter = 0;
            while(counter < (TIMES + WARMUP)){
                System.out.println("=> " + threads + "thr, #" + (counter + 1));
                ExecutorService threadPool = Executors.newFixedThreadPool(threads);

                for (int i = 0; i < threads; i++) {
                    threadPool.submit(new Runnable() {
                        @Override
                        public void run() {
                            DescriptorBenchmark descriptorbm = new DescriptorBenchmark();
                            long start = System.currentTimeMillis();
                            descriptorbm.run(1);
                            long end = System.currentTimeMillis();
                            timings.add(end-start);                        
                            System.out.println(end-start);
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
        
        
        
        
        
        
        FileWriter writer = new FileWriter("/Users/caroline/Desktop/MBASfrom" + START_THREADS + "to" + STOP_THREADS + "in" + STEP_THREADS + ".csv");
        writer.append("threads,time (ms)\n");
        
        int writercounter = START_THREADS;
        for(int j = START_THREADS; j < STOP_THREADS + 1; j += STEP_THREADS){
            ArrayList<Long> times2write = alltimings.get(j);
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
