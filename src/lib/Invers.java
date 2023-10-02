package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Invers {
    private String[] ansinv;

    int nEff;

    String[]x;

    
    public Invers (){
        this.ansinv = new String[99999];
        this.nEff = 0;
        this.x = new String[99999];

    }

    
    public static Matrix identitas(int i) {
        Matrix M = new Matrix(i, i);
        for(int j=0;j<Matrix.getBaris(M); j++){
            for (int k=0;k<Matrix.getKolom(M);k++){
                if (j==k){
                    Matrix.inputElmt(M, j, k, 1);
                }else{
                    Matrix.inputElmt(M, j, k, 0);
                }
            }
        }
        return M;
    }

    public static Matrix konkat(Matrix M1, Matrix M2){
        //Prekondisi dimensi M1 dan M2 sama
        Matrix ans = new Matrix(Matrix.getBaris(M2), Matrix.getKolom(M2)*2);
        for (int i=0; i<Matrix.getBaris(ans); i++){
            for (int j=0; j<Matrix.getKolom(ans); j++){
                if (i<Matrix.getBaris(M2) && j<Matrix.getKolom(M2)){
                    Matrix.inputElmt(ans, i, j, Matrix.getElmt(M1, i, j));
                }else{
                    Matrix.inputElmt(ans, i, j, Matrix.getElmt(M2, i, j-Matrix.getKolom(M2)));
                }
            }
        }
        //Matrix.printMatrix(ans);
        return ans;
    }

    public Matrix invers(Matrix M0) {
        //mengembalikan Matrix invers, jika tidak ada invers mengembalikan matrix 1x1 dengan elemen(0,0) bernilai 0
        Matrix id = identitas(Matrix.getBaris(M0));
        Matrix M = konkat(M0, id);
        Matrix ans;
        int maxBaris = Matrix.getBaris(M0);
        int maxKolom = Matrix.getKolom(M0);
        SPL.eselonRed(M);
        //Matrix.printMatrix(M);
        // cek solusi banyak & gaada solusi
        boolean cek = true;
        int j=0;
        while(cek && j<maxKolom){
            if(Matrix.getElmt(M,maxBaris-1,j)!=0){
                cek = false;
            }
            j++;
        }
        
        if (cek){
            System.out.println("Matriks tidak memiliki balikan");
            this.nEff=1;
            this.ansinv[0]="Matriks tidak memiliki balikan";
            ans = identitas(1);
            Matrix.inputElmt(ans,0,0,0);
        }else{
            ans = new Matrix(maxBaris, maxKolom);
            this.nEff=Matrix.getBaris(M);
            for (int x=0; x<this.nEff; x++){
                this.ansinv[x]="";
            }
            System.out.println("Matrix Invers : ");
            for (int p=0; p<Matrix.getBaris(M); p++){
                for (int q=maxKolom; q<Matrix.getKolom(M); q++){
                    Matrix.inputElmt(ans, p, q-maxKolom, Matrix.getElmt(M, p, q));
                    System.out.format("%.6f", Matrix.getElmt(ans,p,q-maxKolom));
                    this.ansinv[p]+=String.format("%.6f", Matrix.getElmt(ans,p,q-maxKolom));
                    if (q<Matrix.getKolom(M)-1){
                        System.out.print(" ");
                        this.ansinv[p]+=" ";
                    }
                }
                System.out.print("\n");
            }
        }
        return ans;
    }
    
    public static Matrix transpose(Matrix M){
        Matrix N = new Matrix(Matrix.getKolom(M), Matrix.getBaris(M));
        for (int i=0; i<Matrix.getBaris(N); i++){
            for (int j=0; j<Matrix.getKolom(N); j++){
                Matrix.inputElmt(N,i,j,Matrix.getElmt(M,j,i));
            }
        }
        return N;
    }
    
    public Matrix adjoin(Matrix M){
        //mengembalikan Matrix invers, jika tidak ada invers mengembalikan matrix 1x1 dengan elemen(0,0) bernilai 0
        double det = Determinan.detReduksi(M);
        Matrix ans;
        if (det==0){
            System.out.println("Matrix tidak memiliki balikan");
            this.nEff=1;
            this.ansinv[0]="Matriks tidak memiliki balikan";
            ans = identitas(1);
            Matrix.inputElmt(ans,0,0,0);
        }else{
            Matrix adj = new Matrix(Matrix.getBaris(M), Matrix.getKolom(M));
            int sign=1;
            for (int i=0; i<Matrix.getBaris(adj); i++){
                for (int j=0; j<Matrix.getKolom(adj); j++){
                    Matrix.inputElmt(adj,i,j, sign*Determinan.detReduksi(Matrix.minor(M,i,j)));
                    if (Matrix.getKolom(adj)%2==0){
                        if(j==Matrix.getKolom(adj)-1){
                            sign*=1;
                        }else{
                            sign*=-1;
                        }
                    }else{
                        sign*=-1;
                    }
                }
            }
            ans = transpose(adj);
            ans.dotPMatrix(1/det);
            this.nEff=Matrix.getBaris(ans);
            for (int x=0; x<this.nEff; x++){
                this.ansinv[x]="";
            }
            System.out.println("Matrix Invers : ");
            for (int i=0; i<Matrix.getBaris(ans);i++){
                for (int j=0; j<Matrix.getKolom(ans); j++){
                    System.out.format("%.6f", Matrix.getElmt(ans,i,j));
                    this.ansinv[i]+=String.format("%.6f", Matrix.getElmt(ans,i,j));
                    if (j<Matrix.getKolom(M)-1){
                        System.out.print(" ");
                        this.ansinv[i]+=" ";
                    }
                }
                System.out.print("\n");
            }
        }
        return ans;
    }

    private static Matrix keyboard(){
        Scanner input = new Scanner(System.in);
        Matrix M = new Matrix(0,0);
        System.out.print("Masukkan jumlah baris/kolom : ");
        int br = input.nextInt();
        M = new Matrix(br,br) ;
        for(int i=0;i<br;i++){
            for(int j=0;j<br;j++){
                Matrix.inputElmt(M,i,j, input.nextDouble());
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
            M = Invers.keyboard();
        }
        else if(pilihan == 2){
            M = Matrix.inputFile();
        }
        return M;
    }

    public static void inversMatrix(){
        Scanner input = new Scanner(System.in);
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pilih metode yang digunakan");
        System.out.println("1. Matriks balikan");
        System.out.println("2. Adjoin");
        int pilihan = input.nextInt();
        Invers inv = new Invers();
        if (pilihan==1){
            Matrix M = inputMatrix();
            inv.invers(M);
        }else{
            Matrix M = inputMatrix();
            inv.adjoin(M);
        }

        System.out.println("Apakah hasil Invers ingin anda simpan ?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        pilihan = input.nextInt();
        while(pilihan != 1 && pilihan != 2 ){
            System.out.println("Masukan salah silahkan ulangi!");
            pilihan = input.nextInt();
        }
        if(pilihan == 1){
            System.out.print("Masukkan nama file: ");
            String fileName = "";
            try{
                fileName = Fileinput.readLine();
                FileWriter file = new FileWriter("../test/"+fileName);
                int ansLength = inv.nEff;
                for(int i=0;i<ansLength;i++){
                    file.write(inv.ansinv[i]);
                    if (i<ansLength-1){
                        file.write("\n");
                    }
                }
                file.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
