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
    
    public static Matrix inputFile(){
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        Matrix M = new Matrix(0, 0);
        boolean found = false;
        String fileName = "";
        while(!found){
            System.out.print("Masukkan nama file: ");
            found = true;
            try{
                fileName = Fileinput.readLine();
                Scanner file = new Scanner(new File("..\\test\\"+fileName));
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

    public static Matrix minor(Matrix M, int i, int j){
        //Mengembailkan minor  dari elemen M[i,j]
        Matrix out = new Matrix(M.baris-1, M.kolom-1);
        int im, jm;
        im=0;
        for (int ii=0; ii<M.baris; ii++){
            if(ii!=i){
                jm=0;
                for (int jj=0; jj<M.kolom; jj++){
                    if (jj!=j){
                        out.Matrix[im][jm]=M.Matrix[ii][jj];
                        jm++;
                    }
                }
                im++;
            }
        }
        return out;
    }
    /*Testing minor */
    
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
        //Mengembalikan Matrix A dari bentuk Ax=b
        Matrix out = new Matrix(M.baris, M.kolom-1);
        for (int i=0; i<out.baris; i++){
            for (int j=0; j<out.kolom; j++){
                out.Matrix[i][j] = M.Matrix[i][j];
            }
        }
        return out;
    }
    
    public static Matrix getB(Matrix M){
        //Mengembalikan Matrix B dari bentuk Ax=b
        Matrix out = new Matrix(M.baris, 1);
        for (int i=0; i<out.baris; i++){
            for (int j=0; j<out.kolom; j++){
                out.Matrix[i][j] = M.Matrix[i][M.kolom-1];
            }
        }
        return out;
    }
    
}
