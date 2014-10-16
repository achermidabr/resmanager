/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.ejbs;

import br.gov.sc.fatma.resourcemanager.actions.SendEmailAction;
import br.gov.sc.fatma.resourcemanager.commands.CentOSRebootCommand;
import br.gov.sc.fatma.resourcemanager.commands.WindowsCheckDiskInfoCommand;
import br.gov.sc.fatma.resourcemanager.common.AResource;
import br.gov.sc.fatma.resourcemanager.common.ICommand;
import br.gov.sc.fatma.resourcemanager.common.Status;
import br.gov.sc.fatma.resourcemanager.resources.DatabaseResourceBuilder;
import br.gov.sc.fatma.resourcemanager.enums.DatabaseTypeEnum;
import br.gov.sc.fatma.resourcemanager.services.ManagerResource;
import br.gov.sc.fatma.resourcemanager.resources.SiteResource;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level; 
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author Alexandre
 */
@Stateless
public class MonitorBean{

    @Schedule(minute = "*", second = "00", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    public void myTimer() {
        checkBancoRes();
        checkSiteResultRes("http://sinfat.fatma.sc.gov.br");
        checkSiteResultRes("http://sinfatweb.fatma.sc.gov.br");
        checkSiteResultRes("http://gaia.fatma.sc.gov.br");
    }
    
    private Status checkBancoRes(){
        Status result;
        AResource sr = null;
        try {
            sr = new DatabaseResourceBuilder(DatabaseTypeEnum.ORACLE).
                    withURL("jdbc:oracle:thin:@172.19.212.43:1521:sinfat").
                    withUser("producao").
                    withPwd().build().
                    addWarningAction(new SendEmailAction("achermida@gmail.com")).
                    addCommandToChain(new WindowsCheckDiskInfoCommand());
            result = sr.status();
            Map<ICommand,Status> resultsOfTheChain = sr.runChainOfCommands(false);
            Logger.getLogger(MonitorBean.class.getName()).log(Level.FINE, sr.upTime().toString());
            if(!result.isStatusOk()){
                sr.runWarningActions();
            }
            
            Set<ICommand> keys = resultsOfTheChain.keySet();
            for(ICommand comm : keys){
                Logger.getLogger(MonitorBean.class.getName()).log(Level.INFO, resultsOfTheChain.get(comm).getMessage());
            }

            
        } catch (Exception ex) {
            Logger.getLogger(MonitorBean.class.getName()).log(Level.FINE, null, ex);
            result = new Status(false,ex.getMessage(),-1);
            if(sr != null) sr.runWarningActions();
        }
        return result;
    }
    
    private Status checkSiteResultRes(String site) {

        Status st;
        AResource sr = null;
        try {
            sr = new SiteResource(site).
                    addWarningAction(new SendEmailAction("achermida@gmail.com")).
                    addRestartCommand(new CentOSRebootCommand());
            st =  sr.status();
            if(!st.isStatusOk()){
                sr.runWarningActions();
            }else{
                Logger.getLogger(MonitorBean.class.getName()).log(Level.FINE, sr.upTime().toString());
            }
        } catch (URISyntaxException uex) {
            Logger.getLogger(MonitorBean.class.getName()).log(Level.FINE, null, uex);
            st = new Status(false, uex.getMessage(), -1);
            if(sr != null) sr.runWarningActions();
        }catch (Exception ex) {
            Logger.getLogger(MonitorBean.class.getName()).log(Level.FINE, null, ex);
            st = new Status(false, ex.getMessage(), -1);
            if(sr != null) sr.runWarningActions();
        } 

        return st;
    }

}
