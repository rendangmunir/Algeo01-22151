import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.lang.Math;

public class Matrix {

    private int baris;

    private int kolom;

    private double[][] Matrix;

    public Matrix(int baris,int kolom){
        this.Matrix = new double[baris][kolom];
        this.baris = baris;
        this.kolom = kolom;
    }

    private static Matrix keyboard(){
        Scanner input = new Scanner(System.in);
        Matrix M = new Matrix(0,0);
        System.out.print("Masukkan jumlah baris: ");
        int br = input.nextInt();
        System.out.print("Masukkan jumlah kolom: ");
        int kl = input.nextInt();
        M = new Matrix(br,kl) ;
        for(int i=0;i<br;i++){
            for(int j=0;j<kl;j++){
                M.Matrix[i][j] = input.nextDouble();
            }
        }
        return M;
    }

    private static Matrix inputFile(){
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        Matrix M = new Matrix(0, 0);
        boolean found = false;
        String fileName = "";
        while(!found){
            System.out.print("Masukkan nama file: ");
            found = true;
            try{
                fileName = Fileinput.readLine();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            try{
                Scanner file = new Scanner(new File("../test/"+fileName));
                int br = 0;
                int kl = 0;
                while(file.hasNextLine()){
                    br++;
                    kl = file.nextLine().split(" ").length;
                }
                file.close();
                M = new Matrix(br, kl);
                file = new Scanner(new File("../test/"+fileName));
                for(int i=0;i<br;i++){
                    for(int j=0;j<kl;j++){
                        M.Matrix[i][j] = file.nextDouble();
                    }
                }
                file.close();
            }
            catch(FileNotFoundException e){
                found = false;
                e.printStackTrace();
            }
        }
        return M;
    }
    
    public static Matrix inputMatrix(){
        Scanner input = new Scanner(System.in);
        Matrix M = new Matrix(0, 0);
        System.out.println("Pilih cara input :");
        System.out.println("1. Input dari keyboard");
        System.out.println("2. Input dari file");
        int pilihan = input.nextInt();
        while(pilihan != 1 && pilihan != 2){
            System.out.println("Input salah,silahkan ulangi");
            pilihan = input.nextInt();
        }
        if(pilihan == 1){
            M = keyboard();
        }
        else if(pilihan == 2){
            M = inputFile();
        }
        return M;
    }

    public static void printMatrix(Matrix M){
        for(int i=0;i<M.baris;i++){
            for(int j=0;j<M.kolom;j++){
                System.out.print(M.Matrix[i][j]+" ");
            }
            System.out.println("");
        }
    }

    public static void SPL(){
        Scanner input = new Scanner(System.in);
        System.out.println("Pilih metode yang ingin digunakan");
        System.out.println("1. Gauss");
        int pilihan = input.nextInt();
        double[] x = {};
        if(pilihan == 1){
            x = Gauss();
        }
    }

    public static double[] Gauss(){
        double[] x={0};
        Matrix M = inputMatrix();
        int maxBaris = M.baris;
        int maxKolom = M.kolom;
        x = new double[M.baris];

        for(int k=0;k<maxBaris;k++){
            double mx_el = M.Matrix[k][k];
            int mx_row = k;
            
            for(int i=k+1;i<maxBaris;i++){
                if(Math.abs(M.Matrix[i][k]) > mx_el){
                    mx_el = M.Matrix[i][k];
                    mx_row = i;
                }
            }

            for(int i=0;i<maxKolom;i++){
                double temp = M.Matrix[k][i];
                M.Matrix[k][i] = M.Matrix[mx_row][i];
                M.Matrix[mx_row][i] = temp;
            }

            for(int i=k+1;i<maxBaris;i++){
                double fact = M.Matrix[i][k] / M.Matrix[k][k];

                for(int j = k+1; j<maxKolom ;j++){
                    M.Matrix[i][j] -= fact * M.Matrix[k][j];
                }
                M.Matrix[i][k] = 0;
            }
        }
        // cek solusi banyak & gaada solusi
        boolean solBanyak = false;
        boolean minSolusi = false;
        boolean cek = true;
        int j=0;
        while(cek && j<maxKolom-1){
            if(M.Matrix[maxBaris-1][j]!=0){
                cek = false;
            }
            j++;
        }

        if(cek){
            if(M.Matrix[maxBaris-1][maxKolom-1]!=0){
                minSolusi = true;
            }
            else{
                solBanyak = true;
            }
        }

        if(solBanyak){
            System.out.println("SPL ini memiliki banyak solusi");
        }
        else if(minSolusi){
            System.out.println("SPL ini tidak memiliki solusi");
        }
        else{
            for(int i=maxBaris-1;i>=0;i--){
                double tmp = M.Matrix[i][maxKolom-1];
                for(int k=i+1;k<maxBaris;k++){
                    tmp+=x[k]*M.Matrix[i][k]*-1;
                }
                x[i] = tmp/M.Matrix[i][i];
            }

            for(int i=0;i<maxBaris;i++){
                System.out.println("X"+(i+1)+" : "+x[i]);
            }
        }
        return x;
    }

}
