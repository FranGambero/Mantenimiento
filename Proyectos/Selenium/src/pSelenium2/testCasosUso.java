package pSelenium2;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//Autor: Francisco Gambero Salinas
//Fecha: 24 de mayo de 2018

public class testCasosUso {

	WebDriver driver;
	
	
	//Implemento un recolector de errores, el cual permite que el flujo de las pruebas continuas a pesar de que un aserto falle
	//El error se recoge y se lanza por consola un mensaje con la incidencia.
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	
	@Before
	public void setUp() {
		
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Takox\\Documents\\Mantenimiento\\Practica5\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		//driver.get("http://jabega.uma.es/");
		//assertEquals("J�bega, Cat�logo de la Biblioteca de la UMA", driver.getTitle());
		
		 /*
		System.setProperty("webdriver.edge.driver", "C:\\Users\\Takox\\Documents\\Mantenimiento\\Practica5\\MicrosoftWebDriver.exe");
		driver = new EdgeDriver();
		driver.get("http://jabega.uma.es/");
		*/
	}	
	
	@Test
	public void testCU1() {
		WebElement element; //Creo el elemento que navegar� por las distintas p�ginas
		driver.get("http://jabega.uma.es/");
		assertEquals("J�bega, Cat�logo de la Biblioteca de la UMA", driver.getTitle());
		
		element = driver.findElement(By.linkText("Autor"));
		element.click();
		assertEquals("J�bega, Cat�logo de la Biblioteca de la UMA", driver.getTitle());
		
		element = driver.findElement(By.name("SEARCH"));
		element.sendKeys("Jorgensen");
		assertEquals("Jorgensen",element.getAttribute("value"));
		
		WebElement orderByList = driver.findElement(By.name("SORT"));
		boolean encontrado = false;
		
		Iterator<WebElement> iterator = orderByList.findElements(By.tagName("option")).iterator();
		WebElement seleccionado = null;
		
		while(!encontrado && iterator.hasNext()) {
			seleccionado = iterator.next();
			
			//Compruebo que la opci�n seleccionada tiene el valor A, que es T�tulo
			if(encontrado = seleccionado.getAttribute("value").equals("A")) {
				seleccionado.click(); //Selecciono la opci�n
			}
		}
		
		element = driver.findElement(By.name("SORT")); //Tiene ya seleccionada la opcion A
		assertEquals("A", element.getAttribute("value")); //Compruebo que tiene la opci�n seleccionada antes (Punto 6)
		
		WebElement form = driver.findElement(By.tagName("form"));
		form.submit();
		assertEquals("Universidad de Malaga /All Locations", driver.getTitle());
		
		List<WebElement> elements = driver.findElements(By.className("browseEntry"));
		
		int numEntradas = 0;
		  
		for(WebElement w: elements){
			if(w.getText().contains("Jorgensen, Paul C."))
				numEntradas = Integer.parseInt(w.findElement(By.className("browseEntryEntries")).getText());
		}
		  
		assertEquals(2, numEntradas);
		
		element = driver.findElement(By.partialLinkText("Jorgensen, Paul C."));
		element.click();
		assertEquals("Universidad de Malaga /All Locations", driver.getTitle());
		
		//Buscamos exactamente ese t�tulo para diferenciar del libro de 2002
		//element = driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr[4]/td/table/tbody/tr[2]/td[1]/table/tbody/tr[5]/td/table/tbody/tr/td[3]/a/img"));
		//element.click();
		
		List<WebElement> libros = driver.findElements(By.className("briefCitRow"));
		int i = 0;
		while(!libros.get(i).findElement(By.className("briefcitDetail")).getText().contains("2008")){
			i++;
		}
		element = libros.get(i).findElement(By.tagName("img"));
		element.click();
		
		element = driver.findElement(By.tagName("h2"));
		assertEquals("Software Testing : A Craftsman's Approach", element.getText());
		
		//PostCondicion
		assertEquals("Syndetic Solutions - Summary for ISBN Number 9780849374753", driver.getTitle());
				
		driver.close();
	}
	
	@Test
	public void testCU2() {
		driver.get("http://jabega.uma.es/");
		assertEquals("J�bega, Cat�logo de la Biblioteca de la UMA", driver.getTitle());
		
		WebElement element;
		
		//Precondici�n
		assertTrue(driver.findElement(By.className("menuTabActive")).isEnabled() && driver.findElement(By.className("menuTabActive")).getText().equals("Palabra clave"));
		
		element = driver.findElement(By.partialLinkText("Acceso a recursos-e"));
		element.click();
		assertEquals("Universidad de M�laga", driver.getTitle());
		
		element = driver.findElement(By.linkText("Libros electr�nicos"));
		element.click();
		
		WebElement orderByList = driver.findElement(By.name("searchtype"));
		boolean encontrado = false;
		
		Iterator<WebElement> iterator = orderByList.findElements(By.tagName("option")).iterator();
		WebElement seleccionado = null;
		
		while(!encontrado && iterator.hasNext()) {
			seleccionado = iterator.next();
			
			//Compruebo que la opci�n seleccionada tiene el valor A, que es T�tulo
			if(encontrado = seleccionado.getAttribute("value").equals("a")) {
				seleccionado.click(); //Selecciono la opci�n
			}
		}
		
		assertEquals(" AUTOR", seleccionado.getText());
		
		element = driver.findElement(By.name("searcharg"));
		element.sendKeys("Paul C Jorgensen");
		assertEquals("Paul C Jorgensen", element.getAttribute("value"));
		
		WebElement form = driver.findElement(By.tagName("form"));
		form.submit();
		assertEquals("Universidad de Malaga /All Locations", driver.getTitle());
		
		List<WebElement> entries = driver.findElements(By.className("browseEntry"));
		
		for (WebElement webElement : entries) {
			try {
				assertTrue(webElement.getText().startsWith("Paul") || webElement.getText().startsWith("Pa�l"));
			// Incidencia, se encuentran autores con distinto nombre (Pa�l)
			}catch(Throwable t) {
				collector.addError(t);
				System.out.println("Incidencia: Se ha encontrado un autor con nombre no deseado en CU2");
			}
		}
		
		assertTrue(driver.findElement(By.className("yourEntryWouldBeHere")).isDisplayed());
		
		//Uso el iterador y booleano usados previamente, ya que siguen creados en este test
		WebElement orderByListPaul = driver.findElement(By.name("searchtype"));
		
		Iterator<WebElement> iteratorPaul = orderByListPaul.findElements(By.tagName("option")).iterator();
		seleccionado = null;
		encontrado= false;
		
		while(!encontrado && iteratorPaul.hasNext()) {
			seleccionado = iteratorPaul.next();
			
			//Compruebo que la opci�n seleccionada tiene el valor A, que es T�tulo
			if(encontrado = seleccionado.getAttribute("value").equals("X")) {
				seleccionado.click(); //Selecciono la opci�n
			}
		}
		
		assertEquals(" PALABRA CLAVE", seleccionado.getText());
		
		element = driver.findElement(By.name("searcharg"));
		element.clear();
		element.sendKeys("software testing");
		assertEquals("software testing", element.getAttribute("value"));
		
		element = driver.findElement(By.name("SUBMIT"));
		element.click();

		//Compruebo que la p�gina tiene 50 entradas
		entries = driver.findElements(By.className("briefcitTitle"));
		assertEquals(50, entries.size());
		
		for (WebElement webElement : entries) {
			try {
			assertTrue(webElement.getText().toLowerCase().contains("software testing"));
			// Incidencia, se encuentran t�tulos sin el texto de b�squeda "software testing"
			}catch(Throwable t) {
				collector.addError(t);
				System.out.println("Incidencia: Se ha encontrado una entrada sin el texto de b�squeda deseado en CU2");
			}
		}
		
		//El caso de uso est� mal definido, as� que probaremos que accede al titulo del libro, obteniendo
		//informaci�n b�sica sobre el autor, fecha de publicaci�n, edici�n, etc..
		//El caso de uso pide acceder al recurso electr�nico, pero el libro que pide no dispone del mismo (otros libros como el 36 si p.e)
			
		element = driver.findElement(By.linkText("Software testing : a craftsman's approach"));
		//element = driver.findElement(By.cssSelector("a[href*='9780849374753']"));
		//element = driver.findElement(By.xpath("/html/body/div[1]/table/tbody/tr[4]/td/table/tbody/tr[3]/td[1]/table/tbody/tr[41]/td/table/tbody/tr/td[3]/a/img"));
		element.click();
		
		element = driver.findElement(By.tagName("tbody"));
		element = driver.findElements(By.tagName("tr")).get(1);
		element = driver.findElements(By.className("bibInfoData")).get(1);
		
		assertEquals("Software testing : a craftsman's approach", element.getText());
		
		//PostCondicion
		try {
			assertEquals("p�gina de detalles", driver.getTitle());
			// Incidencia, se encuentran t�tulos sin el texto de b�squeda "software testing"
			}catch(Throwable t) {
				collector.addError(t);
				System.out.println("Incidencia: Titulo de la p�gina no es el correcto en CU2");
			}
		
		
		System.out.println("Fin del test");
		driver.close();
	}

}
