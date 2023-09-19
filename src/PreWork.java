import java.util.Arrays;

public class PreWork {

    //1. sum by parity
    //input: {5,12,8,5,3,11,7,2,3,16,4}
    //output: {30, 46}

    public static int[] sumByParity(int[] arr) {
        int[] res = new int[2];
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0)
                res[0] += arr[i];
            else // we are at odd indices
                res[1] += arr[i];
        }
        return res;
    }

    //2. consecutive fours
    //test: {3,3,3,3,2,1,5,5,6,6,6} => true
    //test: {3,3,3,2,1,5,5,6,6,6} => false

    public static boolean consecutiveFours(int[] arr) {
        int count = 1;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] == arr[i + 1]) {
                count++;
            }
            else {
                count = 1;
            }
            if (count >= 4) {
                return true;
            }
        }
        return false;
    }

    //3. expand by index
    // input: {2, 1, 3}
    // output: {0, 0, 1, 2, 2, 2}

    public static int[] expandByIndex(int[] arr) {
        int size = 0;
        int index = 0;
        for (int item : arr) {
            size += item;
        }
        int[] res = new int[size];
        for (int i = 0; i < arr.length; i++) {
            int value = i;
            int repeats = arr[i];
            for (int j = 0; j < repeats; j++) {
                res[index] = value;
                index++;
            }
        }
        return res;
    }


    //4. numerical count
    // input: "abcd3fgh1"
    // output: 2

    public static int numericalCount(String string) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
           if (Character.isDigit(string.charAt(i))) {
               count++;
           }
        }
        return count;
    }


    public static void main(String[] args) {

        }


    }
