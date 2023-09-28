package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SPL {

   private boolean solBanyak;

   private boolean minSolusi;

    
    public SPL(Matrix M){
        this.solBanyak = false;
        this.minSolusi = false;
    }

    private static void eselon(Matrix M){
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        double[] x = new double[Matrix.getBaris(M)];

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

    private static void eselonRed(Matrix M){
        eselon(M);
        int a=0;
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
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

    private static String[] manySolution(Matrix M){
        Matrix.printMatrix(M);
        boolean rowZero = true;
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
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
        String[] param = new String[cntRowzero];
        for(int i=0;i<cntRowzero;i++){
            param[i] = "P" + String.valueOf(i+1);
        }

        String[] x = new String[maxBaris];
        for(int i=0;i<maxBaris;i++){
            x[i] = "";
        }

        int cntParam = 0;
        for(int i=maxBaris-1;i>=0;i--){
            if(cntParam != cntRowzero){
                x[i] = param[cntParam];
                cntParam++;
            }
            else{
                x[i] = String.valueOf(Matrix.getElmt(M, i, maxKolom-1));
                for(int j=i+1;j<maxKolom-1;j++){
                    if(Matrix.getElmt(M, i, j) !=0){
                        x[i] = x[i] + " - " + String.valueOf(Matrix.getElmt(M, i, j)) + x[j]; 
                    }
                }
            }
        }

        String[] ans = new String[maxBaris];
        for(int i=0;i<maxBaris;i++){
            ans[i] = "";
        }

        for(int i=0;i<maxBaris;i++){
            System.out.print("X"+(i+1)+" = "+x[i]);
            ans[i] = ans[i] + "X" + (i+1) + " = " + x[i];
            System.out.println();
        }
        return ans;

    }

  
    public static void ansSPL(){
        Scanner input = new Scanner(System.in);
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pilih metode yang ingin digunakan");
        System.out.println("1. Gauss");
        System.out.println("2. Gauss Jordan");
        System.out.println("3. Kaidah Cramer");
        int pilihan = input.nextInt();
        double[] x = {};
        String[] ans = new String[0];
        if(pilihan == 1){
            ans = Gauss();
        }
        else if(pilihan == 2){
            ans = Gauss_Jordan();
        }
        //else if (pilihan == 3){
        //     ans= cramer();
        // }

        System.out.println("Apakah hasil SPl ingin anda simpan ?");
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
                int ansLength = ans.length;
                for(int i=0;i<ansLength;i++){
                    file.write(ans[i]);
                    file.write("\n");
                }
                file.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        
    }

    private static String[] Gauss(){
        double[] x={0};
        Matrix M = Matrix.inputMatrix();
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
        String[] ans = {""};
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
        if(solBanyak){
            ans = new String[maxBaris];
            eselonRed(M);
            ans =  manySolution(M);
        }
        else if(minSolusi){
            ans = new String[1];
            System.out.println("SPL ini tidak memiliki solusi");
            ans[0] = "SPL ini tidak memiliki solusi" ;
        }
        else{
            ans = new String[maxBaris];
            for(int i=0;i<maxBaris;i++){
                ans[i] = "";
            }
            for(int i=maxBaris-1;i>=0;i--){
                x[i] = Matrix.getElmt(M, i, maxKolom-1);
                for(int k=i+1;k<maxBaris;k++){
                    x[i]+=x[k]*Matrix.getElmt(M, i, k)*-1;
                }
            }

            System.out.println("Hasil SPL menggunakan metode gauss sebagai berikut");
            for(int i=0;i<maxBaris;i++){
                System.out.print("X"+(i+1)+" : ");
                System.out.format("%.6f",x[i]);
                System.out.println("");
                ans[i] = ans[i] + "X" + (i+1) + " : " + String.format("%.6f", x[i]);
            }
        }
        return ans;
    }

    private static String[] Gauss_Jordan(){
        double[] x={0};
        Matrix M = Matrix.inputMatrix();
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
        String[] ans = {""};
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
        if(solBanyak){
            ans = new String[maxBaris];
            ans = manySolution(M);
        }

        else if(minSolusi){
            ans = new String[1];
            System.out.println("SPL ini tidak memiliki solusi");
            ans[0] = "SPL ini tidak memiliki solusi";
        }

        else{
            ans = new String[maxBaris];
            for(int i=0;i<maxBaris;i++){
                x[i] = Matrix.getElmt(M, i, maxKolom-1);
                ans[i] = "";
            }
            System.out.println("Hasil SPL menggunakan metode gauss jordan sebagai berikut");
            for(int i=0;i<maxBaris;i++){
                System.out.print("X"+(i+1)+" : ");
                System.out.format("%.6f",x[i]);
                System.out.println("");
                ans[i] = ans[i] + "X" + (i+1) + " : " + String.format("%.6f", x[i]);
            } 
        }
        return ans;
    }
}
