package sportradar.scoreboard.infrastructure

import sportradar.scoreboard.domain.Game
import java.util.*

/**
 * In-memory storage of games
 */
class GameRepository {
    private var games: Map<UUID, Game> = mapOf()

    fun findInProgressGames() = allGames().filter { !it.finished }

    fun findById(id: UUID): Game? = games[id]

    fun saveGame(game: Game) {
        games = games + (game.id to game)
    }

    internal fun allGames(): List<Game> = games.values.toList()
}