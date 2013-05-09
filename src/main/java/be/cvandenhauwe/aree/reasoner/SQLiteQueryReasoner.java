/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package be.cvandenhauwe.aree.reasoner;

import be.cvandenhauwe.aree.configuration.AreeArguments;
import be.cvandenhauwe.aree.exceptions.InvalidDescriptorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.sqlite.SQLiteConfig;

/**
 *
 * @author Caroline Van den Hauwe <caroline.van.den.hauwe@gmail.com>
 */
public abstract class SQLiteQueryReasoner implements AreeReasoner{
  
    String databaseName = "";
        
    @Override
    public Object process(AreeArguments runtimeArgs, Object obj) throws Exception{
        Class.forName("org.sqlite.JDBC");
        Connection connection = DriverManager.getConnection(
                "jdbc:sqlite:../Aree-files/" + databaseName + ".db", getConfig().toProperties());
 
        Statement statement = connection.createStatement();

        statement.setQueryTimeout(30); //seconds
        ResultSet rs = statement.executeQuery(obj.toString());
        return rs;
    }

    @Override
    public void setup(AreeArguments setupArguments) throws InvalidDescriptorException{
        databaseName = setupArguments.get("dbname").toString();
        if(databaseName.isEmpty()) 
            throw new InvalidDescriptorException("SQLiteQueryReasoner: Database name was not provided.");    
    }
    
    abstract SQLiteConfig getConfig();
}