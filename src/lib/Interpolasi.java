package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Interpolasi {
    
    // Driver Interpolasi
    public static void solveInterpolasi(){
        Scanner input = new Scanner(System.in);
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        double[] x ={0};
        double[] y ={0};
        double X =0;
        int br = 0;
        System.out.println("Pilih cara input :");
        System.out.println("1. Input dari keyboard");
        System.out.println("2. Input dari file");
        int pilihan = input.nextInt();
        while(pilihan != 1 && pilihan != 2){
            System.out.println("Input salah,silahkan ulangi");
            pilihan = input.nextInt();
        }
        // input dari keyboard
        if(pilihan == 1){
            System.out.println("Masukkan n: ");
            br = input.nextInt();
            x = new double[br];
            y = new double[br];
            System.out.println("Masukkan titik titik : ");
            for(int i=0;i<br;i++){
                    x[i] = input.nextDouble();
                    y[i] = input.nextDouble();
                }
            System.out.println("Masukkan nilai X yang ingin ditaksir : ");
            X = input.nextDouble();
        }
        // input dari file
        else if(pilihan == 2){
            boolean found = false;
            String fileName = "";
            while(!found){
                System.out.print("Masukkan nama file: ");
                found = true;
                try{
                    fileName = Fileinput.readLine();
                    Scanner file = new Scanner(new File("..\\test\\"+fileName));
                    while(file.hasNextLine()){
                        br++;
                        file.nextLine();
                    }
                    br--;
                    file.close();
                    file = new Scanner(new File("../test/"+fileName));
                    x = new double[br];
                    y = new double[br];
                    for(int i=0;i<br;i++){
                        x[i] = file.nextDouble();
                        y[i] = file.nextDouble();
                    }
                    X = file.nextDouble();
                    file.close();
                }
                catch(IOException e){
                    found = false;
                    e.printStackTrace();
                }
            }
        }

        // buat matrix
        Matrix M = new Matrix(br, br+1);
        for(int i=0;i<br;i++){
            double a = 1;
            for(int j=0;j<br+1;j++){
                if(j!=br){
                    Matrix.inputElmt(M, i, j, a);
                    a*=x[i];
                }else{
                    Matrix.inputElmt(M, i, j, y[i]);
                }
            }
        }

        // cari nilai a
        SPL spl = new SPL();
        spl.Gauss_Jordan(M);
        String[] a = {""};
        a = spl.x;

        //hitung taksiran
        double p = 0;
        for(int i=0;i<br;i++){
            p+= Double.parseDouble(a[i])*Math.pow(X, i);        
        }

        // simpan output
        String ans = "f(x) = ";
        for(int i=br-1;i>=0;i--){
            ans+=a[i]+"x"+"^"+String.valueOf(i);
            if(i!=0){
                ans+=" + ";
            }
        }
        ans+=", f("+String.valueOf(X)+")"+" = "+String.valueOf(p);
        System.out.println(ans);

        System.out.println("Apakah hasil Interpolasi ingin anda simpan ?");
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
                file.write(ans);
                file.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
