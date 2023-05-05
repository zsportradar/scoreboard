package sportradar.scoreboard.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import sportradar.scoreboard.infrastructure.GameRepository
import java.util.*


class GameServiceTest {
    private val gameRepository = GameRepository()
    private val gameService = GameService(gameRepository)
    private val random = Random()

    @Test
    fun `Games can be added and fetched`() {
        gameRepository.allGames().shouldBeEmpty()

        val id = UUID.randomUUID()
        gameService.addGame(createGame(id))
        gameService.addGame(createGame(UUID.randomUUID()))

        gameRepository.allGames().size shouldBe 2
        gameRepository.findById(id)!!.id shouldBe id
    }

    @Test
    fun `Finished games are no longer among in-progress games`() {
        val id = UUID.randomUUID()
        gameService.addGame(createGame(id))

        gameRepository.findById(id) shouldNotBe null

        gameService.finishGame(id)

        gameRepository.findInProgressGames().find { it.id == id } shouldBe null
        gameRepository.allGames().find { it.id == id } shouldNotBe null
    }

    @Test
    fun `Scores can be updated`() {
        val id = UUID.randomUUID()
        val otherId = UUID.randomUUID()
        gameService.addGame(createGame(id))
        gameService.addGame(createGame(otherId))
        gameService.updateScore(id, (1 to 0))

        gameRepository.findById(id)!!.score shouldBe (1 to 0)
        gameRepository.findById(otherId)!!.score shouldBe (0 to 0)
    }

    @Test
    fun `Unknown games can't be updated`() {
        shouldThrow<NullPointerException> {
            gameService.updateScore(UUID.randomUUID(), (0 to 1))
        }

        shouldThrow<NullPointerException> {
            gameService.finishGame(UUID.randomUUID())
        }
    }

    private fun createGame(id: UUID) = Game(
        id,
        Team("The invincibles"),
        Team("The vincibles"),
        random.nextLong()
    )
}