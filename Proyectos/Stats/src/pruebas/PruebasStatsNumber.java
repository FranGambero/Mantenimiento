package pruebas;
import stats.StatsNumber;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PruebasStatsNumber {
	
	private StatsNumber stats;

	@Before
	public void setUp() throws Exception {
		Number[] lst = {1,2,3};
		stats = new StatsNumber(lst);
	}
	
	/* Pruebas de constructor */ 
	@Test(expected = RuntimeException.class)
	public void arrayNulo() {
		new StatsNumber(null);
	}
	
	@Test(expected = RuntimeException.class)
	public void arrayUnValorNulo() {
		Number[] aux = {1,2,null,29};
		new StatsNumber(aux);
	}
	
	@Test (expected = RuntimeException.class)
	public void modaArrayUno() {
		Number[] lst = {1};
		stats = new StatsNumber(lst);
		stats.moda();
		/*
		 * Incidencia, se espera una exception pero devuelve un fallo
		 */
	}
	
	@Test
	public void modaUnValor() {
		Number[] lst = {1,1,2,3};
		StatsNumber stats2 = new StatsNumber(lst);
		Number[] expected = {1.0};
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
		Number[] lst = {1,1,2,2,3,7};
		StatsNumber stats3 = new StatsNumber(lst);
		Number[] expected = {1.0,2.0}; //antes 2,1
		assertEquals(expected, stats3.moda());
		/*
		 * Incidencia: No devuelve el array con los numeros que son la moda, 1 y 2 en este caso
		 */
		/*
		 * assertArrayEquals y array ordenados para que de bien
		 */
	}
	
	@Test (expected = RuntimeException.class)
	public void mediaArrayVacio() {
		Number [] aux = {};
		stats = new StatsNumber(aux);
		stats.media();
	}
	
	@Test
	public void mediaArrayUnElemento() {
		Number [] aux = {1};
		stats = new StatsNumber(aux);
		//assertEquals(1, stats.media());
		assertEquals(1,(int)stats.media());
	}
	
	@Test public void mediaArrayNElementos() {
		Number [] aux = {1,2,3,7};
		stats = new StatsNumber(aux);
		assertEquals(3.25, stats.media());
		// No es precisa del todo, espera 3.25 pero devuelve 3.0
	}

	@Test
	public void medianaUnElemento() {
		Number[] aux = {7};
		stats = new StatsNumber(aux);
		double expected = 7;
		assertEquals(expected, stats.mediana());
	}
	
	@Test
	public void medianaImparElementos() {
		Number[] aux = {11,11,16,17,25};
		stats = new StatsNumber(aux);
		double expected = 16;
		assertEquals(expected, stats.mediana());
	}
	
	@Test
	public void medianaParElementos() {
		Number[] aux = {1,4,8,8,10,16,16,19};
		stats = new StatsNumber(aux);
		double expected = 9;
		assertEquals(expected, stats.mediana());
	}

	@Test
	public void varianzaUnElemento() {
		Number[] aux = {25};
		stats = new StatsNumber(aux);
		double expected = 0;
		assertEquals(expected, stats.varianza());
	}
	
	@Test
	public void varianzaNElementos() {
		Number[] aux = {12, 10, 9, 9, 10};
		stats = new StatsNumber(aux);
		Number expected = 1.2;
		assertEquals(expected, stats.varianza());
		//No es preciso, deberia devolver 1.2 pero devuelve 1.20000000000028
	}

}
