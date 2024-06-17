import java.io.File;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class FileChecker {
  public static void main(String[] args) throws IOException {
    String filePath = "datasetExample.txt";

    //Create a File object:
    File file = new File(filePath);


    //Check if file exists:
    if(!file.exists()) {
      System.out.println("The file does not exist");
      //This will instead generate the data set function
      try(PrintWriter writer = new PrintWriter(new FileWriter("datasetExample.txt"))) {
        generateDataset(3, writer);
        System.out.println("The file was created");

      }
    }
    else {
      System.out.println("The file exists");
    }
    //TODO: Make a function that will read the data
    //readSumData(file);
    //readDataPrintToConsole(file);
    readDataUsingMatricies(3, file);

  }

  public static void generateData(PrintWriter writer) {
    for(int i = 0; i < 10; ++i) {
      writer.println(i);
    }
  }

  //NOTE: matrixCount is the number of matricies we want to generate data for
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

  //NOTE: Might have loss of precision when converting form long to int, so keep this in mind
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
        printMatrix(currentMatrix, numRows);
        System.out.println("\n");
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

  //TODO: Use this to find the number of rows that a matrix will have
  public static int lgBitwise(long x) {
    return 63 - Long.numberOfLeadingZeros(x);
  }

  //NOTE: This is a test function, only prints to the console
  public static void readDataPrintToConsole(File file) throws IOException {
    try(Scanner scan = new Scanner(file)) {
      while(scan.hasNextLine()) {
        String currentLine = scan.nextLine();
        String[] currentRow = currentLine.split(",");
        for(int i = 0; i < currentRow.length; ++i) {
          System.out.print(currentRow[i] + " "); //Here instead of printing i can store to a matrix
        }
        System.out.println();
      }
    }

  }

  //NOTE: This is just a test function, not part of actual code that I want
  public static void readSumData(File file) throws IOException {
    try(Scanner scan = new Scanner(file)) {
      while(scan.hasNextLine()) {
        String line = scan.nextLine();
        Scanner lineScan = new Scanner(line);

        int sum = 0;
        while(lineScan.hasNext()) {
          sum += lineScan.nextInt();
        }
        System.out.println("Sum of this line is: " + sum);
      }
    }
    //TODO: Should i catch here? Figure this out later

  }
}

