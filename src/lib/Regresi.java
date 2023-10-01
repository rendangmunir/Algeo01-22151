package lib;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Regresi {
    public static double[] gaussReg(Matrix M){
        double[] x={0};
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
        SPL.eselon(M);
        for(int i=maxBaris-1;i>=0;i--){
            x[i] = Matrix.getElmt(M, i, maxKolom-1);
            for(int k=i+1;k<maxBaris;k++){
                x[i]+=x[k]*Matrix.getElmt(M, i, k)*-1;
            }
        }
        //Matrix.printMatrix(M);
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
        //SPL kan matrix normal (bikin fungsi Gauss(Matrix)->Matrix)
        double[] ans = gaussReg(normal);
        int len = ans.length;
        String[] strans = new String[2];
        for (int m=0; m<2; m++){
            strans[m]="";
        }
        System.out.println("Persamaan Linear :");
        System.out.print("f(x) = ");
        strans[0]+="f(x) = ";
        for (int i=0;i<len;i++){
            if (i==0){
                System.out.print(ans[i]+" ");
                strans[0]+=String.valueOf(ans[i])+" ";
            }else{
                System.out.print(ans[i]+"X"+i+" ");
                strans[0]+=String.valueOf(ans[i])+"X"+String.valueOf(i)+" ";
            }
            if (i<len-1){
                System.out.print("+ ");
                strans[0]+="+ ";
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
        strans[1]+=String.valueOf(yn);

        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Apakah hasil Regresi ingin anda simpan ?");
        System.out.println("1. Ya");
        System.out.println("2. Tidak");
        int pilihan = input.nextInt();
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
                int ansLength = strans.length;
                for(int i=0;i<ansLength;i++){
                    file.write(strans[i]);
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