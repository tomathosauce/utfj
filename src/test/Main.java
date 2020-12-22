import utfj.UniConsole;

public class Main {
    static UniConsole c = new UniConsole();
    public static void main(String[] args) {
        P();
        c.print("Text1", UniConsole.GREEN);
        c.println("Text2", UniConsole.BLUE);
        P("Text3");
        c.print("Text4", UniConsole.BLUE, UniConsole.YELLOW_BRIGHTER);
        P("Text5");
        P("Text6");
    }

    static void P(String texto){
        System.out.println(texto);
    }
    static void PS(String texto){
        System.out.print(texto);
    }
    static void P(int texto){
        System.out.println(texto);
    }
    static void PS(int texto){
        System.out.print(texto);
    }
    static void P(){
        System.out.println("");
    }
}
