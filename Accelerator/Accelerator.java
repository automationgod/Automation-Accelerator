package com.Accelerator;

import java.io.File;
import java.io.IOException;


import org.apache.regexp.recompile;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/*
 * @this is basically WebDriver common action methods
 * @this accelerator helps to develop quick automation test development
 */

public class Accelerator {

    private WebDriver driver;
	WebDriverWait wait = new WebDriverWait(driver, 30);
	public Accelerator(WebDriver driver){
		this.driver = driver;
	}
	
	public  boolean JClickElement(By locator) throws Exception {
        try {            

            WebElement element = driver.findElement(locator);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].scrollIntoView({behavior: \"instant\", block: \"center\", inline: \"nerest\"})", element);
            executor.executeScript("arguments[0].click();", element);
            System.out.println("Clicked on Element : " + locator);            
            return  true;
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(   "Unable to Find Element : " +   locator );
            return false;
        }
    }
	
	public  boolean ClickElement(By locator) throws Exception {
        try {
            WebElement element = driver.findElement(locator);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            System.out.println("Clicked on Element : " + locator);            
            return  true;
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(   "Unable to Find Element : " +   locator  );
            return false;
        }
    }
	
	public  boolean EnterText(String strValue, By locator) throws Exception {
        try {
        	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            WebElement element = driver.findElement(locator);
            element.clear();
            element.sendKeys(strValue);
            System.out.println(   "Entered value "  +  strValue + "  in Text box : " +   locator);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(   "Unable to Find Element : " +   locator  );
            return false;
        }
    }
	
	public  boolean EnterTextUsingJS(String strValue, By locator) throws Exception {
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			WebElement element = driver.findElement(locator);
			element.clear();
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].scrollIntoView({behavior: \"instant\", block: \"center\", inline: \"nerest\"})", element);
			executor.executeScript("arguments[0].setAttribute('value', '"+strValue+"')", element);
			System.out.println(   "Entered value "  +  strValue + "  in Text box : " +   locator);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(   "Unable to Find Element : " +   locator  );
            return false;
		}
	}
	public  boolean VerifyControlExist(By locator) throws Exception {
    	boolean isControlExists = false;
        try {
        	
        	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            WebElement element = driver.findElement(locator);
	            if (   element.isDisplayed()  )
	            {
	            	System.out.println(   "Verified that control exists. Control locator : " +   locator);
		            isControlExists = true;
	            }else
	            {
	            	System.out.println( "The control not exist. Control locator : " +   locator);
	            
	            }            
	        }catch (Exception e) {
	        	System.out.println( "The control not exist. Control locator : " +   locator );
	            
	        }
        return isControlExists;
    }
	
	public boolean highlightElement(By locator) throws Exception{
		
		try {
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].setAttribute('style','border: solid 2px red;');", element);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			executor.executeScript("arguments[0].setAttribute('style','border: solid 2px white;');", element);
			System.out.println(   "Highlighted Element " +   locator );
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println( " Unable to Find Element : " +   locator);
			return false;
		}
	}
	
	public boolean verifyPDFContent(String pdfPath, String textToValidate) throws Exception, IOException{
		
		File file = new File(pdfPath);
		PDDocument document = PDDocument.load(file);
		
		PDFTextStripper pdfTextStripper = new PDFTextStripper();
		String text = pdfTextStripper.getText(document);
		
		if(text.contains(textToValidate)){
			System.out.println(   textToValidate + " = is present in pdf ");
			document.close();
			return true;
		}else{
			System.out.println(   textToValidate + " = is not present in pdf " );
			document.close();
			return false;
		}
		
	}
	
	public boolean isCheckBoxSelect(boolean isCheckBoxSelected, By locator) throws Exception{
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		WebElement element = driver.findElement(locator);
		
		if(isCheckBoxSelected == true){
			if(!element.isSelected()){
				ClickElement(locator);
			}
		}else if(isCheckBoxSelected == false){
			if(!element.isSelected()){
				
			}else{
				ClickElement(locator);
			}
		}
		System.out.println( " Checkbox found " + locator);
		return true;
	}
	
	public void waitTillElementDisappear(String elementText){
		
		try {
			String bodyText = null;
			WebElement element = driver.findElement(By.tagName("body"));
			bodyText = element.getText();
			int count = 0;
			do {
				Thread.sleep(2000);
				count = count+2;
				if(bodyText.replaceAll(" ", "").toLowerCase().contains(elementText.toLowerCase())){
					System.out.println("Wait for "+elementText+" to be disappear!");
					if(count >= 300){
						System.out.println( " Application performance is too slow! " );
						Assert.assertFalse(true);
					}
				}
			} while (element.getSize().getHeight() > 0);
		} catch (Exception e) {
			
		}
	}
}
