import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Menu");
        System.out.println("1. SPL");
        int pilihan = input.nextInt();
        if(pilihan == 1){
            Matrix.SPL();
        }
    }
}
