/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.resources;

import br.gov.sc.fatma.resourcemanager.commands.DatabaseCheckIfUPCommand;

/**
 *
 * @author Alexandre
 */
public class PostgresDatabaseResource extends DatabaseResource {

    public PostgresDatabaseResource(String url, String user, String pwd) {
        super();
        checkStatusCommand = new DatabaseCheckIfUPCommand(url,user,pwd,"org.postgresql.Driver");
    }
    
}
