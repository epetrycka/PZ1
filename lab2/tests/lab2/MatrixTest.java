package lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

class MatrixTest {

    private Matrix square1;
    private Matrix rectangle1;
    private Matrix rectangle2;

    @BeforeEach
    void initiateSquareMatrix1(){
        Random r = new Random();
        long seed = r.nextLong();
        int shape = r.nextInt(5)+1;

        square1 = Matrix.random(shape, shape, seed);
    }

    @BeforeEach
    void initiateRectangleMatrix3(){
        Random r = new Random();
        long seed = r.nextLong();
        int row = r.nextInt(5)+1;
        int col = r.nextInt(5)+1;

        while (col == row) {
            col = r.nextInt(5)+1;
        }

        rectangle1 = Matrix.random(row, col, seed);
    }

    @BeforeEach
    public void initiateRectangleMatrix4(){
        Random r = new Random();
        long seed = r.nextLong();
        int row = r.nextInt(5)+1;
        int col = r.nextInt(5)+1;

        while (col == row) {
            col = r.nextInt(5)+1;
        }

        rectangle2 = Matrix.random(row, col, seed);
    }

    @Test
    void MatrixTestNulls(){
        Random r = new Random();
        int expectedRows = r.nextInt(5)+1;
        int expectedCols = r.nextInt(5)+1;
        Matrix m = new Matrix(expectedRows, expectedCols);

        assertEquals(expectedRows, m.rows);
        assertEquals(expectedCols, m.cols);
        assertEquals(expectedRows*expectedCols, m.data.length);

        int any_value = r.nextInt(expectedRows*expectedCols);

        assertNull(m.data[any_value]);
    }

    @Test
    void MatrixTestFromTable(){
        Random r = new Random();
        long seed = r.nextLong();

        Random s = new Random(seed);
        int maxCols = 0;
        int rows = s.nextInt(5);
        Double[][] d = new Double[rows][];
        ArrayList<Double> expectedData = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            int col = s.nextInt(5);

            if (col > maxCols) {
                maxCols = col;
            }

            Double[] row = new Double[col];
            for (int j = 0; j < col; j++) {
                row[j] = s.nextDouble();
                expectedData.add(row[j]);
            }
            d[i] = row;
        }

