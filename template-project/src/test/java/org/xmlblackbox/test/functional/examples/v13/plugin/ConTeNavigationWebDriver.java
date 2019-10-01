package org.xmlblackbox.test.functional.examples.v13.plugin;

import com.thoughtworks.selenium.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.xmlblackbox.test.infrastructure.exception.RunPluginAbnormalTermination;
import org.xmlblackbox.test.infrastructure.interfaces.NewSeleniumNavigation;
import org.xmlblackbox.test.infrastructure.plugin.GenericRunnablePlugin;
import org.xmlblackbox.test.infrastructure.util.MemoryData;
import org.xmlblackbox.test.infrastructure.xml.RunPlugin;


public class ConTeNavigationWebDriver extends GenericRunnablePlugin {

	private final static Logger logger = Logger.getLogger(RunPlugin.class);

	
	@Override
	public void execute(MemoryData memory) throws RunPluginAbnormalTermination{
//		memory.debugMemory();



    	FirefoxProfile profile = new FirefoxProfile();
//    	profile.addAdditionalPreference("network.proxy.no_proxies_on", "conte.it,preventivo.conte.it");
//    	profile.addAdditionalPreference("network.proxy.http_port", "3128");
    	WebDriver driver = new FirefoxDriver(profile);
   	

		driver.get("https://preventivo.conte.it/calculator/calculatorAuto.htm");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		driver.findElement(By.id("page:text_1CNSC")).click();
//		selenium.select("//select[@id='page:text_1CNSC']", "label=Internet");

		Select select = new Select(driver.findElement(By.xpath("//select[@id='page:text_1CNSC']")));
		select.selectByVisibleText("Passaparola");
		
//		driver.findElement(By.cssSelector("option[value=5]")).click();
		driver.findElement(By.id("page:situazione_1:1")).click();
		driver.findElement(By.id("page:targa")).click();
		driver.findElement(By.id("page:targa")).clear();
		driver.findElement(By.id("page:targa")).sendKeys("cc747dd");
		driver.findElement(By.id("page:buttonContinua")).click();
		
//        logger.info("source "+driver.getPageSource());
		driver.findElement(By.id("page:dataEffetto")).click();
		driver.findElement(By.id("page:dataEffetto")).sendKeys("11/09/2011");
		Select selectMeseImm = new Select(driver.findElement(By.xpath("//select[@id='page:mese_immatricolazione']")));
		selectMeseImm.selectByVisibleText("Luglio");
;

		driver.findElement(By.id("page:anno_immatricolazione")).click();
		driver.findElement(By.id("page:anno_immatricolazione")).clear();
		driver.findElement(By.id("page:anno_immatricolazione")).sendKeys("2004");
		driver.findElement(By.id("page:anno_acquisto")).clear();
		driver.findElement(By.id("page:anno_acquisto")).sendKeys("2008");
		


		Select selectAlimentazione = new Select(driver.findElement(By.xpath("//select[@id='page:alimentazione']")));
		selectAlimentazione.selectByVisibleText("Diesel");

		Select selectModelloAuto2 = new Select(driver.findElement(By.xpath("//select[@id='page:modelloAuto']")));
		logger.info("111selectModeloAuto.getOptions().isEmpty() "+selectModelloAuto2.getOptions().isEmpty());

		Select selectMarca = new Select(driver.findElement(By.xpath("//select[@id='page:marca']")));
		selectMarca.selectByVisibleText("FORD");

		int index = 0;
		for (int i = 0; i < 20; i++) {
			Select selectModelloAuto = new Select(driver.findElement(By.xpath("//select[@id='page:modelloAuto']")));
			logger.info("selectModeloAuto.getOptions().isEmpty() "+selectModelloAuto.getOptions().isEmpty());
			
			if (!selectModelloAuto.getOptions().isEmpty()){
				break;
			}else{
				if (i<index){
					break;
				}
				logger.info("Sleep di 1 secondo");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
//		(new WebDriverWait(driver, 10))
//		  .until(new ExpectedCondition<WebElement>(){
//			@Override
//			public WebElement apply(WebDriver d) {
//				return d.findElement(By.xpath("//select[@id='page:modelloAuto']"));
//			}});

		Select selectModello = new Select(driver.findElement(By.xpath("//select[@id='page:modelloAuto']")));
		selectModello.selectByIndex(7);

		for (int i = 0; i < 20; i++) {
			Select selectModeloAuto = new Select(driver.findElement(By.xpath("//select[@id='page:allestimento']")));
			
			if (!selectModeloAuto.getOptions().isEmpty()){
				break;
			}else{
				if (i<index){
					break;
				}
				logger.info("Sleep di 1 secondo");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		Select selectAllestimento= new Select(driver.findElement(By.xpath("//select[@id='page:allestimento']")));
		selectAllestimento.selectByVisibleText("FOCUS 1.8 TDDI CAT 5P. GHIA - 10/1998");

		logger.info("Sleep di 1 secondo");

		driver.findElement(By.id("page:continua_step01")).click();
        
        
        logger.info("");
	}


	@Override
	public List<String> getParametersName() {
		ArrayList<String> ret = new ArrayList();
		return ret;
	}
}
