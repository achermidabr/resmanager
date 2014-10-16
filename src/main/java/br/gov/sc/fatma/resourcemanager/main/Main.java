/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.main;

import br.gov.sc.fatma.resourcemanager.resources.DatabaseResourceBuilder;
import br.gov.sc.fatma.resourcemanager.services.ManagerResource;
import br.gov.sc.fatma.resourcemanager.actions.BeepAction;
import br.gov.sc.fatma.resourcemanager.enums.DatabaseTypeEnum;
import br.gov.sc.fatma.resourcemanager.actions.SendEmailAction;
import br.gov.sc.fatma.resourcemanager.commands.CentOSRebootCommand;
import br.gov.sc.fatma.resourcemanager.commands.WindowsCheckDiskInfoCommand;
import br.gov.sc.fatma.resourcemanager.common.AResource;
import br.gov.sc.fatma.resourcemanager.common.ICommand;
import br.gov.sc.fatma.resourcemanager.common.Status;
import br.gov.sc.fatma.resourcemanager.ejbs.MonitorBean;
import br.gov.sc.fatma.resourcemanager.resources.SiteResource;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandre
 */
public class Main {

    private static final String ADMINMAIL = "alexandrehermida@fatma.sc.gov.br";

    public static void main(String[] args) {
        Main main = new Main();
        AResource sr = new DatabaseResourceBuilder(DatabaseTypeEnum.ORACLE).
                withURL("jdbc:oracle:thin:@172.19.212.43:1521:sinfat").
                withUser("producao").
                withPwd().build().
                addWarningAction(new SendEmailAction(ADMINMAIL)).
                addWarningAction(new BeepAction()).
                addRestartCommand(new CentOSRebootCommand()).
                addCommandToChain(new WindowsCheckDiskInfoCommand());

        AResource sinfatsr = null;
        AResource sinfatWsr = null;
        AResource gaiasr = null;
        try {
            sinfatsr = new SiteResource("http://sinfat.fatma.sc.gov.br").
                    addWarningAction(new SendEmailAction(ADMINMAIL)).
                    addRestartCommand(new CentOSRebootCommand());

            sinfatWsr = new SiteResource("http://sinfatweb.fatma.sc.gov.br").
                    addWarningAction(new SendEmailAction(ADMINMAIL)).
                    addRestartCommand(new CentOSRebootCommand());
            gaiasr = new SiteResource("http://gaia.fatma.sc.gov.br").
                    addWarningAction(new SendEmailAction(ADMINMAIL)).
                    addRestartCommand(new CentOSRebootCommand());

        } catch (URISyntaxException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            if (sr != null) {
                sr.runWarningActions();
            }
        }

        while (true) {
            main.checkBancoRes(sr);
            main.checkSiteResultRes(sinfatsr);
            main.checkSiteResultRes(sinfatWsr);
            main.checkSiteResultRes(gaiasr);
            try {
                Thread.sleep(300000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Status checkBancoRes(AResource sr) {
        Status result;
        try {

            result = sr.status();
            if (!result.isStatusOk()) {
                sr.runWarningActions();
            } else {
                Logger.getLogger(Main.class.getName()).log(Level.INFO, "UPTIME:" + sr.upTime().toString());
                System.out.println("SAINDO? " + sr.upTime().toString());
            }
            
            Map<ICommand, Status> resultsOfTheChain = sr.runChainOfCommands(false);
            Logger.getLogger(MonitorBean.class.getName()).log(Level.FINE, sr.upTime().toString());
            Set<ICommand> keys = resultsOfTheChain.keySet();
            for (ICommand comm : keys) {
                Logger.getLogger(MonitorBean.class.getName()).log(Level.INFO, resultsOfTheChain.get(comm).getMessage());
            }

        } catch (Exception ex) {
            Logger.getLogger(ManagerResource.class.getName()).log(Level.FINE, null, ex);
            result = new Status(false, ex.getMessage(), -1);
            if (sr != null) {
                sr.runWarningActions();
            }
        }
        return result;
    }

    private Status checkSiteResultRes(AResource sr) {

        Status st;
        try {

            st = sr.status();
            if (!st.isStatusOk()) {
                sr.runWarningActions();
            } else {
                Logger.getLogger(Main.class.getName()).log(Level.INFO, sr.upTime().toString());
            }

        } catch (Exception ex) {
            Logger.getLogger(ManagerResource.class.getName()).log(Level.FINE, null, ex);
            st = new Status(false, ex.getMessage(), -1);
            if (sr != null) {
                sr.runWarningActions();
            }
        }

        return st;
    }
}
