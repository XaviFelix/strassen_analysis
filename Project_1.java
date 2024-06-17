import java.io.File;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Project_1 {
  
  public static void main(String[] args) throws IOException {
    //Name of the text file that contains matrix data
    String dataFile = "dataset.txt";

    //File object:
    File file = new File(dataFile);

    //look for dataset file in current directory. Creates one if it doesn't exist
    findDataSet(file);

    //Extract data from dataset file and compute
    readDataUsingMatricies(3, file);
  }

  //NOTE: Check if dataset exists:
  public static void findDataSet(File file) throws IOException {
    //Check if file in current directory exists
    if(!file.exists()) {
      System.out.println("The file does not exist");
      //This will instead generate the data set function
      try(PrintWriter writer = new PrintWriter(new FileWriter("dataset.txt"))) {
        generateDataset(3, writer);
        System.out.println("The file was created");
      }
    }
    else {
      System.out.println("The file exists");
    }
  }

  //NOTE: matrixCount is the number of matricies we want to generate data for default is 100
  public static void generateDataset(int matrixCount, PrintWriter writer) {
    Random generator = new Random();
    for(int i = 2, j = 0; j < matrixCount; i *= 2, j++) {

      int printCounter = 0;
      for(int k = 0; k < i * i; ++k) {
        writer.print(generator.nextInt(5));
        printCounter++;
        if(printCounter % i == 0)
          writer.print(",");
        else
          writer.print(" ");
      }
      writer.println();
    }
  }

  //NOTE: This function reads data and computes using the algorithm
  //TODO: Do i need matrixCount in this function?? Figure this out!
  //TODO: Re-name funciton to: beginAnalysis(File file)
  public static void readDataUsingMatricies(int matrixCount, File file) throws IOException {
    int[][] currentMatrix;
    try(Scanner scan = new Scanner(file)) {
      while(scan.hasNextLine()) {
        String currentLine = scan.nextLine();
        String[] currentDataFrame = currentLine.split(",");
        int numRows = currentDataFrame.length;
        currentMatrix = new int[numRows][numRows];
        for(int i = 0; i < numRows; ++i) {
          Scanner scan_2 = new Scanner(currentDataFrame[i]);
          for(int j = 0; j < numRows; ++j) {
            currentMatrix[i][j] = scan_2.nextInt();
          }
          scan_2.close();
        }
        //printMatrix(currentMatrix, numRows);
        //System.out.println("\n");
        //NOTE: This is where we record the time of the algorithm
        //TODO: Find the Average of this:
        for(int i = 0; i < 10; i++){

        }
        timeClassicMatrixMult(numRows, currentMatrix);
        //TODO: Build: timeRecursiveMatrixMult() and timeStrassenAlgorithm()
      }
    }
  }

  //TODO: This needs to record the findings into a separate file
  //TODO: This also needs to get the average first before printing to the separate file
  public static void timeClassicMatrixMult(int rows, int[][] currentMatrix) {
    //NOTE: I'm choosing 10 trials for this analysis
    double trials = new double[10];
    int m = trials.length;

    for(int i = 0; i < m; ++i) {
      long startTime = System.nanoTime();
      classicMatrixMult(rows, currentMatrix);
      long endTime = System.nanoTime();
      long elapsedTime = endTime - startTime;

      //NOTE: this line is for debugging purposes
      System.out.println("Elased time in seconds: " + elapsedTime / 1_000_000_000.0 + "\n");

      //NOTE: Store current time
      trials[i] = elapsedTime;
    }
    //TODO: Find the average here using trials array

  }

  public static void classicMatrixMult(int size,  int array1[][]) {
    int [][] array3 = new int[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        for (int k = 0; k < size; k++) {
         array3[i][j] += array1[i][k] * array1[k][j]; 
        }
      }
    }
   //TODO: This is just a debug funciton, delete when project is finished
   printMatrix(array3, size); 

  }

  //TODO: This needs to be tested 
  public static double findAverageTime(double[] trials) {
    Arrays.sort(tirals);
  }


  public static void printMatrix(int[][] matrix, int size) {
    for(int i = 0; i < size; ++i) {
      System.out.print("[");
      for(int j = 0; j < size; ++j) {
        System.out.print(matrix[i][j]);
        if(j < size - 1)
          System.out.print(" ");
      }
      System.out.println("]");
    }
  }

  //NOTE: This is a test function for getting the sum of this matrix:
  public static void printSum(int[][] matrix, int size) {
    int sum = 0;
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
        sum += matrix[i][j];
      }
    }
    System.out.println("Sum: " + sum);
  }
}
