/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.benchmark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class DescriptorBenchmark extends Benchmark{

    private ArrayList<Long> timings = new ArrayList<Long>();
    
    public DescriptorBenchmark() {}
    
    public void run(int times){ 
        FileInputStream input = null;
        try {        
            
            String descriptorPath = "/Users/caroline/Thesis/Code/Aree/descriptors/";
            String descriptor = "areedesc-stringtopdb.xml"; //EDIT THIS
            input = new FileInputStream(descriptorPath + descriptor);
            byte[] bytes = IOUtils.toByteArray(input);
            
            while(times >= 0){
                long startTime = System.currentTimeMillis();
                HttpURLConnection conn = connect(
                        new URL("http://localhost:8080/Aree/newconfiguration/post"),
                        "application/xml",
                        bytes, "POST");
                long endTime = System.currentTimeMillis();
                timings.add(endTime-startTime);
                times--;
                conn.disconnect();
            }
            
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DescriptorBenchmark.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(DescriptorBenchmark.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DescriptorBenchmark.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getCSV(int drops){
        int size = timings.size() - drops;
        StringBuilder sb = new StringBuilder(size * 20);
        sb.append(timings.get(drops));
        
        for(int i = drops + 1; i < size; i++){
            sb.append(",\n");
            sb.append( + timings.get(i));
        }        
        return sb.toString();        
    }
    
    public long getAverage(int drops){
        int number = timings.size() - drops;
        int sum = 0;
        for(int i = drops; i < number; i++){
            sum += timings.get(i);
        } 
        return sum/number;
    }
}
