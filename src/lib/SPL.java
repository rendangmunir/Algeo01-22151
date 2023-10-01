package lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SPL {

   private String[] ans;

   int nEff;

   String[]x;

    
    public SPL(){
        this.ans = new String[99999];
        this.nEff = 0;
        this.x = new String[99999];
    }

    public String[] getSolusispl(SPL spl){
        return spl.x;
    }

    public int getNeffspl(SPL spl){
        return spl.nEff;
    }

    public static void eselon(Matrix M){
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);

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

    public static void eselonRed(Matrix M){
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

    public void manySolution(Matrix M){
        boolean rowZero = true;
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        this.nEff = maxBaris;
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

        for(int i=0;i<maxBaris;i++){
            this.x[i] = "";
        }

        int cntParam = 0;
        for(int i=maxBaris-1;i>=0;i--){
            if(cntParam != cntRowzero){
                this.x[i] = param[cntParam];
                cntParam++;
            }
            else{
                this.x[i] = String.valueOf(Matrix.getElmt(M, i, maxKolom-1));
                for(int j=i+1;j<maxKolom-1;j++){
                    if(Matrix.getElmt(M, i, j) !=0){
                        this.x[i] = this.x[i] + " - " + String.valueOf(Matrix.getElmt(M, i, j)) + this.x[j]; 
                    }
                }
            }
        }

        for(int i=0;i<maxBaris;i++){
            this.ans[i] = "";
        }

        for(int i=0;i<maxBaris;i++){
            System.out.print("X"+(i+1)+" = "+this.x[i]);
            this.ans[i] = this.ans[i] + "X" + (i+1) + " = " + this.x[i];
            System.out.println();
        }

    }

  
    public static void ansSPL(){
        Scanner input = new Scanner(System.in);
        BufferedReader Fileinput = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Pilih metode yang ingin digunakan");
        System.out.println("1. Gauss");
        System.out.println("2. Gauss Jordan");
        System.out.println("3. Matriks Balikan");
        System.out.println("4. Kaidah Cramer");
        int pilihan = input.nextInt();
        double[] x = {};
        SPL spl = new SPL();
        if(pilihan == 1){
            Matrix M = Matrix.inputMatrix();
            spl.Gauss(M);
        }
        else if(pilihan == 2){
            Matrix M = Matrix.inputMatrix();
            spl.Gauss_Jordan(M);
        }
        else if(pilihan == 3){
            spl.splInvers();
        }
        else if (pilihan == 4){
            spl.cramer();
        }

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
                int ansLength = spl.nEff;
                for(int i=0;i<ansLength;i++){
                    file.write(spl.ans[i]);
                    file.write("\n");
                }
                file.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        
    }

    public void Gauss(Matrix M){
        double[] x={0};
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
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
            eselonRed(M);
            manySolution(M);
        }
        else if(minSolusi){
            this.nEff = 1;
            System.out.println("SPL ini tidak memiliki solusi");
            this.ans[0] = "SPL ini tidak memiliki solusi" ;
        }
        else{
            this.nEff = maxBaris;
            for(int i=0;i<maxBaris;i++){
                this.ans[i] = "";
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
                this.x[i] = String.format("%.6f", x[i]);
                this.ans[i] = ans[i] + "X" + (i+1) + " : " + this.x[i];
            }
        }
    }

    public void Gauss_Jordan(Matrix M){
        double[] x={0};
        int maxBaris = Matrix.getBaris(M);
        int maxKolom = Matrix.getKolom(M);
        x = new double[maxBaris];
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
            this.nEff = maxBaris;
            manySolution(M);
        }

        else if(minSolusi){
            this.nEff = 1;
            System.out.println("SPL ini tidak memiliki solusi");
            this.ans[0] = "SPL ini tidak memiliki solusi";
        }

        else{
            this.nEff = maxBaris;
            for(int i=0;i<maxBaris;i++){
                x[i] = Matrix.getElmt(M, i, maxKolom-1);
                this.ans[i] = "";
            }
            System.out.println("Hasil SPL menggunakan metode gauss jordan sebagai berikut");
            for(int i=0;i<maxBaris;i++){
                System.out.print("X"+(i+1)+" : ");
                System.out.format("%.6f",x[i]);
                System.out.println("");
                this.x[i] = String.format("%.6f", x[i]);
                this.ans[i] = ans[i] + "X" + (i+1) + " : " + this.x[i];
            } 
        }
    }

    //SPL METODE MATRIKS BALIKAN

    //menggunakan inputan matrix A dan B 
    //matrix A merupakan konstanta variabel SPL
    //matrix B merupakan hasil persamaan SPL
    //matrix A akan di invers

    public static void splInvers() 
    {
        Scanner input = new Scanner(System.in);

        //input jumlah baris dan kolom
        System.out.print("Baris = ");
        int baris = input.nextInt();
        System.out.print("Kolom = ");
        int kolom = input.nextInt();

        //deklarasi matriks 
        double A[][] = new double [baris] [kolom];
        double inversA[][] = new double [baris][baris];
        double B[] = new double [baris];
        double  X[] = new double  [baris];
        double I[][] = new double[baris][baris]; //jumlah kolom dan baris matrix I sama
        double result[][] = new double [baris][kolom+kolom];

        //input matriks A
        for (int i = 0; i < baris; i++)
        {
            for (int j = 0; j < kolom; j++)
            {
                System.out.print("Masukkan nilai Matrix A baris ke-" + (i+1) + " dan kolom ke-" + (j+1) + "= ");
                A[i][j]=input.nextDouble();
            }
        }
        
        //inisialisasi matrix identitas
        for (int i = 0; i < baris; i++) 
        {
            for (int j = 0; j < kolom; j++) 
            {
                if (i == j) 
                {
                    I[i][j] = 1;
                } 
                else 
                {
                    I[i][j] = 0;
                }
            }
        }

        //menambahkan matrix A dengan matrix identitas
        for (int i = 0; i < baris; i++)
        {
            for (int j = 0; j < kolom; j++)
            {
                result[i][j] = A[i][j];
            }
            for (int j = 0; j < kolom; j++)
            {
                result[i][kolom + j] = I[i][j];
            }
        }

        // Melakukan operasi Gauss-Jordan 
        Matrix hasilGaussJordan = Gauss_Jordan(result); //memanggil fungsi Gauss_jordan
       

        //ambil matrix invers
        for (int i = 0; i < baris; i++) 
        {
            for (int j = 0; j < baris; j++) 
            {
                inversA[i][j] = hasilGaussJordan[i][kolom + j];
            }
        }

        //input matriks B
        for (int i = 0; i < baris; i++)
        {
            System.out.print("Masukkan nilai matrix B baris ke-" + (i+1) + " dan kolom ke-1 = ");
            B[i]=input.nextDouble();
        }

        //solusi SPL
        System.out.println("solusi spl = ");

        for (int i = 0; i < baris; i++)
        {
            double kali = 0.0;
            for (int j = 0; j < kolom; j++)
            {
                kali += inversA[i][j] * B[j];
            }
            X[i] = kali;
        }

        //tampilkan solusi SPL
        for (int i = 0; i < baris; i++)
        {
            System.out.println("x" + (i+1) + "= " + X[i]);
        }

    }
    /* Cramer */
    public void cramer(){
        Matrix M = Matrix.inputMatrix();
        Matrix A = Matrix.getA(M);
        Matrix B = Matrix.getB(M);
        double DetA = Determinan.detReduksi(A);
        double[] Det = new double[Matrix.getKolom(A)];
        double[] X = new double[Matrix.getKolom(A)];
        for (int i=0; i<Matrix.getKolom(A); i++){
            Matrix temp = new Matrix(Matrix.getBaris(A), Matrix.getKolom(A));
            temp=Matrix.getA(M);
            for (int j=0; j<Matrix.getBaris(A); j++){
                Matrix.inputElmt(temp, j, i, Matrix.getElmt(B,j,0));
                if (j==Matrix.getBaris(A)-1){
                    Det[i]=Determinan.detReduksi(temp);
                }
            }
        }
        this.nEff=Matrix.getKolom(A);
        for (int i=0; i<Matrix.getKolom(A); i++){
            X[i]=(double)Det[i]/DetA;
            System.out.println("X" + (i+1) + " : " + X[i]);
            this.ans[i]="X"+(i+1)+" : "+ String.valueOf(X[i]);
        }
    }
}
