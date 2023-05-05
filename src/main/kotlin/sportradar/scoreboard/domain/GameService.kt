package sportradar.scoreboard.domain

import sportradar.scoreboard.infrastructure.GameRepository
import java.util.*

class GameService(
    private val gameRepo: GameRepository
) {
    fun addGame(game: Game) = gameRepo.saveGame(game)

    fun updateScore(id: UUID, score: Pair<Int, Int>) {
        gameRepo.saveGame(
            gameRepo.findById(id)!!
                .withScore(score)
        )
    }

    fun finishGame(id: UUID) {
        gameRepo.saveGame(
            gameRepo.findById(id)!!
                .withFinished()
        )
    }
}