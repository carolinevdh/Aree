/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.exceptions;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class IncorrectComponentUsageException extends Exception {
    public IncorrectComponentUsageException(){}
    public IncorrectComponentUsageException(String message){
        super(message);
    }
}