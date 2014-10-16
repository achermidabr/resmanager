/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.actions;

import br.gov.sc.fatma.resourcemanager.common.IAction;
import br.gov.sc.fatma.resourcemanager.services.ManagerResource;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandre
 */
public class SendEmailAction implements IAction{
    
    String _email;
    
    public SendEmailAction(String email){
        _email = email;
    }

    @Override
    public void execute() {
        Logger.getLogger(SendEmailAction.class.getName()).log(Level.WARNING, "Um email para "+_email+" serah enviado.");
    }
    
    
}
