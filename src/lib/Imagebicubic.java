package lib;

import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Imagebicubic {
    public static void solveImage(){
        Scanner input = new Scanner(System.in);
        BufferedImage image= null;
        System.out.println("Masukkan nama file yang ingin diperbesar :");
        String imgAdress = "../test/"+input.nextLine();
        System.out.println("Masukkan skala perbesaran :");
        int scale = input.nextInt();

        try{
            image = ImageIO.read(new File(imgAdress));
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    public static void Bicubicspline(){
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
    }
}
