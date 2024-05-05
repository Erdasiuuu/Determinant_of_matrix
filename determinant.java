import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Random;

public class Main {
    
    private static final int INPUT = 1;
    private static final int CALC = 2;
    private static final int OUTPUT = 3;
    private static final int EXIT = 4;
    private static final int MAX_SIZE = 100;
    private static final double EPS = 1e-6;
    
    private static final int COLUMN = 1;
    private static final int ROW = 2;
    
	public static void main(String[] args) {
		int choice = 0;
		Matrix matrix = new Matrix();
		Scanner scanner = new Scanner(System.in);
		while(choice != EXIT) {
		    printMenu();
		    choice = scanner.nextInt();
            scanner.nextLine();
    		switch (choice) {
    			case INPUT:
    				matrix.fillMatrix(scanner);
    				break;
    			case CALC:
    			    matrix.calculateMatrix();
    			    break;
    			case OUTPUT:
    			    matrix.printMatrix();
    			    break;
    			case EXIT:
    			    break;
    			default:
                    printErrorInput();			
    			    break;
    		}
		}
	}
	
	public static void printMenu() {
        System.out.printf("Введите один из вариантов меню.\n");
        System.out.printf("1. Ввод данных (вручную/случайно). \n");
        System.out.printf("2. Вычислить матрицу и найти опеределитель\n");
        System.out.printf("3. Вывести матрицу\n");
        System.out.printf("4. Завершение программы\n");
	}
	
	public static void printInputMenu() {
        System.out.printf("\nВведите один из вариантов меню.\n");
        System.out.printf("1. Вручную.\n");
        System.out.printf("2. Случайно.\n");
    }
	
    public static void printErrorInput() {
            System.out.printf("\nНеверный ввод. Попробуйте еще раз\n");
    }
    
    public static void printWarningLen() {
        System.out.printf("\nРазмер матрицы не может быть более 100х100\n");
    }
    
    public static class Matrix {
    	private boolean matrixExist;
    	private boolean matrixCalculate;
    	private int size = MAX_SIZE;
    	private double[][] matrix = new double[size][size];
    	private int row;
    	private int col;
    	private double det = 1;
    	private Scanner scanner = new Scanner(System.in);
    
