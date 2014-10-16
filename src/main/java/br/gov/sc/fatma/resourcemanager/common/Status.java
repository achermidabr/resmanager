/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.common;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alexandre
 */
@XmlRootElement
public class Status {
    private boolean _statusOk;
    private String _message;
    private Integer _code;
    private Object _something;
    
    public Status(){
        _statusOk = false;
    }
    
    public Status(boolean status){
        _statusOk = status;
    }
    
    public Status(boolean status, String message, Integer code){
        _statusOk = status;
        _message = message;
        _code = code;
    }
    
    public Object getSomething(){
        return _something;
    }
    
    public void setSomething(Object something){
        _something=something;
    }
    
    public String getMessage() {
        return _message;
    }

    public Integer getCode() {
        return _code;
    }
    
    public boolean isStatusOk(){
        return _statusOk;
    }

    @Override
    public String toString() {
        return "Status{" + "_statusOk=" + _statusOk + ", _message=" + _message + ", _code=" + _code + '}';
    }
    
    
}
