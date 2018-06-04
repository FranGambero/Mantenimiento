package pSelenium2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.swing.JOptionPane;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class testRec {
	/* Nivel de profundidad */
	private static final int DEPTH_LEVEL = 2;
	
	/* Página a inspeccionar */
	private static final String WEBSITE = "https://www.jabega.uma.es/";
	
	/* Seguir el camino que recorre el WebDriver en la consola (Se mostrará en forma de árbol) */
	private static final boolean FOLLOW_PATH_ON_CONSOLE = true;
	
	/* Cambiar a gusto */
	private static final String GECKODRIVER_LOCATION = "D:\\me\\student-life\\university\\drive\\ProgramData\\geckodriver.exe";
	private static final String CHROMEDRIVER_LOCATION = "C:\\\\Users\\\\Takox\\\\Documents\\\\Mantenimiento\\\\Practica5\\\\chromedriver_win32\\\\chromedriver.exe";
	private static WebDriver driver;
	
	/* Antes de empezar cualquier test, iniciar el driver */
	@BeforeClass public static void beforeAll(){
		System.setProperty("webdriver.gecko.driver", GECKODRIVER_LOCATION);
		System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_LOCATION);
		
		/* Diálogo para elegir Driver */
		String[] options = {"Google Chrome", "Mozilla Firefox"};
		int n = JOptionPane.showOptionDialog(
			null
			,"¿Qué navegador quieres usar para la prueba?"
			,"Elección del Driver de Selenium"
			,JOptionPane.YES_NO_OPTION
			,JOptionPane.QUESTION_MESSAGE
			,null
			,options
			,options[0]
		);
		
		if(options[n].equals("Mozilla Firefox"))	driver = new FirefoxDriver();
		else if(options[n].equals("Google Chrome"))	driver = new ChromeDriver();
	}
	
	/* Recoge todos los enlaces en 'stack', dado un driver 'd' */
	private void getAnchors(WebDriver d, Stack<String> stack){
		List<WebElement> $anchors = d.findElements(By.tagName("a"));
		int i = 0;
		String href;
		while(i < $anchors.size() && null != $anchors.get(i)){
			href = $anchors.get(i++).getAttribute("href");
			if(
				null != href
				&&
				!href.isEmpty()
				&&
				isValidURL(href)
				&&
				!stack.contains(href)
			)	stack.push(href);
		}
	}
	
	/* Comprueba si 'href' es una URL válida (que no sea un link a # o una llamada javascript) */
	private boolean isValidURL(String href){
		return
			href.charAt(0) == '.'
			|| href.charAt(0) == '/'
			|| href.startsWith("https://")
			|| href.startsWith("http://")
		;
	}
	
	/* Comprueba si la URL 'href' pertenece al dominio del sitio web especificado */
	private boolean belongsToDomain(String href, String website){
		return
		href.startsWith(website) /* URLs que empiezan por el sitio web indicado */
		||
		(
			href.charAt(0) == '/'
			&&	/* URL relativa a la raíz tipo "/hola/" */
			href.charAt(1) != '/'
		);
	}
	
	/* Atajo */
	private boolean belongsToDomain(String href){
		return belongsToDomain(href, WEBSITE);
	}
	
	/* Comprueba si la página 'href' es la DUMA */
	private boolean isiDUMA(String href){
		return href.startsWith("https://idp.uma.es");
	}
	
	/* Comprueba si la 'page' contiene la palabra "error" */
	private boolean hasErrors(String page){
		return page.toLowerCase().contains("error");
	}
	
	/* Intentar hacer log-in en la DUMA */
