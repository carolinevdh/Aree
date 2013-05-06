/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.reasoner;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class PlainReasoner implements AreeReasoner{

    @Override
    public Object process(Object obj) throws Exception{
        return "plainreasoner: " +obj;
    }    
}
