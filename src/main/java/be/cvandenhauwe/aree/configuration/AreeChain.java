/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import java.util.ArrayList;

/**
 *
 * @author caroline
 */
public class AreeChain extends ArrayList<AreeLink>{
    
    @Override
    public String toString(){
        String str = "";
        for(AreeLink link : this){
            str += link.getName() + " ";
        }
        return str;
    }
}
