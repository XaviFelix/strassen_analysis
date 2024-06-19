import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Arrays;

public class Project_1_Final {

    public static void main(String[] args) throws IOException {

        // number of matricies for this analysis (from 2^2 -> 2^11)
        int matrixCount = 11;

        // Creates a txt file containing the average time of all algorithms onto current
        // directory
        try (PrintWriter writer = new PrintWriter(new FileWriter("averages.txt"))) {
            beginAnalysis(matrixCount, writer);
        }
    }

    // Fills matricies with random data and all 3 algorithms are tested yeilding an average.
    // The number of trials is 10 for this analysis
    public static void beginAnalysis(int matrixCount, PrintWriter writer) throws IOException {
        int[][] matrixA;
        int[][] matrixB;
        Random generator = new Random();
        int numTrials = 10;

        for (int i = 2, j = 0; j < matrixCount; i *= 2, j++) {
            matrixA = new int[i][i];
            matrixB = new int[i][i];

            // populate the matricies with random data:
            for (int row = 0; row < i; row++) {
                for (int col = 0; col < i; col++) {
                    matrixA[row][col] = generator.nextInt(5);
                    matrixB[row][col] = generator.nextInt(5);
                }
            }

            // Now do computations with that data: classic_matrixMult, DAC, Strassen's algo
            double averageTimeClassic = timeClassicMatrixMult(matrixA, matrixB, numTrials);
            double averageTimeDAC = timeDACMatrixMult(matrixA, matrixB, numTrials);
            double averageTimeStrassen = timeStrassen(matrixA, matrixB, numTrials);

            // Creates txt file for google sheets or excel
            writer.println(averageTimeClassic + "\t" + averageTimeDAC + "\t" + averageTimeStrassen);

            //Debug: printing data to console
            System.out.println("Classic | DAC | Strassen : " + averageTimeClassic + " " +
            averageTimeDAC + " " + averageTimeStrassen);
        }
    }

    // This returns the average from a set of trials (choosing 10 for
    // this analysis)
    public static double timeClassicMatrixMult(int[][] matrixA, int[][] matrixB, int numTrials) {
        double[] trials = new double[numTrials];
        int m = trials.length;

        // Choosing 10 trials and recording each trial based on start and end times
        for (int i = 0; i < m; ++i) {
            long startTime = System.nanoTime();
            classicMatrixMult(matrixA, matrixB);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;

            // Storing current time, converting to seconds
            trials[i] = elapsedTime / 1_000_000_000.0;
        }
        return findAverageTime(trials);
    }

    // Classic Matrix Multiplication O(n^3)
    public static void classicMatrixMult(int matrixA[][], int matrixB[][]) {
        int size = matrixA.length;
        int[][] array3 = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    array3[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }

    // This returns the average from a set of trials (choosing 10 for
    // this analysis)
    public static double timeDACMatrixMult(int[][] matrixA, int[][] matrixB, int numTrials) {
        double[] trials = new double[numTrials];
        int m = trials.length;

        // Choosing 10 trials and recording each trial based on start and end times
        for (int i = 0; i < m; ++i) {
            long startTime = System.nanoTime();
            matrixMultDAC(matrixA, matrixB);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;

             // Storing current time, converting to seconds
            trials[i] = elapsedTime / 1_000_000_000.0;
        }
        return findAverageTime(trials);
    }

    // Divide and conquer O(n^3)
    public static void matrixMultDAC(int[][] matrixA, int[][] matrixB) {
        int size = matrixA.length;
        int[][] matrixC = new int[size][size];
        int partition = size / 2;

        for (int i = 0; i < partition; i++) {
            for (int j = 0; j < partition; j++) {
                // Submatrices C11, C12, C21, C22
                for (int k = 0; k < partition; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j]
                            + matrixA[i][k + partition] * matrixB[k + partition][j];
                    matrixC[i][j + partition] += matrixA[i][k] * matrixB[k][j + partition]
                            + matrixA[i][k + partition] * matrixB[k + partition][j + partition];
                    matrixC[i + partition][j] += matrixA[i + partition][k] * matrixB[k][j]
                            + matrixA[i + partition][k + partition] * matrixB[k + partition][j];
                    matrixC[i + partition][j + partition] += matrixA[i + partition][k] * matrixB[k][j + partition]
                            + matrixA[i + partition][k + partition] * matrixB[k + partition][j + partition];
                }
            }
        }
    }

