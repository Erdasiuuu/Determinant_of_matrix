import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

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
    while (choice != EXIT) {
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
}
