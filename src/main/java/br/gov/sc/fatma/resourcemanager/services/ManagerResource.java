/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.services;

import br.gov.sc.fatma.resourcemanager.enums.DatabaseTypeEnum;
import br.gov.sc.fatma.resourcemanager.actions.SendEmailAction;
import br.gov.sc.fatma.resourcemanager.commands.CentOSRebootCommand;
import br.gov.sc.fatma.resourcemanager.common.AResource;
import br.gov.sc.fatma.resourcemanager.common.Status;
import br.gov.sc.fatma.resourcemanager.resources.DatabaseResourceBuilder;
import br.gov.sc.fatma.resourcemanager.resources.SiteResource;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * REST Web Service
 *
 * @author Alexandre
 */
@Path("")
public class ManagerResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public ManagerResource() {
    }

    /**
     * Retrieves representation of an instance of
     * br.gov.sc.fatma.resourcemanager.main.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatus() {
        return Response.ok(new Status(true)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sinfat/status")
    public Response getStatusSinfat() {
        return checkSiteResultRes("http://sinfat.fatma.sc.gov.br").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sinfatweb/status")
    public Response getStatusSinfatWeb() {
        return checkSiteResultRes("http://sinfatweb.fatma.sc.gov.br").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ws/status")
    public Response getStatusWS() {
        return checkSiteResultRes("http://ws.fatma.sc.gov.br").build();
    }

    @GET
    @Produces("application/xml")
    @Path("oracle/status")
    public Response getStatusOracle() {
        AResource ores = new DatabaseResourceBuilder(DatabaseTypeEnum.ORACLE).
                    withURL("jdbc:oracle:thin:@172.19.212.43:1521:sinfat").
                    withUser("producao").
                    withPwd("$s1nf47_or4cl3p#").build().
                    addWarningAction(new SendEmailAction("achermida@gmail.com")).
                    addRestartCommand(new CentOSRebootCommand());
        ResponseBuilder result;

        try {
            result = Response.ok(ores.status());
        } catch (Exception ex) {
            Logger.getLogger(ManagerResource.class.getName()).log(Level.FINE, null, ex);
            result = Response.ok(new Status(false,ex.getMessage(),-1));
        }
        return result.build();
    }

    private ResponseBuilder checkSiteResultRes(String site) {

        Status st;
        try {
            st = new SiteResource(site).status();
        } catch (URISyntaxException uex) {
            Logger.getLogger(ManagerResource.class.getName()).log(Level.FINE, null, uex);
            st = new Status(false, uex.getMessage(), -1);
        }catch (Exception ex) {
            Logger.getLogger(ManagerResource.class.getName()).log(Level.FINE, null, ex);
            st = new Status(false, ex.getMessage(), -1);
        }

        return Response.ok(st);
    }
}
