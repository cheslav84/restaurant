//package com.epam.havryliuk.restaurant.controller;
//
//import com.epam.havryliuk.restaurant.model.service.OrderService;
//import com.epam.havryliuk.restaurant.model.util.annotations.AnnotationProcessor;
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//@WebListener
//public class ContextListener implements ServletContextListener {
//    private static final Logger LOG = LogManager.getLogger(ContextListener.class);
//
//
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        ServletContextListener.super.contextInitialized(sce);
//        AnnotationProcessor.init();
//        LOG.debug("Initialisation completed.");
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
////        ServletContextListener.super.contextDestroyed(sce);
//        //todo з'ясувати чи необхідно закривати пул конекшинів томкет чи він сам їх закриває?
//    }
//}
