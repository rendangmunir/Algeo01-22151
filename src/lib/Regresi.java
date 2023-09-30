package lib;

import java.util.Scanner;

public class Regresi {
    public static void regresiLinearBerganda() {
        Matrix M = Matrix.inputMatrix();
        Matrix normal = new Matrix(Matrix.getBaris(M), Matrix.getKolom(M)+1);
        //Membuat Normal estimation matrix
        for (int i=0; i<Matrix.getBaris(normal); i++){
            for (int j=0; j<Matrix.getKolom(normal); j++){
                if (i==0 && j==0){
                    Matrix.inputElmt(normal,i,j,Matrix.getBaris(M));
                }else if (j==0 && i!=0){
                    Matrix.inputElmt(normal,i,j,Matrix.getElmt(normal, 0, i));
                }else{
                    int sum=0;
                    if (i==0){
                        for (int ii=0;i<Matrix.getBaris(M); ii++){
                            sum+=Matrix.getElmt(M, ii, j-1);
                        }
                    }else{
                        for (int ii=0;i<Matrix.getBaris(M); ii++){
                            sum+=Matrix.getElmt(M, ii, j-1)*Matrix.getElmt(M, ii, i-1);
                        }
                    }
                    Matrix.inputElmt(normal, i, j, sum);
                }
            }
        }
        //SPL kan matrix normal (bikin fungsi Gauss(Matrix)->Matrix)
        
        Matrix ans = SPL.Gauss(normal);
        System.out.println("Persamaan Linear :");
        System.out.print("Y = ");
        for (int i=0;i<Matrix.getKolom(ans);i++){
            if (i==0){
                System.out.print(Matrix.getElmt(ans, 0, i)+" ");
            }else{
                System.out.print(Matrix.getElmt(ans, 0, i)+"X"+i+" ");
            }
        }
        System.out.println("Masukkan X baru ");
        //for (int i=0;i<Matrix.getKolom(ans);i++){

    }
}
