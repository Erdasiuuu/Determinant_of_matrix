import java.util.Scanner;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Random;

public class Main {
    
    /**
     * @brief Константные переменные для дальнейшего использования
     * 
     */
    private static final int INPUT = 1;
    private static final int CALC = 2;
    private static final int OUTPUT = 3;
    private static final int EXIT = 4;
    private static final int MAX_SIZE = 100;
    private static final double EPS = 1e-10;
    
    private static final int COLUMN = 1;
    private static final int ROW = 2;
    
    /**
     * @brief Программа для подсчета определителя
     * 
     * Программа в виде простого меню, которое имеет 4 опции
     * 1. Ввод данных
     * 2. Вычисление опеределителя и матрицы с меньшими размерами
     * 3. Вывод данных
     * 4. Завершение
     * 
     * Все необходимые данные будут запрашиваться до тех пор, пока не будут введены
     * полностью корректно(кроме ручного ввода матриц). Например, при неправильном вводе 
     * размера случайной матрицы программа снова будет заправшивать данные, до тех пор 
     * пока не будут введены корректные данные.
     * 
     * При ручном вводе данных размер матрицы оперделяется по количеству чисел в первой строке.
     * Если длина следующих строк после первой меньше, то оставшиеся элементы матрицы заполняются
     * нулями.
     * Например при вводе:
     * | 1 2 3 |
     * | 4 5   | 
     * | 6     |
     * будет создана матрица
     * | 1 2 3 |
     * | 4 5 0 |
     * | 6 0 0 |
     * 
     * Максимальный размер матрицы 100х100
     * При вводе первой строки длиннее 100 элементов все элементы после сотого будут утеряны,
     * размер матрицы будет равен 100.
     * Аналогично при вводе строк, которые будут длиннее первой элементы после максимальной длины
     * будут утеряны.
     * Например при вводе:
     * | 1 2 3 |
     * | 4 5 6 7 8 | 
     * | 9     |
     * будет создана матрица
     * | 1 2 3 |
     * | 4 5 6 |
     * | 9 0 0 |
     */
    
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
	
	private static void printMenu() {
        System.out.printf("Введите один из вариантов меню.\n");
        System.out.printf("1. Ввод данных (вручную/случайно). \n");
        System.out.printf("2. Вычислить матрицу и найти опеределитель\n");
        System.out.printf("3. Вывести матрицу\n");
        System.out.printf("4. Завершение программы\n");
	}
	
	private static void printInputMenu() {
        System.out.printf("\nВведите один из вариантов меню.\n");
        System.out.printf("1. Вручную.\n");
        System.out.printf("2. Случайно.\n");
    }
	
    private static void printErrorInput() {
            System.out.printf("\nНеверный ввод. Попробуйте еще раз\n");
    }
    
    private static void printWarningLen() {
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
    
        /**
         * @brief Начало заполнения матрицы
         * 
         * Предыдущие введенные данные обнуляются и предлагаются варианты
         * заполнения матрицы(вручную/случайно), далее запрашивается
         * столбец и строка для их последующего удаления
         */
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
        
        
        /**
         * @brief Ручное заполнения матрицы
         * 
         * Считываются строки, каждая разбивается по элементам(разделителем 
         * является пробел) и заполняет матрицу.
         */
    	public void fillMatrixManual() {
    		for (int i = 0; i < size; i++) {
    			String line = scanner.nextLine();
    			String[] splitLine = line.split("\\s+");
    			if (i == 0 && splitLine.length <= size) {
    			    size = splitLine.length;
    			}
    			for (int j = 0; j < splitLine.length && j < size; j++) {
                    matrix[i][j] = Double.parseDouble(splitLine[j]);   
	            }
    		}
    	}
    	
        /**
         * @brief Заполнение матрицы случайными числами
         * 
         */
    	
    	public void fillMatrixRandom() {
    	    Random random = new Random();
    	    size = 0;
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
    	
        /**
         * @brief Начало вычислений над матрицой
         * 
         */
    	
    	public void calculateMatrix() {
    	    if (matrixExist == true && matrixCalculate == false) {
    	        matrixCalculate = true;
    	        det = 1;
    	        double[][] copy = new double[size][size];
    	        for (int i = 0; i < size; i++) {
    	            copy[i] = matrix[i].clone();
    	        }
    	        findDet();
       	        for (int i = 0; i < size; i++) {
    	            matrix[i] = copy[i].clone();
    	        }
    	        getSmallMatrix();
    	    }
    	    else {
    	        System.out.printf("\nНе введена матрица\n");
    	    }
    	    if (matrixExist == true) {
    	        System.out.printf("\nВычисления завершены\n");
    	    }
    	}
    	
        /**
         * @brief Получение меньшей матрицы
         * 
         * Т.к матрица будет на 1 порядок меньше, то необходимо уменьшить размер.
         * Номер строки и стоолбца нужно уменьшить, т.к индексация массивов идет
         * с 0.
         */
    	
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
    	
        /**
         * @brief вычисления опеределителя
         * 
         * Проходимся по каждому столбцу и подымаем наверх самое большое абсолютное
         * число. Нужно привести матрицу к треугольному виду, чтобы можно было
         * вычислить определитель с помощью перемножения всех элементов главной
         * диагонали.
         */
    	
    	public void findDet() {
    	    for (int i = 0; i < size && Math.abs(det) >= EPS; i++) {
    	        upBiggerValue(i);
    	        if (Math.abs(matrix[i][i]) >= EPS) {
    	            diffRows(i);
    	        }
    	        det *= matrix[i][i];
    	    }
    	}
    	
        /**
         * @brief Меняем самую верхнюю строку со строкой, в столбце которого
         * находится максимальный элемент.
         * 
         */
    	
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
                        matrix[i][j]-= div * matrix[index][j];
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
    
        /**
         * @brief Вывод матрицы на экран
         * 
         */
    
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
        
        /**
         * @brief Поиск максимально длинного элемента
         * 
         */
        
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
        
        /**
         * @brief Вычисляется длина элемента.
         * 
         */
        
        public int elemLen(double value) {
            int len = Math.abs(value) < 1 ? 1 : 0;
            if (value < 0) {
                len++;
            }
            while (Math.abs(value) >= 1) {
                value /= 10;
                len++;
            }
            return len;
        }
    }
}
