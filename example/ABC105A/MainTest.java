import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MainTest {

  @Test
  public void test1() {
    final String actual = String.valueOf(Main.solveA(99, 33));
    assertEquals("0", actual);
  }

  @Test
  public void test7() {
    final String actual = String.valueOf(Main.solveA(100, 10));
    assertEquals("0", actual);
  }

  @Test
  public void test3() {
    final String actual = String.valueOf(Main.solveA(3, 89));
    assertEquals("1", actual);
  }

  @Test
  public void test5() {
    final String actual = String.valueOf(Main.solveA(78, 25));
    assertEquals("1", actual);
  }

  @Test
  public void test10() {
    final String actual = String.valueOf(Main.solveA(91, 7));
    assertEquals("0", actual);
  }

  @Test
  public void test6() {
    final String actual = String.valueOf(Main.solveA(100, 1));
    assertEquals("0", actual);
  }

  @Test
  public void test9() {
    final String actual = String.valueOf(Main.solveA(99, 49));
    assertEquals("1", actual);
  }

  @Test
  public void test11() {
    final String actual = String.valueOf(Main.solveA(27, 81));
    assertEquals("1", actual);
  }

  @Test
  public void test4() {
    final String actual = String.valueOf(Main.solveA(7, 3));
    assertEquals("1", actual);
  }

  @Test
  public void test2() {
    final String actual = String.valueOf(Main.solveA(21, 2));
    assertEquals("1", actual);
  }

  @Test
  public void test0() {
    final String actual = String.valueOf(Main.solveA(100, 100));
    assertEquals("0", actual);
  }

  @Test
  public void test8() {
    final String actual = String.valueOf(Main.solveA(1, 1));
    assertEquals("0", actual);
  }

}
