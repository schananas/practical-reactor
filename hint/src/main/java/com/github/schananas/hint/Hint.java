package com.github.schananas.hint;

/**
 * @author Stefan Dragisic
 */
public interface Hint {

   default void printHint(String hint) {
       System.out.println("\n\n\n");
       System.out.println("\n\n\n");
       System.out.println(hint);
       System.out.println("\n\n\n");
       System.out.println("\n\n\n");
   }

}
