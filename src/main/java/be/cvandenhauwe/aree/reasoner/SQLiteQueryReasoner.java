/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.reasoner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public class SQLiteQueryReasoner implements AreeReasoner{
  
    
    @Override
    public Object process(Object obj) throws Exception { 
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:/users/caroline/Development/Tools/Chemistry.db");

        Statement statement = connection.createStatement();

        statement.setQueryTimeout(30); //seconds
        ResultSet rs = statement.executeQuery(obj.toString());
        return rs;
    }
}
