import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RleProgramTest {
    //method 1
    @Test
    public void toHexStringTest() {
        byte[] data = {3,15,6,4}; //input
        String result = "3f64";   //expected result
        assertEquals(result, RleProgram.toHexString(data));

    }

    //method 2
    @Test
    public void countRunsTest() {
        byte[] flatData = {15,15,1,1,1,6,2,2}; //input
        int groups = 4;                        // expected result
        assertEquals(groups, RleProgram.countRuns(flatData));
    }

    // method 3
    @Test
    public void encodeRleTest() {
        byte[] flatData = {4,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1 }; //input
        byte[] rleData = {2,4,15,1,15,1,5,1};   // expected result

        byte[] actualResult = RleProgram.encodeRle(flatData);

        assertArrayEquals(rleData, actualResult);
    }

    //method 4
    @Test
    public void getDecodedLength() {
        byte[] rleData= {3,15,6,4}; //input
        int result = 9;             // expected result
        assertEquals(result, RleProgram.getDecodedLength(rleData));
    }

    //method 5
    @Test
    public void decodeRleTest() {
        byte[] rleData = {3,15,6,4};                    //input
        byte[] expectedResult = {15,15,15,4,4,4,4,4,4}; //expected result

        byte[] actualResult = RleProgram.decodeRle(rleData); //result from program

        assertArrayEquals(expectedResult, actualResult);
    }

    //method 6
    @Test
    public void stringToDataTest() {
        String dataString = "3f64";
        byte[] expectedResult = {3,15,6,4};
        byte[] actualResult = RleProgram.stringToData(dataString);

        assertArrayEquals(expectedResult, actualResult);
    }

    //method 7
    @Test
    public void toRleStringTest() {
        byte[] rleData = {15, 15, 6, 4};
        String expectedResult = "15f:64";
        String actualResult = RleProgram.toRleString(rleData);

        assertEquals(expectedResult, actualResult);
    }

    //method 8
    @Test
    public void stringToRleTest() {
        String rleString = "15f:64";
        byte[] expectedResult = {15, 15, 6, 4};
        byte[] actualResult = RleProgram.stringToRle(rleString);

        assertArrayEquals(expectedResult,  actualResult);
    }


}
