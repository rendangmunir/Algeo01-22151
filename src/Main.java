import lib.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    //    Matrix M=Matrix.inputMatrix();
        // System.out.println(Determinan.detReduksi(M));
        // Determinan.detKofaktor(M);
        Scanner input = new Scanner(System.in);
        System.out.println("Menu");
        System.out.println("1. SPL");
        System.out.println("2. Determinan");
        System.out.println("3. Invers");
        System.out.println("4. Regresi Linear Berganda");
        System.out.println("5. Bicubic Spline Interpolation");
        System.out.println("6. Interpolasi Polinom");
        int pilihan = input.nextInt();
        if(pilihan == 1){
            SPL.ansSPL();
        }else if (pilihan==2){
            Determinan.ansDet();
        }else if (pilihan==3){
            Invers.inversMatrix();
        }else if (pilihan==4){
            Regresi.regresiLinearBerganda();
        }else if (pilihan==5){
            Bicubic.solveBicubic();
        }else if (pilihan==6){
            Interpolasi.solveInterpolasi();
        }
    }
}
