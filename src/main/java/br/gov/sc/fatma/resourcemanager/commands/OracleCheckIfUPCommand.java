/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.commands;

import br.gov.sc.fatma.resourcemanager.common.ICommand;
import br.gov.sc.fatma.resourcemanager.common.Status;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandre
 */
public class OracleCheckIfUPCommand implements ICommand {

    private String _url;
    private String _user;
    private String _pwd;
    public OracleCheckIfUPCommand(String url, String user, String password){
        _url = url;
        _user = user;
        _pwd = password;
    }
    
    @Override
    public Status execute() {
        Logger.getLogger(OracleCheckIfUPCommand.class.getName()).log(Level.INFO, "Oracle JDBC Connection Testing");
        Status result = new Status();
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Logger.getLogger(OracleCheckIfUPCommand.class.getName()).log(Level.FINE, "Oracle JDBC Driver Registered!");
            connection = DriverManager.getConnection(_url,_user,_pwd);
//                    "jdbc:oracle:thin:@172.19.212.43:1521:sinfat", "producao",
//                    "$s1nf47_or4cl3p#");

            boolean connResult = connection != null;
            result = new Status(connResult, connResult ? "Connection Established" : "Failed to make connection!", 0);
            Logger.getLogger(OracleCheckIfUPCommand.class.getName()).log(Level.FINE, "Connection: "+connResult);

        } catch (ClassNotFoundException e1) {
            result = new Status(false, "Where is your Oracle JDBC Driver? ["+e1.getMessage()+"]", -1);
        } catch (SQLException e2) {
            result = new Status(false, "Connection Failed! Check output console ["+e2.getMessage()+"]", -1);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {}
            }
        }
        return result;
    }

}
