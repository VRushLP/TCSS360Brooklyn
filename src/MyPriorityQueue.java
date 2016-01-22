import java.util.ArrayList;
import java.util.List;

/**
 * The array implementation of a PriorityQueue. The child of a node is located at indexes
 * (2i+1) and (2i+2). 0 has children at 1 and 2, 1 has children at 3 and 4, and so on. By
 * extension, the parent of a node is at the (i - 1)/2 (6's parent is at 5/2, 2. 10's parent is
 * at 9/2, 4) etc. This implementation is a minimum priority queue.
 *
 * @author Robert Ferguson Primary coder.
 * @author Ian Cresse Code Review
 * @param <T> The object that the queue holds. Must implement comparable. compareTo is assumed
 *            to return a negative number of the object comparing is smaller than the object it
 *            is being compared to.
 */
public class MyPriorityQueue <T extends Comparable<T>>
{
    List<T> prio;
    int size;

    /**
     * Creates a new priorityQueue.
     */
    public MyPriorityQueue()
    {
        prio = new ArrayList<T>();
    }

    /**
     * Adds a new element to the queue.
     * 
     * The element is added to the end and then bubbles up to it's proper priority in the
     * queue.
     * 
     * @param element The element to add.
     */
    public void add(T element)
    {
        prio.add(size, element);
        bubbleUp(size++);

        // Make sure the heap invariant is maintained.
        if (hasLeftChild(0))
            assert prio.get(0).compareTo(prio.get(leftChildOf(0))) < 0;
        if (hasRightChild(0))
            assert prio.get(0).compareTo(prio.get(rightChildOf(0))) < 0;

    }

    /**
     * Removes and returns the item in the queue with the most priority. Brings the next item
     * up to be removed.
     * 
     * This is accomplished by creating a reference to the top most (highest priority) element
     * and then swapping it to the place of the lowest priority element. It is then removed.
     * Since the lowest priority element is now out of place at the top of the queue, it is
     * allowed to sink until it reaches where it is supposed to be in priority.
     */
    public T remove()
    {
        T temp = getRoot();
        swapIndexes(0, --size);
        prio.remove(size);
        int test = sink();

        // Make sure the heap invariant is maintained.
        if (hasParent(test))
            assert prio.get(parentOf(test)).compareTo(prio.get(test)) < 0;
        if (hasLeftChild(test))
            assert prio.get(test).compareTo(prio.get(leftChildOf(test))) < 0;
        if (hasRightChild(test))
            assert prio.get(test).compareTo(prio.get(rightChildOf(test))) < 0;

        return temp;
    }

    /**
     * Causes the index passed to rise up to its priority.
     * 
     * @param index The index to bubbleUp.
     */
    private void bubbleUp(int index)
    {
        // as long as you have a parent and you're of a higher priority than your parent
        while (hasParent(index) && prio.get(index).compareTo(prio.get(parentOf(index))) < 0)
        {
            swapIndexes(index, parentOf(index));
            index = parentOf(index);
        }
    }

    /**
     * Sinks the element at the highest priority until it reaches their actual priority.
     */
    private int sink()
    {
        int index = 0;
        while (hasLeftChild(index))
        {
            int smallerChild = childToPromote(index);
            if (prio.get(index).compareTo(prio.get(smallerChild)) >= 0)
                swapIndexes(index, smallerChild);
            else
                return index; // it's sunk as low as it needs to
            index = smallerChild;
        }
        return index;
    }

    /**
     * Swaps the indexes in a priority queue.
     *
     * @param index the first index.
     * @param swap the index it's swapping with.
     */
    private void swapIndexes(int index, int swap)
    {
        T temp = (T) prio.get(index);
        prio.set(index, prio.get(swap));
        prio.set(swap, temp);
    }

    /**
     * Returns the index of a child element that is of a higher priority than the other child
     * element.
     * 
     * @param index The index that has child to test against each other.
     * @return The index of the winner.
     */
    private int childToPromote(int index)
    {
        if (!hasRightChild(index))
            return leftChildOf(index);
        else
            return (prio.get(leftChildOf(index)).compareTo(prio.get(rightChildOf(index)))) <= 0 ? leftChildOf(index)
                    : rightChildOf(index);
    }

    /**
     * Get the index of the left child of the index you passed to it. Does not guarantee that
     * the index exists in the list.
     *
     * @param index the index to check.
     * @return The index that the left child is located at.
     */
    private int leftChildOf(int index)
    {
        return (2 * index) + 1;
    }

    /**
     * Get the index of the right child of the index you passed to it. Does not guarantee that
     * the index exists in the list.
     *
     * @param index the index to check.
     * @return The index that the right child is located at.
     */
    private int rightChildOf(int index)
    {
        return (2 * index) + 2;
    }

    /**
     * Returns whether or not an element has a child to it's left. Elements are defined as
     * having a left child if the index equal to twice their index, plus 1, exists in the
     * priority queue.
     * 
     * @param index The index to check.
     * @return Whether or not the node has a left child.
     */
    private boolean hasLeftChild(int index)
    {
        return leftChildOf(index) < size;
    }

    /**
     * Returns whether or not an element has a child to it's right. Elements are defined as
     * having a left right if the index equal to twice their index, plus 2, exists in the
     * priority queue.
     * 
     * @param index The index to check.
     * @return Whether or not the node has a right child.
     */
    private boolean hasRightChild(int index)
    {
        return rightChildOf(index) < size;
    }

    /**
     * Get the index of the parent of the index you passed to it. Does not guarantee that the
     * index exists in the list.
     *
     * @param index the index to check.
     * @return The index that the parent is located at.
     */
    private int parentOf(int index)
    {
        return (index - 1) / 2;
    }

    /**
     * Checks to see if the priority queue is empty.
     *
     * @return Returns true if there is nothing in the queue, false otherwise.
     */
    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Returns how many objects are currently in the queue.
     *
     * @return Size of the queue.
     */
    public int size()
    {
        return size;
    }

    /**
     * Gives a reference to the first object in the queue.
     *
     * @return The element in the queue with the most priority.
     */
    private T getRoot()
    {
        return (T) prio.get(0);
    }

    private boolean hasParent(int index)
    {
        return index > 0; // only the root does not have a parent.
    }

    public String toString()
    {
        StringBuilder tempString = new StringBuilder();
        for (T element : prio)
        {
            tempString.append(element.toString());
        }
        return tempString.toString();
    }
}
