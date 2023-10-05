package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SPL {

   private String[] ans;

   int nEff;

   String[]x;

    // ADT SPL
    public SPL(){
        this.ans = new String[99999];
        this.nEff = 0;
        this.x = new String[99999];
    }

    public String[] getSolusispl(SPL spl){
        return spl.x;
    }

    public int getNeffspl(SPL spl){
        return spl.nEff;
    }

    public static void eselon(Matrix M){
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);

        // OBE
        for(int k=0;k<maxBaris;k++){
            double mx_el = Matrix.getElmt(M, k, k);
            int mx_row = k;
            
            for(int i=k+1;i<maxBaris;i++){
                if(Math.abs(Matrix.getElmt(M, i, k)) > mx_el){
                    mx_el = Matrix.getElmt(M, i, k);
                    mx_row = i;
                }
            }

            for(int i=0;i<maxKolom;i++){
                double temp = Matrix.getElmt(M, k, i);
                Matrix.inputElmt(M, k, i, Matrix.getElmt(M, mx_row, i)); 
                Matrix.inputElmt(M, mx_row, i, temp); 
            }

            for(int i=k+1;i<maxBaris;i++){
                if(Matrix.getElmt(M, k, k) != 0){
                    double fact = Matrix.getElmt(M, i, k) / Matrix.getElmt(M, k, k);

                    for(int j = k+1; j<maxKolom ;j++){
                        Matrix.inputElmt(M,i,j,Matrix.getElmt(M, i, j)-(fact*Matrix.getElmt(M, k, j)));
                    }
                    Matrix.inputElmt(M, i, k, 0);
                }
            }
        }
        
        // membuat 1 utama
        for(int i=0;i<maxBaris;i++){
            for(int j=i+1;j<maxKolom;j++){
                if(Matrix.getElmt(M, i, i) != 0){
                    Matrix.inputElmt(M,i,j,Matrix.getElmt(M, i, j)/(Matrix.getElmt(M, i, i)));     
                }
            }
            if(Matrix.getElmt(M, i, i) != 0){
                Matrix.inputElmt(M,i,i,Matrix.getElmt(M, i, i)/(Matrix.getElmt(M, i, i)));
            }  
        }
    }

    public static void eselonRed(Matrix M){
        eselon(M);
        int a=0;
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        // buat 0 diatas dan bawah 1 utama
        while(a<Matrix.getBaris(M)){
            //cek atas
            for(int i=a-1;i>=0;i--){
                if(Matrix.getElmt(M, a, a) !=0){
                    double fact = Matrix.getElmt(M, i, a) / Matrix.getElmt(M, a, a);

                    for(int j = a+1; j<maxKolom ;j++){
                        Matrix.inputElmt(M,i,j,Matrix.getElmt(M, i, j)-(fact*Matrix.getElmt(M, a, j)));
                    }
                    Matrix.inputElmt(M, i, a, 0);
                }
            }

            //cek bawah
            for(int i=a+1;i<maxBaris;i++){
                if(Matrix.getElmt(M, a, a) !=0){
                    double fact = Matrix.getElmt(M, i, a) / Matrix.getElmt(M, a, a);

                    for(int j = a+1; j<maxKolom ;j++){
                        Matrix.inputElmt(M,i,j,Matrix.getElmt(M, i, j)-(fact*Matrix.getElmt(M, a, j)));
                    }
                    Matrix.inputElmt(M, i, a, 0);
                }
            }
            a++;
        }
    }

    // proses untuk solusi banyak
    public void manySolution(Matrix M){
        boolean rowZero = true;
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        this.nEff = maxBaris;
        int br = maxBaris-1;
        int cntRowzero = 0;
        while(rowZero){
            for(int j=0;j<maxKolom;j++){
                if(Matrix.getElmt(M,br,j)!=0){
                rowZero = false;
                }
            }
            if(rowZero){
                cntRowzero++;
            }
            br--;
        }
        // buat variable
        String[] param = new String[cntRowzero];
        for(int i=0;i<cntRowzero;i++){
            param[i] = "P" + String.valueOf(i+1);
        }

        for(int i=0;i<maxBaris;i++){
            this.x[i] = "";
        }

        int cntParam = 0;
        for(int i=maxBaris-1;i>=0;i--){
            if(cntParam != cntRowzero){
                this.x[i] = param[cntParam];
                cntParam++;
            }
            else{
                this.x[i] = String.valueOf(Matrix.getElmt(M, i, maxKolom-1));
                for(int j=i+1;j<maxKolom-1;j++){
                    if(Matrix.getElmt(M, i, j) !=0){
                        this.x[i] = this.x[i] + " - " + String.valueOf(Matrix.getElmt(M, i, j)) + this.x[j]; 
                    }
                }
            }
        }

        for(int i=0;i<maxBaris;i++){
            this.ans[i] = "";
        }
        
        // simpan solusi
        for(int i=0;i<maxBaris;i++){
            System.out.print("X"+(i+1)+" = "+this.x[i]);
            this.ans[i] = this.ans[i] + "X" + (i+1) + " = " + this.x[i];
            System.out.println();
        }

    }

    // Driver SPL
    public static void ansSPL(){
        Scanner input = new Scanner(System.in);
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        // Menu SPL
        System.out.println("Pilih metode yang ingin digunakan");
        System.out.println("1. Gauss");
        System.out.println("2. Gauss Jordan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        int pilihan = input.nextInt();
        double[] x = {};
        SPL spl = new SPL();
        if(pilihan == 1){
            Matrix M = Matrix.inputMatrix();
            spl.Gauss(M);
        }
        else if(pilihan == 2){
            Matrix M = Matrix.inputMatrix();
            spl.Gauss_Jordan(M);
        }
        else if(pilihan == 3){
             spl.splInvers();
        }
        else if (pilihan == 4){
            spl.cramer();
        }

        // menampilkan solusi spl
        System.out.println("Solusi SPl : ");
        for(int i=0;i<spl.nEff;i++){
            System.out.println(spl.ans[i]);
        }

        System.out.println("Apakah hasil SPl ingin anda simpan ?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        pilihan = input.nextInt();
        while(pilihan != 1 && pilihan != 2 ){
            System.out.println("Masukan salah silahkan ulangi!");
            pilihan = input.nextInt();
        }
        // simpan hasil di file
        if(pilihan == 1){
            System.out.print("Masukkan nama file: ");
            String fileName = "";
            try{
                fileName = Fileinput.readLine();
                FileWriter file = new FileWriter("../test/"+fileName);
                int ansLength = spl.nEff;
                for(int i=0;i<ansLength;i++){
                    file.write(spl.ans[i]);
                    file.write("\n");
                }
                file.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        
    }

    public void Gauss(Matrix M){
        double[] x={0};
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
        eselon(M);
        // cek solusi banyak & gaada solusi
        boolean solBanyak = false;
        boolean minSolusi = false;
        boolean cek = true;
        int j=0;
        while(cek && j<maxKolom-1){
            if(Matrix.getElmt(M,maxBaris-1,j)!=0){
                cek = false;
            }
            j++;
        }

        if(cek){
            if(Matrix.getElmt(M,maxBaris-1,maxKolom-1)!=0){
                minSolusi = true;
            }
            else{
                solBanyak = true;
            }
        }
        //solusi banyak
        if(solBanyak){
            eselonRed(M);
            manySolution(M);
        }
        //gaada solusi
        else if(minSolusi){
            this.nEff = 1;
            System.out.println("SPL ini tidak memiliki solusi");
            this.ans[0] = "SPL ini tidak memiliki solusi" ;
        }
        //solusi unik
        else{
            this.nEff = maxBaris;
            for(int i=0;i<maxBaris;i++){
                this.ans[i] = "";
            }
            for(int i=maxBaris-1;i>=0;i--){
                x[i] = Matrix.getElmt(M, i, maxKolom-1);
                for(int k=i+1;k<maxBaris;k++){
                    x[i]+=x[k]*Matrix.getElmt(M, i, k)*-1;
                }
            }

            for(int i=0;i<maxBaris;i++){
                this.x[i] = String.format("%.6f", x[i]);
                this.ans[i] = ans[i] + "X" + (i+1) + " : " + this.x[i];
            }
        }
    }

    public void Gauss_Jordan(Matrix M){
        double[] x={0};
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
        eselonRed(M);
         // cek solusi banyak & gaada solusi
        boolean solBanyak = false;
        boolean minSolusi = false;
        boolean cek = true;
        int j=0;
        while(cek && j<maxKolom-1){
            if(Matrix.getElmt(M,maxBaris-1,j)!=0){
                cek = false;
            }
            j++;
        }

        if(cek){
            if(Matrix.getElmt(M,maxBaris-1,maxKolom-1)!=0){
                minSolusi = true;
            }
            else{
                solBanyak = true;
            }
        }
        // solusi banyak
        if(solBanyak){
            this.nEff = maxBaris;
            manySolution(M);
        }
        // gaada solusi
        else if(minSolusi){
            this.nEff = 1;
            System.out.println("SPL ini tidak memiliki solusi");
            this.ans[0] = "SPL ini tidak memiliki solusi";
        }
        //solusi unik
        else{
            this.nEff = maxBaris;
            for(int i=0;i<maxBaris;i++){
                x[i] = Matrix.getElmt(M, i, maxKolom-1);
                this.ans[i] = "";
            }
            for(int i=0;i<maxBaris;i++){
                this.x[i] = String.format("%.6f", x[i]);
                this.ans[i] = ans[i] + "X" + (i+1) + " : " + this.x[i];
            } 
        }
    }

    public void splInvers(){
        Scanner input = new Scanner(System.in);
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pilih metode yang digunakan");
        System.out.println("1. Matriks balikan");
        System.out.println("2. Adjoin");
        Invers inv = new Invers();
        Matrix invA = new Matrix(0,0);
        Matrix M = new Matrix(0,0);
        Matrix A = new Matrix(0,0);
        Matrix B = new Matrix(0,0);
        int pilihan = input.nextInt();
        if (pilihan==1){
            M = (Matrix.inputMatrix());
            A = Matrix.getA(M);
            B = Matrix.getB(M);
            invA = inv.invers(A);
        }else if (pilihan == 2){
            M = (Matrix.inputMatrix());
            A = Matrix.getA(M);
            B = Matrix.getB(M);
            invA = inv.adjoin(A);
        }
        if (!(Matrix.getKolom(invA)==1 && Matrix.getBaris(invA)==1 && Matrix.getElmt(invA, 0, 0)==0)){
            Matrix ans = Matrix.crossMatrix(invA, B);
            this.nEff=Matrix.getBaris(ans);
            for (int i=0; i<this.nEff; i++){
                this.ans[i]="X"+(i+1)+" : "+ String.format("%.6f", Matrix.getElmt(ans, i, 0));
            }
        }else{
            this.nEff=1;
            this.ans[0]="Solusi parametrik";
        }
        
    }
    /* Cramer */
    public void cramer(){
        Matrix M = Matrix.inputMatrix();
        Matrix A = Matrix.getA(M);
        Matrix B = Matrix.getB(M);
        double DetA = Determinan.detReduksi(A);
        double[] Det = new double[Matrix.getKolom(A)];
        double[] X = new double[Matrix.getKolom(A)];
        for (int i=0; i<Matrix.getKolom(A); i++){
            Matrix temp = new Matrix(Matrix.getBaris(A), Matrix.getKolom(A));
            temp=Matrix.getA(M);
            for (int j=0; j<Matrix.getBaris(A); j++){
                Matrix.inputElmt(temp, j, i, Matrix.getElmt(B,j,0));
                if (j==Matrix.getBaris(A)-1){
                    Det[i]=Determinan.detReduksi(temp);
                }
            }
        }
        this.nEff=Matrix.getKolom(A);
        for (int i=0; i<Matrix.getKolom(A); i++){
            X[i]=(double)Det[i]/DetA;
            this.ans[i]="X"+(i+1)+" : "+ String.format("%.6f", X[i]);
        }
    }
}
