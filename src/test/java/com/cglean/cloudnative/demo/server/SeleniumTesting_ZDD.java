package com.cglean.cloudnative.demo.server;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class SeleniumTesting_ZDD {
	
	
	
	@Test
	public  void SeleniumTesting() throws InterruptedException {
			
			{
				System.setProperty("webdriver.chrome.driver", "/var/lib/jenkins/workspace/seleniumProject/chromedriver.exe");
				WebDriver driver = new ChromeDriver();
				//WebDriver driver = new FirefoxDriver();
				driver.navigate().to("http://shows-cgleanv1.cglean.com/");
				driver.manage().window().maximize();
				Thread.sleep(1000);

				driver.findElement(
						By.xpath("//div[@id='bs-example-navbar-collapse-1']/ul/li/a"))
						.click();

				driver.findElement(By.id("loadButton")).click();
				Thread.sleep(10000);

				driver.findElement(By.id("loadButton")).click();

				driver.findElement(
						By.xpath("//div[@id='bs-example-navbar-collapse-1']/ul/li[2]/a"))
						.click();
				Thread.sleep(10000);

				driver.findElement(
						By.cssSelector("div[id$='bs-example-navbar-collapse-1']>ul>li:nth-child(3)>a"))
						.click();
				Thread.sleep(1000);

				driver.findElement(By.xpath("//a[text()[contains(.,'Refresh')]]"))
						.click();
				Thread.sleep(10000);

				driver.findElement(By.id("loadButton")).click();
				Thread.sleep(10000);

				driver.findElement(
						By.xpath("//a[text()[contains(.,'Stop Simulation')]]"))
						.click();
				Thread.sleep(1000);

				WebElement shows = driver.findElement(By
						.cssSelector("div[id$='bluegreenBlock']>p"));


				String showcount = shows.getText();

				System.out.print(showcount);
				
				String[] splitStr = showcount.split("\\s+");
				
				String results = "";
				if(Integer.parseInt(splitStr[2])>5){
					results = "PASS";
				}else{
					results = "FAIL";
				}


				System.out.println(results);
				driver.close();
				
				
			}
		}

}
