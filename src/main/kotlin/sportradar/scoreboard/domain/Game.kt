package sportradar.scoreboard.domain

import java.util.UUID

/**
 * Represents the state of a game
 */
data class Game(
    val id: UUID,
    val homeTeam: Team,
    val awayTeam: Team,
    val timeStamp: Long,
    val score: Pair<Int, Int> = 0 to 0,
    val finished: Boolean = false,
) {
    init {
        require(score.first >= 0 && score.second >= 0) { "Score must be non-negative" }
    }

    fun withScore(newScore: Pair<Int, Int>): Game = copy(score = newScore)

    fun withFinished(): Game = copy(finished = true)

    fun totalScore() = score.first + score.second
}

@JvmInline
value class Team(val name: String)
