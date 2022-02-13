package Patern.interfaces;

public class Console {

    public void log(String ... args) {
        for(String arg : args) {
            System.out.print(arg);
        }
        System.out.println("");
    }

}