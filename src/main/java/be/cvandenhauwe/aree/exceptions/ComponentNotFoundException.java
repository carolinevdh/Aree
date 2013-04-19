/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.exceptions;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class ComponentNotFoundException extends Exception{
    public ComponentNotFoundException(){}
    public ComponentNotFoundException(String message){
        super(message);
    }
}
