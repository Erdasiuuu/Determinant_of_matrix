import java.util.Scanner;

public class Main {
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
    while (choice != Constants.EXIT.getValue()) {
      printMenu();
      choice = scanner.nextInt();
      scanner.nextLine();
      switch (choice) {
        case Constants.INPUT.getValue():
          matrix.fillMatrix(scanner);
          break;
        case Constants.CALC.getValue():
          matrix.calculateMatrix();
          break;
        case Constants.OUTPUT.getValue():
          matrix.printMatrix();
          break;
        case Constants.EXIT.getValue():
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
}
