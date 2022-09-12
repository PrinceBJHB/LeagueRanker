import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.PrivateMethodTester

class test_ extends AnyFlatSpec with Matchers with PrivateMethodTester {
  "Given the match results as list of strings, the Calculator.calculatePoints method" should "return a map containing the team points" in {
    val testResults: List[String] = List(
      "Lions 3, Snakes 3",
      "Tarantulas 1, FC Awesome 0",
      "Lions 1, FC Awesome 1",
      "Tarantulas 3, Snakes 1",
      "Lions 4, Grouches 0"
      )

    val expectedPoints: Map[String, Int] = Map("FC Awesome" -> 1, "Snakes" -> 1, "Lions" -> 5, "Grouches" -> 0, "Tarantulas" -> 6)

    val calculatePoints = PrivateMethod[Map[String, Int]]('calculatePoints)
    val points = Calculator invokePrivate calculatePoints(testResults)

    points shouldEqual expectedPoints
  }

  "Given a map of points, the Calculator.getRanking method" should "return a sorted list of the rankings" in {
    val testPoints: Map[String, Int] = Map("FC Awesome" -> 1, "Snakes" -> 1, "Lions" -> 5, "Grouches" -> 0, "Tarantulas" -> 6)
    val expectedRankings: List[String] = List(
      "1. Tarantulas, 6 pts",
      "2. Lions, 5 pts",
      "3. FC Awesome, 1 pt",
      "3. Snakes, 1 pt",
      "5. Grouches, 0 pts"
    )

    val getRanking = PrivateMethod[List[String]]('getRanking)
    val ranking = Calculator invokePrivate getRanking(testPoints)

    ranking shouldEqual expectedRankings
  }

  "Given a map of points with unordered names, the Calculator.getRanking method" should "return an alphabetically sorted list" in {
    val testPoints: Map[String, Int] = Map("TeamE" -> 0, "TeamF" -> 3, "TeamC" -> 0, "TeamD" -> 3, "TeamA" -> 0, "TeamB" -> 3)
    val expectedRankings: List[String] = List(
      "1. TeamB, 3 pts",
      "1. TeamD, 3 pts",
      "1. TeamF, 3 pts",
      "4. TeamA, 0 pts",
      "4. TeamC, 0 pts",
      "4. TeamE, 0 pts"
    )

    val getRanking = PrivateMethod[List[String]]('getRanking)
    val ranking = Calculator invokePrivate getRanking(testPoints)

    ranking shouldEqual expectedRankings
  }
}
