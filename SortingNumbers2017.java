/******************************************************************************
 * Author:         Janahan Dhushenthen                                        *
 * Date:           May 8, 2017                                                *
 * Description:    Sorts a list of phone numbers using various methods        *
 ******************************************************************************/

import java.util.*;
import java.io.*;

class SortingNumbers2017 {

    public static int count;

    /**************************************************************************
     *                                                                        *
     *                              MAIN METHOD                               *
     *                                                                        *
     **************************************************************************/

    public static void main(String[] args) throws Exception {
        char choice = ' ';
        Scanner sc = new Scanner(System.in);

        // read in the phone numbers from file
        ArrayList<Long> list = readFile("phoneNumsList.txt");
        System.out.println("Phone nums:\n" + list);
        while (choice != 'E' && choice != 'e') {

            // print the menu onto console
            printMenu();
            choice = sc.nextLine().charAt(0);

            // call sort technique based on input from menu
            switch (choice) {
                case 'b':
                case 'B': // bubble
                    System.out.println("Bubble sort: \n" + bubbleSort(list) + "\nNumber of swaps: " + count);
                    list = readFile("phoneNumsList.txt");
                    break;
                case 'f':
                case 'F': // binary search
                    System.out.println("Binary search:");
                    binarySearch(radixSort(list));
                    list = readFile("phoneNumsList.txt");
                    break;
                case 'i':
                case 'I': // insertion
                    System.out.println("Insertion sort: \n" + insertionSort(list) + "\nNumber of swaps: " + count);
                    list = readFile("phoneNumsList.txt");
                    break;
                case 'm':
                case 'M': // merge
                    count = 0;
                    System.out.println("Recursive merge sort: \n" + recursiveMergeSort(list) + "\nNumber of merges: " + count);
                    list = readFile("phoneNumsList.txt");
                    count = 0;
                    System.out.println("Merge sort (without recursion): \n" + mergeSort(list) + "\nNumber of merges: " + count);
                    list = readFile("phoneNumsList.txt");
                    break;
                case 'r':
                case 'R': // radix
                    System.out.println("Radix sort: \n" + radixSort(list) + "\nNumber of moves: " + count);
                    list = readFile("phoneNumsList.txt");
                    break;
                case 's':
                case 'S': // selection
                    System.out.println("Selection sort: \n" + selectionSort(list) + "\nNumber of swaps: " + count);
                    list = readFile("phoneNumsList.txt");
                    break;
            }
        }


    }

    /**************************************************************************
     *                                                                        *
     *                            READFILE METHOD                             *
     *                                                                        *
     **************************************************************************/

    public static ArrayList<Long> readFile(String fileName) {
        ArrayList<Long> list = new ArrayList<>();
        try {
            Scanner s = new Scanner(new File(fileName));

            // adds next phone number to list
            while (s.hasNextLong()) {
                list.add(s.nextLong());
            }
        } catch (IOException iox) {
            System.out.println("Problem reading " + fileName);
        } catch (NoSuchElementException nsee) {
            System.out.println("No such element exception in reading " + fileName);
        }
        return list;
    }

    /**************************************************************************
     *                                                                        *
     *                            PRINTMENU METHOD                            *
     *                                                                        *
     **************************************************************************/

    public static void printMenu() {
        System.out.println("Press B for bubble sort");
        System.out.println("Press F for binary search");
        System.out.println("Press I for insertion sort");
        System.out.println("Press M for merge sort");
        System.out.println("Press S for selection sort");
        System.out.println("Press R for radix sort");
    }

    /**************************************************************************
     *                                                                        *
     *                            BUBBlESORT METHOD                           *
     *                                                                        *
     **************************************************************************/

    public static ArrayList<Long> bubbleSort(ArrayList<Long> list) {
        count = 0;
        int switches;

        // keeps sorting until no switches are made
        do {
            switches = 0;
            for (int i = 0; i < list.size() - 1; i++) {

                // switches numbers if the first number is greater than the second
                if (list.get(i) > list.get(i + 1)) {
                    count++;
                    switches++;
                    long temp = list.get(i);
                    list.set(i, list.get(i + 1));
                    list.set(i + 1, temp);
                }
            }
        } while (switches > 0);

        return list;
    }

    /**************************************************************************
     *                                                                        *
     *                          SELECTION SORT METHOD                         *
     *                                                                        *
     **************************************************************************/

    public static ArrayList<Long> selectionSort(ArrayList<Long> list) {
        count = 0;

        // goes through each index as a starting position
        for (int i = 0; i < list.size() - 1; i++) {

            // initializes minimum number/index at starting position
            long min = list.get(i);
            int minIndex = i;

            // checks for lowest remaining number in list
            for (int j = i; j < list.size() - 1; j++) {
                if (min > list.get(j + 1)) {
                    min = list.get(j + 1);
                    minIndex = j + 1;
                }
            }

            // switches minimum number and starting number
            list.set(minIndex, list.get(i));
            list.set(i, min);
            count++;
        }

        return list;
    }

    /**************************************************************************
     *                                                                        *
     *                          INSERTION SORT METHOD                         *
     *                                                                        *
     **************************************************************************/

    public static ArrayList<Long> insertionSort(ArrayList<Long> list) {
        count = 0;

        // goes through each index as a starting position
        for (int i = 1; i < list.size(); i++) {

            // initializes minimum number/index at starting position
            long min = list.get(i);
            int minIndex = i;

            // finds where to place number
            while (minIndex > 0 && list.get(minIndex - 1) > min) {
                list.set(minIndex, list.get(minIndex - 1));
                minIndex -= 1;
            }

            // places number in sorted position
            list.set(minIndex, min);
            count++;
        }

        return list;
    }

