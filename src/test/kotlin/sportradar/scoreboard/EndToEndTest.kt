package sportradar.scoreboard

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import sportradar.scoreboard.domain.Game
import sportradar.scoreboard.domain.GameService
import sportradar.scoreboard.domain.ScoreBoard
import sportradar.scoreboard.domain.Team
import sportradar.scoreboard.infrastructure.GameRepository
import java.util.*

class EndToEndTest {
    private val gameRepository = GameRepository()
    private val gameService = GameService(gameRepository)
    private val scoreBoardTest = ScoreBoard(gameRepository)

    @Test
    fun `Board keeps track of the state of the games`() {
        val timeStamp = System.currentTimeMillis()
        val ancientGame = Game(
            UUID.randomUUID(),
            Team("Ancient Rome"),
            Team("Ancient Greece"),
            timeStamp
        )
        gameService.addGame(ancientGame)
        gameService.finishGame(ancientGame.id)

        val norwaySweden = Game(
            UUID.randomUUID(),
            Team("Norway"),
            Team("Sweden"),
            timeStamp
        )
        gameService.addGame(norwaySweden)

        val ukFrance = Game(
            UUID.randomUUID(),
            Team("UK"),
            Team("France"),
            timeStamp + 1
        )
        gameService.addGame(ukFrance)

        gameService.updateScore(norwaySweden.id, 1 to 3)
        gameService.updateScore(ukFrance.id, 2 to 3)
        gameService.updateScore(norwaySweden.id, 2 to 3)

        scoreBoardTest.liveScore().joinToString(separator = "\n") { it.displayValue() } shouldBe """
            UK 2 - France 3
            Norway 2 - Sweden 3
        """.trimIndent()

    }
}