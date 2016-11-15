package project7;

/**
  * What does it do?
  *
  * @author James Osborne
  * @version 1.0 
  * File: <filename>
  * Created:  <current date>
  * ©Copyright James Osborne. All rights reserved.
  * Summary of Modifications:
  *     XX month XXXX – JAO – 
  * 
  * Description: 
  */

public class ArrayPosition implements Position {

    private int index;
        private Object element;

    public ArrayPosition() {
        this (-1, null);
    }

    public ArrayPosition(int newIndex, Object newElement) {
        index = newIndex;
        element = newElement;
    }

    public Object element () {
        return element;
    }

    public void setElement (Object newElement) {
        element = newElement;
    }

    public int getIndex () {
        return index;
    }

    public void setIndex (int newIndex) {
        index = newIndex;
    }
}