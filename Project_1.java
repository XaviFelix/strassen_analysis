import java.io.File;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;

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

  //TODO: Make it so that it generates 2 datasets for two matricies
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
        //NOTE: This funciton can take in a trials argument to be more flexible
        // if i want more tirals in the future
        System.out.println("Average: " + timeClassicMatrixMult(numRows, currentMatrix, 10));
        //TODO: Build: timeRecursiveMatrixMult() and timeStrassenAlgorithm()
      }
    }
  }

  //TODO: This needs to record the findings into a separate file as well
  //NOTE: This returns the average of a set of number of trials (choosing 10 for this analysis) 
  public static double timeClassicMatrixMult(int rows, int[][] currentMatrix, int numTrials) {
    double[] trials = new double[numTrials];
    int m = trials.length;

    for(int i = 0; i < m; ++i) {
      long startTime = System.nanoTime();
      classicMatrixMult(rows, currentMatrix);
      long endTime = System.nanoTime();
      long elapsedTime = endTime - startTime;

      //NOTE: this line is for debugging purposes
      //System.out.println("Elased time in seconds: " + elapsedTime / 1_000_000_000.0 + "\n");

      //NOTE: Store current time, its converted to seconds
      trials[i] = elapsedTime / 1_000_000_000.0;
    }
    //TODO: Find the average here using trials array
    printMatrix(currentMatrix, rows); //NOTE: This is for debugging
    return findAverageTime(trials);
  }

  //TODO: This needs a third argument that takes in a second matrix
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
   //printMatrix(array3, size); 

  }

  //TODO:  I tested this a couple of times already, it seems to work. Review it one more time later
  public static double findAverageTime(double[] trials) {
    Arrays.sort(trials);
    double sum = 0.0;
    int N = trials.length;
    for(int i = 1; i < N - 1; ++i) {
      sum += trials[i];
    }
    return sum / (N - 2);
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
  //TODO: Delete when project is finished
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
