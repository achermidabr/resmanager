/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.sc.fatma.resourcemanager.common;

/**
 *
 * @author Alexandre
 */
public interface ICommand {
    public Status execute() throws Exception;
}