//	private void tryToLoginInDUMA(WebDriver d, String email, String password) throws InterruptedException{
//		WebElement $form = d.findElement(By.cssSelector("#formulario1"));
//		WebElement $name = $form.findElement(By.cssSelector("#edit-name"));
//		TestUtils.simulateWriting($name, email);
//		
//		WebElement $pass = $form.findElement(By.cssSelector("#edit-pass"));
//		TestUtils.simulateWriting($pass, password);
//		
//		$form.findElement(By.cssSelector("#submit_ok")).click();
//		
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(d)
//	       .withTimeout(30, TimeUnit.SECONDS)
//	       .pollingEvery(1, TimeUnit.SECONDS)
//	       .ignoring(NoSuchElementException.class);
//		
//		$form = wait.until(new Function<WebDriver, WebElement>() {
//	    	public WebElement apply(WebDriver driver) {
//	    		return driver.findElement(By.cssSelector("#formulario1"));
//	    	}
//	    });
//		
//		if(FOLLOW_PATH_ON_CONSOLE)
//			if(hasErrors(d.getPageSource())){
//				System.out.print("<Error al identificarse con credenciales falsas>");
//			}else{
//				System.out.print("<Identificación correcta con credenciales falsas>");
//			}
//	}
	
	/*
	 * explora todos los enlaces de una 'stack',
	 * usando el driver 'd',
	 * anotando todas las páginas visitadas en 'pageIndex',
	 * en un nivel de profundidad 'level'
	 * 
	 */
	private void crawlInside(
		final WebDriver d
		,Stack<String> stack
		,List<String> pageIndex
		,int level
		,String stackCounterString
	) throws InterruptedException{
		
		/* Si no nos hemos excedido a la hora de bajar niveles en el árbol */
		if(level <= DEPTH_LEVEL){
			String href;
			int stackCounter = 1;
			
			/* Mientras queden páginas a visitar en nuestro nivel */
			while(!stack.isEmpty()){
				
				/* Sacamos una página de la pila */
				href = stack.pop();
				
				/* Si no está en nuestro índice de páginas visitadas */
				if(!pageIndex.contains(href)){
					
					/* La añadimos a nuestro índice de páginas visitadas */
					pageIndex.add(href);
					
					/* La visitamos */
					d.get(href);
					
					/* Actualizamos la URL */
					href = d.getCurrentUrl();
					
					/* Comprobamos si es válida (que no se haya ido del dominio y que no tenga errores) */
					boolean isValid =
						(
							isiDUMA(href)
							||
							belongsToDomain(href)
						)
						&& !hasErrors(d.getPageSource())
					;
					
					
					/* Imprimimos en consola cuando sea necesario */
					if(FOLLOW_PATH_ON_CONSOLE){
						System.out.println();
						for(int i = 1; i < level; i++)	System.out.print("│   ");
						
						if(stack.isEmpty())	System.out.print("└");
						else				System.out.print("├");
							
						System.out.print("───");
						
						if(isValid) System.out.print(stackCounterString+stackCounter + "> " + href);
						else 		System.out.print("[ERROR] "+stackCounterString+stackCounter + "> " + href);		
					}
					
					if(isValid){
						/* Si es válido, creamos una nueva pila con todas los enlaces de esta página */
						Stack<String> localStack = new Stack<String>();
						getAnchors(d, localStack);
						
						/* aplicamos recursividad */
						crawlInside(d, localStack, pageIndex, level+1, stackCounterString+stackCounter+".");
					}else if(FOLLOW_PATH_ON_CONSOLE && isiDUMA(href)){ /* Si estamos en la DUMA */
						/* Intentamos un login con credenciales falsas */
						//tryToLoginInDUMA(d, "emailfalso@uma.es", "contraseñaincorrecta");
					}
					
					++stackCounter;
				}
			}
		}
	}

	@Test
	public void crawlTest() throws InterruptedException{
		/* inicializamos la stack primaria y el índice de páginas visitadas */
		Stack<String> hrefStack = new Stack<String>();
		List<String> pagesVisited = new ArrayList<String>();
		
		/* Cargamos la página principal */
		driver.get(WEBSITE);
		System.out.print(WEBSITE);
		
		/* Obtenemos todos los enlaces de la página principal */
		getAnchors(driver, hrefStack);
		
		/* Ahora recorremos las páginas hasta llegar al límite de recursividad */
		crawlInside(driver, hrefStack, pagesVisited, 1, "");
		
		/* Cerramos el navegador y el driver */
		driver.quit();
	}
	
}
