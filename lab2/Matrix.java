package lab2;

import java.util.Random;

public class Matrix {
    Double[] data;
    int rows;
    int cols;

    Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        data = new Double[rows*cols];
    }

    Matrix(double[][] d) {
        this.cols = 0;
        this.rows = d.length;

        for (double[] row : d) {
            if (this.cols < row.length) {
                this.cols = row.length;
            }
        }

        data = new Double[rows * cols];
        int dataIndex = 0;

        for (double[] row : d) {
            for (int colIndex = 0; colIndex < this.cols; colIndex++) {
                if (colIndex < row.length) {
                    data[dataIndex] = row[colIndex];
                } else {
                    data[dataIndex] = 0.0;
                }
                dataIndex++;
            }
        }
    }

    Double[][] asArray(){
        Double[][] matrixAsArray = new Double[this.rows][this.cols];
        int colIndex = 0;
        int rowIndex = 0;

        for(Double value: this.data){
            matrixAsArray[rowIndex][colIndex] = value;
            colIndex++;
            if (colIndex == this.cols){
                colIndex = 0;
                rowIndex++;
            }
        }

        return matrixAsArray;
    }

    Double get(int r, int c){
        if (r >=this.rows || c >=this.cols){
            throw new ArrayIndexOutOfBoundsException("Valid index: r = " + r + ", c = " + c);
        }
        else {
            return this.data[r * this.cols + c];
        }
    }

    void set(int r, int c, Double value){
        if (r >= this.rows || c >= this.cols) {
            throw new ArrayIndexOutOfBoundsException("Valid index: r = " + r + ", c = " + c);
        } else {
            this.data[r * this.cols + c] = value;
        }
    }

    public String toString(){
        StringBuilder buf = new StringBuilder();
        int colIndex = 0;
        int rowIndex = 0;

        buf.append("[");
        for (Double value: this.data){
            buf.append(value);
            colIndex++;
            if (colIndex == this.cols && rowIndex != this.rows-1){
                colIndex = 0;
                rowIndex++;
                buf.append("\n");
            }
            else if (colIndex != this.cols && rowIndex != this.rows)
            {
                buf.append(", ");
            }
        }
        buf.append("]");

        return buf.toString();
    }

    void reshape(int newRows, int newCols){
        if (this.rows*this.cols != newRows*newCols){
            throw new RuntimeException(String.format("%d x %d matrix can't be reshaped to %d x %d", this.rows, this.cols, newRows, newCols));
        }
        this.rows = newRows;
        this.cols = newCols;
    }

    int[] shape(){
        return new int[]{this.rows, this.cols};
    }

    void copy(Matrix m){
        this.rows = m.rows;
        this.cols = m.cols;
        this.data = m.data;
    }

    Matrix add(Matrix m){
        if (this.rows != m.rows || this.cols != m.cols){
            throw new RuntimeException("Matrices must have the same shape");
        }
        else {
            int index = 0;
            Matrix result = new Matrix(this.rows, this.cols);
            for(Double value: m.data){
                result.data[index] = this.data[index] + value;
                index++;
            }
            return result;
        }
    }

    Matrix sub(Matrix m){
        if (this.rows != m.rows || this.cols != m.cols){
            throw new RuntimeException("Matrices must have the same shape");
        }
        else {
            int index = 0;
            Matrix result = new Matrix(this.rows, this.cols);
            for(Double value: m.data){
                result.data[index] = this.data[index] - value;
                index++;
            }
            return result;
        }
    }

    Matrix mul(Matrix m){
        if (this.rows != m.rows || this.cols != m.cols){
            throw new RuntimeException("Matrices must have the same shape");
        }
        else {
            int index = 0;
            Matrix result = new Matrix(this.rows, this.cols);
            for(Double value: m.data){
                result.data[index] = this.data[index] * value;
                index++;
            }
            return result;
        }
    }

    Matrix div(Matrix m){
        if (this.rows != m.rows || this.cols != m.cols){
            throw new RuntimeException("Matrices must have the same shape");
        }
        else {
            int index = 0;
            Matrix result = new Matrix(this.rows, this.cols);
            for(Double value: m.data){
                if (value == 0){
                    throw new IllegalArgumentException("Division by zero");
                }
                result.data[index] = this.data[index] / value;
                index++;
            }
            return result;
        }
    }

    Matrix add(double w){
        int index = 0;
        Matrix result = new Matrix(this.rows, this.cols);
        while (index < (this.rows*this.cols))
        {
            result.data[index] = this.data[index] + w;
            index++;
        }
        return result;
    }

    Matrix sub(double w){
        int index = 0;
        Matrix result = new Matrix(this.rows, this.cols);
        while (index < (this.rows*this.cols))
        {
            result.data[index] = this.data[index] - w;
            index++;
        }
        return result;
    }

    Matrix mul(double w){
        int index = 0;
        Matrix result = new Matrix(this.rows, this.cols);
        while (index < (this.rows*this.cols))
        {
            result.data[index] = this.data[index] * w;
            index++;
        }
        return result;
    }

    Matrix div(double w){
        if (w == 0){
            throw new IllegalArgumentException("Division by zero");
        }
        int index = 0;
        Matrix result = new Matrix(this.rows, this.cols);
        while (index < (this.rows*this.cols))
        {
            result.data[index] = this.data[index] / w;
            index++;
        }
        return result;
    }

    Matrix dot(Matrix m){
        if (this.cols != m.rows) {
            throw new IllegalArgumentException("Matrix A's number of columns must be the same as Matrix B's number of rows");
        }

        Matrix result = new Matrix(this.rows, m.cols);

        for (int rowIndex = 0; rowIndex < this.rows; rowIndex++) {
            for (int colIndex = 0; colIndex < m.cols; colIndex++) {
                result.data[rowIndex * m.cols + colIndex] = 0.0;

                for (int i = 0; i < this.cols; i++) {
                    result.data[rowIndex * m.cols + colIndex] += this.get(rowIndex, i) * m.get(i, colIndex);
                }
            }
        }

        return result;
    }

    double frobenius(){
        double result = 0.0;
        for (int index = 0; index < this.rows*this.cols; index++){
            result += Math.pow(this.data[index],2);
        }
        result = Math.sqrt(result);
        return result;
    }

    public static Matrix random(int rows, int cols, long seed){
        Matrix m = new Matrix(rows,cols);
        Random r = new Random(seed);

        for (int rowIndex = 0; rowIndex < m.rows; rowIndex++) {
            for (int colIndex = 0; colIndex < m.cols; colIndex++) {
                m.set(rowIndex, colIndex, r.nextDouble());
            }
        }

        return m;
    }

    public static Matrix eye(int n){
        double[][] data = new double[n][n];
        int index = 0;

        while (index < n){
            data[index][index] = 1.0;
            index++;
        }
        Matrix m = new Matrix(data);
        return m;
    }

    void swapRows(int row1, int row2){
        if (row1 >= this.rows || row2 >= this.rows){
            throw new ArrayIndexOutOfBoundsException("Row index out of bounds");
        }
        for(int col = 0; col < this.cols; col++){
            double temp = this.get(row1, col);
            this.set(row1, col, this.get(row2,col));
            this.set(row2, col, temp);
        }
    }

    double determinant(){
        if (this.rows != this.cols){
            throw new RuntimeException("Matrix must be square");
        }

        int rowsChangeCount = 0;
        int n = this.rows;
        Matrix m = new Matrix(n,n);
        m.copy(this);

        for (int i=0; i < n; i++){

            if (m.get(i,i) == 0.0){
                boolean swapped = false;

                for(int check = i+1; check < n; check++){
                    if (m.get(check, i) != 0.0){
                       m.swapRows(i, check);
                       rowsChangeCount++;
                       swapped = true;
                       break;
                    }
                }
                if (!swapped){
                    return 0.0;
                }
            }

            for (int row = i+1; row < n; row++){
                  if (m.get(row, i) != 0.0){
                      double factor = (-1)*(m.get(row, i) / m.get(i, i));
                      for (int col = i; col < n; col++){
                          double newValue = m.get(row, col) + m.get(i, col) * factor;
                         m.set(row, col, newValue);
                      }
                  }
            }
        }

        double result = 1;

        for (int i=0; i < n; i++){
            result *= m.get(i,i);
        }

        result *= Math.pow((-1),rowsChangeCount);
        return result;
    }

    Matrix invert() {
        if (this.rows != this.cols) {
            throw new RuntimeException("Matrix must be square to use this method");
        }
        double determinant = this.determinant();
        if (determinant == 0.0) {
            throw new RuntimeException("Determinant is zero, matrix is singular and cannot be inverted");
        }
        int n = this.rows;
        Matrix augmented = new Matrix(n, 2 * n);
        Matrix identity = Matrix.eye(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmented.set(i, j, this.get(i, j));
                augmented.set(i, j + n, identity.get(i, j));
            }
        }
        for (int i = 0; i < n; i++) {
            if (augmented.get(i, i) == 0) {
                boolean swapped = false;
                for (int k = i + 1; k < n; k++) {
                    if (augmented.get(k, i) != 0) {
                        augmented.swapRows(i, k);
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    throw new RuntimeException("Matrix is singular and cannot be inverted");
                }
            }
            double pivot = augmented.get(i, i);
            for (int j = 0; j < 2 * n; j++) {
                augmented.set(i, j, augmented.get(i, j) / pivot);
            }
            for (int k = 0; k < n; k++) {
                if (k != i) {
                    double factor = augmented.get(k, i);
                    for (int j = 0; j < 2 * n; j++) {
                        augmented.set(k, j, augmented.get(k, j) - factor * augmented.get(i, j));
                    }
                }
            }
        }
        Matrix inverse = new Matrix(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverse.set(i, j, augmented.get(i, j + n));
            }
        }

        return inverse;
    }
}
