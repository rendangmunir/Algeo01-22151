import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

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
    /*Operasi Matriks */
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

    public void plusBaris(int Baris1, int Baris2){
        int i;
        for (i=0; i<kolom; i++){
            Matrix[Baris1][i] += Matrix[Baris2][i];
        }
    }
    
    public void minBaris(int Baris1, int Baris2){
        int i;
        for (i=0; i<kolom; i++){
            Matrix[Baris1][i] += Matrix[Baris2][i];
        }
    }
    /*Augmented */
    
}
