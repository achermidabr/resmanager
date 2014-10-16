/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.resources;

import br.gov.sc.fatma.resourcemanager.common.AResource;
import br.gov.sc.fatma.resourcemanager.common.Status;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandre
 */
public abstract class DatabaseResource extends AResource {
    
    public DatabaseResource() {
        super();
        upTime();
    }
    
    @Override
    public Status status() {
        try {
            return checkStatusCommand.execute();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseResource.class.getName()).log(Level.WARNING, null, ex);
            return new Status(false,ex.getMessage(),-1);
        }
    }

}
