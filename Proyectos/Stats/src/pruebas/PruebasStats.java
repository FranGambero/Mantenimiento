package pruebas;
import stats.Stats;
//import Stats.StatsException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PruebasStats {
	
	private Stats stats;

	@Before
	public void setUp() throws Exception {
		int[] lst = {1,2,3};
		stats = new Stats(lst);
	}
	
	/* Pruebas de constructor */ 
	@Test(expected = RuntimeException.class)
	public void arrayNulo() {
		new Stats(null);
	}
	
	@Test (expected = RuntimeException.class)
	public void modaArrayUno() {
		int[] lst = {1};
		stats = new Stats(lst);
		stats.moda();
		/*
		 * Incidencia, se espera una exception pero devuelve un fallo
		 */
	}
	
	@Test
	public void modaUnValor() {
		int[] lst = {1,1,2,3};
		Stats stats2 = new Stats(lst);
		int[] expected = {1};
		assertArrayEquals(expected, stats2.moda());
		/*
		 * Incidencia, no devuelve el array con la moda, que deber�a ser 1 en este caso
		 */
		/*
		 * Con el assertArrayEquals SÍ funciona, hay problemas en JUNIT4
		 * en la comparación de Arrays
		 */
	}
	
	@Test
	public void modaVariosValores() {
		int[] lst = {1,1,2,2,3,7};
		Stats stats3 = new Stats(lst);
		int[] expected = {1,2}; //antes 2,1
		assertArrayEquals(expected, stats3.moda());
		/*
		 * Incidencia: No devuelve el array con los numeros que son la moda, 1 y 2 en este caso
		 */
		/*
		 * assertArrayEquals y array ordenados para que de bien
		 */
	}
	
	@Test (expected = RuntimeException.class)
	public void mediaArrayVacio() {
		int [] aux = {};
		stats = new Stats(aux);
		stats.media();
	}
	
	@Test
	public void mediaArrayUnElemento() {
		int [] aux = {1};
		stats = new Stats(aux);
		//assertEquals(1, stats.media());
		assertEquals(1,(int)stats.media());
	}
	
	@Test public void mediaArrayNElementos() {
		int [] aux = {1,2,3,7};
		stats = new Stats(aux);
		assertEquals(3.25, stats.media(), 0.0001);
		// No es precisa del todo, espera 3.25 pero devuelve 3.0
	}

	@Test
	public void medianaUnElemento() {
		int[] aux = {7};
		stats = new Stats(aux);
		double expected = 7;
		assertEquals(expected, stats.mediana(), 0.0001);
	}
	
	@Test
	public void medianaImparElementos() {
		int[] aux = {11,11,16,17,25};
		stats = new Stats(aux);
		double expected = 16;
		assertEquals(expected, stats.mediana(), 0.0001);
	}
	
	@Test
	public void medianaParElementos() {
		int[] aux = {1,4,8,8,10,16,16,19};
		stats = new Stats(aux);
		double expected = 9;
		assertEquals(expected, stats.mediana(), 0.0001);
	}

	@Test
	public void varianzaUnElemento() {
		int[] aux = {25};
		stats = new Stats(aux);
		double expected = 0;
		assertEquals(expected, stats.varianza(), 0.0001);
	}
	
	@Test
	public void varianzaNElementos() {
		int[] aux = {12, 10, 9, 9, 10};
		stats = new Stats(aux);
		double expected = 1.2;
		assertEquals(expected, stats.varianza(), 0.0001);
	}

}
