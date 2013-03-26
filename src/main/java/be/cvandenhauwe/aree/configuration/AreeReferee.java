/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.configuration;

import be.cvandenhauwe.aree.input.AreeInput;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class AreeReferee {
    
    @Inject
    private AreeInput pi;
    
    @PostConstruct
    public void doSomething(){
        System.out.println(pi.produceInput("============Great Success.=============="));
    }
}