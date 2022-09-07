package example;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ABC139_ATest {

  @Test
  public void test1() {
    final String actual = String.valueOf(ABC139_A.solveA("CSS", "CSR"));
    assertEquals("2", actual);
  }

  @Test
  public void test7() {
    final String actual = String.valueOf(ABC139_A.solveA("SRR", "CRR"));
    assertEquals("2", actual);
  }

  @Test
  public void test6() {
    final String actual = String.valueOf(ABC139_A.solveA("RSC", "RCS"));
    assertEquals("1", actual);
  }

  @Test
  public void test12() {
    final String actual = String.valueOf(ABC139_A.solveA("SSR", "SSR"));
    assertEquals("3", actual);
  }

  @Test
  public void test4() {
    final String actual = String.valueOf(ABC139_A.solveA("CSS", "SSC"));
    assertEquals("1", actual);
  }

  @Test
  public void test3() {
    final String actual = String.valueOf(ABC139_A.solveA("SSS", "SSS"));
    assertEquals("3", actual);
  }

  @Test
  public void test11() {
    final String actual = String.valueOf(ABC139_A.solveA("SRC", "RSC"));
    assertEquals("1", actual);
  }

  @Test
  public void test8() {
    final String actual = String.valueOf(ABC139_A.solveA("RRC", "CRS"));
    assertEquals("1", actual);
  }

  @Test
  public void test2() {
    final String actual = String.valueOf(ABC139_A.solveA("CCC", "SSS"));
    assertEquals("0", actual);
  }

  @Test
  public void test9() {
    final String actual = String.valueOf(ABC139_A.solveA("CRS", "CRS"));
    assertEquals("3", actual);
  }

  @Test
  public void test13() {
    final String actual = String.valueOf(ABC139_A.solveA("CCC", "SCS"));
    assertEquals("1", actual);
  }

  @Test
  public void test10() {
    final String actual = String.valueOf(ABC139_A.solveA("RRR", "SSS"));
    assertEquals("0", actual);
  }

  @Test
  public void test5() {
    final String actual = String.valueOf(ABC139_A.solveA("RSC", "RCC"));
    assertEquals("2", actual);
  }

  @Test
  public void test0() {
    final String actual = String.valueOf(ABC139_A.solveA("RCR", "CRC"));
    assertEquals("0", actual);
  }
}
