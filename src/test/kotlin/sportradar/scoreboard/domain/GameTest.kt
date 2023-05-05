package sportradar.scoreboard.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.util.*

class GameTest {
    private val random = Random()

    @Test
    fun `New game has score of 0-0 and is in-progress`() {
        val game = createGame()

        game.score shouldBe (0 to 0)
        game.finished shouldBe false
    }

    @Test
    fun `Score can be updated`() {
        var game = createGame()
        game = game.withScore((1 to 0))

        game.score shouldBe (1 to 0)
    }

    @Test
    fun `Score shouldn't be negative`() {
        val game = createGame()

        shouldThrow<IllegalArgumentException> {
            game.withScore((1 to -1))
        }

        shouldThrow<IllegalArgumentException> {
            game.withScore((-1 to 1))
        }
    }

    @Test
    fun `Game can be finished`() {
        var game = createGame()
        game = game.withFinished()

        game.finished shouldBe true
    }

    @Test
    fun `Game keeps information on teams`() {
        val game = createGame()

        game.homeTeam.name shouldBe "The invincibles"
        game.awayTeam.name shouldBe "The vincibles"
    }

    private fun createGame() = Game(
        UUID.randomUUID(),
        Team("The invincibles"),
        Team("The vincibles"),
        random.nextLong()
    )
}