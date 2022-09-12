import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MainTest {

  @Test
  public void test6() {
    final String actual = String.valueOf(Main.solveA(2, 11, 4));
    assertEquals("4", actual);
  }

  @Test
  public void test8() {
    final String actual = String.valueOf(Main.solveA(51, 90, 3));
    assertEquals("1", actual);
  }

  @Test
  public void test12() {
    final String actual = String.valueOf(Main.solveA(1, 100, 99));
    assertEquals("99", actual);
  }

  @Test
  public void test1() {
    final String actual = String.valueOf(Main.solveA(100, 1, 10));
    assertEquals("0", actual);
  }

  @Test
  public void test10() {
    final String actual = String.valueOf(Main.solveA(3, 86, 28));
    assertEquals("28", actual);
  }

  @Test
  public void test3() {
    final String actual = String.valueOf(Main.solveA(5, 49, 10));
    assertEquals("9", actual);
  }

  @Test
  public void test7() {
    final String actual = String.valueOf(Main.solveA(2, 1, 100));
    assertEquals("0", actual);
  }

  @Test
  public void test11() {
    final String actual = String.valueOf(Main.solveA(1, 100, 100));
    assertEquals("100", actual);
  }

  @Test
  public void test9() {
    final String actual = String.valueOf(Main.solveA(11, 66, 5));
    assertEquals("5", actual);
  }

  @Test
  public void test0() {
    final String actual = String.valueOf(Main.solveA(3, 9, 5));
    assertEquals("3", actual);
  }

  @Test
  public void test2() {
    final String actual = String.valueOf(Main.solveA(4, 99, 23));
    assertEquals("23", actual);
  }

  @Test
  public void test4() {
    final String actual = String.valueOf(Main.solveA(8, 64, 9));
    assertEquals("8", actual);
  }

  @Test
  public void test5() {
    final String actual = String.valueOf(Main.solveA(100, 100, 100));
    assertEquals("1", actual);
  }

}
