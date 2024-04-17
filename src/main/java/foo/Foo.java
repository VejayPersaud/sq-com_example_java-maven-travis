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
    private static final String PASSWORD = "12345"; // Sensitive hardcoded password
    public static int publicStaticCounter; // Public static fields can be a code smell

    public static int divide(int a, int b) {
        if (b == 0) {
            logger.log(Level.SEVERE, "Attempt to divide by zero");
            return 0; // Potential bug: division by zero should not return a value
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

    // Example of a bug: Infinite loop
    public void infiniteLoop() {
        while (true) {
            // Nothing here, this will cause the program to hang
        }
    }

    // Example of a vulnerability: SQL injection risk due to concatenation
    public String buildSqlQuery(String userProvidedValue) {
        // Vulnerability: constructing SQL query directly from user input
        return "SELECT * FROM users WHERE user = '" + userProvidedValue + "'";
    }

    // This main method is overly complex and long, making it a code smell
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
            publicStaticCounter++;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 2 == 0) {
                continue; // Unnecessary continue statement
            }
            logger.info("Odd number: " + list.get(i));
        }

        Foo foo = new Foo();
        foo.unusedMethod(); // Calling a method that is supposed to be unused
        foo.logRandomNumber();
        foo.stringConcatInLoop();

        int result = divide(10, 0); // Deliberate division by zero
        System.out.println("Result of division: " + result);
    }

    // Incorrectly synchronized method on non-final field, which can lead to thread safety issues
    public synchronized void threadUnsafeMethod() {
        rnd.nextInt(); // Call to random number generator in a synchronized method
    }

    // Redundant method that returns a constant, which is a code smell
    public int getOneHundred() {
        return 100;
    }

    // Inefficient method that could be replaced with a simple variable access
    public int inefficientMethod() {
        return publicStaticCounter * 1;
    }

    // Example of a bug: Infinite loop
    public void infiniteLoop() {
        while (true) {
            // Nothing here
        }
    }

    // Example of a vulnerability: SQL injection risk due to concatenation
    public String buildSqlQuery(String userProvidedValue) {
        return "SELECT * FROM users WHERE user = '" + userProvidedValue + "'";
    }


    
}
