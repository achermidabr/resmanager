/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.commands;

import br.gov.sc.fatma.resourcemanager.common.ICommand;
import br.gov.sc.fatma.resourcemanager.common.Status;

/**
 *
 * @author Alexandre
 */
public class CentOSRebootCommand implements ICommand {

    private static final String command = "sudo reboot";

    @Override
    public Status execute() throws Exception{
        Process pr = Runtime.getRuntime().exec(command);
        return new Status(true,pr.toString(),0);
    }
}
