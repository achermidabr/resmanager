/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.resources;

import br.gov.sc.fatma.resourcemanager.enums.DatabaseTypeEnum;
import br.gov.sc.fatma.resourcemanager.resources.PostgresDatabaseResource;
import br.gov.sc.fatma.resourcemanager.resources.DatabaseResource;
import br.gov.sc.fatma.resourcemanager.resources.OracleDatabaseResource;

/**
 *
 * @author Alexandre
 */
public class DatabaseResourceBuilder {
    public DatabaseTypeEnum dbtype;
    private String url;
    private String user;
    private String pwd;
    
    public DatabaseResourceBuilder(DatabaseTypeEnum dbtype){
        this.dbtype = dbtype;
    }
    
    public DatabaseResourceBuilder withURL(String url){
        this.url = url;
        return this;
    }
    
    public DatabaseResourceBuilder withUser(String usr){
        this.user = usr;
        return this;
    }
    
    public DatabaseResourceBuilder withPwd(String pwd){
        this.pwd = pwd;
        return this;
    }
    
    public DatabaseResource build(){
        DatabaseResource result;
        if(dbtype == DatabaseTypeEnum.ORACLE){
            result = new OracleDatabaseResource(url,user,pwd);
        }else if (dbtype == DatabaseTypeEnum.POSTGRESQL){
            result = new PostgresDatabaseResource(url,user,pwd);
        }else{
            result = new OracleDatabaseResource(url,user,pwd); 
        }
        
        return result;
    }
}
