package org.xmlblackbox.test.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 *
 * @author
 */


public class Configurator {

  private final static Logger log = Logger.getLogger(Configurator.class);
  
  private final static String pathLog4JProperties ="log4j.properties";

  private final static String pathPropertiesFile ="exampleTest.properties";

  private static Properties cachedProps;
  private static boolean isConfigureLog = false;

  public Configurator() {
  }

  public static Properties getProperties() {

	 log.info("pathPropertiesFile "+pathPropertiesFile );
	 cachedProps=new Properties();
     InputStream is = Configurator.class.getClassLoader().getResourceAsStream(pathPropertiesFile);
     try {
       cachedProps.load(is);
       log.info("prop loaded "+cachedProps );
   	 }
     catch (IOException ex) {
       ex.printStackTrace();
     }finally{
    	 try {
			is.close();
		} catch (IOException e) {
			log.error("Eccezione nella chiusura dello stream del file delle prooperties", e);
		}
     }
    
    return cachedProps;
  }

  public static Properties getProperties(String nomeFile) {

      Properties validazioneProps=new Properties();
      InputStream is = Configurator.class.getClassLoader().getResourceAsStream(nomeFile);
      try {
          validazioneProps.load(is);
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }finally{
     	 try {
 			is.close();
 		} catch (IOException e) {
 			log.error("Eccezione nella chiusura dello stream del file delle prooperties", e);
 		}
      }
     
     return validazioneProps;
   }

  public static void addProperties(Properties props, String nomeFile) {

      if(props == null)
         props=new Properties();

      InputStream is = Configurator.class.getClassLoader().getResourceAsStream(nomeFile);
      try {
          props.load(is);
          log.info("Configuration file loaded");
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }finally{
     	 try {
 			is.close();
 		} catch (IOException e) {
 			log.error("Eccezione nella chiusura dello stream del file delle prooperties", e);
 		}
      }
     
   }

  public static void configureLog4J() {

    if (!isConfigureLog) {
      InputStream is = null;
      try {
          is = Configurator.class.getClassLoader().getResourceAsStream(pathLog4JProperties);
      }catch (Exception ex) {
        ex.printStackTrace();
      }
      Properties prop = new Properties();
      try {
        prop.load(is);
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }

      PropertyConfigurator.configure(prop);
      log.info("Configurazione log4J per i TEST Ok");
      isConfigureLog = true;
    }

  }




}
/*
 * $Id: $
 * $Log:$
 */
