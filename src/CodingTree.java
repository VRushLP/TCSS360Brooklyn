/*
 * TCSS342 - Assignment 3 CodingTree - Spring 2015
 */

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of Huffman's algorithm. Given a String, it calculates the frequency of
 * characters in the string and stores them in a map. After it has scanned the entire input, it
 * builds a Huffman tree using a priority queue. After the Huffman tree has been built, the
 * entire tree is traversed in pre-order. When a leaf is reached, it stores the path used to
 * reach that leaf into a new Map which can be used to generate binary Strings for encoding.
 * 
 * @author Ian Cresse: Created Huffman encoding logic, including HuffmanNode.
 * @author Robert Ferguson: Updated HuffmanNode with toString method for debugging. Code
 *         review.
 * 
 */
public class CodingTree
{
    private Map<Character, String> codes;
    private Map<Character, Integer> charFrequency;
    private HuffmanNode root;

    public CodingTree(String message)
    {
        root = new HuffmanNode((char) 0, 0); // null char
        codes = new HashMap<Character, String>();
        charFrequency = new HashMap<Character, Integer>();

        frequencyCount(message);
        buildTree();
        encode(root);
    }

    /**
     * Gets the frequency count of the characters in the passed String.
     * 
     * @param message the String to count the characters of.
     */
    private void frequencyCount(String message)
    {
        char parts[] = message.toCharArray();
        for (char c : parts)
        {
            if (charFrequency.containsKey(c))
                charFrequency.put(c, charFrequency.get(c) + 1);
            else
                charFrequency.put(c, 1);
        }
    }

    private void buildTree()
    {
        root = innerBuildTree();
    }

    private HuffmanNode innerBuildTree()
    {
        MyPriorityQueue<HuffmanNode> pq = new MyPriorityQueue<HuffmanNode>();
        for (Character c : charFrequency.keySet())
            pq.add(new HuffmanNode(c, charFrequency.get(c)));

        // remove first two elements (lowest frequencies) and makes them a new node
        while (pq.size() > 1)
            pq.add(new HuffmanNode(pq.remove(), pq.remove()));

        return pq.remove();
    }

    /**
     * Calls the recursive method to encode the values in the Huffman tree.
     * 
     * @param root The root of the Huffman tree.
     */
    private void encode(HuffmanNode root)
    {
        encode(root, "");
    }

    /**
     * A recursive method to encode the values in the Huffman tree. If it reaches a root, it
     * puts the value of that root and the path it was took to that root into a HashMap. If it
     * is a not a root, it calls encode on it's left child and appends a 0 to the path, then
     * calls encode on it's right child and appends a 1 to the path.
     * 
     * @param root The Root of the Huffman Tree.
     * @param path The path currently taken down the Huffman tree.
     */
    private void encode(HuffmanNode root, String path)
    {
        if (isLeaf(root))
            codes.put(root.value, path);
        else
        {
            encode(root.left, path + '0');
            encode(root.right, path + '1');
        }
    }

    public String getBinaryString(char c)
    {
        return codes.get(c);
    }

    /**
     * Checks whether or not a node is a leaf. A leaf is defined as a node with no children.
     * 
     * @param root The node to check.
     * @return True if it is a leaf, false otherwise.
     */
    private boolean isLeaf(HuffmanNode root)
    {
        return root.left == null && root.right == null;
    }

    /**
     * Gives the toString of the map used to store characters and their associated bit strings.
     * 
     * @return The characters and their huffman assigned bit strings.
     */
    public String codesToString()
    {
        return codes.toString();
    }

    @Override
    public String toString()
    {
        return root.toString();
    }

    /**
     * Contains a character and the frequency of that character, as well as references to it's
     * left and right children, if they exist.
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

        private HuffmanNode(HuffmanNode theLeft, HuffmanNode theRight)
        {
            left = theLeft;
            right = theRight;
            value = (char) 0;
            frequency = theLeft.frequency + theRight.frequency;
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
