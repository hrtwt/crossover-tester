import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MainTest {

  @Test
  public void test3() {
    final String actual = String.valueOf(Main.solveA(79445));
    assertEquals("158890", actual);
  }

  @Test
  public void test0() {
    final String actual = String.valueOf(Main.solveA(2));
    assertEquals("2", actual);
  }

  @Test
  public void test10() {
    final String actual = String.valueOf(Main.solveA(262703497));
    assertEquals("525406994", actual);
  }

  @Test
  public void test5() {
    final String actual = String.valueOf(Main.solveA(999999999));
    assertEquals("1999999998", actual);
  }

  @Test
  public void test1() {
    final String actual = String.valueOf(Main.solveA(36426));
    assertEquals("36426", actual);
  }

  @Test
  public void test6() {
    final String actual = String.valueOf(Main.solveA(90081));
    assertEquals("180162", actual);
  }

  @Test
  public void test7() {
    final String actual = String.valueOf(Main.solveA(476190629));
    assertEquals("952381258", actual);
  }

  @Test
  public void test8() {
    final String actual = String.valueOf(Main.solveA(3));
    assertEquals("6", actual);
  }

  @Test
  public void test4() {
    final String actual = String.valueOf(Main.solveA(10));
    assertEquals("10", actual);
  }

  @Test
  public void test2() {
    final String actual = String.valueOf(Main.solveA(1));
    assertEquals("2", actual);
  }

  @Test
  public void test11() {
    final String actual = String.valueOf(Main.solveA(1000000000));
    assertEquals("1000000000", actual);
  }

  @Test
  public void test9() {
    final String actual = String.valueOf(Main.solveA(433933447));
    assertEquals("867866894", actual);
  }

}
