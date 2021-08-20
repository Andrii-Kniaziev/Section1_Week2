package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = (e: Int) => e > 5
    val s5 = (e: Int) => e < 10
    val s6 = (e: Int) => e >= 5 && e <= 10


  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersect") {
    new TestSets:
      val s = intersect(s4, s5)
      assert(contains(s, 6), "Intersect 6")
      assert(contains(s, 9), "Intersect 9")
      assert(!contains(s, 10), "Intersect out of bound")
  }

  test("exists") {
    new TestSets:
      val f = (e: Int) => e * e >= 36
      val f1 = (e: Int) => e * e < 36
      assert(exists(s4, f), "exists square of (e) that greater or equal to 36, where e > 5")
      assert(!exists(s4, f1), "does not exist square of (e) that less then 36, where e > 5")
  }

  test("map") {
    new TestSets:
      val mapped = map(s6, (x) => x + 10)
      assert(contains(mapped, 15), "new set contsins 15")
      assert(contains(mapped, 20), "new set contsins 20")
      assert(!contains(mapped, 14), "new set does not contain 14")
      assert(!contains(mapped, 21), "new set does not contain 21")
      assert(contains(s6, 10) && !contains(mapped, 10), "old set contains value that new set doesn`t contain")
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds

