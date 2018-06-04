package pSelenium2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class testRecursivo {

	WebDriver driver;
	private static String dominioOriginal = "";
	private String direccionSecundaria = "";
	private String direccionFinal = "";
	
	
	//Implemento un recolector de errores, el cual permite que el flujo de las pruebas continuas a pesar de que un aserto falle
	//El error se recoge y se lanza por consola un mensaje con la incidencia.
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
	
	private List<String> convertirWeToString(List<WebElement> listaEnlaces){
		List<String> listaLinks = new ArrayList<String>();
		
		for ( WebElement we : listaEnlaces) {
			listaLinks.add(we.getAttribute("href")); 
		}
		
		
		return listaLinks; 
	}
	
	private boolean isIDUMAorError(String enlace) {
		return enlace.startsWith("https://idp.uma.es");
	}
	
	private void loggingIDUMA(String enlaceLog) {
		WebElement username = driver.findElement(By.id("edit-name"));
		WebElement password = driver.findElement(By.id("edit-pass"));
		WebElement botonLog = driver.findElement(By.id("submit_ok"));
		
		username.sendKeys("cuenta@falsa.es");
		password.sendKeys("contrasenafalsa");
		botonLog.click();
		
		if(driver.getPageSource().toLowerCase().contains("error")){
			System.out.println("Error en el intento de loggeo");
		}else {
			System.out.println("Error exitoso WTF!!!");
		}
	}
	
	@Test
	public void testMaximo() {
		driver.get("http://jabega.uma.es/");
		assertEquals("Jábega, Catálogo de la Biblioteca de la UMA", driver.getTitle());
		dominioOriginal = driver.getCurrentUrl();
		
		//Imprimo enlace original a partir del cual haremos el arbol
		System.out.println("Comenzamos con direccion:\n " + dominioOriginal);
		
		
		if(dominioOriginal != null && dominioOriginal.contains("uma.es")){
			//Creamos lista con todos los enlaces
			List<WebElement> listaMaxima = driver.findElements(By.tagName("a"));
			List<String> listaEnlaces = convertirWeToString(listaMaxima);
			
			//Recorro todos los enlaces de la lista original
			for(String enlace: listaEnlaces) {
				if((enlace != null && enlace.contains("uma.es")) || enlace.startsWith("https://idp.uma.es")) {
					System.out.println("---" + enlace);
					
					driver.get(enlace);
					
					//Cojo el enlace que hay cargado en la web actual
					//Verifico que el enlace ese == enlace
					//assertEquals(enlace, driver.getCurrentUrl());
					
					
					List<WebElement> listaSecundaria = driver.findElements(By.tagName("a"));
					List<String> listaEnlacesSecundaria = convertirWeToString(listaSecundaria);
					
					for(String enlaceSecundario : listaEnlacesSecundaria) {
						if(enlaceSecundario != null && enlaceSecundario.contains("uma.es")) {
							System.out.println("------" + enlaceSecundario);
							
							if(enlaceSecundario.startsWith("https://idp.uma.es"))
								loggingIDUMA(enlaceSecundario);
								//Al intentar loggearnos con datos falsos nos manda
								//a esperar X tiempo antes de dejarnos volver a intentarlo
						}
					}
				}
				
				//System.out.println("Enlace encontrado");
			}
		}
		driver.close();
	}
	
	
	
//	@Test
//	public void testRecursivo() {
//		driver.get("http://jabega.uma.es/");
//		assertEquals("Jábega, Catálogo de la Biblioteca de la UMA", driver.getTitle());
//		
//		//dominioOriginal = driver.getCurrentUrl();
//		String direccionSecundaria = "";
//		String direccionFinal = "";
//		
//		//Los enlaces tendrán el tag HTML <a>, los recogo todos con findElements
//		List<WebElement> listaPrimaria = driver.findElements(By.tagName("a"));
//		List<WebElement> listaSecundaria = null;
//		//Set<String> websVisitadas = new TreeSet<String>();
//			
//			//Recorro todos los enlaces de la lista Original
//			for(WebElement enlace: listaPrimaria) {
//				//Extraigo la dirección de cada enlace de la lista original
//				direccionSecundaria = enlace.getAttribute("href");
//				//System.out.println("---- " + direccionSecundaria);
//				
//				//Compruebo que la direccion no es nula y que pertenece a uma.es
//				if(direccionSecundaria.isEmpty() || direccionSecundaria == null) {
//						System.out.println(direccionSecundaria + " || Link nulo o vacío."); //antes enlace.getText()
//				} else if(direccionSecundaria.contains("uma.es")) {
//					System.out.println("---- " + direccionSecundaria);
//					
//					//Entro a la dirección secundaria y crea listas con sus nuevos enlaces
//					driver.get(direccionSecundaria);
//					listaSecundaria = driver.findElements(By.tagName("a"));
//					
//					//Recorro la nueva lista del 3º nivel
//					if (listaSecundaria != null) {
//						
//						for(WebElement enlaceSecundario : listaSecundaria) {
//							direccionFinal = enlaceSecundario.getAttribute("href");
//							
//							if(direccionFinal.isEmpty() || direccionFinal == null) {
//								System.out.println(direccionFinal + " || Link nulo o vacío."); //antes enlace.getText()
//							} else if(direccionFinal.contains("uma.es")) {
//								System.out.println("----------" + direccionFinal);
//					}
//					}
//					}
//					} 
//				}
//	}

}
