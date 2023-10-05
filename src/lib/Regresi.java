package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Regresi {
    public static double[] gaussReg(Matrix M){
        //Mengembalikan nilai-nilai x1,x2,... dari Matrix M dengan metode gauss
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
        return x;
    }
    public static void regresiLinearBerganda() {
        Scanner input = new Scanner(System.in);
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pilih cara input :");
        System.out.println("1. Input dari keyboard");
        System.out.println("2. Input dari file");
        Matrix M = new Matrix(0,0);
        double[] xbaru = {0};
        int pil=0;
        int pilihan = input.nextInt();
        while(pilihan != 1 && pilihan != 2){
            System.out.println("Input salah,silahkan ulangi");
            pilihan = input.nextInt();
        }
        if(pilihan == 1){
            pil=1;
            System.out.print("Masukkan banyak data : ");
            int br = input.nextInt();
            System.out.print("Masukkan jumlah variabel peubah : ");
            int kl =input.nextInt()+1;
            M = new Matrix(br, kl);
            for(int i=0;i<br;i++){
                for(int j=0;j<kl;j++){
                    Matrix.inputElmt(M, i, j, input.nextDouble());;
                }
            }
        }
        else if(pilihan == 2){
            M = new Matrix(0, 0);
            boolean found = false;
            String fileName = "";
            while(!found){
                System.out.print("Masukkan nama file: ");
                found = true;
                try{
                    fileName = Fileinput.readLine();
                    Scanner file = new Scanner(new File("..\\test\\"+fileName));
                    int br = 0;
                    int kl = 0;
                    while(file.hasNextLine()){
                        br++;
                        kl = file.nextLine().split(" ").length;
                    }
                    br--;
                    kl++;
                    file.close();
                    M = new Matrix(br, kl);
                    file = new Scanner(new File("../test/"+fileName));
                    for(int i=0;i<br;i++){
                        for(int j=0;j<kl;j++){
                            Matrix.inputElmt(M, i, j, file.nextDouble());
                        }
                    }
                    xbaru = new double[kl];
                    xbaru[0]=1;
                    for (int i=1; i<kl;i++){
                        xbaru[i]=file.nextDouble(); 
                        //System.out.println(xbaru[i]);
                    }
                    file.close();
                }
                catch(IOException e){
                    found = false;
                    e.printStackTrace();
                }
            }
        }
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
        //SPL kan matrix normal
        double[] ans = gaussReg(normal);
        int len = ans.length;
        String[] strans = new String[2];
        for (int m=0; m<2; m++){
            strans[m]="";
        }
        //Tampilkan Persamaan linear
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
        if (pil ==1){
            //Input x1,x2,... baru
            System.out.println("Masukkan X baru : ");
            int kol = ans.length;
            xbaru = new double[kol];
            double yn = 0;
            xbaru[0]=1;
            for(int i=1;i<kol;i++){
                xbaru[i] = input.nextDouble();
                if (i==1){
                    strans[0]+=", f(" + String.valueOf(xbaru[i]);
                    strans[1]+="f(" + String.valueOf(xbaru[i]);
                }else{
                    strans[0]+=", " + String.valueOf(xbaru[i]);
                    strans[1]+=", " + String.valueOf(xbaru[i]);
                }
            }
            strans[0]+=") = ";
            strans[1]+=") = ";
            //Hitung y baru
            for (int i=0;i<len;i++){
                yn+=ans[i]*xbaru[i];
            }
            //simpan jawaban dalam bentuk string
            strans[0]+=String.valueOf(yn);
            strans[1]+=String.valueOf(yn);
            System.out.println(strans[1]);
        }else{
            double yn = 0;
            strans[0]+=", f(";
            strans[1]+="f(";
            for (int i=0;i<len;i++){
                yn+=ans[i]*xbaru[i];
                if (i>0){
                    strans[0]+=String.valueOf(xbaru[i]);
                    strans[1]+=String.valueOf(xbaru[i]);
                    if (i<len-1){
                        strans[0]+=", ";
                        strans[1]+=", ";
                    }
                }
            }
            strans[0]+=") = "+String.valueOf(yn);
            strans[1]+=") = "+String.valueOf(yn);
            System.out.println(strans[1]);
        }

        System.out.println("Apakah hasil Regresi ingin anda simpan ?");
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
                int ansLength = 1;
                for(int i=0;i<1;i++){
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