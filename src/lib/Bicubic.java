package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.lang.Math;

public class Bicubic {

    private static void inputBicubic(Matrix M,Matrix M2){
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        boolean found = false;
        String fileName = "";
        while(!found){
            System.out.print("Masukkan nama file: ");
            found = true;
            try{
                fileName = Fileinput.readLine();
                Scanner file = new Scanner(new File("..\\test\\"+fileName));
                int br = 4;
                int kl = 4;
                file = new Scanner(new File("../test/"+fileName));
                for(int i=0;i<br;i++){
                    for(int j=0;j<kl;j++){
                        Matrix.inputElmt(M, i, j, file.nextDouble());
                    }
                }
                Matrix.inputElmt(M2, 0, 0, file.nextDouble());
                Matrix.inputElmt(M2, 0, 1, file.nextDouble());
                file.close();
            }
            catch(IOException e){
                found = false;
                e.printStackTrace();
            }
        }
    }

    public static void solveBicubic(){
        Matrix M = new Matrix(4,4);
        Matrix M2 = new Matrix(1,2);
        inputBicubic(M,M2);
        double a = Matrix.getElmt(M2, 0, 0);
        double b = Matrix.getElmt(M2, 0, 1);
        
        // ubah mat 4x4 ke 16x1
        Matrix f = new Matrix(16, 1);
        int fcnt = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                Matrix.inputElmt(f, fcnt, 0, Matrix.getElmt(M, i, j));
                fcnt++;
            }
        }
        
        // Buat Matrix X
        Matrix X = new Matrix(16, 16);
        int kolom = 0;
        int baris = 0;
        //f(x,y)
        for (int x = 0; x < 2;x++){
            for (int y = 0; y < 2; y++) {
                kolom = 0;
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        Matrix.inputElmt(X, baris, kolom,Math.pow(x, i) * Math.pow(y, j));
                        kolom++;
                    }
                }
                baris++;
            }
        }

        //fx(x,y)
        for (int y = 0; y < 2;y++){
            for (int x = 0; x < 2; x++) {
                kolom = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        if(i!=0){
                            Matrix.inputElmt(X, baris, kolom,i * Math.pow(x, i-1) * Math.pow(y, j));
                        }
                        kolom++;
                    }
                }
                baris++;
            }
        }

        //fy(x,y)
        for (int y = 0; y < 2;y++){
            for (int x= 0; x < 2; x++) {
                kolom = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        if(j!=0){
                            Matrix.inputElmt(X, baris, kolom,j * Math.pow(x, i) * Math.pow(y, j-1));
                        }
                        kolom++;
                    }
                }
                baris++;
            }
        }

        //fxy(x,y)
        for (int y = 0; y < 2;y++){
            for (int x= 0; x < 2; x++) {
                kolom = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        if(i!=0 && j!=0){
                            Matrix.inputElmt(X, baris, kolom,i * j * Math.pow(x, i-1) * Math.pow(y, j-1));
                        }
                        kolom++;
                    }
                }
                baris++;
            }
        }

        //invers X
        //cari a
        //hitung f(a,b)
    }
}
