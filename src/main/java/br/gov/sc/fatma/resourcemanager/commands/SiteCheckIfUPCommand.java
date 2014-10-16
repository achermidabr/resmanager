/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.commands;

import br.gov.sc.fatma.resourcemanager.common.ICommand;
import br.gov.sc.fatma.resourcemanager.common.Status;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Alexandre
 */
public class SiteCheckIfUPCommand implements ICommand {

    private final URI _site;

    public SiteCheckIfUPCommand(URI site) {
        _site = site;
    }

    @Override
    public Status execute() {
        Logger.getLogger(SiteCheckIfUPCommand.class.getName()).log(Level.FINE, "-------- "+_site.getPath()+" Connection Testing ------");
        Status result;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(_site);
            Logger.getLogger(SiteCheckIfUPCommand.class.getName()).log(Level.INFO, "Executing request " + httpget.getRequestLine());

            ResponseHandler<String> responseHandler = new BasicResponseHandler() {
                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        return "200";
                    } else {
                        return String.valueOf(status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            result = new Status(responseBody.equals("200"),"",Integer.valueOf(responseBody));
        } catch (IOException ex) {
            Logger.getLogger(SiteCheckIfUPCommand.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
            ex.printStackTrace();
            result = new Status(false,ex.getMessage(),-1);
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {}
        }
        
        return result;
    }

}
