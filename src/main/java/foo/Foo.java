package foo;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Foo class with potential issues for static analysis
 */
public class Foo {

    private static Logger logger = Logger.getLogger(Foo.class.getName());
    private Random rnd = new Random();

    public static int divide(int a, int b) {
        if (b == 0) {
            logger.log(Level.SEVERE, "Attempt to divide by zero");
            return 0; //bug: division by zero should not return a value
        }
        return a / b;
    }

    public void unusedMethod() {
        //Code smell: method is never used
        System.out.println("This method is never used");
    }

    public void logRandomNumber() {
        //Vulnerability: predictable pseudorandom number generator
        System.out.println("Random number: " + rnd.nextInt());
    }

    public void stringConcatInLoop() {
        String s = "";
        for (int i = 0; i < 10; i++) {
            //Code smell: using string concatenation in a loop
            s += i + " ";
        }
        logger.info(s);
    }

    //code smell: public variable should be private with a getter method
    public int publicInt = 10;

    //bug: equals method should check the object type before casting
    public boolean equals(Object obj) {
        Foo other = (Foo) obj;
        return this.publicInt == other.publicInt;
    }

    //Vulnerability: Hardcoded password in source code
    private String password = "admin123";

    //Bug: Unchecked exception in method signature
    public static int divideWithException(int a, int b) throws RuntimeException {
        if (b == 0) {
            throw new RuntimeException("Can't divide by zero!");
        }
        return a / b;
    }

    //Bug: potential null pointer dereference
    public void printLength(String str) {
        if (str.equals("test")) {
            System.out.println("It's a test string");
        }
        System.out.println("String length: " + str.length());
    }

    //Code smell: misleading method name, should be addNumbers
    public int subtractNumbers(int a, int b) {
        return a + b;
    }
}
