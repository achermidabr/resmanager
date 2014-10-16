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
public class DatabaseCheckIfUPCommand implements ICommand {

    private String _url;
    private String _user;
    private String _pwd;
    private String _driverName;

    public DatabaseCheckIfUPCommand(String url, String user, String password, String driverName) {
        _url = url;
        _user = user;
        _pwd = password;
        _driverName = driverName;
    }

    @Override
    public Status execute() throws Exception {
       Logger.getLogger(DatabaseCheckIfUPCommand.class.getName()).log(Level.INFO, "JDBC Connection Testing");
        Status result = new Status();
        Connection connection = null;
        try {
            Class.forName(_driverName);
            Logger.getLogger(DatabaseCheckIfUPCommand.class.getName()).log(Level.INFO, "JDBC Driver Registered!");
            connection = DriverManager.getConnection(_url,_user,_pwd);
            boolean connResult = connection != null;
            result = new Status(connResult, connResult ? "Connection Established" : "Failed to make connection!", 0);
            Logger.getLogger(DatabaseCheckIfUPCommand.class.getName()).log(Level.INFO, "Connection: "+connResult);

        } catch (ClassNotFoundException e1) {
            result = new Status(false, "Where is your JDBC Driver? ["+e1.getMessage()+"]", -1);
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