    /**************************************************************************
     *                                                                        *
     *                              MERGE METHOD        	                  *
     *                                                                        *
     **************************************************************************/

    public static ArrayList<Long> merge(ArrayList<Long> a, ArrayList<Long> b) {
        ArrayList<Long> sorted = new ArrayList<>();
        int indexA = 0, indexB = 0;

        // adds smallest element of lists to beginning
        while (indexA < a.size() && indexB < b.size()) {
            if (a.get(indexA) < b.get(indexB)) {
                sorted.add(a.get(indexA));
                indexA++;
            } else {
                sorted.add(b.get(indexB));
                indexB++;
            }
        }

        // adds remaining elements of the larger list
        while (indexA < a.size()) {
            sorted.add(a.get(indexA));
            indexA++;
        }
        while (indexB < b.size()) {
            sorted.add(b.get(indexB));
            indexB++;
        }

        count++;
        return sorted;
    }

    /**************************************************************************
     *                                                                        *
     *                     RECURSIVE MERGE SORT METHOD                        *
     *                                                                        *
     **************************************************************************/

    public static ArrayList<Long> recursiveMergeSort(ArrayList<Long> list) {
        ArrayList<Long> head = new ArrayList<>();
        ArrayList<Long> tail = new ArrayList<>();

        // checks if list has more than one element
        if (list.size() > 1) {

            // splits list into two parts
            for (int i = 0; i < list.size() / 2; i++)
                head.add(list.get(i));
            for (int i = list.size() / 2; i < list.size(); i++)
                tail.add(list.get(i));

            // merges split lists
            list = merge(recursiveMergeSort(head), recursiveMergeSort(tail));
        }

        return list;
    }

    /**************************************************************************
     *                                                                        *
     *                MERGE SORT METHOD (WITHOUT RECURSION)                   *
     *                                                                        *
     **************************************************************************/

    public static ArrayList<Long> mergeSort(ArrayList<Long> list) {
        ArrayList<ArrayList<Long>> arrays = new ArrayList<>();

        // creates list of lists, each containing one element
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Long> temp = new ArrayList<>();
            temp.add(list.get(i));
            arrays.add(temp);
        }

        // checks if outer list has more than one list
        while (arrays.size() > 1) {
            ArrayList<ArrayList<Long>> temp = new ArrayList<>();

            // merges adjacent inner lists
            for (int i = 0; i < arrays.size() - 1; i += 2) {
                temp.add(merge(arrays.get(i), arrays.get(i + 1)));
            }

            // adds remaining list if there is an odd number of lists
            if (arrays.size() % 2 != 0)
                temp.add(arrays.get(arrays.size() - 1));

            arrays = temp;
        }

        return arrays.get(0);
    }

    /**************************************************************************
     *                                                                        *
     *                          RADIX SORT METHOD                             *
     *                                                                        *
     **************************************************************************/

    public static ArrayList<Long> radixSort(ArrayList<Long> list) {
        count = 0;
        int digits = 0;

        // finds largest number of digits any number has in list
        for (int i = 0; i < list.size(); i++)
            if ((int) Math.log10(list.get(i)) > digits)
                digits = (int) Math.ceil(Math.log10(list.get(i)));

        // goes through each digit, starting from the end
        for (int i = 0; i < digits; i++) {
            int[] array = new int[10];

            // stores how many numbers have each digit
            for (int j = 0; j < list.size(); j++)
                array[(int) Math.floor((list.get(j) % Math.pow(10, i + 1))/Math.pow(10, i))]++;

            // adds previous digit count to following digit count for each digit
            for (int j = 0; j < array.length - 1; j++)
                array[j + 1] += array[j];

            ArrayList<Long> temp = new ArrayList<>(list);

            // goes through list backwards, reducing digit count for each number with digit, and placing number at index of digit count
            for (int k = list.size() - 1; k >= 0; k--) {
                array[(int) Math.floor((list.get(k) % Math.pow(10, i + 1))/Math.pow(10, i))]--;
                temp.set(array[(int) Math.floor((list.get(k) % Math.pow(10, i + 1))/Math.pow(10, i))], list.get(k));
                count++;
            }

            list = temp;
        }

        return list;
    }

    /**************************************************************************
     *                                                                        *
     *                        BINARY SEARCH METHOD                            *
     *                                                                        *
     **************************************************************************/

    public static void binarySearch(List<Long> list) {
        count = 0;
        System.out.println(list);
        Scanner s = new Scanner(System.in);
        System.out.println("Enter a phone number");
        long num = s.nextLong();
        boolean found = false;
        int midpoint;
        int index = 0;

        // keeps searching until there are no numbers left
        while(list.size() > 0) {
            midpoint = list.size()/2;
            index += midpoint;

            // checks if number is at midpoint
            if(list.get(midpoint) == num) {
                found = true;
                break;
            }

            // keeps the sublist before the midpoint if the number is less than the midpoint element
            else if(list.get(midpoint) > num) {
                list = list.subList(0, midpoint);
                index -= midpoint;
            }

            // keeps the sublist after the midpoint if the number is greater than the midpoint element
            else {
                list = list.subList(midpoint+1, list.size());
                index += 1;
            }

            count++;
        }

        if(found) {
            System.out.println(num + " is at index " + index);
            System.out.println("Number of splits: " + count);
        }
        else
            System.out.println("Phone number not found");
    }
}
