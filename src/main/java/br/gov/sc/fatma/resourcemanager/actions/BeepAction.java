/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.actions;

import br.gov.sc.fatma.resourcemanager.common.IAction;
import java.awt.Toolkit;

/**
 *
 * @author Alexandre
 */
public class BeepAction implements IAction{

    @Override
    public void execute() {
        Toolkit.getDefaultToolkit().beep();
    }
    
}
