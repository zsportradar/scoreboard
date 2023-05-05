package sportradar.scoreboard.domain

import sportradar.scoreboard.infrastructure.GameRepository

/**
 * The score board providing live score summary
 */
class ScoreBoard(
    private val games: GameRepository
) {
    fun liveScore(): List<GameSummary> =
        games.findInProgressGames()
            .sortedWith(compareBy<Game>({ it.totalScore() }, { it.timeStamp }).reversed())
            .map { GameSummary(it) }
}

class GameSummary(private val game: Game) {
    fun displayValue() = "${game.homeTeam.name} ${game.score.first} - ${game.awayTeam.name} ${game.score.second}"
}
