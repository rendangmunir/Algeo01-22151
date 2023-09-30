package lib;

import java.util.Scanner;

public class Regresi {
    public static double[] gaussReg(Matrix M){
        double[] x={0};
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
        SPL.eselon(M);
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
            SPL.eselonRed(M);
            //SPL.manySolution(M);
        }
        else if(minSolusi){
        }
        else{
            for(int i=maxBaris-1;i>=0;i--){
                x[i] = Matrix.getElmt(M, i, maxKolom-1);
                for(int k=i+1;k<maxBaris;k++){
                    x[i]+=x[k]*Matrix.getElmt(M, i, k)*-1;
                }
            }
        }
        return x;
    }
    public static void regresiLinearBerganda() {
        Matrix M = Matrix.inputMatrix();
        Matrix normal = new Matrix(Matrix.getKolom(M), Matrix.getKolom(M)+1);
        //Membuat Normal estimation matrix
        for (int i=0; i<Matrix.getBaris(normal); i++){
            for (int j=0; j<Matrix.getKolom(normal); j++){
                if (i==0 && j==0){
                    Matrix.inputElmt(normal,i,j,Matrix.getBaris(M));
                }else if (j==0 && i!=0){
                    Matrix.inputElmt(normal,i,j,Matrix.getElmt(normal, 0, i));
                }else{
                    double sum=0;
                    if (i==0){
                        for (int ii=0;ii<Matrix.getBaris(M); ii++){
                            sum+=Matrix.getElmt(M, ii, j-1);
                        }
                    }else{
                        for (int ii=0;ii<Matrix.getBaris(M); ii++){
                            sum+=Matrix.getElmt(M, ii, j-1)*Matrix.getElmt(M, ii, i-1);
                        }
                    }
                    Matrix.inputElmt(normal, i, j, sum);
                }
            }
        }
        Matrix.printMatrix(normal);
        //SPL kan matrix normal (bikin fungsi Gauss(Matrix)->Matrix)
        double[] ans = gaussReg(normal);
        int len = ans.length;
        System.out.println("Persamaan Linear :");
        System.out.print("Y = ");
        for (int i=0;i<len;i++){
            if (i==0){
                System.out.print(ans[i]+" ");
            }else{
                System.out.print(ans[i]+"X"+i+" ");
            }
            if (i<len-1){
                System.out.print("+ ");
            }
        }
        System.out.print("\n");
        System.out.println("Masukkan X baru : ");
        Scanner input = new Scanner(System.in);
        int kol = ans.length;
        double[] xbaru = new double[kol];
        double yn = 0;
        xbaru[0]=1;
        for(int i=1;i<kol;i++){
            xbaru[i] = input.nextDouble();
        }
        for (int i=0;i<len;i++){
            yn+=ans[i]*xbaru[i];
        }
        System.out.println(yn);
    }
}