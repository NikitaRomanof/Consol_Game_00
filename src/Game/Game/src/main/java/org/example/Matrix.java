package org.example;

import java.util.Arrays;
import java.util.Objects;

public class Matrix {
    private int row;
    private int col;
    private int[][] matrix;

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        matrix = new int[this.row][this.col];
    }

    public Matrix(int size) {
        this.row =  size;
        this.col =  size;
        matrix = new int[this.row][this.col];
    }

    public void fillMatrix(int fillNumber) {
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                matrix[i][j] = fillNumber;
            }
        }
    }

    public void printMatrix() {
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    public Matrix() {
        row = 1;
        col = 1;
        matrix = new int[row][col];
    }

    public void setRow(int row) {
        if (row <= 0) {
            throw new ArithmeticException("setRow: value must be greater than zero");
        }
        this.row = row;
        matrix = new int[this.row][col];
    }

    public void setCol(int col) {
        if (col <= 0) {
            throw new ArrayIndexOutOfBoundsException("setCol: value must be greater than zero");
        }
        this.col = col;
        matrix = new int[row][this.col];
    }

    public void setMatrix(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new ArrayIndexOutOfBoundsException("setMatrix: value must be greater than zero");
        }
        this.row = row;
        this.col = col;
        matrix = new int[this.row][this.col];
    }

    public void setOneCell(int posRow, int posCol, int value) {
        if (row < 0 || col < 0 || posRow > row || posCol > col) {
            throw new ArrayIndexOutOfBoundsException("setOneCell: value must be greater -1 and less row/col");
        }
        matrix[posRow][posCol] = value;
    }

    public double getOneCell(int posRow, int posCol) {
        if (row < 0 || col < 0 || posRow > row || posCol > col) {
            throw new ArrayIndexOutOfBoundsException("getOneCell: value must be greater -1 and less row/col");
        }
        return matrix[posRow][posCol];
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    private void sumOrSubMatrix(Matrix other, int sign) {
        if (row != other.row || col != other.col) {
            throw new ArrayIndexOutOfBoundsException("sumOrSubMatrix: Rows/columns of matrices are not equal");
        }
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                matrix[i][j] += (sign * other.matrix[i][j]);
            }
        }
    }

    public void sumMatrix(Matrix other) {
        sumOrSubMatrix(other, 1);
    }

    public void subMatrix(Matrix other) {
        sumOrSubMatrix(other, -1);
    }


    public void mulMatrixOnNumber(int number) {
        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                matrix[i][j] *= number;
            }
        }
    }

    public void mulMatrix(Matrix other) {
        if (col != other.row) {
            throw new ArithmeticException("mulMatrix: Columns are not equal to rows");
        }
        Matrix tmp = new Matrix(row, other.col);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < other.col; j++) {
                for (int n = 0; n < other.row; n++) {
                    tmp.matrix[i][j] += matrix[i][n] * other.matrix[n][j];
                }
            }
        }
        col = other.col;
        matrix = tmp.matrix;
    }

    public Matrix transpose() {
        Matrix tmp = new Matrix(col, row);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                tmp.matrix[j][i] = matrix[i][j];
            }
        }
        return tmp;
    }

    private Matrix getReducedMatrix(int rowsReduced, int colsReduced) {
        Matrix tmp = new Matrix(row - 1, col - 1);
        int newRows = 0, newCols = 0;
        for (int i = 0; i < row; i++) {
            if (i != rowsReduced) {
                for (int j = 0; j < row; j++) {
                    if (j != colsReduced) {
                        tmp.matrix[newRows][newCols] = matrix[i][j];
                        newCols++;
                    }
                }
                newRows++;
                newCols = 0;
            }
        }
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix1 = (Matrix) o;
        return row == matrix1.row && col == matrix1.col && Arrays.equals(matrix, matrix1.matrix);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(row, col);
        result = 31 * result + Arrays.hashCode(matrix);
        return result;
    }
}
