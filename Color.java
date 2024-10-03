/* 
 * Esta clase es la encargada de guardar colores RGB y poder manupularlos en la matriz.
 * Todos los metodos que esten y vallan a estar escritos en esta clase tienen que ver unicamente 
 * con los colores. Ya sea invertir colores o cambiarles la tonalidad
 */
public class Color {

    private int R,G,B;

    public Color (int R, int G, int B){
        this.R=Limitar(R);
        this.G=Limitar(G);
        this.B=Limitar(B);
    }

    public int Limitar(int Tono){
        if (Tono<0) {
            return 0;
        }
        else if (Tono>255) {
            return 255;
        }
        else{
            return Tono;
        }
    }

    public int getRed(){
        return this.R;
    }

    public int getGreen(){
        return this.G;
    }

    public int getBlue(){
        return this.B;
    }

    public void setColor(int R, int G, int B){
        this.R=Limitar(R);
        this.G=Limitar(G);
        this.B=Limitar(B);
    }

    public void invColor(){
        setColor(255-this.R, 255-this.G, 255-this.B);
    }

    public void convSepia() {
        int NR = (int) Math.round((R * 0.393) + (G * 0.769) + (B * 0.189));
        int NG = (int) Math.round((R * 0.349) + (G * 0.686) + (B * 0.168));
        int NB = (int) Math.round((R * 0.272) + (G * 0.534) + (B * 0.131));
    
        NR = Limitar(NR);
        NG = Limitar(NG);
        NB = Limitar(NB);
    
        setColor(NR, NG, NB);
    }
    

    public String toString(){
        return "["+this.getRed()+"],["+this.getGreen()+"],["+this.getBlue()+"]";
    }
}