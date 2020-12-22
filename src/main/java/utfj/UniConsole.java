package utfj;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

/** For unicode output on windows platform
 * @author Sandy_Yin
 * https://stackoverflow.com/a/8921509
 * 
 * This code was altered to include the use of colors when printing text
 */
public class UniConsole {
    static int STD_OUTPUT_HANDLE = -11;

    static Kernel32 INSTANCE = null;
    static Pointer handle;

    public static short BLACK = 0;
    public static short BLUE = 1;
    public static short GREEN = 2;
    public static short CYAN = 3;
    public static short RED = 4;
    public static short PURPLE = 5;
    public static short YELLOW = 6;
    public static short WHITE = 7;
    public static short BLACK_BRIGHTER = 8;
    public static short BLUE_BRIGHTER = 9;
    public static short GREEN_BRIGHTER = 10;
    public static short CYAN_BRIGHTER = 11;
    public static short RED_BRIGHTER = 12;
    public static short PURPLE_BRIGHTER = 13;
    public static short YELLOW_BRIGHTER = 14;
    public static short WHITE_BRIGHTER = 15;

    public static void setColor(short foreground, short background){
        short code = (short) (background*16+foreground);
        INSTANCE.SetConsoleTextAttribute(handle, code);
    }

    public static void setColor(short foreground){
        INSTANCE.SetConsoleTextAttribute(handle, foreground);
    }

    public static void setDefaultColor(){
        INSTANCE.SetConsoleTextAttribute(handle, WHITE);
    }

    public interface Kernel32 extends StdCallLibrary {
        public Pointer GetStdHandle(int nStdHandle);

        public Pointer SetConsoleTextAttribute(Pointer hConsoleOutput, short wAttributes);

        public boolean WriteConsoleW(Pointer hConsoleOutput, char[] lpBuffer,
                int nNumberOfCharsToWrite,
                IntByReference lpNumberOfCharsWritten, Pointer lpReserved);
    }

    public UniConsole() {

        String os = System.getProperty("os.name").toLowerCase();
        if (os.startsWith("win")) {
            //LoadLibrary was deprecated in the original code so load was used instead
            INSTANCE = (Kernel32) Native.load("kernel32", Kernel32.class);
            
            //It seems that you have to print twice in order for colors to appear on the console
            //So I found this workaround
            if(INSTANCE != null){
                print("", WHITE_BRIGHTER);
            }
        }
    }

    public void printTest(String message, short foreground){
        boolean successful = false;
        setColor(foreground);
        if (INSTANCE != null) {
            handle = INSTANCE.GetStdHandle(STD_OUTPUT_HANDLE);
        char[] buffer = message.toCharArray();
        IntByReference lpNumberOfCharsWritten = new IntByReference();
            
        successful = INSTANCE.WriteConsoleW(handle, buffer, buffer.length,
                lpNumberOfCharsWritten, null);
            if(successful)
                System.out.println();
        }
        if (!successful) {
            System.out.println(message);
        }
        setDefaultColor();
    }

    private boolean write(String message){
        handle = INSTANCE.GetStdHandle(STD_OUTPUT_HANDLE);
        char[] buffer = message.toCharArray();
        IntByReference lpNumberOfCharsWritten = new IntByReference();
            
        return INSTANCE.WriteConsoleW(handle, buffer, buffer.length,
                lpNumberOfCharsWritten, null);
    }

    public void println(String message) {
        boolean successful = false;
        if (INSTANCE != null) {
            successful = write(message);
            if(successful)
                System.out.println();
        }
        if (!successful) {
            System.out.println(message);
        }
    }

    public void print(String message) {
        boolean successful = false;
        if (INSTANCE != null) {
            successful = write(message);
        }
        if (!successful) {
            System.out.print(message);
        }
    }

    public void println(String message, short foreground) {
        boolean successful = false;
        setColor(foreground);
        if (INSTANCE != null) {
            successful = write(message);
            if(successful)
                System.out.println();
        }
        if (!successful) {
            System.out.println(message);
        }
        setDefaultColor();
    }

    public void print(String message, short foreground) {
        boolean successful = false;
        setColor(foreground);
        if (INSTANCE != null) {
            successful = write(message);
        }
        if (!successful) {
            System.out.print(message);
        }
        setDefaultColor();
    }

    public void println(String message, short foreground, short background) {
        boolean successful = false;
        setColor(foreground, background);
        if (INSTANCE != null) {
            successful = write(message);
            if(successful)
                System.out.println();
        }
        if (!successful) {
            System.out.println(message);
        }
        setDefaultColor();
    }

    public void print(String message, short foreground, short background) {
        setColor(foreground, background);
        boolean successful = false;
        if (INSTANCE != null) {
            successful = write(message);
        }
        if (!successful) {
            System.out.print(message);
        }
        setDefaultColor();
    }
}
