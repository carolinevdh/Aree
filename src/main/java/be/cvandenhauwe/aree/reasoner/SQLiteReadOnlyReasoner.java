/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.reasoner;

import org.sqlite.SQLiteConfig;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class SQLiteReadOnlyReasoner extends SQLiteQueryReasoner{

    @Override
    SQLiteConfig getConfig() {
        SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(true);
        return config;
    }
    
}
