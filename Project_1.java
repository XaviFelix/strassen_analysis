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
    readDataUsingMatricies(5, file);
  }

  //NOTE: Check if file exists:
  public static void findDataSet(File file) throws IOException {
    //Check if file in current directory exists
    if(!file.exists()) {
      System.out.println("The file does not exist");
      //This will instead generate the data set function
      try(PrintWriter writer = new PrintWriter(new FileWriter("dataset.txt"))) {
        generateDataset(5, writer);
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
        printSum(currentMatrix, numRows);
      }
    }
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

  //NOTE: Get the sum of this matrix:
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
