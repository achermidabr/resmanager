/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.common;

import br.gov.sc.fatma.resourcemanager.resources.DatabaseResource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 *
 * @author Alexandre
 */
public abstract class AResource {

    protected List<ICommand> restartCommands;
    protected List<IAction> warningActions;
    protected ICommand checkStatusCommand;
    protected DateTime startTime;
    protected List<ICommand> chainOfCommands;
    
   protected AResource(){
        warningActions = new ArrayList<>();
        restartCommands = new ArrayList<>();
        chainOfCommands = new ArrayList<>();
        checkStatusCommand = null;
    }

    public Period upTime() {
        if (startTime == null) {
            startTime = new DateTime();
        }
        DateTime today = new DateTime();
        return new Period(startTime, today);
    }

    public boolean runRestartCommands() {

        for (ICommand command : restartCommands) {
            try {
                command.execute();
            } catch (Exception ex) {
                Logger.getLogger(AResource.class.getName()).log(Level.WARNING, null, ex);
            }
        }

        return true;
    }

    public boolean runWarningActions() {
        for (IAction action : warningActions) {
            action.execute();
        }

        return true;
    }
    
    public Map<ICommand,Status> runChainOfCommands(boolean breakOnError) {

        Map<ICommand,Status> result = new TreeMap<>();
        
        for (ICommand command : chainOfCommands) {
            Status res;
            try {
                res = command.execute();
                result.put(command, res);
            } catch (Exception ex) {
                Logger.getLogger(AResource.class.getName()).log(Level.WARNING, null, ex);
                if(breakOnError){
                    res = new Status(false,ex.getMessage(),-1);
                    result.put(command, res);
                    break;
                }
            }
        }

        return result;
    }

    public AResource addWarningAction(IAction action) {
        warningActions.add(action);
        return this;
    }

    public AResource addRestartCommand(ICommand command) {
        restartCommands.add(command);
        return this;
    }

    public AResource addCommandToChain(ICommand command) {
        chainOfCommands.add(command);
        return this;
    }

    public Status status() {
        try {
            return checkStatusCommand.execute();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseResource.class.getName()).log(Level.WARNING, null, ex);
            return new Status(false, ex.getMessage(), -1);
        }
    }
}
