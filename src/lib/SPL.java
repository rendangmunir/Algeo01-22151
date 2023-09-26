package lib;

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
                double fact = Matrix.getElmt(M, i, k) / Matrix.getElmt(M, k, k);

                for(int j = k+1; j<maxKolom ;j++){
                    Matrix.inputElmt(M,i,j,Matrix.getElmt(M, i, j)-(fact*Matrix.getElmt(M, k, j)));
                }
                Matrix.inputElmt(M, i, k, 0);
            }
        }
        

        for(int i=0;i<maxBaris;i++){
            for(int j=i+1;j<maxKolom;j++){
                 Matrix.inputElmt(M,i,j,Matrix.getElmt(M, i, j)/(Matrix.getElmt(M, i, i)));     
            }
            Matrix.inputElmt(M,i,i,Matrix.getElmt(M, i, i)/(Matrix.getElmt(M, i, i)));  
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
                double fact = Matrix.getElmt(M, i, a) / Matrix.getElmt(M, a, a);

                for(int j = a+1; j<maxKolom ;j++){
                    Matrix.inputElmt(M,i,j,Matrix.getElmt(M, i, j)-(fact*Matrix.getElmt(M, a, j)));
                }
                Matrix.inputElmt(M, i, a, 0);
            }

            //cek bawah
            for(int i=a+1;i<maxBaris;i++){
                double fact = Matrix.getElmt(M, i, a) / Matrix.getElmt(M, a, a);

                for(int j = a+1; j<maxKolom ;j++){
                    Matrix.inputElmt(M,i,j,Matrix.getElmt(M, i, j)-(fact*Matrix.getElmt(M, a, j)));
                }
                Matrix.inputElmt(M, i, a, 0);
            }
            a++;
        }
    }

  
    public static void ansSPL(){
        Scanner input = new Scanner(System.in);
        System.out.println("Pilih metode yang ingin digunakan");
        System.out.println("1. Gauss");
        System.out.println("2. Gauss Jordan");
        int pilihan = input.nextInt();
        double[] x = {};
        if(pilihan == 1){
            Gauss();
        }
        else if(pilihan == 2){
            Gauss_Jordan();
        }
    }

    private static void Gauss(){
        double[] x={0};
        Matrix M = Matrix.inputMatrix();
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
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
            System.out.println("SPL ini memiliki banyak solusi");
        }
        else if(minSolusi){
            System.out.println("SPL ini tidak memiliki solusi");
        }
        else{
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
            }
        }
    }

    private static void Gauss_Jordan(){
        double[] x={0};
        Matrix M = Matrix.inputMatrix();
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
        eselonRed(M);
        for(int i=0;i<maxBaris;i++){
            x[i] = Matrix.getElmt(M, i, maxKolom-1);
        }

        System.out.println("Hasil SPL menggunakan metode gauss jordan sebagai berikut");
        for(int i=0;i<maxBaris;i++){
            System.out.print("X"+(i+1)+" : ");
            System.out.format("%.6f",x[i]);
            System.out.println("");
        } 
    }
}
