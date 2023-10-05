package lib;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Determinan {
    public static double detReduksi(Matrix M) {
        //Mencari determinan dengan reduksi baris
        Matrix N = Matrix.copyMatrix(M);

        // Proses mengurutkan baris
        int[] zeroCount = new int[Matrix.getBaris(N)];
        int swapCount = 0;
        for (int i = 0; i < Matrix.getBaris(N); i++) { // Kalkulasi jumlah 0
            zeroCount[i] = 0;
            int j = 0;
            while (j < Matrix.getKolom(N) && Matrix.getElmt(N,i,j) == 0) {
                zeroCount[i]++;
                j++;
            }
        }
        for (int i = 0; i < Matrix.getBaris(N); i++) { // Algoritma Pengurutan
            for (int j = 0; j < Matrix.getBaris(N) - 1; j++) {
                if (zeroCount[j] > zeroCount[j + 1]) {
                    int temp;
                    N.tukarBaris(j, j + 1);
                    swapCount++;
                    temp = zeroCount[j];
                    zeroCount[j] = zeroCount[j + 1];
                    zeroCount[j + 1] = temp;
                }
            }
        }
        // Proses mereduksi baris
        int indent = 0;

        for (int i = 0; i < Matrix.getBaris(N); i++) {
            // Mencari sel bernilai
            while (i + indent < Matrix.getKolom(N) && Matrix.getElmt(N, i, i+indent) == 0) {
                indent++;
            }

            if (i + indent < Matrix.getKolom(N)) {
                // Pengurangan baris dibawahnya
                for (int j = i + 1; j < Matrix.getBaris(N); j++) {
                    N.minBaris(j, i, Matrix.getElmt(N, j, i+indent) / Matrix.getElmt(N, i, i+indent));
                }
            }
        }

        // Proses menghitung jumlah diagonal
        double det = Matrix.getElmt(N, 0, 0);
        for (int i = 1; i < Matrix.getBaris(N) && i<Matrix.getKolom(N); i++) {
            det *= Matrix.getElmt(N, i, i);
        }
        det *= ((swapCount % 2) == 0) ? 1 : -1;
        return det;
    }
    public static double detKofaktor(Matrix M){
        //Menghitung determinan dengan metode kofaktor
        int sign;
        int maxBaris = Matrix.getBaris(M);
        double det;
        if (maxBaris==1){
            det=Matrix.getElmt(M, 0, 0);
        }else if (maxBaris==2){
            det=Matrix.getElmt(M, 0, 0)*Matrix.getElmt(M, 1, 1) - Matrix.getElmt(M, 0, 1)*Matrix.getElmt(M, 1, 0);
        }else{
            det=0;
            for (int j=0; j<Matrix.getBaris(M); j++){
                det+=Matrix.getElmt(M, 0, j)*(Determinan.detKofaktor(Matrix.minor(M,0,j)))*(j%2==1? -1 : 1);
            }
        }
        return det;
    }
    public static  String[] detReduksi(){
        //Tampilkan determinan dan simpan jawaban ke bentuk string
        Matrix M = Matrix.inputMatrix();
        String[] ans = new String[1];
        double det = detReduksi(M);
        System.out.println("Determinan = "+ det);
        ans[0]="Determinan = " + String.valueOf(det);
        return ans;
    }
    public static  String[] detKofaktor(){
        //Tampilkan determinan dan simpan jawaban ke bentuk string
        Matrix M = Matrix.inputMatrix();
        String[] ans = new String[1];
        double det = detKofaktor(M);
        System.out.println("Determinan = "+ det);
        ans[0]="Determinan = " + String.valueOf(det);
        return ans;
    }

    public static void ansDet(){
        //Driver determinan
        Scanner input = new Scanner(System.in);
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pilih metode yang ingin digunakan");
        System.out.println("1. Metode Reduksi Baris");
        System.out.println("2. Metode Kofaktor");
        int pilihan = input.nextInt();
        String[] ans = {""};
        if(pilihan == 1){
            ans = detReduksi();
        }
        else if(pilihan == 2){
            ans = detKofaktor();
        }

        System.out.println("Apakah hasil Determinan ingin anda simpan ?");
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
