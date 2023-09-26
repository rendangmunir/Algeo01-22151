package lib;
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
            catch(IOException e){
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

    /*Operasi Matrix */

    public static int getBaris(Matrix M){
        return M.baris;
    }

    public static int getKolom(Matrix M){
        return M.kolom;
    }

    public static double getElmt(Matrix M,int i,int j){
        return M.Matrix[i][j];
    }

    public static void inputElmt(Matrix M,int i,int j,double value){
        M.Matrix[i][j] = value;
    }

    public static Matrix dotPMatrix(Matrix M, double k){
        Matrix out = new Matrix(M.baris, M.kolom);
        int i,j;
        for (i=0; i<M.baris; i++){
            for (j=0; j<M.kolom; j++){
                out.Matrix[i][j] = M.Matrix[i][j]*k;
            }
        }
        return out;
    }

    public void dotPMatrix(double k){
        this.Matrix = dotPMatrix(this, k).Matrix;
    }

    public static Matrix crossMatrix(Matrix M, Matrix N){
        //prekondisi : M.Kolom==N.Baris
        Matrix out = new Matrix(M.baris, N.kolom);
        for (int i = 0; i < out.baris; i++) {
            for (int j = 0; j < out.kolom; j++) {
                out.Matrix[i][j] = 0;
                for (int k = 0; k < M.kolom; k++) {
                    out.Matrix[i][j] += M.Matrix[i][k] * N.Matrix[k][j];
                }
            }
        }

        return out;
        
    }

    /*OBE */
    public void tukarBaris(int Baris1, int Baris2){
        double[] temp = Matrix[Baris1];
        Matrix[Baris1] = Matrix[Baris2];
        Matrix[Baris2] =temp;
    }

    public void kaliBaris(int Baris, double k){
        int i;
        for (i=0; i<kolom; i++){
            Matrix[Baris][i] *= k;
        }
    }

    public void plusBaris(int Baris1, int Baris2, double k){
        int i;
        for (i=0; i<kolom; i++){
            Matrix[Baris1][i] += Matrix[Baris2][i]*k;
        }
    }
    public void plusBaris(int Baris1, int Baris2){
        plusBaris(Baris1, Baris2,1);
    }

    public void minBaris(int Baris1, int Baris2, double k){
        plusBaris(Baris1, Baris2, -k);
    }

    public void minBaris(int Baris1, int Baris2){
        plusBaris(Baris1, Baris2, -1);
    }
    public static void copyMatrix(Matrix M1, Matrix M2){
        M2.baris = M1.baris;
        M2.kolom = M1.kolom;

        for (int i=0; i<M1.baris; i++){
            for (int j=0; j<M1.kolom; j++){
                M2.Matrix[i][j]=M1.Matrix[i][j];
            }
        }
    }

    public static Matrix copyMatrix(Matrix M1){
        Matrix M2 = new Matrix(M1.baris, M1.kolom);
        copyMatrix(M1,M2);
        return M2;
    }
    /* Augmented */
    public static Matrix getA(Matrix M){
        Matrix out = new Matrix(M.baris, M.kolom-1);
        for (int i=0; i<out.baris; i++){
            for (int j=0; j<out.kolom; j++){
                out.Matrix[i][j] = M.Matrix[i][j];
            }
        }
        return out;
    }

    public static Matrix getB(Matrix M){
        Matrix out = new Matrix(M.baris, 1);
        for (int i=0; i<out.baris; i++){
            for (int j=0; j<out.kolom; j++){
                out.Matrix[i][j] = M.Matrix[i][M.kolom-1];
            }
        }
        return out;
    }

    /* Determinan */
    public static double Determinan(Matrix M) {
        Matrix N = copyMatrix(M);

        // Proses mengurutkan baris
        int[] zeroCount = new int[N.baris];
        int swapCount = 0;
        for (int i = 0; i < N.baris; i++) { // Kalkulasi jumlah 0
            zeroCount[i] = 0;
            int j = 0;
            while (j < N.kolom && N.Matrix[i][j] == 0) {
                zeroCount[i]++;
                j++;
            }
        }
        for (int i = 0; i < N.baris; i++) { // Algoritma Pengurutan
            for (int j = 0; j < N.baris - 1; j++) {
                if (zeroCount[j] > zeroCount[j + 1]) {
                    int temp;
                    N.tukarBaris(j, j + 1);
                    swapCount++;
                    temp = zeroCount[j];
                    zeroCount[j] = zeroCount[j + 1];
                    zeroCount[j + 1] = temp;
                }
            }
        }
        // Proses mereduksi baris
        int indent = 0;

        for (int i = 0; i < N.baris; i++) {
            // Mencari sel bernilai
            while (i + indent < N.kolom && N.Matrix[i][i + indent] == 0) {
                indent++;
            }

            if (i + indent < N.kolom) {
                // Pengurangan baris dibawahnya
                for (int j = i + 1; j < N.baris; j++) {
                    N.minBaris(j, i, N.Matrix[j][i + indent] / N.Matrix[i][i + indent]);

                }
            }
        }

        // Proses menghitung jumlah diagonal
        double det = N.Matrix[0][0];
        for (int i = 1; i < N.baris && i<N.kolom; i++) {
            det *= N.Matrix[i][i];
        }
        det *= ((swapCount & 2) == 0) ? 1 : -1;
        return det;
    }

    /* Cramer */
    public static void cramer(){
        Matrix M = inputMatrix();
        Matrix A = getA(M);
        Matrix B = getB(M);
        double DetA = Determinan(A);
        double[] Det = new double[A.kolom];
        double[] X = new double[A.kolom];
        for (int i=0; i<A.kolom; i++){
            Matrix temp = new Matrix(A.baris, A.kolom);
            temp=getA(M);
            for (int j=0; j<A.baris; j++){
                temp.Matrix[j][i]=B.Matrix[j][0];
                if (j==A.baris-1){
                    Det[i]=Determinan(temp);
                }
            }
        }
        for (int i=0; i<A.kolom; i++){
            X[i]=(double)Det[i]/DetA;
            System.out.println("X" + (i+1) + " : " + X[i]);
        }


    }
    
}
