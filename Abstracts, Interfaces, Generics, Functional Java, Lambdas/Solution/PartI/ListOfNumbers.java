import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;


public class ListOfNumbers {
    private ArrayList<RDFTriple<Integer, Integer, Integer>> rdfTripleList;
    private final String fileName;

    // Constructing instance with specified file name for RDF>>
    
    public ListOfNumbers(String fileName) {
        this.rdfTripleList = new ArrayList<>();
        this.fileName = fileName;
    }

    // Default
    public ListOfNumbers() {
        this(null); 
    }

    //getting list of RDF 
    public ArrayList<RDFTriple<Integer, Integer, Integer>> getRdfTripleList() {
        return this.rdfTripleList;
    }
    // Filling list
    public void populateListWithRandomData() {
    	//before adding data>>
        this.rdfTripleList.clear(); 
        //making up random integers for triple
        for (int i = 0; i < 100; i++) {
            int number1 = (int) (Math.random() * 10000);
            int number2 = (int) (Math.random() * 10000);
            int number3 = (int) (Math.random() * 10000);
            RDFTriple<Integer, Integer, Integer> triple = new RDFTriple<>(number1, number2, number3);
            this.rdfTripleList.add(triple);
        }
    }

    // get RDF triples
    public void readListFromFile() {
        if (this.fileName == null) {
            System.err.println("No input file name specified.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
            	// Splitting lines to components and converting into RDF triples.
                String[] nums = line.split(" ");
                if (nums.length == 3) {
                    RDFTriple<Integer, Integer, Integer> triple = new RDFTriple<>(
                        Integer.parseInt(nums[0]), Integer.parseInt(nums[1]), Integer.parseInt(nums[2]));
                    this.rdfTripleList.add(triple);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Writing RDF triples to output
    public void writeListToFile(String outputFile) {
        try (PrintWriter out = new PrintWriter(new FileWriter(outputFile))) {
        	
            for (RDFTriple<Integer, Integer, Integer> triple : this.rdfTripleList) {
                out.println(triple.getSubj() + " " + triple.getPred() + " " + triple.getObj());
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Printing content of file in console
    public static void printFileContents(String fileName) throws IOException {
        try (RandomAccessFile input = new RandomAccessFile(fileName, "r")) {
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ListOfNumbers listOfNumbers = new ListOfNumbers("numberfile.txt");
        printFileContents("numberfile.txt");
        listOfNumbers.readListFromFile();
        listOfNumbers.populateListWithRandomData();
        listOfNumbers.writeListToFile("outFile.txt");
    }
}