        public void fillMatrix(Scanner scanner) {
            printInputMenu();
            int choice = 0;
            matrixExist = true;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = 0;
                }
            }
            do {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                    printWarningLen();
                    fillMatrixManual();
                    size = 7;
                    matrixOut();
                    break;
                    case 2:
                    printWarningLen();
                    fillMatrixRandom();
                    break;
                    default:
                    printErrorInput();
                    printInputMenu();
                    break;
                }
            } while(choice != 1 && choice != 2);
            inputRowCol(scanner);
            matrixCalculate = false;
        }
        
    	public void fillMatrixManual() {
    		for (int i = 0; i < size; i++) {
    			String line = scanner.nextLine();
    			String[] splitLine = line.split("\\s+");
    			if (i == 0) {
    			    size = splitLine.length;
    			}
    			fillMatrixLine(i, splitLine);
    		}
    	}
    	
    	public void fillMatrixLine(int index, String line[]) {
	        for (int i = 0; i < line.length && i < size; i++) {
	            matrix[index][i] = Double.parseDouble(line[i]);
	        }
    	}
    	
    	public void fillMatrixRandom() {
    	    Random random = new Random();
    	    size++;
    	    while (size <= 0 || size > 100) {
    	        System.out.printf("Введите размер матрицы\n"); 
    	        size = scanner.nextInt();
                scanner.nextLine();
    	        if (size <= 0 || size > 100) {
    	            System.out.printf("Число x не удовлетворяет одному из условий\nx > 0 и x <= 100\n\n");   
    	        }
    	    }
    	    for (int i = 0; i < size; ++i) {
    	        for (int j = 0; j < size; ++j) {
    	            matrix[i][j] = (double)(random.nextInt()) % 150;
    	        }
    	    }
    	    System.out.printf("Сгенерированная матрица\n"); 
    	    matrixOut();
    	}
    	
    	public void calculateMatrix() {
    	    if (matrixExist == true) {
    	        matrixCalculate = true;
    	        double[][] copy = new double[size][size];
    	        for (int i = 0; i < size; i++) {
    	            copy[i] = matrix[i].clone();
    	        }
    	        findDet();
       	        for (int i = 0; i < size; i++) {
    	            matrix[i] = copy[i].clone();
    	        }
    	        getSmallMatrix();
    	        System.out.printf("\nВычисления завершены\n");
    	    }
    	    else {
    	        System.out.printf("\nНе введена матрица\n");
    	    }
    	}
    	
    	public void getSmallMatrix() {
    	   int row_correct = 0;
    	   size--;
    	   row--;
    	   col--;
            for (int i = 0; i < size; i++) {
                    int col_correct = 0;
                    for (int j = 0; j < size; j++) {
                            if (row == i) {
                                    row_correct = 1;
                            }
                            if (col== j) {
                                    col_correct = 1;
                            }
                            matrix[i][j] = matrix[i + row_correct][j + col_correct];
                    }
            }
    	}
    	
    	public void findDet() {
    	    for (int i = 0; i < size && det != 0; i++) {
    	        upBiggerValue(i);
    	        if (Math.abs(matrix[i][i]) >= EPS) {
    	            diffRows(i);
    	        }
    	        det *= matrix[i][i];
    	    }
    	}
    	
    	public void upBiggerValue(int index) {
    	    int max_index = index;
    	    double max = Math.abs(matrix[index][index]);
    	    for (int i = index + 1; i < size; i++) {
    	        if (Math.abs(matrix[i][index]) > max) {
    	            max_index = i;
    	            max = Math.abs(matrix[i][index]);
    	        }
    	    }
    	    if (max_index != index) {
    	        double[] tmp = matrix[index].clone();
    	        matrix[index] = matrix[max_index];
    	        matrix[max_index] = tmp.clone();
    	        det = -det;
    	    }
    	}
    	
    	public void diffRows(int index) {
    	    for (int i = index + 1; i < size; i++) {
    	        double div = matrix[i][index] / matrix[index][index];
                for (int j = index; j < size; j++) {
                        matrix[i][j] -= div * matrix[index][j];
                }
    	    }
    	}
    	
    	public void inputRowCol(Scanner scanner) {
    	    int value = 0;
    	    for (int i = COLUMN; i <= ROW; i++) {
    	        while (checkValidRowCol(value) == false) {
    	            printInputRowColMenu(i);
    	            value = scanner.nextInt();
                    scanner.nextLine();
    	            if (checkValidRowCol(value) == false) {
    	                System.out.printf("Число x не удовлетворяет одному из условий\nx > 0 и x <= размер матрицы\n\n");
    	            }
    	        }
    	        if (i == COLUMN) {
    	            col = value;
    	        }
    	        else if (i == ROW) {
    	            row = value;
    	        }
    	        value = 0;
    	    }
    	}
    	
    	public boolean checkValidRowCol(int value) {
    	    return value > 0 && value <= size;
    	}
    	
    	public void printInputRowColMenu(int index) {
    	    if (index == COLUMN) {
    	        System.out.printf("Введите номер столбца\n");
    	    }
    	    else if (index == ROW) {
    	        System.out.printf("Введите номер строки\n");
    	    }
    	}
    	
    	public void printMatrix() {
    	    if (matrixCalculate == false) {
    	        System.out.printf("\nНе проведены вычисления\n");
    	    }
            else {
                matrixOut();
                System.out.printf("Определитель исходной матрицы равен %f\n", det);
            }
    	}
    
        public void matrixOut() {
            int maxLen = findMaxLen() + 3;
            for (int i = 0; i < size; ++i) {
    			for (int j = 0; j < size; ++j) {
    			    int len = elemLen(matrix[i][j]);
    			    for (int blank = 0; blank < maxLen - len; blank++) {
    			        System.out.printf(" ");
    			    }
    				System.out.printf("%f", matrix[i][j]);
    			}
    			System.out.printf("\n");
    		}
        }
        
        public int findMaxLen() {
            int maxLen = elemLen(matrix[0][0]);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int len = elemLen(matrix[i][j]);
                    if (maxLen < len) {
                        maxLen = len;
                    }
                }
            }
            return maxLen;
        }
        
        public int elemLen(double value) {
            int len = Math.abs(value) < 1 ? 1 : 0;
            if (value < 0) {
                len++;
            }
            value = trunc(value);
            while (Math.abs(value) >= 1) {
                value /= 10;
                len++;
            }
            return len;
        }
        
        public double trunc(double value) {
            if (value > 0) {
                value = Math.floor(value);
            }
            else {
                value = Math.ceil(value);
            }
            return value;
        }
    }
}
