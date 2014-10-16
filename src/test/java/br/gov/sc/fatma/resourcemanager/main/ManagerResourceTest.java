///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package br.gov.sc.fatma.resourcemanager.main;
//
//import javax.ws.rs.core.Response;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Alexandre
// */
//public class ManagerResourceTest {
//    
//    public ManagerResourceTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of getStatus method, of class ManagerResource.
//     */
//    @Test
//    public void testGetStatus() {
//        System.out.println("getStatus");
//        ManagerResource instance = new ManagerResource();
//        String expResult = "";
//        String result = instance.getStatus();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getStatusSinfat method, of class ManagerResource.
//     */
//    @Test
//    public void testGetStatusSinfat() {
//        System.out.println("getStatusSinfat");
//        ManagerResource instance = new ManagerResource();
//        Response result = instance.getStatusSinfat();
//        assertEquals(200, result.getStatus());
//    }
//
//    /**
//     * Test of getStatusSinfatWeb method, of class ManagerResource.
//     */
//    @Test
//    public void testGetStatusSinfatWeb() {
//        System.out.println("getStatusSinfatWeb");
//        ManagerResource instance = new ManagerResource();
//        Response result = instance.getStatusSinfatWeb();
//        assertEquals(200, result.getStatus());
//    }
//
//    /**
//     * Test of getStatusWS method, of class ManagerResource.
//     */
//    @Test
//    public void testGetStatusWS() {
//        System.out.println("getStatusWS");
//        ManagerResource instance = new ManagerResource();
//        Response result = instance.getStatusWS();
//        assertEquals(200, result.getStatus());
//    }
//
//    /**
//     * Test of getStatusOracle method, of class ManagerResource.
//     */
//    @Test
//    public void testGetStatusOracle() {
//        System.out.println("getStatusOracle");
//        ManagerResource instance = new ManagerResource();
//        Response result = instance.getStatusOracle();
//        assertEquals(200, result.getStatus());
//    }
//    
//}
