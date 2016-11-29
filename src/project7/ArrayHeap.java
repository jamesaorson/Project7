package project7;

import java.util.Random;

/**
  * Provides an ArrayHeap object which maintains the heap order property.
  *
  * @author James Osborne
  * @version 1.0 
  * File: ArrayHeap.java
  * Created:  15 Nov 2016
  * ©Copyright James Osborne. All rights reserved.
  * Summary of Modifications:
  *     15 Nov 2016 – JAO – Created bubbleUp(), bubbleDown(), and swapItems().
  *     Got these methods working correctly, following given pseudocode and
  *     testing most or all edge cases, more testing needed for confidence.
  *     16 Nov 2016 - JAO - Fixed some efficiency and arbitrary checking issues
  *     within the bubbleUp() method.
  * 
  * Description: This ArrayHeap uses an array to store Item objects, having a 
  * key and a value. The purpose of this implementation is to show how an array
  * can be used efficiently to implement a heap and maintain it's heap order 
  * property, doing inserts and removes in nlog(n) time.
  */

public class ArrayHeap extends ArrayBinaryTree implements Heap {
    Comparator heapComp;

    public ArrayHeap(Comparator newComp) {
        this (newComp, DEFAULT_SIZE);
    }

    public ArrayHeap(Comparator newComp, int newSize) {
        super (newSize);
        heapComp = newComp;
    }
    
    // you may want to expand main; it is just provided as a sample
    public static void main (String[] args) {
	Comparator myComp = new IntegerComparator();
        Heap myHeap = new ArrayHeap (myComp, 8);
        int key;
        int value;
        Random random = new Random();
        
        for (int i = 0; i < 10000; ++i) {
            key = random.nextInt(10000);
            value = random.nextInt(10000);
            
            myHeap.add(new Integer(key), new Integer(value));
        }

        System.out.println(myHeap.size());
        
        Item removedItem;
        int prevKey = -1;
        
        while (!myHeap.isEmpty()) {
            removedItem = (Item) myHeap.removeRoot();
            System.out.print("Key:   " + removedItem.key() + "\t");
            System.out.println("Removed " + removedItem.element());
            
            if (prevKey > (int) removedItem.key()) {
                System.out.println("Heap order not satisfied");
                break;
            }
            
            prevKey = (int) removedItem.key();
        }
    }

    @Override
    /**
      * Adds a new Item to the heap with the specified key and value.
      * @param newKey is the key relevant to the new Item being added.
      * @param newElement is the value which goes with the corresponding key.
      * @return the Position of the Item that was added to the heap.
      */
    public Position add(Object newKey, Object newElement)
                        throws InvalidObjectException {
        //Uses comparator to be certain the key is of the correct type.
        //In this case, an Integer.
        if (!heapComp.isComparable(newKey)) {
            throw new InvalidObjectException("Key is of invalid type");
        }
        //Same check as with the key. The newElement should also be an Integer.
        if (!heapComp.isComparable(newElement)) {
            throw new InvalidObjectException("Value is of invalid type");
        }
        
        //New item to be added to the heap.
        Item newItem = new Item(newKey, newElement);
        
        //If the array is full, this expandsit, doubling the capacity.
        if (btArray.length == size) {
            //size << 1 is using a bitshift to multiply by 2.
            ArrayPosition[] newArray = new ArrayPosition[size << 1];
            
            //Copy old elements to newArray
            for (int i = 0; i < size; ++i) {
                newArray[i] = btArray[i];
            }
            
            //Assign new array to heap's array.
            btArray = newArray;
        }
        
        //Add newItem to the end of the array to prepare for bubbleUp()
        btArray[size] = new ArrayPosition(size, newItem);
        
        //I post-increment here because I need to correct new size for the
        //bubbleUp() algorithm, but I need the correct array position, which
        //requires size to be one less for indexing.
        bubbleUp(btArray[size++]);
        
        return btArray[size - 1];
    }

