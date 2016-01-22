import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * Decoder is an object that accepts two input files necessary for decoding a Huffman encoded
 * text: the encoded text itself and a file of the map of codes. Decoder runs everything upon
 * creation, so assigning a new Decoder to a variable is not necessary, it only needs to be
 * declared.
 * 
 * @author Ian Cresse - Primary Coder
 * @author Robert Ferguson - Cleanup, formatting and restructuring
 * @version May 7th, 2015
 */
public class Decoder
{
    /** The map of codes to be built from the codes file. */
    Map<Character, String> codesMap;

    /**
     * Constructor for the Decoder class that parses files and handles output.
     * 
     * @param compressedFile - the Huffman encoded file to read in.
     * @param codeFile - the text file representation of the map used to encode the first file.
     * @param outputFile - the file to print output to.
     */
    public Decoder(File compressedFile, File codeFile, File outputFile)
    {
        try
        {
            codesMap = new HashMap<>();
            rebuildMap(codeFile);

            BitInputStream bitInput = new BitInputStream(compressedFile.getPath());
            StringBuilder bitFile = new StringBuilder();

            int bit = bitInput.readBit();
            while (bit != -1)
            {
                bitFile.append(bit);
                bit = bitInput.readBit();
            }

            BufferedWriter decompressedOutput = new BufferedWriter(new FileWriter(outputFile));
            decompressedOutput.write(decode(bitFile.toString(), codesMap));
            decompressedOutput.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Builds a Huffman tree of HuffmanNodes from a file that contains the Huffman encoding
     * map.
     * 
     * @param codeInput - the map of codes in a text file.
     */
    private void rebuildMap(File codeInput)
    {
        StringBuilder codeFile = new StringBuilder();
        int c = 0;
        try
        {
            BufferedReader inputFileReader = new BufferedReader(new FileReader(codeInput));
            while ((c = inputFileReader.read()) != -1)
            {
                char character = (char) c;
                codeFile.append(character);
            }
            inputFileReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String manipCode = codeFile.toString().substring(1, codeFile.toString().length() - 1);
        String[] token = manipCode.split(", ");

        for (int i = 0; i < token.length; i++)
        {
            codesMap.put(token[i].charAt(0), token[i].substring(2));
        }

        // System.out.println(codesMap.toString());

    }

    /**
     * Decode takes a String representation of the bits in the encoded file and decodes them
     * according to the map of codes.
     * 
     * @param bits - a String representation of the bits in the encoded file.
     * @param codes - a map of codes indicating HuffmanNode positions within a tree.
     * @return String - the decoded text.
     */
    private String decode(String bits, Map<Character, String> codes)
    {
        HuffmanNode temp = new HuffmanNode((char) 0, 0);
        for (Character c : codes.keySet())
        {
            decode(temp, c, codes.get(c));
        }
        return decodeToFile(bits, temp);
    }

    /**
     * This decode is a recursive method that rebuilds the tree from the map of codes, one code
     * at a time.
     * 
     * @param root - a reference to the root of the Huffman tree.
     * @param value - the character will eventually be put in a node.
     * @param path - the path to the correct node.
     * @return HuffmanNode - a HuffmanNode with relevant information.
     */
    private HuffmanNode decode(HuffmanNode root, Character value, String path)
    {
        if (path.length() == 0)
        {
            root = new HuffmanNode(value, 0);
        }
        else if (path.charAt(0) == '0')
        {
            if (root.left == null)
                root.left = decode(new HuffmanNode((char) 0, 0), value, path.substring(1));
            else
                root.left = decode(root.left, value, path.substring(1));

        }
        else
        {
            if (root.right == null)
                root.right = decode(new HuffmanNode((char) 0, 0), value, path.substring(1));
            else
                root.right = decode(root.right, value, path.substring(1));

        }
        return root;
    }

    /**
     * decodeToFile accepts an encoded file and the tree used to encode it and decodes each
     * character.
     * 
     * @param bits - a String representation of the bits in the encoded file.
     * @param root - a reference to the root of the file.
     * @return String - the entire decoded text.
     */
    private String decodeToFile(String bits, HuffmanNode root)
    {
        HuffmanNode temp = root;
        // BitInputStream file = new BitInputStream(bits);
        // int bit = file.readBit();
        int lengthOfBits = bits.length();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < lengthOfBits; i++)
        {
            if (temp.value != (char) 0)
            {
                text.append(temp.value);
                temp = root;
            }
            if (bits.charAt(i) == '0')
            {
                temp = temp.left;
            }
            else if (bits.charAt(i) == '1')
            {
                temp = temp.right;
            }
        }
        return text.toString();
    }

    /**
     * Contains a character and the frequency of that character, as well as references to it's
     * left and right children, if they exist. The only difference between this private class
     * and the one contained in CodingTree is that this one does not contain the second
     * constructor, as it is never used.
     *
     * @author Ian Cresse Primary coder
     * @author Robert Ferguson Code review and clean up.
     */
    private class HuffmanNode implements Comparable<HuffmanNode>
    {
        private HuffmanNode left;
        private HuffmanNode right;
        private char value;
        private int frequency;

        private HuffmanNode(char theValue, int theFrequency)
        {
            left = null;
            right = null;
            value = theValue;
            frequency = theFrequency;
        }

        @Override
        public String toString()
        {
            StringBuilder string = new StringBuilder();
            if (left != null)
                string.append(left.toString());

            string.append("(" + value + ", " + frequency + ")");

            if (right != null)
                string.append(right.toString());

            return string.toString();
        }

        @Override
        public int compareTo(HuffmanNode other)
        {
            return frequency - other.frequency;
        }
    }
}
