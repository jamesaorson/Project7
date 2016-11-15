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

public interface Comparator {

    public boolean isLessThan (Object obj1, Object obj2) 
			throws InvalidObjectException;

    public boolean isLessThanOrEqualTo (Object obj1, Object obj2) 
			throws InvalidObjectException;

    public boolean isGreaterThan (Object obj1, Object obj2) 
			throws InvalidObjectException;

    public boolean isGreaterThanOrEqualTo (Object obj1, Object obj2) 
			throws InvalidObjectException;

    public boolean isEqual (Object obj1, Object obj2) 
			throws InvalidObjectException;

    public boolean isComparable (Object obj);
}