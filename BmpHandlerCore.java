import java.io.FileOutputStream;
import java.io.IOException;

public class BmpHandlerCore {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java BMPImageHandler -core <archivo.bmp>");
            return;
        }

        String flag = args[0];
        String archivo = args[1];

        if (flag.equals("-core")) {
            System.out.println("Ejecutando la Parte I del proyecto...");

            try {
                // Leer la imagen original
                Tablero tablero = leerBMP(archivo);

                // Generar las imágenes roja, verde, azul y sepia
                generarImagenes(tablero, archivo);
            } catch (IOException e) {
                System.out.println("Error al procesar la imagen: " + e.getMessage());
            }
        } else {
            System.out.println("Bandera no reconocida. Solo se soporta -core para esta versión.");
        }
    }

    public static Tablero leerBMP(String archivo) throws IOException {
        // Aquí iría el código para leer la imagen BMP y generar el Tablero.
        System.out.println("Leyendo la imagen BMP: " + archivo);
        // Solo como ejemplo, vamos a devolver un Tablero vacío (esto debe implementarse).
        int ancho = 100;  // Esto se debería leer del header del archivo BMP
        int alto = 100;   // Esto se debería leer del header del archivo BMP
        Tablero tablero = new Tablero(ancho, alto);
        return tablero;
    }

    public static void generarImagenes(Tablero tablero, String archivo) throws IOException {
        // Generar imagen roja
        Tablero redTablero = clonarTablero(tablero);
        aplicarFiltroRojo(redTablero);
        escribirBMP(redTablero, archivo.replace(".bmp", "-red.bmp"));

        // Generar imagen verde
        Tablero greenTablero = clonarTablero(tablero);
        aplicarFiltroVerde(greenTablero);
        escribirBMP(greenTablero, archivo.replace(".bmp", "-green.bmp"));

        // Generar imagen azul
        Tablero blueTablero = clonarTablero(tablero);
        aplicarFiltroAzul(blueTablero);
        escribirBMP(blueTablero, archivo.replace(".bmp", "-blue.bmp"));

        // Generar imagen sepia
        Tablero sepiaTablero = clonarTablero(tablero);
        sepiaTablero.convSepiaMtz();
        escribirBMP(sepiaTablero, archivo.replace(".bmp", "-sepia.bmp"));

        System.out.println("Imágenes generadas con éxito.");
    }

    public static Tablero clonarTablero(Tablero original) {
        int ancho = original.getAncho();
        int alto = original.getAlto();
        Tablero copia = new Tablero(ancho, alto);

        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                copia.setColor(i, j, original.getColor(i, j));
            }
        }
        return copia;
    }

    public static void aplicarFiltroRojo(Tablero tablero) {
        for (int i = 0; i < tablero.getAlto(); i++) {
            for (int j = 0; j < tablero.getAncho(); j++) {
                Color color = tablero.getColor(i, j);
                tablero.setColor(i, j, new Color(color.getRed(), 0, 0));
            }
        }
    }

    public static void aplicarFiltroVerde(Tablero tablero) {
        for (int i = 0; i < tablero.getAlto(); i++) {
            for (int j = 0; j < tablero.getAncho(); j++) {
                Color color = tablero.getColor(i, j);
                tablero.setColor(i, j, new Color(0, color.getGreen(), 0));
            }
        }
    }

    public static void aplicarFiltroAzul(Tablero tablero) {
        for (int i = 0; i < tablero.getAlto(); i++) {
            for (int j = 0; j < tablero.getAncho(); j++) {
                Color color = tablero.getColor(i, j);
                tablero.setColor(i, j, new Color(0, 0, color.getBlue()));
            }
        }
    }

    public static void escribirBMP(Tablero tablero, String archivoSalida) throws IOException {
        int ancho = tablero.getAncho();
        int alto = tablero.getAlto();
        int padding = (4 - (ancho * 3) % 4) % 4; // BMP debe alinear las filas a múltiplos de 4 bytes

        // Tamaños calculados para el archivo BMP
        int fileSize = 54 + (ancho * 3 + padding) * alto;  // 54 bytes es el tamaño del header

        try (FileOutputStream archivo2 = new FileOutputStream(archivoSalida)) {
            // Escribir el BMP Header
            archivo2.write(new byte[]{0x42, 0x4D}); // BM en ASCII
            archivo2.write(intToBytes(fileSize));   // Tamaño del archivo en bytes
            archivo2.write(new byte[]{0, 0, 0, 0}); // Reservado
            archivo2.write(new byte[]{54, 0, 0, 0}); // Offset de los datos de los píxeles (54 bytes)

            // Escribir el DIB Header (40 bytes)
            archivo2.write(new byte[]{40, 0, 0, 0}); // Tamaño del DIB header
            archivo2.write(intToBytes(ancho));       // Ancho de la imagen
            archivo2.write(intToBytes(alto));        // Alto de la imagen
            archivo2.write(new byte[]{1, 0});        // Número de planos
            archivo2.write(new byte[]{24, 0});       // Bits por píxel (24 bits para RGB)
            archivo2.write(new byte[]{0, 0, 0, 0});  // Sin compresión
            archivo2.write(intToBytes((ancho * 3 + padding) * alto)); // Tamaño de la imagen
            archivo2.write(new byte[]{0x13, 0x0B, 0, 0});  // Resolución horizontal (2835 px/m)
            archivo2.write(new byte[]{0x13, 0x0B, 0, 0});  // Resolución vertical (2835 px/m)
            archivo2.write(new byte[]{0, 0, 0, 0});        // Colores en la paleta (0 = todos)
            archivo2.write(new byte[]{0, 0, 0, 0});        // Colores importantes (0 = todos)

            // Escribir los datos de los píxeles (fila por fila, de abajo hacia arriba)
            for (int i = alto - 1; i >= 0; i--) { // BMP almacena las filas de abajo a arriba
                for (int j = 0; j < ancho; j++) {
                    Color color = tablero.getColor(i, j);
                    archivo2.write(color.getBlue());   // Escribir componente Azul
                    archivo2.write(color.getGreen());  // Escribir componente Verde
                    archivo2.write(color.getRed());    // Escribir componente Rojo
                }
                // Añadir padding al final de cada fila, si es necesario
                archivo2.write(new byte[padding]);
            }

            System.out.println("Imagen BMP guardada exitosamente en: " + archivoSalida);
        }
    }

    // Método auxiliar para convertir un entero en un array de 4 bytes (little endian)
    public static byte[] intToBytes(int valor) {
        return new byte[]{
                (byte) (valor & 0xFF),
                (byte) ((valor >> 8) & 0xFF),
                (byte) ((valor >> 16) & 0xFF),
                (byte) ((valor >> 24) & 0xFF)
        };
    }
}
