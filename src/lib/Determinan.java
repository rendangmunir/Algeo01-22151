package lib;

public class Determinan {
    public static double detReduksi(Matrix M) {
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
        double sign=1;
        double det;
        det=0;
        for (int j=0; j<Matrix.getBaris(M); j++){
            det+=Matrix.getElmt(M, 0, j)*(Determinan.detReduksi(Matrix.minor(M,0,j)))*sign;
            sign*=-1;
        }
        //System.out.println("Determinan = "+ det);
        return det;
    }
}
