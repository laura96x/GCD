import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Laura Phan on 09/12/17.
 * Class: MATH 1165-090
 * Project 1: The Greatest Common Divisor [GCD]
 */
public class Project1 {

    public static void main(String[] args) {

        //Directions: Implement the Euclidean Algorithm to compute the greatest common divisor of any
        //two integers. These two integers should be able to be positive, negative, zero, or any combination
        //thereof. (Hint: be careful when dealing with zero and negative numbers.)
        //Input: 2 integers
        //Output: 1 integer (ie: the gcd of the given integers)
        //Extra Credit: Find the linear combination of the 2 given integers which sums to their gcd.

        // Get two numbers and absolute value them
        Scanner input = new Scanner(System.in);
        System.out.print("Enter first number: ");
        int og1 = input.nextInt();                  // og = original
        int og1Pos = Math.abs(og1);
        System.out.print("Enter second number: ");
        int og2 = input.nextInt();
        int og2Pos = Math.abs(og2);

        int gcd = 0;
        int sign1 = 0, sign2 = 0;       // check whether the signs have changed, 0 = not changed
        if (og1 != og1Pos) {
            sign1 = 1;
        }
        if (og2 != og2Pos) {
            sign2 = 1;
        }

        // Put bigger number first
        int big, small;
        int flip = 0;                   // check whether numbers have been flipped, 0 = not flipped
        if (og1Pos < og2Pos) {
            big = og2Pos;
            small = og1Pos;
            flip = 1;
        } else {
            big = og1Pos;
            small = og2Pos;
        }

        // Create arraylists
        ArrayList<Integer> qList = new ArrayList<>();       // contains the quotient
        ArrayList<Integer> rList = new ArrayList<>();       // contains the remainder
        ArrayList<Integer> bigList = new ArrayList<>();     // contains the bigger number
        ArrayList<Integer> smallList = new ArrayList<>();   // contains the smaller number

        bigList.add(big);
        smallList.add(small);

        // Implement the Euclidean Algorithm
        int q = 0;
        int oldR = small, newR = 1;
        int newBig = big;
        int newSmall = small;

        if(small == 0){
            if (big == 0) {
                System.out.println("\ngcd(" + og1 + ", " + og2 + ") = undefined");
            } else {
                gcd = big;
                System.out.println("\ngcd(" + og1 + ", " + og2 + ") = " + gcd);
            }
        } else {
            do {
                q = newBig / newSmall;
                qList.add(-q);
                newR = newBig % newSmall;
                rList.add(newR);
                if (newR == 1) {
                    gcd = newR;
                    System.out.println("\ngcd(" + og1 + ", " + og2 + ") = " + gcd);
                    break;
                } else if (newR == 0) {
                    gcd = oldR;
                    System.out.println("\ngcd(" + og1 + ", " + og2 + ") = " + gcd);
                    break;
                }
                newBig = newSmall;
                bigList.add(newBig);
                newSmall = newR;
                smallList.add(newSmall);
                oldR = newR;
            } while (newR != -1);


            // Display lists
            System.out.println("bigList:");
            for (int item : bigList) {
                System.out.println(item);
            }
            System.out.println("-----------");
            System.out.println("smallList:");
            for (int item : smallList) {
                System.out.println(item);
            }
            System.out.println("-----------");
            System.out.println("qList:");
            for (int item : qList) {
                System.out.println(item);
            }
            System.out.println("-----------");
            System.out.println("rList:");
            for (int item : rList) {
                System.out.println(item);
            }
            System.out.println("-----------");

            if (og1 != og2) {
                if (rList.get(rList.size()-1) == 0 && rList.size() > 1) {
                    // remove the last row if it has a zero remainder AND if it has more than one row
                    bigList.remove(bigList.size()-1);
                    smallList.remove(smallList.size()-1);
                    qList.remove(qList.size()-1);
                    rList.remove(rList.size()-1);
                }
            }
        }

        // Implement backwards substitution
        int currentBig, nextBig = 0;            // 'current' starts on last row of the arrays
        int nextSmall = 0;                      // 'next' refers to the terms on the previous rows
        int currentQ, nextQ = 0;
        int a1 = 0, a2 = 1, b1 = 0, b2 = 0;     // a2 = u, b2 = v (most of the time)

        if (big != 0 && small != 0){ // check whether gcd was undefined
            if (rList.size() > 1){ // check the sizes of the arrays, didn't have to be rList

                currentQ = qList.get(qList.size()-1);

                for (int i = 1; i < rList.size(); i++){ // Start in last row and go back

                    currentBig = bigList.get(bigList.size()-i);

                    if ( i != rList.size()) {
                        nextBig = bigList.get(bigList.size()-i-1);
                        nextSmall = smallList.get(smallList.size()-i-1);
                        nextQ = qList.get(qList.size()-i-1);
                    }


                    System.out.println("currentBig: "+currentBig);
                    System.out.println("currentQ: "+currentQ);

                    System.out.println("nextBig: "+nextBig);
                    System.out.println("nextSmall: "+nextSmall);
                    System.out.println("nextQ: "+nextQ);

                    b1 = currentBig;
                    b2 = (a2*b1 + nextSmall*(currentQ)*(nextQ)) / b1;
                    //b2 = (1 + (currentQ)*(nextQ));


                    System.out.println("b1: "+b1);
                    System.out.println("b2: "+b2);

                    a1 = nextBig;
                    a2 = currentQ;


                    System.out.println("a1: "+a1);
                    System.out.println("a2: "+a2);

                    currentQ = b2;
                    System.out.println("-----------");
                }
            } else {
                a1 = bigList.get(0);
                a2 = 1;
                b1 = smallList.get(0);
                b2 = -(a1 - 1)/b1;
            }

            // Negate u and v under certain conditions
            // I don't know why some of it works but it works
            if (flip == 0) // not flipped
            {
                if (sign1 == 1) { // if only first is negative
                    a2 = -a2;
                }
                if (sign2 == 1) { // if only second is negative
                    b2 = -b2;
                }
            } else { // flipped
                if (sign1 == 1 && sign2 == 1) { // if both are negative
                    a2 = -a2;
                    b2 = -b2;
                } else if (sign1 == 1) { // if only first is negative
                    b2 = -b2;
                } else if (sign2 == 1) { // if only second is negative
                    a2 = -a2;
                }
            }

            // Display linear combination without the values of u and v
            System.out.println("\nLinear Combination:");
            System.out.println("gcd(" + og1 + ", " + og2 + ") = "+ gcd +" = (" + og1 + ")u + (" + og2 + ")v");

            // Check if numbers were flipped and display the values of u and v
            if (flip == 0) { // was not flipped
                System.out.println("u = " + a2);
                System.out.println("v = " + b2);
                /*
                System.out.println("-----------");
                System.out.println(gcd + " = " + (og1*a2+og2*b2)); // math check
                */
            } else { // was flipped
                System.out.println("u = " + b2);
                System.out.println("v = " + a2);
                /*
                System.out.println("-----------");
                System.out.println(gcd + " = " + (og2*a2+og1*b2)); // math check
                */
            }
        } else if (big != 0) { // when one of the numbers is zero
            System.out.println("\nLinear Combination:");
            System.out.println("gcd(" + og1 + ", " + og2 + ") = " + gcd + " = (" + og1 + ")u + (" + og2 + ")v");

            if (og1 == 0) {
                a2 = 1;
                b2 = gcd/og2;
            } else {
                b2 = 1;
                a2 = gcd/og1;
            }
            System.out.println("u = " + a2);
            System.out.println("v = " + b2);
        }
    }
}