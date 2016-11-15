package project7;

/**
  * What does it do?
  *
  * @author James Osborne
  * @version 1.0 
  * File: ArrayHeap.java
  * Created:  15 Nov 2016
  * ©Copyright James Osborne. All rights reserved.
  * Summary of Modifications:
  *     XX month XXXX – JAO – 
  * 
  * Description: 
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

        myHeap.add(new Integer(14),new Integer(14));
        myHeap.add(new Integer(17),new Integer(17));
        myHeap.add(new Integer(3),new Integer(3));
        myHeap.add(new Integer(2),new Integer(21));
        myHeap.add(new Integer(8),new Integer(8));
        myHeap.add(new Integer(7),new Integer(18));
        myHeap.add(new Integer(1),new Integer(1));

        System.out.println(myHeap.size());
        
        while (!myHeap.isEmpty()) {
            Item removedItem = (Item) myHeap.removeRoot();
            System.out.print("Key:   " + removedItem.key() + "     ");
            System.out.println("Removed " + removedItem.element());
        }
        
        System.out.println("All nodes removed");
    }

    @Override
    /**
      * 
      */
    public Position add(Object newKey, Object newElement) throws InvalidObjectException {
        if (!heapComp.isComparable(newKey)) {
            throw new InvalidObjectException("Key is of invalid type");
        }
        if (!heapComp.isComparable(newElement)) {
            throw new InvalidObjectException("Value is of invalid type");
        }
        
        Item insertItem = new Item(newKey, newElement);
        
        if (btArray.length == size) {
            ArrayPosition[] newArray = new ArrayPosition[size << 1];
            
            for (int i = 0; i < size; ++i) {
                newArray[i] = btArray[i];
            }
            
            btArray = newArray;
        }
        
        btArray[size] = new ArrayPosition(size, insertItem);
        ++size;
        
        bubbleUp(btArray[size - 1]);
        
        return null;
    }

    @Override
    /**
      * 
      */
    public Object removeRoot() throws EmptyHeapException {
        if (size == 0) {
            throw new EmptyHeapException("Heap is empty");
        }
        
        ArrayPosition root = btArray[0];
        Item rootItem = (Item) root.element();
        Item result = new Item(rootItem.key(), rootItem.element());
        Item lastItem = (Item) btArray[size - 1].element();
        
        swapElements(rootItem, lastItem);
        btArray[size - 1] = null;
        --size;
        
        bubbleDown();
        
        return result;
    }
    
    /**
      * 
      */
    private void bubbleUp(ArrayPosition pos) {
        ArrayPosition root = btArray[0];
        ArrayPosition parent;
        Item posItem;
        Item parentItem;
        
        //It is necessary to check if pos is the root before dereferencing
        //pos's parent pointer.
        if (pos != root) {
            //If index is even, position is the rightChild.
            if ((pos.getIndex() & 1) == 0) {
                parent = btArray[(pos.getIndex() >> 1) - 1];
            }
            //If index is odd, position is the leftChild.
            else {
                parent = btArray[(pos.getIndex() - 1) >> 1];
            }

            posItem = (Item) pos.element();
            parentItem = (Item) parent.element();

            while (pos != root && heapComp.isLessThan((int) posItem.key(), 
                                                      (int) parentItem.key())) {
                swapElements(posItem, parentItem);
                pos = (ArrayPosition) parent(pos);
                
                posItem = (Item) pos.element();
                
                //After swapping pos with it's parent, I must check if it is now
                //the root before dereferencing pos's parent pointer.
                if (pos != root) {
                    parentItem = (Item) parent(pos).element();
                }
            }
        }
    }
    
    /**
      * 
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

        while (pos != null) {
            if (isInternal(pos)) {
                leftChild = (ArrayPosition) leftChild(pos);
                rightChild = (ArrayPosition) rightChild(pos);
                posItem = (Item) pos.element();
                smallestItem = (Item) smallest.element();
                leftItem = (Item) leftChild.element();
                
                if (rightChild == null) {
                    if (heapComp.isLessThan((int) leftItem.key(),
                                            (int) smallestItem.key())) {
                        smallest = leftChild;
                        smallestItem = leftItem;
                    }
                }
                else {
                    rightItem = (Item) rightChild.element();
                    
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
                if (heapComp.isLessThan(smallestItem.key(), posItem.key())) {
                    swapElements(smallestItem, posItem);
                    pos = smallest;
                }
                else {
                    pos = null;
                }
            }
            else {
                pos = null;
            }
        }
    }
    
    /**
      * 
      */
    private void swapElements(Item item1, Item item2) {
        int tempKey = (int) item1.key();
        int tempValue = (int) item1.element();
        
        item1.setKey(item2.key());
        item1.setElement(item2.element());
        
        item2.setKey(tempKey);
        item2.setElement(tempValue);
    }
}