    @Override
    /**
      * Removes root Position and returns it as a Position.
      * @return the root Position.
      */
    public Object removeRoot() throws EmptyHeapException {
        if (size == 0) {
            throw new EmptyHeapException("Heap is empty");
        }
        
        ArrayPosition root = btArray[0];
        Item rootItem = (Item) root.element();
        Item result = new Item(rootItem.key(), rootItem.element());
        Item lastItem = (Item) btArray[size - 1].element();
        
        //Swaps rootItem and lastItem to prepare for bubbleDown().
        swapItems(rootItem, lastItem);
        btArray[--size] = null;
        
        bubbleDown();
        
        return result;
    }
    
    /**
      * Goes up the tree from the inserted Position after an add() until 
      * the heap order is satisfied.
      * @param pos is the position you are bubbling up from. 
      */
    private void bubbleUp(ArrayPosition pos) {
        ArrayPosition root = btArray[0];
        ArrayPosition parent = (ArrayPosition) parent(pos);
        Item posItem = (Item) pos.element();
        
        //Loop through the bubbleUp) algorithm as long as we have not bubbled
        //all the way up to the root, where it is necessarily the smallest Item,
        //or until it is larger than its parent, satisfying heap order.
        while (pos != root && heapComp.isLessThan((int) posItem.key(), 
                            (int) ((Item) parent.element()).key())) {
            //Swap pos with its parent since it has proven to be smaller.
            swapItems(posItem, (Item) parent.element());
            
            //Reassign variables since pos has moved in the heap.
            pos = (ArrayPosition) parent(pos);
            posItem = (Item) pos.element();
            parent = (ArrayPosition) parent(pos);
        }
    }
    
    /**
      * Goes down the tree from the root after a remove()
      * until the heap order is satisfied.
      */
    private void bubbleDown() {
        ArrayPosition pos = btArray[0];
        ArrayPosition smallest = pos;
        ArrayPosition leftChild;
        ArrayPosition rightChild;
        Item posItem;
        Item smallestItem;
        Item leftItem;
        Item rightItem;

        //Loops until heap property is satisfied and I set pos equal to null.
        while (pos != null) {
            //If pos has children, it can continue to bubbleDown().
            if (isInternal(pos)) {
                leftChild = (ArrayPosition) leftChild(pos);
                rightChild = (ArrayPosition) rightChild(pos);
                posItem = (Item) pos.element();
                smallestItem = (Item) smallest.element();
                leftItem = (Item) leftChild.element();
                
                //If rightChild is null, pos would only have a leftChild.
                if (rightChild == null) {
                    if (heapComp.isLessThan((int) leftItem.key(),
                                            (int) smallestItem.key())) {
                        smallest = leftChild;
                        smallestItem = leftItem;
                    }
                }
                //Otherwise, pos has both children and must check as such.
                else {
                    rightItem = (Item) rightChild.element();
                    
                    //Compare left and right children to find who is smaller.
                    if (heapComp.isLessThan((int) leftItem.key(), 
                                            (int) rightItem.key())) {
                        smallest = leftChild;
                        smallestItem = leftItem;
                    }
                    else {
                        smallest = rightChild;
                        smallestItem = rightItem;
                    }
                }
                //If pos is not the smallest of itself and its children, must 
                //swap pos with smallest to continue bubbling down.
                if (heapComp.isLessThan(smallestItem.key(), posItem.key())) {
                    swapItems(smallestItem, posItem);
                    pos = smallest;
                }
                //Otherwise, set pos equal to null to end bubbleDown().
                else {
                    pos = null;
                }
            }
            //If pos has no children, set pos equal to null to end bubbleDown().
            else {
                pos = null;
            }
        }
    }
    
    /**
      * @param item1 is the first of the items to be swapped.
      * @param item2 is the second of the items to be swapped.
      */
    private void swapItems(Item item1, Item item2) {
        //Must store both key and value rather than object reference.
        int tempKey = (int) item1.key();
        int tempValue = (int) item1.element();
        
        //Rather than swapping pointers, I must swap the data inside of the
        //objects, avoiding errors that would occur if temp was a pointer.
        item1.setKey(item2.key());
        item1.setElement(item2.element());
        
        item2.setKey(tempKey);
        item2.setElement(tempValue);
    }
}