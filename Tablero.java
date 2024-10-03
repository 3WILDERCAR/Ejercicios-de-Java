/* 
 * Esta es la clase que guardara un tablero con los clolores a guardar
 * Tdodo lo que tenga que ver con manipular el tablero de los colores ira en este clase
 * cosas como cambiar los lugares de los colores en en tablero y otras cosas que requieran manipular el tablero
 */
public class Tablero {
    private Color[][] tablero;
    private int ancho, alto;

    public Tablero(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        tablero = new Color[alto][ancho];  // El alto es el número de filas, el ancho es el número de columnas

        // Inicializar el tablero con colores blancos por defecto (255,255,255)
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                tablero[i][j] = new Color(255, 255, 255);
            }
        }
    }

    public int getAncho() {
        return this.ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    public void setColor(int i, int j, Color color) {
        if (i >= 0 && i < alto && j >= 0 && j < ancho) {
            tablero[i][j] = color;
        } else {
            System.out.println("Índice fuera de rango.");
        }
    }

    public Color getColor(int i, int j) {
        if (i >= 0 && i < alto && j >= 0 && j < ancho) {
            return tablero[i][j];
        } else {
            return null;
        }
    }

    public void invColorMtz(){
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                tablero[i][j].invColor();
            }
        }
    }

    public void convSepiaMtz(){
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                tablero[i][j].convSepia();
            }
        }
    }

    public String toString() {
        StringBuilder cadena = new StringBuilder();
        for (int i = 0; i < alto; i++) {
            for (int j = 0; j < ancho; j++) {
                Color color = tablero[i][j];
                cadena.append("(").append(color.getRed()).append(", ").append(color.getGreen()).append(", ").append(color.getBlue()).append(")");
                if (j < ancho - 1) {
                    cadena.append(" ");
                }
            }
            cadena.append("\n");
        }
        return cadena.toString();
    }
}