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

public class InvalidObjectException extends RuntimeException {

    public InvalidObjectException(String errorMsg) {
        super (errorMsg);
    }
}
