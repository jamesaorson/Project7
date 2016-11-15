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

public interface Heap extends BinaryTree {
  // may want to throw FullHeapException unless an extensible array used
  public Position add(Object newKey, Object newElement) throws InvalidObjectException;

  public Object removeRoot() throws EmptyHeapException;
}
