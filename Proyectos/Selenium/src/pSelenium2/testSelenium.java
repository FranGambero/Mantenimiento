package pSelenium2;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

// Autor: Francisco Gambero Salinas
// Fecha: 21 de mayo de 2018

public class testSelenium {

	/*
	@Test
	public void testFirefox() {
		System.setProperty("webdriver.gecko.driver", "C:\\Users\\Takox\\Documents\\Mantenimiento\\Practica5\\geckodriver-v0.20.1-win32\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.google.com");
		assertEquals("Google", driver.getTitle());
		driver.quit();
	}
	*/
	
	@Test
	public void testCreditos() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Takox\\Documents\\Mantenimiento\\Practica5\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.uma.es");
		assertEquals("Inicio - Universidad de Málaga", driver.getTitle());
		
		WebElement element;
		
		element = driver.findElement(By.partialLinkText("Estudiar"));
		element.click();
		
		element = driver.findElement(By.partialLinkText("Grado"));
		element.click();
		assertEquals("OFERTA DE GRADO - Listado de grados - Universidad de Málaga", driver.getTitle());
		
		element = driver.findElement(By.partialLinkText("Grado en Ingeniería del Software"));
		element.click();
		assertEquals("GRADO EN INGENIERÍA DEL SOFTWARE - GRADO EN INGENIERÍA DEL SOFTWARE - INICIO - Universidad de Málaga", driver.getTitle());
		
		element = driver.findElement(By.partialLinkText("PROGRAMACIÓN DOCENTE"));
		element.click();
		assertEquals("Asignaturas - Universidad de Málaga", driver.getTitle());
		
		element = driver.findElement(By.partialLinkText("Mantenimiento y Pruebas del Software"));
		element.click();
		assertEquals("Mantenimiento y Pruebas del Software - Universidad de Málaga", driver.getTitle());
		
		List<WebElement> displays = driver.findElements(By.className("displayField"));
		  
		  String res = "";
		  
		  for(WebElement w: displays){
		   if(w.getText().contains("Teóricos"))
		    res = w.getText();
		  }

		  int creditos;
		  
		  try(Scanner sc = new Scanner(res)){
		   boolean encontrado = false;
		   String linea = "";

		   while(sc.hasNextLine() && !encontrado){
		    linea = sc.nextLine();
		    encontrado = linea.contains("Teóricos");
		   }

		   Scanner creditSc = new Scanner(linea);
		   creditos = creditSc.nextInt();
		   creditSc.close();
		  }
		  
		  assertEquals(6, creditos);
		  driver.close();
	}
	
	@Test
	public void testNoticias() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Takox\\Documents\\Mantenimiento\\Practica5\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.uma.es");
		assertEquals("Inicio - Universidad de Málaga", driver.getTitle());
		
		WebElement element;
		
		element = driver.findElement(By.partialLinkText("más noticias »"));
		element.click();
		assertEquals("Sala de Prensa - Noticias sala de prensa" + 
				" - Universidad de Málaga", driver.getTitle());
		
		List<WebElement> noticias = driver.findElements(By.className("itemCollection"));
		
		int numNoticias = noticias.size();
		
		assertEquals(10, numNoticias);
		
		driver.close();
	}
	
	@Test
	public void testNumeroTelefono() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Takox\\Documents\\Mantenimiento\\Practica5\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.uma.es");
		assertEquals("Inicio - Universidad de Málaga", driver.getTitle());
		
		WebElement element;
		
		element = driver.findElement(By.partialLinkText("Contacta"));
		element.click();
				
		element = driver.findElement(By.partialLinkText("Buscador de personal"));
		element.click();
		assertEquals("DUMA - Buscador de DUMA", driver.getTitle());
		
		WebElement nameText = driver.findElement(By.id("id_nombre"));
		WebElement surnameText = driver.findElement(By.id("id_apellido_1"));
		
		WebElement form = driver.findElement(By.tagName("form"));
				
		nameText.sendKeys("Francisco");
		surnameText.sendKeys("Durán");
		form.submit();
		
		element = driver.findElement(By.partialLinkText("Duran Muñoz, Francisco Javier"));
		element.click();
		assertEquals("DUMA - Datos de Francisco Javier Durán Muñoz en DUMA", driver.getTitle());
		
		List<WebElement> elements;
		  
		elements = driver.findElements(By.className("col-md-10"));
		  
		String res="";
		  
		for(WebElement w : elements) {
		   if(w.getText().contains("952132820")) {
			   res= w.getText();
			   break;
		   	}
		 }
		  
		assertEquals("952132820",res);
		
		driver.close();		
		
	}
	
}
