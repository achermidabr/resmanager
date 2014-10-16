/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.resources;

import br.gov.sc.fatma.resourcemanager.commands.SiteCheckIfUPCommand;
import br.gov.sc.fatma.resourcemanager.common.AResource;
import br.gov.sc.fatma.resourcemanager.common.IAction;
import br.gov.sc.fatma.resourcemanager.common.ICommand;
import br.gov.sc.fatma.resourcemanager.common.Status;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandre
 */
public class SiteResource extends AResource{
        
    public SiteResource(String site) throws URISyntaxException{
        super();
        checkStatusCommand = new SiteCheckIfUPCommand(new URI(site));
        upTime();
    }

    @Override
    public Status status() {
        try {
            return checkStatusCommand.execute();
        } catch (Exception ex) {
            Logger.getLogger(SiteResource.class.getName()).log(Level.WARNING, null, ex);
            return new Status();
        }
    }
    
}
