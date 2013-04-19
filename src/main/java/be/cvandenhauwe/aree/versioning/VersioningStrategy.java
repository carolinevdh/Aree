/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.versioning;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public enum VersioningStrategy {
    ONLY, //only the specified version may be used
    DESC, //start at the newest/specified version and try older if failure
    ASC, //start at the oldest/specified version and try newer if failure
    RANDOM //pick a random version
}
