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

    public static Matrix eselon(Matrix M){
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);

        // OBE
        int i =0;
        for(int k=0;k<maxKolom-1;k++){
            if(i == maxBaris) break;
            boolean is0 = false;
            
            double cek0 = Math.abs(Matrix.getElmt(M, i, k));
            if(cek0 == 0){
                int rowNow = i+1;
                is0 = true;
                while(is0 && rowNow<maxBaris){
                    if(Matrix.getElmt(M, rowNow, k) != 0){
                        is0 = true;
                        for(int j=0;j<maxKolom;j++){
                            double temp = Matrix.getElmt(M, rowNow, j);
                            Matrix.inputElmt(M, rowNow, j, Matrix.getElmt(M, i, j)); 
                            Matrix.inputElmt(M, i, j, temp); 
                        }
                        is0=false;
                    }
                    rowNow++;
                }
            }

            if(!is0){
                double fact = Matrix.getElmt(M, i, k);
                for(int j = k; j< maxKolom;j++){
                    Matrix.inputElmt(M, i, j, Matrix.getElmt(M, i, j)/fact);
                }
                for(int y = i+1;y<maxBaris;y++){
                    fact = Matrix.getElmt(M, y, k);
                    if(fact !=0){
                        for(int z = k ;z<maxKolom;z++){
                            Matrix.inputElmt(M, y, z,  Matrix.getElmt(M, y, z)-(Matrix.getElmt(M, i, z)*fact));
                        }
                    }
                }
                i++;
            }
        }
        return M;
    }

    public static Matrix eselonRed(Matrix M){
        M = eselon(M);
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        for(int i=maxBaris-1;i >=0;i--){
            for(int j=0;j<maxKolom-1;j++){
                if(Matrix.getElmt(M, i, j) == 1){
                    for(int k = i-1;k>=0;k--){
                        double fact = Matrix.getElmt(M, k, j);
                        for(int l = j;l<maxKolom;l++){
                            Matrix.inputElmt(M, k, l, Matrix.getElmt(M, k, l) - (Matrix.getElmt(M, i, l)*fact));
                        }
                    }
                    break;
                }
            }
        }
        return M;
    }

    // proses untuk solusi banyak
    public void manySolution(Matrix M){
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        boolean[] visited = new boolean[maxKolom-1];
        String[] param = new String[maxKolom-1];
        int num =0;
        this.nEff = maxKolom-1;
        for(int i=0;i<maxKolom-1;i++){
            visited[i] = false;
        }
        for(int i=0;i<maxBaris;i++){
            for(int j=i;j<maxKolom-1;j++){
                if(Matrix.getElmt(M,i,j) == 1){
                    visited[j] = true;
                    String tmp = "";
                    if(Matrix.getElmt(M, i, maxKolom-1)!=0){
                        tmp += String.valueOf(Matrix.getElmt(M, i, maxKolom-1));
                    }
                    for(int k=j+1;k<maxKolom-1;k++){
                        if(M.getElmt(M, i, k) != 0){
                            if(!visited[k]){
                                visited[k] = true;
                                num++;
                                param[k] = "P" + String.valueOf(num);
                                this.ans[k] = "X" + String.valueOf(k+1) + " = " + String.valueOf(param[k]);
                            }
                            if(Matrix.getElmt(M,i, k) > 0) tmp += (tmp.length() == 0 ? "" : " - ") + (Math.abs(Matrix.getElmt(M,i, k)) != 1.0 ? Double.toString(Math.abs(Matrix.getElmt(M,i, k))) : "") + param[k];
                            else tmp += (tmp.length() == 0 ? "" : " + ") + (Math.abs(Matrix.getElmt(M,i, k)) != 1.0 ? Double.toString(Math.abs(Matrix.getElmt(M,i, k))) : "") + param[k];

                        }
                    }
                    this.ans[j] = "X" + String.valueOf(j+1) + " = " + tmp ;
                    break;
                }
                else{
                    if(!visited[j]){
                        visited[j] = true;
                        num++;
                        param[j] = "P" + String.valueOf(num);
                        this.ans[j] = "X" + Integer.toString(j+1) + " = " + param[j] + "\n";
                    }
                }
            }
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
      //  x = new double[maxBaris];
        M = eselon(M);
        boolean solusiUnik= true;
        boolean minSolusi = false;
        boolean cek = true;
        int j=0;
        // cek gaada solusi
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
        }

        // cek solusi unik 
        if(Matrix.getBaris(M) < Matrix.getKolom(M)-1){
            solusiUnik = false;
        }
        if(solusiUnik){
            for(int i=0;i<maxKolom-1;i++){
                if(Matrix.getElmt(M, i, i) != 1){
                    solusiUnik = false;
                }
            }
        }

        if(minSolusi){
            this.nEff = 1;
            this.ans[0] = "SPL ini tidak memiliki solusi" ;
        }
        else if (solusiUnik){
            x = new double[maxKolom-1];
            this.nEff = maxKolom -1;
            for(int i=0;i<maxKolom-1;i++){
                this.ans[i] = "";
            }
            for(int i=maxKolom-2;i>=0;i--){
                x[i] = Matrix.getElmt(M, i, maxKolom-1);
                for(int k=i+1;k<maxKolom-1;k++){
                    x[i]+=x[k]*Matrix.getElmt(M, i, k)*-1;
                }
            }

            for(int i=0;i<maxBaris;i++){
                this.x[i] = String.format("%.6f", x[i]);
                this.ans[i] = ans[i] + "X" + (i+1) + " : " + this.x[i];
            }
        }
        else{
            eselonRed(M);
            manySolution(M);
        }
    }

    public void Gauss_Jordan(Matrix M){
        double[] x={0};
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
       // x = new double[maxBaris];
        M = eselonRed(M);
        boolean solusiUnik= true;
        boolean minSolusi = false;
        boolean cek = true;
        int j=0;
        // cek gaada solusi
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
        }

        // cek solusi unik 
        if(Matrix.getBaris(M) < Matrix.getKolom(M)-1){
            solusiUnik = false;
        }
        if(solusiUnik){
            for(int i=0;i<maxKolom-1;i++){
                if(Matrix.getElmt(M, i, i) != 1){
                    solusiUnik = false;
                }
            }
        }

        if(minSolusi){
            this.nEff = 1;
            this.ans[0] = "SPL ini tidak memiliki solusi" ;
        }
        else if (solusiUnik){
            x = new double[maxKolom-1];
            this.nEff = maxKolom-1;
            for(int i=0;i<maxKolom-1;i++){
                this.ans[i] = "";
            }
            for(int i=maxKolom-2;i>=0;i--){
                x[i] = Matrix.getElmt(M, i, maxKolom-1);
            }

            for(int i=0;i<maxKolom-1;i++){
                this.x[i] = String.format("%.6f", x[i]);
                this.ans[i] = ans[i] + "X" + (i+1) + " : " + this.x[i];
            }
        }
        else{
            manySolution(M);
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
