package fwUpdate;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Screen;


public class LS9FwUpdate {
	
	private WebDriver driver= null;
	WebDriverWait wait;
	JavascriptExecutor js;
	Screen screen;
	String chooseFile="C:\\Academy\\LibreAutomation\\resources\\chooseFile.png";
	String chooseLocation="C:\\Academy\\LibreAutomation\\resources\\chooseLocation.png";
	String typeLocation="C:\\Academy\\ls9AD_firmarweupdate\\firmware";
	String selectImage="C:\\Academy\\LibreAutomation\\resources\\83_image_network.png";
	String clickUpdate="C:\\Academy\\LibreAutomation\\resources\\clickUpdate.png";
	String confirmUpdate="C:\\Academy\\LibreAutomation\\resources\\confirmUpdate.png";
	String ipAddress="192.168.1.105";
	String indexmap= "/index.asp";
	String schema="http://";
	String baseUrl =schema+ipAddress+ indexmap;

	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		screen = new Screen();
		js = (JavascriptExecutor) driver;
		wait=new WebDriverWait(driver, 60);
	}
	
	public void enterDeviceIPAddress() {
		driver.get(baseUrl);
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS); 
		// driver.manage().window().maximize();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			System.out.println("you have entered the wrong Ip Address/loading webpage taking too much time");
		}
		js.executeScript("window.scrollBy(0,3000)");
	}

	
	public void scrollDownAndClickUpdate() throws InterruptedException {
		WebElement deviceName = driver.findElement(By.id("AdvBtn_UPG"));
		deviceName.click();
		Thread.sleep(2000);
	}

	public void select_83_Network_Image() throws InterruptedException {
		try {
			Thread.sleep(2000);
			screen.click(chooseFile);
			screen.click(chooseLocation);
			screen.type(typeLocation);
			screen.type(Key.ENTER);
			screen.doubleClick(selectImage);
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void startFirmwareUpdate() {
		try {
			screen.click(clickUpdate);
			Thread.sleep(2000);

			wait.until(ExpectedConditions.titleIs("LIBRE-SYNC Firmware"));
			wait.until(ExpectedConditions.titleIs("LIBRE-SYNC Firmware"));
			screen.click(confirmUpdate);
			Thread.sleep(10000);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			driver.close();
		}

	}

	public void runpythonscript() {
		StringWriter writer = new StringWriter(); //ouput will be stored here
		 System.out.println("inside runpythonscript");
	    ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptContext context = new SimpleScriptContext();
	    System.out.println("inside runpythonscript---2");
	    context.setWriter(writer); //configures output redirection
	    ScriptEngine engine = manager.getEngineByName("python");
	    try {
			engine.eval(new FileReader("serialcomm.py"), context);
		} catch (FileNotFoundException | ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println("inside runpythonscript---3");
	    System.out.println(writer.toString());
	}
public static void main(String args[]) throws InterruptedException {
	LS9FwUpdate fwupdate = new LS9FwUpdate();
	
	fwupdate.openBrowser();
	fwupdate.enterDeviceIPAddress();
	try {
		fwupdate.scrollDownAndClickUpdate();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	fwupdate.select_83_Network_Image();
	fwupdate.startFirmwareUpdate();
	fwupdate.runpythonscript();
	
}
}
