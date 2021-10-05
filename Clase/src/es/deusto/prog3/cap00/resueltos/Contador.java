package es.deusto.prog3.cap00.resueltos;

/** Clase de contador para ejercicio de concurrencia con hilos 0.10 - resuelto
 * @author andoni.eguiluz @ ingenieria.deusto.es
 * 
 * Permite instanciar contadores que incrementan o decrementan su valor
 */
public class Contador {
	// Parte static - main de prueba (ejercicio de hilo)
	
	private static long MS_PAUSA = 0; // Milisegundos de pausa en el incremento / decremento
	
	public static void main(String[] args) {
		// Creamos un contador
		Contador cont = new Contador();
		System.out.println( "Valor inicial de contador: " + cont );
		// Incrementamos 1.000 veces (en un hilo)
		Thread hilo1 = new Thread() {
			public void run() {
				for (int i=0; i<1000; i++) {
					cont.inc(1);
					if (MS_PAUSA>0) try { Thread.sleep(MS_PAUSA); } catch (InterruptedException e) { } // Pausa entre iteraciones si procede
				}
			}
		};
		// Decrementamos 1.000 veces (en otro hilo)
		Thread hilo2 = new Thread() {
			public void run() {
				for (int i=0; i<1000; i++) {
					cont.dec(1);
					if (MS_PAUSA>0) try { Thread.sleep(MS_PAUSA); } catch (InterruptedException e) { } // Pausa entre iteraciones si procede
				}
			}
		};
		// Lanzamos los dos hilos
		hilo1.start();
		hilo2.start();
		// Esperamos a que acaben
		try {
			hilo1.join();
			hilo2.join();
		} catch (InterruptedException e) {
		}
		System.out.println( "Valor final de contador: " + cont );
	}
	
	// Clase para instancias - parte no static
	
	/** Crea un contador con valor inicializado a 0
	 */
	public Contador() {
		valor = 0;
	}
	
	private int valor;
	/** Incrementa el valor del contador
	 * @param incremento	Cantidad a incrementar (debe ser positiva)
	 */
	public synchronized void inc( int incremento ) {
		int temporal = valor;
		temporal = temporal + incremento;
		valor = temporal;
	}
	/** Decrementa el valor del contador
	 * @param decremento	Cantidad a decrementar (debe ser positiva, se restará del valor)
	 */
	public synchronized void dec( int decremento ) {
		int temporal = valor;
		temporal = temporal - decremento;
		valor = temporal;
	}
	
	@Override
	public String toString() {
		return "" + valor;
	}
}