import scala.io.StdIn.readLine
import scala.io.Source
import scala.collection.mutable.ListBuffer
import scala.collection.immutable.ListMap

object LeagueRanker {
    var filename: String = ""

    def main(args: Array[String]) {
        if (args.size > 0) filename = args(0)

        val results = getLines()
        val points = calculatePoints(results)
        val rankings = getRanking(points)

        printRankings(rankings)
    }

    private def printRankings(rankings: List[String]) {
        rankings.foreach(x => println(x))
    }

    private def getLines(): List[String] = {
        var lines = ListBuffer[String]()
        var line: String = ""

        if (filename.isEmpty) {
            println("Input match score:")

            do {
                line = readLine()
                if (!line.isEmpty) lines += line
            } while (!line.isEmpty)
        } else {
            for (l <- Source.fromFile(filename).getLines) {
                if (!l.isEmpty) lines += l
            }
        }

        return lines.toList
    }

    private def getNameAndScore(team: String): Array[String] = {
        var splitResults: Array[String] = team.reverse.split(" ", 2).map(x => x.reverse)
        return splitResults
    }

    private def calculatePoints(input: List[String]): Map[String, Int] = {
        var results = scala.collection.mutable.Map[String, Int]()
        input.foreach { line =>
            val teams: Array[Array[String]] = line.split(",").map(x => getNameAndScore(x.trim()))
            val teamA: Array[String] = teams(0)
            val teamB: Array[String] = teams(1)

            if (!results.contains(teamA(1))) results += ((teamA(1), 0))
            if (!results.contains(teamB(1))) results += ((teamB(1), 0))

            if (teamA(0).toInt > teamB(0).toInt) {
                results(teamA(1)) += 3
            } else if (teamA(0).toInt == teamB(0).toInt) {
                results(teamA(1)) += 1
                results(teamB(1)) += 1
            } else {
                results(teamB(1)) += 3
            }
        }
        return results.toMap
    }

    private def getRanking(results: Map[String, Int]): List[String] = {
        val alphabeticalResults: Map[String, Int] = ListMap(results.toSeq.sortBy(_._1):_*)
        val sortedResults: Map[String, Int] = ListMap(alphabeticalResults.toSeq.sortWith(_._2 > _._2):_*)
        var i, rank, prevValue = 0
        var rankings = ListBuffer[String]()

        for ((k, v) <- sortedResults) {
            if (v != prevValue) rank = i + 1

            if (v == 1)
                rankings += s"$rank. $k, $v pt"
            else
                rankings += s"$rank. $k, $v pts"
            
            prevValue = v
            i += 1
        }

        return rankings.toList
    }
}