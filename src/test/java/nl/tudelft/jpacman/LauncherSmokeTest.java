package nl.tudelft.jpacman;

import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Smoke tests for JPacman.
 *
 * These tests exercise the full game stack through the Launcher,
 * verifying high-level user stories without mocking any internals.
 *
 * User story tested:
 *   As a player,
 *   I want to be able to suspend the game
 *   So that I can pause and do something else
 *
 *   Scenario 1: Given the game has started;
 *               When the player clicks the "Stop" button;
 *               Then the game is not in progress.
 */
public class LauncherSmokeTest {

    private Launcher launcher;

    @BeforeEach
    void setup() {
        launcher = new Launcher();
        launcher.makeGame();
    }

    @AfterEach
    void tearDown() {
        launcher.dispose();
    }

    /**
     * The game should not be in progress before start() is called.
     */
    @Test
    void gameNotInProgressBeforeStart() {
        Game game = launcher.getGame();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Scenario: Given the game has started,
     * then isInProgress() should return true.
     */
    @Test
    void gameIsInProgressAfterStart() {
        Game game = launcher.getGame();
        game.start();
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * Scenario: Given the game has started,
     * when stop() is called,
     * then the game is no longer in progress.
     * This directly tests the "suspend the game" user story.
     */
    @Test
    void gameIsNotInProgressAfterStop() {
        Game game = launcher.getGame();
        game.start();
        assertThat(game.isInProgress()).isTrue();

        game.stop();
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * Calling start() twice should not cause errors —
     * the second call should be ignored (game already in progress).
     */
    @Test
    void startingTwiceHasNoEffect() {
        Game game = launcher.getGame();
        game.start();
        game.start(); // second call should be a no-op
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * Calling stop() when the game is not running should
     * not cause errors and game should remain not in progress.
     */
    @Test
    void stoppingWhenNotStartedHasNoEffect() {
        Game game = launcher.getGame();
        assertThat(game.isInProgress()).isFalse();
        game.stop(); // should be a no-op
        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * The game should be resumable after being stopped —
     * start() after stop() should put the game back in progress.
     */
    @Test
    void gameCanBeResumedAfterStop() {
        Game game = launcher.getGame();
        game.start();
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        assertThat(game.isInProgress()).isTrue();
    }

    /**
     * The game should have at least one player after being created.
     */
    @Test
    void gameHasAtLeastOnePlayer() {
        Game game = launcher.getGame();
        assertThat(game.getPlayers()).isNotEmpty();
    }

    /**
     * The player should be alive at the start of the game.
     */
    @Test
    void playerIsAliveAtStart() {
        Game game = launcher.getGame();
        assertThat(game.getPlayers().get(0).isAlive()).isTrue();
    }

    /**
     * There should be pellets remaining at the start of the game.
     */
    @Test
    void pelletsRemainingAtStart() {
        Game game = launcher.getGame();
        assertThat(game.getLevel().remainingPellets()).isPositive();
    }
}