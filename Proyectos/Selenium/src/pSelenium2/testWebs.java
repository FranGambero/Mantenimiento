package pSelenium2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//Autor: Francisco Gambero Salinas
//Fecha: 29 de mayo de 2018

public class testWebs {
	
WebDriver driver;
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Before
	public void setUp() {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Takox\\Documents\\Mantenimiento\\Practica5\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		//driver.get("http://jabega.uma.es/");
		//assertEquals("Jábega, Catálogo de la Biblioteca de la UMA", driver.getTitle());
		
		 /*
		System.setProperty("webdriver.edge.driver", "C:\\Users\\Takox\\Documents\\Mantenimiento\\Practica5\\MicrosoftWebDriver.exe");
		driver = new EdgeDriver();
		driver.get("http://jabega.uma.es/");
		*/
	}	

	@Test
	public void testEnlaces() {
		driver.get("http://www.jabega.uma.es");
		
		//Los enlaces tendrán el tag HTML <a>, los recogo todos con findElements
		List<WebElement> enlacesList = driver.findElements(By.tagName("a"));
		List<String> websVisitadas = new ArrayList<String>();
		
		//Recorro todos los enlaces con un for
		for(WebElement enlace: enlacesList) {
			//Extraigo la dirección del enlace
			String direccion = enlace.getAttribute("href");
		
			if(direccion.isEmpty() || direccion == null) {
				System.out.println(enlace.getText() + " || Link nulo o vacío.");
			} else if(direccion.contains("http://www.jabega.uma.es")) {
				System.out.println(enlace.getText() + " || contiene patrón deseado.");
				websVisitadas.add(direccion);
			} 
		}
				
		driver.close();
		
	}

}
