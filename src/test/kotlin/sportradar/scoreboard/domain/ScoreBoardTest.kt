package sportradar.scoreboard.domain

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import sportradar.scoreboard.infrastructure.GameRepository
import java.util.*


class ScoreBoardTest {
    private val gameRepository = GameRepository()
    private val scoreBoard = ScoreBoard(gameRepository)

    @Test
    fun `No games results in empty ScoreBoard`() {
        scoreBoard.liveScore().shouldBeEmpty()
    }

    @Test
    fun `ScoreBoard excludes finished games`() {
        saveGames(
            Game(UUID.randomUUID(), Team("Mexico"), Team("Canada"), System.currentTimeMillis(), 2 to 2, true)
        )
        scoreBoard.liveScore().shouldBeEmpty()
    }

    @Test
    fun `Live score reflects the games in the expected order`() {
        val now = System.currentTimeMillis()
        saveGames(
            Game(UUID.randomUUID(), Team("Mexico"), Team("Canada"), now + 1, 0 to 5),
            Game(UUID.randomUUID(), Team("Spain"), Team("Brazil"), now + 2, 10 to 2),
            Game(UUID.randomUUID(), Team("Germany"), Team("France"), now + 3, 2 to 2),
            Game(UUID.randomUUID(), Team("Uruguay"), Team("Italy"), now + 4, 6 to 6),
            Game(UUID.randomUUID(), Team("Argentina"), Team("Australia"), now + 5, 3 to 1)
        )
        scoreBoard.liveScore().joinToString(separator = "\n") { it.displayValue() } shouldBe """
            Uruguay 6 - Italy 6
            Spain 10 - Brazil 2
            Mexico 0 - Canada 5
            Argentina 3 - Australia 1
            Germany 2 - France 2
        """.trimIndent()
    }

    private fun saveGames(vararg games: Game) = games.forEach { gameRepository.saveGame(it) }
}