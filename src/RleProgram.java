import java.util.Arrays;
import java.util.Scanner;

public class RleProgram {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // 1. Display welcome message
        System.out.println("Welcome to the RLE image encoder!");
        System.out.println(); //skip a line

        // 2. Display color test with the message
        System.out.println("Displaying Spectrum Image:");
        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);
        System.out.println(); //skip a line

        String userInputString = "";
        String resultString = "";
        byte[] imageData = null;
        byte[] userInputRle = null;
        byte[] rleByteData = null;
        byte[] userInputFlat = null;
        boolean loopContinue = true;

        
         while (loopContinue) {



             System.out.println("RLE Menu");
            System.out.println("--------");
            System.out.println("0. Exit");
            System.out.println("1. Load File");
            System.out.println("2. Load Test Image");
            System.out.println("3. Read RLE String");
            System.out.println("4. Read RLE Hex String");
            System.out.println("5. Read Data Hex String");
            System.out.println("6. Display Image");
            System.out.println("7. Display RLE String");
            System.out.println("8. Display Hex RLE Data");
            System.out.println("9. Display Hex Flat Data");
            System.out.println(); //skip a line
            System.out.print("Select a Menu Option: ");
            int userMenuOption = scanner.nextInt();
            // 3. Display the menu = Part A: while loop or if-else chains

                // 3.0 - option 1
                // exit program
                if (userMenuOption == 0) {
                    loopContinue = false;
                }
                // 3.1 - option 1
                // load file using ConsoleGfx.loadFile(userInput) and you want to store the returned byte[] into the imageData
                else if (userMenuOption == 1) {
                    System.out.print("Enter name of file to load: ");
                    String userFileLoad = scanner.next();
                    imageData = ConsoleGfx.loadFile(userFileLoad);
                }
                // 3.2 - option 2
                //store ConsoleGfx.testImage to the imageData array
                else if (userMenuOption == 2) {
                    imageData = ConsoleGfx.testImage;
                    System.out.println("Test image data loaded.");
                }
                // 3.3 - option 3
                // reads RLE data from user in decimal w/ delimiters
                else if (userMenuOption == 3) {
                    System.out.print("Enter an RLE string to be decoded: ");
                    userInputString = scanner.next();
                    userInputRle = stringToRle(userInputString);
                    userInputFlat = decodeRle(userInputRle);
                }
                // 3.4 - option 4
                // reads RLE data from user in hexadecimal w/o delimiters
                else if (userMenuOption == 4) {
                    System.out.print("Enter the hex string holding RLE data: ");
                    userInputString = scanner.next();
                    userInputRle = stringToData(userInputString);
                    userInputFlat = decodeRle(userInputRle);
                }
                //3.5 - option 5
                // reads flat data from user in hexadecimal
                else if (userMenuOption == 5) {
                    System.out.print("Enter the hex string holding flat data: ");
                    userInputString = scanner.next();
                    userInputFlat = stringToData(userInputString);
                }
                // 3.6 - option 6
                // display image stored inside imageData array
                else if (userMenuOption == 6) {
                    System.out.println("Displaying image...");
                    if (imageData == null) {
                        System.out.println("(no data)");
                    }
                    else {
                        ConsoleGfx.displayImage(imageData);
                    }
                }
                // 3.7 - option 7
                // convert current data into RLE w/ delimiters
                else if (userMenuOption == 7){
                    if (userInputFlat == null) {
                        System.out.println("RLE representation: (no data)");
                    }
                    else {
                        rleByteData = encodeRle(userInputFlat);
                        resultString = toRleString(rleByteData);
                        System.out.println("RLE representation: " + resultString);
                    }

                }
                // 3.8 - option 8
                // convert current data into RLE hexadecimal w/o delimiters
                else if (userMenuOption == 8) {
                    if (userInputFlat == null) {
                        System.out.println("RLE hex values: (no data)");
                    }
                    else {
                        rleByteData = encodeRle(userInputFlat);
                        resultString = toHexString(rleByteData);
                        System.out.println("RLE hex values: " + resultString);
                    }

                }
                // 3.9 - option 9
                // displays current flat data in hexadecimal w/o delimiters
                else if (userMenuOption == 9) {
                    if (userInputFlat == null) {
                        System.out.println("Flat hex values: (no data)");
                    }
                    else {
                        resultString = toHexString(userInputFlat);
                        System.out.println("Flat hex values: " + resultString);
                    }

                }
                else {
                    System.out.println("Error! Invalid Input.");
                }

                System.out.println(); //skip a line
        }
    }

    // method 1
    public static String toHexString(byte[] data) {
        String res = "";
        String str = "";
        for (int i = 0; i < data.length; i++) {
            if (data[i] <= 9) {
                str = "" + data[i]; //converts decimal value to string
            }
            else { //manually changes each decimal value from 10-15 into its hexadecimal equivalent
                if (data[i] == 10) {
                    str = "a";
                }
                else if(data[i] == 11) {
                    str = "b";
                }
                else if(data[i] == 12) {
                    str = "c";
                }
                else if(data[i] == 13) {
                    str = "d";
                }
                else if(data[i] == 14) {
                    str = "e";
                }
                else if(data[i] == 15) {
                    str = "f";
                }
            }
            res += str;
        }
        return res;
    }

    // method 2
    public static int countRuns(byte[] flatData) { //ideas from pre-work videos by Professor Zhou
        int count = 1;
        int group = 1;
        for (int i = 0; i < flatData.length - 1; i++) {
            if (flatData[i] == flatData[i+1]) { //if the number is the same as the number after it, increase number of values in run
                count++;
            }
            else { //when a number changes, it is considered a separate run
                group++;
                count = 1;
            }
            if (count >= 15) { //once reaching 15 digits in one run, start another run
                group++;
                count = 1;
            }
        }
        return group;
    }

    //method 3
    public static byte[] encodeRle(byte[] flatData) { //ideas from prework video by Professor Zhou
        int count = 1;
        int index = 0;
        byte[] valueArr = new byte[0];
        for (int i = 0; i < flatData.length - 1; i++) {
            if (flatData[i] == flatData[i+1]) { //if there is a run
                count++; //increase the number of values in that run
                if (count >= 15) {
                    byte[] newArr = Arrays.copyOf(valueArr, valueArr.length + 2); // example on how to increase size of array from W3schools.com
                    valueArr = newArr;
                    valueArr[index] = (byte)count;
                    index++;
                    valueArr[index] = flatData[i];
                    index++;
                    count = 0;
                }
            }
            else { //the run breaks
                byte[] newArr = Arrays.copyOf(valueArr, valueArr.length + 2); // example on how to increase size of array from W3schools.com
                valueArr = newArr;
                valueArr[index] = (byte)count;
                index++;
                valueArr[index] = flatData[i];
                index++;
                count = 1;
            }
        }
        //consider the last 2 values that are not considered in the previous loops
        if (flatData[flatData.length - 1] == flatData[flatData.length - 2]) {
            byte[] newArr = Arrays.copyOf(valueArr, valueArr.length + 2);
            valueArr = newArr;
            valueArr[index] = (byte)count;
            index++;
            valueArr[index] = flatData[flatData.length - 1];
            index++;
        }
        else {
            byte[] newArr = Arrays.copyOf(valueArr, valueArr.length + 2);
            valueArr = newArr;
            valueArr[index] = 1;
            index++;
            valueArr[index] = flatData[flatData.length - 1];
        }
        return valueArr;
    }

    // method 4
    public static int getDecodedLength(byte[] rleData) {
        int result = 0;
        for(int i = 0; i < rleData.length; i++) {
            if (i % 2 == 0) { // only even indices
                result += rleData[i];
            }
        }
        return result;
    }

    //method 5
    public static byte[] decodeRle(byte[] rleData) {
        int size = 0;
        int index = 0;
        for (int i = 0; i < rleData.length; i++) {
            if (i % 2 == 0) { //use even indices to determine size of new byte array
                size += rleData[i];
            }
        }
        byte[] res = new byte[size];
        for (int j = 0; j < rleData.length; j = j + 2) {
            int repeats = rleData[j]; //store the value that will be repeated
            for (int k = index; k < index + repeats; k++) { //starting at the next empty value and repeating as many times as the value repeats
                res[k] = rleData[j+1];
            }
            index += repeats; //move index to next empty position in array
        }
        return res;
    }

    //method 6
    public static byte[] stringToData(String dataString) {
        byte[] res = new byte[dataString.length()];
        for (int i = 0; i < dataString.length(); i++) {
            char value = dataString.charAt(i);
            String str = Character.toString(value);
            res[i] = Byte.parseByte(str, 16); //example on how convert string to byte via javatpoint.com
        }
        return res;
    }

    //method 7
    public static String toRleString(byte[] rleData) {
        String result = "";
        String countRuns = "";
        String hexValue = "";
        for (int i = 0; i < rleData.length; i = i + 1) {
            if (i%2 == 0) { //even indices indicate number stays the sanme
                countRuns = "" + rleData[i]; //convert to string so it can be added to string result
                result += countRuns;
            }
            else {
                byte[] hexArray = new byte[] {rleData[i]}; //convert value into a byte array so it can be applied in method 1
                hexValue = toHexString(hexArray); //convert to hex value
                result += hexValue;
                if (i != (rleData.length - 1)) { //if not at the last pair, put a colon in between the pairs
                    result += ":";
                }
            }
        }
        return result;
    }
    //method 8
    public static byte[] stringToRle(String rleString) {
        String[] rleArray = rleString.split(":"); //idea from Andrew Penton (TA)
        byte[] result = new byte[rleArray.length * 2]; //byte is twice as long as array because each spot in array represents run length & run value
        for (int i = 0; i < rleArray.length; i = i + 1) {
            result[i * 2] = Byte.parseByte(rleArray[i].substring(0,(rleArray[i].length() - 1))); //string -> byte conversion example via javatpoint.com
            //manually convert hex value to decimal
            char hexValue = rleArray[i].charAt(rleArray[i].length() - 1); //convert to char to compare in if statement
            if (hexValue == 'a' || hexValue == 'A') {
                result[(i * 2) + 1] = 10;
            }
            else if (hexValue == 'b' || hexValue == 'B') {
                result[(i * 2) + 1] = 11;
            }
            else if (hexValue == 'c' || hexValue == 'C') {
                result[(i * 2) + 1] = 12;
            }
            else if (hexValue == 'd' || hexValue == 'D') {
                result[(i * 2) + 1] = 13;
            }
            else if (hexValue == 'e' || hexValue == 'E') {
                result[(i * 2) + 1] = 14;
            }
            else if (hexValue == 'f' || hexValue == 'F') {
                result[(i * 2) + 1] = 15;
            }
            else {
                result[(i * 2) + 1]= (byte) (hexValue - '0'); //have to subtract from '0' because value will be written as '4' instead of 4
            }
        }
        return result;
    }
}
