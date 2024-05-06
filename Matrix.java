import java.util.Random;
import java.util.Scanner;

public class Matrix {
  private boolean matrixExist;
  private boolean matrixCalculate;
  private int size = Constants.MAX_SIZE.getValue();
  private double[][] matrix = new double[size][size];
  private int row;
  private int col;
  private double det = 1;
  private final double EPS = 1e-8;
  private Scanner scanner = new Scanner(System.in);

  /**
   * @brief Начало заполнения матрицы
   *
   * Предыдущие введенные данные обнуляются и предлагаются варианты
   * заполнения матрицы(вручную/случайно), далее запрашивается
   * столбец и строка для их последующего удаления
   */
  public void fillMatrix(Scanner scanner) {
    Main.printInputMenu();
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
          Main.printWarningLen();
          fillMatrixManual();
          break;
        case 2:
          Main.printWarningLen();
          fillMatrixRandom();
          break;
        default:
          Main.printErrorInput();
          Main.printInputMenu();
          break;
      }
    } while (choice != 1 && choice != 2);
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
    while (size <= 0 || size > Constants.MAX_SIZE.getValue()) {
      System.out.printf("Введите размер матрицы\n");
      size = scanner.nextInt();
      scanner.nextLine();
      if (size <= 0 || size > Constants.MAX_SIZE.getValue()) {
        System.out.printf("Число x не удовлетворяет одному из условий\nx > 0 и x <= 100\n\n");
      }
    }
    for (int i = 0; i < size; ++i) {
      for (int j = 0; j < size; ++j) {
        matrix[i][j] = (double) (random.nextInt()) % 150;
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
    } else {
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
        if (col == j) {
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
        matrix[i][j] -= div * matrix[index][j];
      }
    }
  }

  public void inputRowCol(Scanner scanner) {
    int value = 0;
    for (int i = Constants.COLUMN.getValue(); i <= Constants.ROW.getValue(); i++) {
      while (checkValidRowCol(value) == false) {
        printInputRowColMenu(i);
        value = scanner.nextInt();
        scanner.nextLine();
        if (checkValidRowCol(value) == false) {
          System.out.printf(
              "Число x не удовлетворяет одному из условий\nx > 0 и x <= размер матрицы\n\n");
        }
      }
      if (i == Constants.COLUMN.getValue()) {
        col = value;
      } else if (i == Constants.ROW.getValue()) {
        row = value;
      }
      value = 0;
    }
  }

  public boolean checkValidRowCol(int value) {
    return value > 0 && value <= size;
  }

  public void printInputRowColMenu(int index) {
    if (index == Constants.COLUMN.getValue()) {
      System.out.printf("Введите номер столбца\n");
    } else if (index == Constants.ROW.getValue()) {
      System.out.printf("Введите номер строки\n");
    }
  }

  public void printMatrix() {
    if (matrixCalculate == false) {
      System.out.printf("\nНе проведены вычисления\n");
    } else {
      System.out.printf("\nРезультат\n\n", det);
      matrixOut();
      System.out.printf("Определитель исходной матрицы равен %f\n\n", det);
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
