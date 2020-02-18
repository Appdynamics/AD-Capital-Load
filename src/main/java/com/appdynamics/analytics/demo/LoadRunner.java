package com.appdynamics.analytics.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

/**
 * Load-Gen for jsp
 */
public class LoadRunner implements Runnable {
    static final Logger logger = Logger.getLogger(LoadRunner.class.getName());

    private static int processorPort;
    private static int portalPort;
    private static String portalUrl = "";
    private static String processorUrl = "";
    private static int users;
    private static int sleepTimeInMillis;


    public LoadRunner() {

    }

    @Override
    public void run() {
        while (true) {
            callPortalAuthenticate(portalUrl,portalPort);
            sleep();
            callPortalSubmitApplication(portalUrl, portalPort);
            sleep();
            callProcessorCreditCheck(processorUrl, processorPort);
            sleep();
            callProcessorUnderWrite(processorUrl, processorPort);
            sleep();
        }

    }

    private void sleep() {
        try {
            Thread.currentThread().sleep(sleepTimeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        parseArgs(args);
        CountDownLatch countDownLatch = new CountDownLatch(users);
        LoadRunner runner = new LoadRunner();
        for(int i = 0; i < users; i++) {
            Thread thread = new Thread(runner);
            thread.start();
        }
        countDownLatch.await();
    }


    private static void parseArgs(String[] args) {
        logger.info(args[0] + " " + args[1]);
        portalUrl = args[0];
        processorUrl = args[1];
        if(args.length > 2){
            users = Integer.parseInt(args[2]);
        } else {
            users = 25;
        }
        if(args.length > 3){
            sleepTimeInMillis = Integer.parseInt(args[3]);
        } else {
            sleepTimeInMillis = 300;
        }
        portalPort = 8080;
        processorPort = 8080;
    }
    private static void callPortalAuthenticate(String portalUrl, int portalPort){
        try {
            URL myURL = new URL("http://" + portalUrl + ":" + portalPort + "/portal/CustomerLogin");
            URLConnection myURLConnection = myURL.openConnection();
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(myURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                logger.info(inputLine);
            }
            br.close();
        }
        catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
    private static void callPortalSubmitApplication(String portalUrl, int portalPort){
        try {
            URL myURL = new URL("http://" + portalUrl + ":" + portalPort + "/portal/SubmitApplication");
            URLConnection myURLConnection = myURL.openConnection();
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(myURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                logger.info(inputLine);
            }
            br.close();
        }
        catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private static void callProcessorCreditCheck(String processorUrl, int processorPort){
        try {
            URL myURL = new URL("http://" + processorUrl + ":" + processorPort + "/processor/CreditCheck");
            URLConnection myURLConnection = myURL.openConnection();

            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(myURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                logger.info(inputLine);
            }
            br.close();
        }
        catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    private static void callProcessorUnderWrite(String processorUrl, int processorPort){
        try {
            URL myURL = new URL("http://" + processorUrl + ":" + processorPort + "/processor/Underwrite");
            URLConnection myURLConnection = myURL.openConnection();
            // open the stream and put it into BufferedReader
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(myURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                logger.info(inputLine);
            }
            br.close();
        }
        catch (MalformedURLException e) {
            logger.warning(e.getMessage());
        }
        catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
