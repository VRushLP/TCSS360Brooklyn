import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Reads in a .txt file and encodes it using the Huffman encoding algorithm. The text in the
 * file is then compressed using the Huffman codes generated and stored in a new file. The
 * Huffman codes used to compress the original file are also stored in a new file. The original
 * file is unchanged.
 * 
 * Can also read in a compressed file and decompresses it, assuming a file is available that
 * has the Huffman codes associated with it.
 * 
 * This project is a collaboration between Robert Ferguson and Ian Cresse.
 * 
 * This project uses BitInputStream.java and BitOutputStream.java, which were fetched from
 * -https://courses.cs.washington.edu/courses/cse143/14su/homework/7/BitInputStream.java
 * -https://courses.cs.washington.edu/courses/cse143/14su/homework/7/BitOutputStream.java
 * 
 * The files are unmodified, except for documentation at the top which is now in JavaDoc form
 * and credits the authors.
 * 
 * @authors Robert Ferguson, Ian Cresse
 */
public class Main
{
    // Mess with this one!
    private static final String inputPath = ".\\DonQuixote.txt";

    // 'bad' filenames. Don't mess with these.
    private static final String codeOutputPath = ".\\codes.txt";
    private static final String compressedOutputPath = ".\\compressed.txt";
    private static final String decompressedOutputPath = ".\\decompressed.txt";

    // Better filenames. Don't mess with these either!
    // private static final String txt = ".txt";
    // private static final String codeOutputPath = inputPath.substring(0, inputPath.length()
    // - txt.length())
    // + "HuffmanCodes.txt";
    // private static final String compressedOutputPath = inputPath.substring(0,
    // inputPath.length() - txt.length())
    // + "Compressed.txt";
    // private static final String decompressedOutputPath = inputPath.substring(0,
    // inputPath.length() - txt.length())
    // + "Decompressed.txt";

    private static final int COMPRESSION_PRECISION = 4;

    private static BufferedReader inputFileReader;

    /**
     * Private constructor to avoid instantiation.
     */
    private Main()
    {
    }

    /**
     * Reads text from an input file, then encodes the file using a Huffman Tree. The Huffman
     * codes and the compressed file are both put into files.
     * 
     * @param theArgs Unused in this program.
     */
    public static void main(String[] theArgs)
    {
        long startTime = System.currentTimeMillis();
        try
        {
            File inputFile = new File(inputPath);
            long inputFileSize = inputFile.length();

            inputFileReader = new BufferedReader(new FileReader(inputFile));

            System.out.println("File found!");
            System.out.println("Size of file \"" + inputFile.getName() + "\" is "
                    + inputFileSize + " bytes");

            StringBuilder inputString = new StringBuilder();

            int c = 0;
            while ((c = inputFileReader.read()) != -1)
            {
                char character = (char) c;
                inputString.append(character);
            }

            System.out.println("Input read successfully in "
                    + (System.currentTimeMillis() - startTime) + "ms");
            CodingTree encodedFile = new CodingTree(inputString.toString());

            File codeOutputFile = new File(codeOutputPath);
            BufferedWriter codeFileWriter = new BufferedWriter(new FileWriter(codeOutputFile));

            codeFileWriter.write(encodedFile.codesToString());
            codeFileWriter.close();

            File compressedOutputFile = new File(compressedOutputPath);
            BitOutputStream compressionStream = new BitOutputStream(compressedOutputPath);

            // Do this call once instead of n times. (potentially millions)
            long stop = inputString.length();
            for (int i = 0; i < stop; i++)
            {
                // bitString is assumed to be entirely 1's and 0's, but...
                String bitString = encodedFile.getBinaryString(inputString.charAt(i));
                // As a precaution, each bit is checked. Malformed bits are ignored.
                for (int j = 0; j < bitString.length(); j++)
                {
                    if (bitString.charAt(j) == '1')
                        compressionStream.writeBit(1);
                    else if (bitString.charAt(j) == '0')
                        compressionStream.writeBit(0);
                }
                // This check causes an O(n) length increase in runtime, but it is not
                // appreciable.
            }

            long compressedOutputFileSize = compressedOutputFile.length();
            System.out.println("Compressed file size in bytes: " + compressedOutputFileSize);
            compressionStream.close();

            /*
             * Calculate the compression rate. There is no way to realistically do this without
             * BigDecimal, since it involves division using longs that needs to be represented
             * with a decimal after the division.
             */
            BigDecimal compressionRate = BigDecimal.ZERO;
            compressionRate = compressionRate.add(new BigDecimal(compressedOutputFileSize));
            compressionRate = compressionRate.divide(new BigDecimal(inputFileSize),
                    COMPRESSION_PRECISION, RoundingMode.HALF_EVEN);

            // prints out the compression rate with the decimal moved right by 2 so it can be
            // output as a percentage.
            System.out.println("Compression Rate: "
                    + compressionRate.movePointRight(2).toString() + "%");
            System.out.println("Total run time of encoding was "
                    + (System.currentTimeMillis() - startTime) + "ms");

            startTime = System.currentTimeMillis();
            File decompressedOutputFile = new File(decompressedOutputPath);
            new Decoder(compressedOutputFile, codeOutputFile, decompressedOutputFile);

            System.out.println("Total run time of decoding was "
                    + (System.currentTimeMillis() - startTime) + "ms");

        }
        catch (IOException e)
        {
            System.out.println("Error: File not found. Check the inputPath String and try again.");
            e.printStackTrace();
        }
    }
}
