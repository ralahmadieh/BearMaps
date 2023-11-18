package bearmaps.utils.pq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/* A MinHeap class of Comparable elements backed by an ArrayList. */
public class MinHeap<E extends Comparable<E>> {

    /* An ArrayList that stores the elements in this MinHeap. */
    private ArrayList<E> contents;
    private HashMap<E, Integer> indexOfContents;
    private int size;

    /* Initializes an empty MinHeap. */
    public MinHeap() {
        contents = new ArrayList<>();
        indexOfContents = new HashMap<>();
        contents.add(null);
    }

    /* Returns the element at index INDEX, and null if it is out of bounds. */
    private E getElement(int index) {
        if (index >= contents.size()) {
            return null;
        } else {
            return contents.get(index);
        }
    }

    /* Sets the element at index INDEX to ELEMENT. If the ArrayList is not big
       enough, add elements until it is the right size. */
    private void setElement(int index, E element) {
        while (index >= contents.size()) {
            contents.add(null);
        }
        contents.set(index, element);
        indexOfContents.put(element, index);
    }

    /* Swaps the elements at the two indices. */
    private void swap(int index1, int index2) {
        E element1 = getElement(index1);
        E element2 = getElement(index2);
        setElement(index2, element1);
        setElement(index1, element2);
    }

    /* Prints out the underlying heap sideways. Use for debugging. */
    @Override
    public String toString() {
        return toStringHelper(1, "");
    }

    /* Recursive helper method for toString. */
    private String toStringHelper(int index, String soFar) {
        if (getElement(index) == null) {
            return "";
        } else {
            String toReturn = "";
            int rightChild = getRightOf(index);
            toReturn += toStringHelper(rightChild, "        " + soFar);
            if (getElement(rightChild) != null) {
                toReturn += soFar + "    /";
            }
            toReturn += "\n" + soFar + getElement(index) + "\n";
            int leftChild = getLeftOf(index);
            if (getElement(leftChild) != null) {
                toReturn += soFar + "    \\";
            }
            toReturn += toStringHelper(leftChild, "        " + soFar);
            return toReturn;
        }
    }

    /* Returns the index of the left child of the element at index INDEX. */
    private int getLeftOf(int index) {
        return (2 * index);
    }

    /* Returns the index of the right child of the element at index INDEX. */
    private int getRightOf(int index) {
        return  ((2*index) + 1);
    }

    /* Returns the index of the parent of the element at index INDEX. */
    private int getParentOf(int index) {
        if (index == 1) {
            return 1;
        }
        if (index % 2 == 0) {
            return  (index / 2);
        }
        return (index - 1) / 2;
    }

    /* Returns the index of the smaller element. At least one index has a
       non-null element. If the elements are equal, return either index. */
    private int min(int index1, int index2) {
        E elem1 = getElement(index1);
        E elem2 = getElement(index2);
        if (elem1 == null) {
            return index2;
        }
        if (elem2 == null) {
            return index1;
        }
        if (elem1.compareTo(elem2) < 0) {
            return index1;
        }
        else {
            return index2;
        }
    }

    /* Returns but does not remove the smallest element in the MinHeap. */
    public E findMin() {
        return getElement(1);
    }

    /* Bubbles up the element currently at index INDEX. */
    public void bubbleUp(int index) {
        int parentIndex = getParentOf(index);
        if (parentIndex != 0) {
            if (contents.get(parentIndex).compareTo(contents.get(index)) > 0) {
                swap(index, parentIndex);
                bubbleUp(parentIndex);
            }
        }
    }

    /* Bubbles down the element currently at index INDEX. */
    private void bubbleDown(int index) {
        int leftChildIndex = getLeftOf(index);
        int rightChildIndex = getRightOf(index);
        if (leftChildIndex >= contents.size()) {
            return;
        } else {
            int child = min(leftChildIndex, rightChildIndex);
            if (contents.get(child).compareTo(contents.get(index)) < 0) {
                swap(index, child);
                bubbleDown(child);
            }
        }
    }

    /* Returns the number of elements in the MinHeap. */
    public int size() {
        return size;
    }

    /* Inserts ELEMENT into the MinHeap. If ELEMENT is already in the MinHeap,
       throw an IllegalArgumentException.*/
    public void insert(E element) {
        if (contains(element)) {
            throw new IllegalArgumentException("Element already exists");
        }
        contents.add(element);
        indexOfContents.put(element, contents.size() - 1);
        bubbleUp(contents.size() - 1);
        size++;
    }

    /* Returns and removes the smallest element in the MinHeap. */
    public E removeMin() {
        E minValue = getElement(1);
        swap(1, contents.size() - 1);
        contents.remove(contents.size() - 1);
        indexOfContents.remove(minValue);
        size--;
        bubbleDown(1);
        return minValue;
    }

    /* Replaces and updates the position of ELEMENT inside the MinHeap, which
       may have been mutated since the initial insert. If a copy of ELEMENT does
       not exist in the MinHeap, throw a NoSuchElementException. Item equality
       should be checked using .equals(), not ==. */
    public void update(E element) {
        if (!contains(element)) {
            throw new NoSuchElementException();
        }
        int indexOfelem = indexOfContents.get(element);
        setElement(indexOfelem, element);
        bubbleUp(indexOfelem);
        bubbleDown(indexOfelem);
    }

    /* Returns true if ELEMENT is contained in the MinHeap. Item equality should
       be checked using .equals(), not ==. */
    public boolean contains(E element) {
        return indexOfContents.containsKey(element);
    }
}