    // This returns the average from a set of trials (choosing 10 for
    // this analysis)
    public static double timeStrassen(int[][] matrixA, int[][] matrixB, int numTrials) {
        double[] trials = new double[numTrials];
        int m = trials.length;

        // Choosing 10 trials and recording each trial based on start and end times
        for (int i = 0; i < m; ++i) {
            long startTime = System.nanoTime();
            strassenMult(matrixA, matrixB);
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;

            // Storing current time, converting to seconds
            trials[i] = elapsedTime / 1_000_000_000.0;
        }
        return findAverageTime(trials);
    }

    public static void strassenMult(int[][] A, int[][] B) {
        int n = A.length;
        int[][] resultMatrix = new int[n][n];

        // Partition into sub matricies
        int[][] A11 = new int[n / 2][n / 2];
        int[][] A12 = new int[n / 2][n / 2];
        int[][] A21 = new int[n / 2][n / 2];
        int[][] A22 = new int[n / 2][n / 2];

        int[][] B11 = new int[n / 2][n / 2];
        int[][] B12 = new int[n / 2][n / 2];
        int[][] B21 = new int[n / 2][n / 2];
        int[][] B22 = new int[n / 2][n / 2];

        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j + n / 2];
                A21[i][j] = A[i + n / 2][j];
                A22[i][j] = A[i + n / 2][j + n / 2];

                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j + n / 2];
                B21[i][j] = B[i + n / 2][j];
                B22[i][j] = B[i + n / 2][j + n / 2];
            }
        }

        // Calculate M1 -> M7
        int[][] M1 = new int[n / 2][n / 2];
        int[][] M2 = new int[n / 2][n / 2];
        int[][] M3 = new int[n / 2][n / 2];
        int[][] M4 = new int[n / 2][n / 2];
        int[][] M5 = new int[n / 2][n / 2];
        int[][] M6 = new int[n / 2][n / 2];
        int[][] M7 = new int[n / 2][n / 2];

        // M1 = (A11 + A22)(B11 + B22)
        int[][] temp1 = add(A11, A22);
        int[][] temp2 = add(B11, B22);
        M1 = matrixMult(temp1, temp2);

        // M2 = (A21 + A22)B11
        temp1 = add(A21, A22);
        M2 = matrixMult(temp1, B11);

        // M3 = A11(B12 - B22)
        temp1 = subtract(B12, B22);
        M3 = matrixMult(A11, temp1);

        // M4 = A22(B21 - B11)
        temp1 = subtract(B21, B11);
        M4 = matrixMult(A22, temp1);

        // M5 = (A11 + A12)B22
        temp1 = add(A11, A12);
        M5 = matrixMult(temp1, B22);

        // M6 = (A21 - A11)(B11 + B12)
        temp1 = subtract(A21, A11);
        temp2 = add(B11, B12);
        M6 = matrixMult(temp1, temp2);

        // M7 = (A12 - A22)(B21 + B22)
        temp1 = subtract(A12, A22);
        temp2 = add(B21, B22);
        M7 = matrixMult(temp1, temp2);

        // Calculate C11, C12, C21, C22
        int[][] C11 = add(subtract(add(M1, M4), M5), M7);
        int[][] C12 = add(M3, M5);
        int[][] C21 = add(M2, M4);
        int[][] C22 = add(subtract(add(M1, M3), M2), M6);

        // Join C11, C12, C21, C22 into result matrix
        for (int i = 0; i < n / 2; i++) {
            for (int j = 0; j < n / 2; j++) {
                resultMatrix[i][j] = C11[i][j];
                resultMatrix[i][j + n / 2] = C12[i][j];
                resultMatrix[i + n / 2][j] = C21[i][j];
                resultMatrix[i + n / 2][j + n / 2] = C22[i][j];
            }
        }
    }

    // Add two matricies together
    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    // Subtract two matricies together
    private static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    // Multiply two matricies together (This one returns the result)
    public static int[][] matrixMult(int matrixA[][], int matrixB[][]) {
        int size = matrixA.length;
        int[][] matrixC = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return matrixC;
    }

    // Given m trials, find average (min and max are subtracted)
    public static double findAverageTime(double[] trials) {
        Arrays.sort(trials);
        double sum = 0.0;
        int N = trials.length;
        for (int i = 1; i < N - 1; ++i) {
            sum += trials[i];
        }
        return sum / (N - 2);
    }

    // Print to console for debug purposes
    public static void printMatrix(int[][] matrix, int size) {
        for (int i = 0; i < size; ++i) {
            System.out.print("[");
            for (int j = 0; j < size; ++j) {
                System.out.print(matrix[i][j]);
                if (j < size - 1)
                    System.out.print(" ");
            }
            System.out.println("]");
        }
    }
}