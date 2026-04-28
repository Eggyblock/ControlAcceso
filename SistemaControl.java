import java.io.*;
import java.util.Scanner;

public class SistemaControl {
	static int puntos = 0;
	static int integridad = 100;
	static int nivelActual = 1;

	public static void leerArchivo(String nombreArchivo) {
		try {
			FileReader fr = new FileReader(nombreArchivo);
			BufferedReader br = new BufferedReader(fr);
			String linea;

			System.out.println("--- INICIANDO ESCANEO DE SEGURIDAD ---");
			while ((linea = br.readLine()) != null) {
				procesarDato(linea);
			}

			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("Error: No se pudo acceder a la base de datos.");
		}
	}

	public static void procesarDato(String codigo) {
		boolean corrupto = false;
		for (int i = 0; i < codigo.length(); i++) {
			char c = codigo.charAt(i);
			if (c < '0' || c > '9') {
				corrupto = true;
				break;
			}
		}

		if (corrupto) {
			System.out.println("[ALERTA] Código '" + codigo + "' corrupto detectado.");
			integridad -= 10;
			puntos -= 5;
		} else {
			System.out.println("[OK] Acceso autorizado para: " + codigo);
			puntos += 10;
		}
	}

	public static void main(String[] args) {
		// Los saludos de la suerte
		System.out.println("Te quiero Migue");


		Scanner sc = new Scanner(System.in);
		int opcion = 0;

		System.out.println("\nSISTEMA DE CONTROL EAFIT - MODO RECUPERACIÓN");

		do {
			System.out.println("\n--- ESTADO: PUNTOS: " + puntos + " | INTEGRIDAD: " + integridad + "% ---");
			System.out.println("1. Cargar base de datos (.csv)");
			System.out.println("2. Ver estadísticas finales");
			System.out.println("3. Salir");
			System.out.print("Seleccione una opción: ");

			// Validar que el usuario ingrese un número
			if (sc.hasNextInt()) {
				opcion = sc.nextInt();
			} else {
				System.out.println("Por favor, ingresa un número válido.");
				sc.next(); // Limpiar el error
				continue;
			}

			if (opcion == 1) {
				System.out.print("Nombre del archivo (ej: datos.csv): ");
				String nombre = sc.next();

				File f = new File(nombre);
				if (f.exists()) {
					leerArchivo(nombre);
				} else {
					System.out.println("[ERROR] El archivo '" + nombre + "' no existe.");
				}
			} else if (opcion == 2) {
				System.out.println("\n======= REPORTE DE SEGURIDAD =======");
				System.out.println("Puntaje acumulado: " + puntos);
				System.out.println("Integridad del sistema: " + integridad + "%");
				if (integridad > 80) System.out.println("Estado: SEGURO");
				else if (integridad > 40) System.out.println("Estado: RIESGO MODERADO");
				else System.out.println("Estado: CRÍTICO");
				System.out.println("====================================");
			}

			// Si la integridad llega a 0, forzamos la salida
			if (integridad <= 0) {
				System.out.println("\n!!! SISTEMA COLAPSADO: LA REBELIÓN GANÓ !!!");
				break;
			}

		} while (opcion != 3); // <-- Aquí faltaba cerrar el do-while

		System.out.println("Finalizando Centinela...");
		sc.close(); // Cerrar el scanner
	} // <-- Aquí cierra el main
} // <-- Aquí cierra la clase