        int listIndex = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < d[i].length; j++) {
                assertEquals(expectedData.get(listIndex), d[i][j], "Values are different: [" + i + "][" + j + "]");
                listIndex++;
            }
        }
    }

    @Test
    void asArraySquareCase() {
        Double[][] matrixAsArray = square1.asArray();

        int rows = square1.rows;
        int cols = square1.cols;
        int rowIndex = 0;
        int colIndex = 0;
        Double[][] expectedArray= new Double[rows][cols];

        for (Double value: square1.data) {
            expectedArray[rowIndex][colIndex] = value;
            colIndex++;
            if(colIndex == cols) {
                colIndex = 0;
                rowIndex++;
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                assertEquals(expectedArray[i][j], matrixAsArray[i][j], "Values are different: [" + i + "][" + j + "]");
            }
        }
    }

    @Test
    void asArrayRectangleCase() {
        Double[][] matrixAsArray = rectangle1.asArray();

        int rows = rectangle1.rows;
        int cols = rectangle1.cols;
        int rowIndex = 0;
        int colIndex = 0;
        Double[][] expectedArray= new Double[rows][cols];

        for (Double value: rectangle1.data) {
            expectedArray[rowIndex][colIndex] = value;
            colIndex++;
            if(colIndex == cols) {
                colIndex = 0;
                rowIndex++;
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                assertEquals(expectedArray[i][j], matrixAsArray[i][j], "Values are different: [" + i + "][" + j + "]");
            }
        }
    }

    @Test
    void get() {
        Double[] expectedData = rectangle1.data;
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                assertEquals(expectedData[i * cols + j], rectangle1.get(i, j), "Values are different: [" + i + "][" + j + "]");
            }
        }

        Random r = new Random();
        int rowOutOfBounds = rows + r.nextInt(rows);
        int colOutOfBounds = cols + r.nextInt(cols);

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            rectangle1.get(rowOutOfBounds, r.nextInt(cols)+1);
        });

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            rectangle1.get(r.nextInt(rows)+1, colOutOfBounds);
        });
    }

    @Test
    void set() {
        Double[] expectedData = rectangle1.data;
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;
        Random r = new Random();

        Double[] newValues = new Double[rows*cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newValues[i * cols + j] = r.nextDouble();
                rectangle1.set(i, j, newValues[i * cols + j]);
            }
        }

        for (int i = 0; i < rows*cols; i++) {
            assertEquals(newValues[i], expectedData[i], "Values are different: [" + i + "][" + i + "]");
        }

        int rowOutOfBounds = rows + r.nextInt(rows);
        int colOutOfBounds = cols + r.nextInt(cols);
        Double anyValue = r.nextDouble();

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            rectangle1.set(rowOutOfBounds, r.nextInt(cols)+1, anyValue);
        });

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            rectangle1.set(r.nextInt(rows)+1, colOutOfBounds, anyValue);
        });

    }

    @Test
    void testToString() {
        Double[] expectedNumbers = rectangle1.data;
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;

        String matrixInString = rectangle1.toString();
        String matrixWithoutBracesAndSpaces = matrixInString.replaceAll("\\n", ",");
        matrixWithoutBracesAndSpaces = matrixWithoutBracesAndSpaces.replaceAll("[\\[\\]\\s]", "");

        String[] cleanNumbers = matrixWithoutBracesAndSpaces.split(",");

        assertEquals(rows * cols, cleanNumbers.length, "The number of elements in the string does not match the expected matrix size.");

        assertEquals(matrixInString.charAt(0), '[');

        for(int i = 0; i < rows*cols; i++) {
            assertEquals(expectedNumbers[i], Double.parseDouble(cleanNumbers[i]), "Values are different: [" + i + "]");
        }

        assertEquals(matrixInString.charAt(matrixInString.length()-1), ']');
    }

    @Test
    void reshape() {
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;
        Random r = new Random();
        int newRows = r.nextInt(rows*cols) + 1;
        int newCols = r.nextInt(rows*cols) + 1;

        if (newRows*newCols != rows*cols){
            assertThrows(RuntimeException.class, ()->{
                rectangle1.reshape(newRows, newCols);
            });
        }
        else {
            rectangle1.reshape(newRows, newCols);
            assertEquals(newRows, rectangle1.rows);
            assertEquals(newCols, rectangle1.cols);
        }
    }

    @Test
    void shape() {
        assertEquals(rectangle1.rows, rectangle1.shape()[0]);
        assertEquals(rectangle1.cols, rectangle1.shape()[1]);
    }

    @Test
    void copy() {
        rectangle1.copy(rectangle2);

        assertEquals(rectangle1.rows, rectangle2.rows);
        assertEquals(rectangle1.cols, rectangle2.cols);

        for(int i = 0; i < rectangle1.rows*rectangle1.cols; i++) {
            assertEquals(rectangle1.data[i], rectangle2.data[i]);
        }
    }

    @Test
    void add() {
        Double[] expectedData = rectangle1.data;
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;
        Random r = new Random();
        double addValue = r.nextDouble(5.0) + 1;
        Matrix result = rectangle1.add(addValue);

        for(int i = 0; i < rows*cols; i++) {
            assertEquals(expectedData[i] + addValue, result.data[i], "Values are different: [" + i + "][" + i + "]");
        }
    }

    @Test
    void sub() {
        Double[] expectedData = rectangle1.data;
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;
        Random r = new Random();
        double subValue = r.nextDouble(5.0) + 1;
        Matrix result = rectangle1.sub(subValue);

        for(int i = 0; i < rows*cols; i++) {
            assertEquals(expectedData[i] - subValue, result.data[i], "Values are different: [" + i + "][" + i + "]");
        }
    }

    @Test
    void mul() {
        Double[] expectedData = rectangle1.data;
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;
        Random r = new Random();
        double mulValue = r.nextDouble(5.0) + 1;
        Matrix result = rectangle1.mul(mulValue);

        for(int i = 0; i < rows*cols; i++) {
            assertEquals(expectedData[i] * mulValue, result.data[i], "Values are different: [" + i + "][" + i + "]");
        }
    }

    @Test
    void div() {
        Double[] expectedData = rectangle1.data;
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;
        Random r = new Random();
        double divValue = r.nextDouble(5.0) + 1;
        Matrix result = rectangle1.div(divValue);

        for(int i = 0; i < rows*cols; i++) {
            assertEquals(expectedData[i] / divValue, result.data[i], "Values are different: [" + i + "][" + i + "]");
        }

        double illegalDivValue = 0;
        assertThrows(IllegalArgumentException.class, ()->{rectangle1.div(illegalDivValue);});
    }

    @Test
    void testAdd() {
        Random r = new Random();
        long seed1 = r.nextLong();
        long seed2 = r.nextLong();
        int row = r.nextInt(5)+1;
        int col = r.nextInt(5)+1;

        while (col == row) {
            col = r.nextInt(5)+1;
        }

        rectangle1 = Matrix.random(row, col, seed1);
        rectangle2 = Matrix.random(row, col, seed2);

        Matrix result = rectangle1.add(rectangle2);

        for(int i = 0; i < row*col; i++) {
            assertEquals(result.data[i], rectangle1.data[i]+rectangle2.data[i], "Values are different: [" + (rectangle1.data[i] + "+" + rectangle2.data[i]) + "][" + result.data[i] + "]");
        }

        assertThrows(RuntimeException.class, ()->{
            rectangle1.add(square1);
        });
    }

    @Test
    void testSub() {
        Random r = new Random();
        long seed1 = r.nextLong();
        long seed2 = r.nextLong();
        int row = r.nextInt(5)+1;
        int col = r.nextInt(5)+1;

        while (col == row) {
            col = r.nextInt(5)+1;
        }

        rectangle1 = Matrix.random(row, col, seed1);
        rectangle2 = Matrix.random(row, col, seed2);

        Matrix result = rectangle1.sub(rectangle2);

        for(int i = 0; i < row*col; i++) {
            assertEquals(result.data[i], rectangle1.data[i]-rectangle2.data[i], "Values are different: [" + (rectangle1.data[i] + "+" + rectangle2.data[i]) + "][" + result.data[i] + "]");
        }

        assertThrows(RuntimeException.class, ()->{
            rectangle1.sub(square1);
        });
    }

    @Test
    void testMul() {
        Random r = new Random();
        long seed1 = r.nextLong();
        long seed2 = r.nextLong();
        int row = r.nextInt(5)+1;
        int col = r.nextInt(5)+1;

        while (col == row) {
            col = r.nextInt(5)+1;
        }

        rectangle1 = Matrix.random(row, col, seed1);
        rectangle2 = Matrix.random(row, col, seed2);

        Matrix result = rectangle1.mul(rectangle2);

        for(int i = 0; i < row*col; i++) {
            assertEquals(result.data[i], rectangle1.data[i]*rectangle2.data[i], "Values are different: [" + (rectangle1.data[i] + "+" + rectangle2.data[i]) + "][" + result.data[i] + "]");
        }

        assertThrows(RuntimeException.class, ()->{
            rectangle1.mul(square1);
        });
    }

    @Test
    void testDiv() {
        Random r = new Random();
        long seed1 = r.nextLong();
        long seed2 = r.nextLong();
        int row = r.nextInt(5)+1;
        int col = r.nextInt(5)+1;

        while (col == row) {
            col = r.nextInt(5)+1;
        }

        rectangle1 = Matrix.random(row, col, seed1);
        rectangle2 = Matrix.random(row, col, seed2);

        Matrix result = rectangle1.div(rectangle2);

        for(int i = 0; i < row*col; i++) {
            assertEquals(result.data[i], rectangle1.data[i]/rectangle2.data[i], "Values are different: [" + (rectangle1.data[i] + "+" + rectangle2.data[i]) + "][" + result.data[i] + "]");
        }

        assertThrows(RuntimeException.class, ()->{
            rectangle1.div(square1);
        });

        int i = r.nextInt(row);
        int j = r.nextInt(col);
        rectangle2.set(i, j, 0.0);
        assertThrows(IllegalArgumentException.class, ()->{rectangle1.div(rectangle2);});
    }

    @Test
    void dot() {
        Random r = new Random();
        long seed1 = r.nextLong();
        long seed2 = r.nextLong();
        int row1 = r.nextInt(5)+1;
        int col1 = r.nextInt(5)+1;
        int row2 = col1;
        int col2 = r.nextInt(5)+1;

        rectangle1 = Matrix.random(row1, col1, seed1);
        rectangle2 = Matrix.random(row2, col2, seed2);

        Matrix result = rectangle1.dot(rectangle2);

        Double[][] array1 = rectangle1.asArray();
        Double[][] array2 = rectangle2.asArray();

        for (int rowIndex1 = 0; rowIndex1 < row1; rowIndex1++) {
            for (int colIndex2 = 0; colIndex2 < col2; colIndex2++) {
                double value = 0.0;
                for (int i = 0; i < col1; i++) {
                    value += array1[rowIndex1][i] * array2[i][colIndex2];
                }
                assertEquals(value, result.get(rowIndex1, colIndex2), "Values are different at position [" + rowIndex1 + "][" + colIndex2 + "] " + "Expected: " + value + ", Found: " + result.get(rowIndex1, colIndex2));
            }
        }
    }

    @Test
    void frobenius() {
        Double[] expectedData = rectangle1.data;
        int rows = rectangle1.rows;
        int cols = rectangle1.cols;

        double expectedFrobeniusSquared = 0;

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                expectedFrobeniusSquared += Math.pow(expectedData[i * cols + j],2);
            }
        }

        assertEquals(Math.sqrt(expectedFrobeniusSquared), rectangle1.frobenius());
    }

    @Test
    void random() {
        Random r = new Random();
        long seed = r.nextLong();
        int row = r.nextInt(5)+1;
        int col = r.nextInt(5)+1;

        rectangle2 = Matrix.random(row, col, seed);

        Double[] listOfRandoms = new Double[row*col];
        Random s = new Random(seed);
        for (int i = 0; i < row*col; i++) {
            listOfRandoms[i] = s.nextDouble();
        }

        for(int i = 0; i < row*col; i++) {
            assertEquals(listOfRandoms[i], rectangle2.data[i], "Values are different: [ " + listOfRandoms[i] + " bo i " + i +" ] [ " + rectangle2.data[i] + " ]");
        }

        Matrix expectedResult = new Matrix(row,col);
        expectedResult.data = listOfRandoms;
        assertEquals(0.0, Math.round((expectedResult.sub(rectangle2)).frobenius()*Math.pow(10,14))/Math.pow(10,14));
    }

    @Test
    void eye() {
        int rows = square1.rows;

        Matrix eyeResult = Matrix.eye(square1.rows);

        assertEquals(Math.sqrt(rows), eyeResult.frobenius());

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < rows; j++) {
                if (i == j){
                    assertEquals(1.0, eyeResult.get(i,j));
                }
                else{
                    assertEquals(0.0, eyeResult.get(i,j));
                }
            }
        }
    }

    @Test
    void swapRows() {
        Random r = new Random();
        long seed = r.nextLong();
        int row = r.nextInt(5)+2;
        int col = r.nextInt(5)+1;

        while (col == row) {
            col = r.nextInt(5)+1;
        }

        rectangle1 = Matrix.random(row, col, seed);

        int validRow1 = r.nextInt(row) + row;
        int validRow2 = r.nextInt(row) + row;

        if (validRow1 >= rectangle1.rows || validRow2 >= rectangle1.rows){
            assertThrows(ArrayIndexOutOfBoundsException.class, ()->{rectangle1.swapRows(validRow1, validRow2);});
        }

        int row1 = r.nextInt(row);
        int row2 = r.nextInt(row);

        while (row1 == row2) {
            row2 = r.nextInt(row);
        }

        Double[] row1ExpectedData = new Double[col];
        Double[] row2ExpectedData = new Double[col];

        for (int i = 0; i < col; i++) {
            row2ExpectedData[i] = rectangle1.get(row1, i);
            row1ExpectedData[i] = rectangle1.get(row2, i);
        }

        rectangle1.swapRows(row1, row2);

        for (int i = 0; i < col; i++) {
            assertEquals(row2ExpectedData[i], rectangle1.get(row2, i), "Valid value in row2");
            assertEquals(row1ExpectedData[i], rectangle1.get(row1, i), "Valid value in row1");
        }
    }

    @Test
    void determinant() {
        assertThrows(RuntimeException.class, ()->{rectangle1.determinant();});

        Matrix m = new Matrix(new double[][]{
                {2.23453, 5.23451, 8.03245},
                {4.46471, 3.36567, 9.68624},
                {2.34677, 7.23423, 3.00843}
        });

        double result = m.determinant();
        assertEquals(110.719, Math.round(result * 1000.0)/1000.0);

        Matrix m1 = new Matrix(new double[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
        double result1 = m1.determinant();
        assertEquals(1, result1);

        Matrix m2 = new Matrix(new double[][]{
                {2, 5, 8},
                {4, 3, 9},
                {2, 7, 3}
        });

        double result2 = m2.determinant();
        assertEquals(98, result2);

        Matrix m3 = new Matrix(new double[][]{
                {0, 0, 0},
                {4, 0, 9},
                {2, 7, 0}
        });

        double result3 = m3.determinant();
        assertEquals(0, result3);
    }

    @Test
    void invert() {
        if (square1.determinant() == 0) {
            assertThrows(RuntimeException.class, () -> {
                square1.invert();
            });
        } else {
            Matrix inverted = square1.invert();
            Matrix eyeResult = Matrix.eye(square1.rows);
            Matrix result = inverted.dot(square1);
            assertEquals(eyeResult.rows, result.rows);
            for (int i = 0; i < result.rows * result.cols; i++) {
                assertEquals(eyeResult.data[i], Math.round(result.data[i]*Math.pow(10,10))/Math.pow(10,10), "Values are different: [ " + i + " ] Expected: " + eyeResult.data[i] + " Found: " + result.data[i]);
            }

            assertEquals(0.0, Math.round((eyeResult.sub(result)).frobenius()*Math.pow(10,14))/Math.pow(10,14));
        }
    }
}