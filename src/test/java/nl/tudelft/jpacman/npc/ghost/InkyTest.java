package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class InkyTest {
    private GhostMapParser mapParser;
    private PlayerFactory playerFactory;

    @BeforeEach
    void setup() {
        PacManSprites sprites = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        PointCalculator pointCalculator = mock(PointCalculator.class);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        playerFactory = new PlayerFactory(sprites);
    }

    /**
     * When both Blinky and the player are on the board, Inky should
     * compute a valid target and return a direction.
     */
    @Test
    void inkyMovesWhenBlinkyAndPlayerPresent() {
        Level level = mapParser.parseMap(List.of("B    P    I"));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Inky inky = (Inky) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        // Both Blinky and player exist — Inky should produce a move
        assertThat(move).isPresent();
    }

    /**
     * When there is NO player on the board, Inky cannot compute his target
     * and should return Optional.empty().
     */
    @Test
    void inkyReturnsEmptyWithNoPlayer() {
        Level level = mapParser.parseMap(List.of("B    I"));

        // No player registered
        Inky inky = (Inky) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * When there is NO Blinky on the board, Inky cannot compute his vector
     * and should return Optional.empty().
     * This is unique to Inky — no other ghost depends on another ghost.
     */
    @Test
    void inkyReturnsEmptyWithNoBlinky() {
        Level level = mapParser.parseMap(List.of("P    I"));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Inky inky = (Inky) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * When both Blinky and player exist but Inky is walled off
     * from his computed destination, he should return Optional.empty().
     */
    @Test
    void inkyReturnsEmptyWhenWalledOff() {
        Level level = mapParser.parseMap(List.of("B P#I"));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Inky inky = (Inky) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Inky's move should differ from simply chasing the player.
     * His indirect targeting (extending the Blinky->aheadOfPlayer vector)
     * means he targets a point beyond the player, not the player directly.
     *
     * With Blinky and Inky both to the left of the player (facing EAST),
     * the extended vector lands far to the EAST of the player —
     * so Inky should move EAST, past the player's position.
     */
    @Test
    void inkyTargetsDifferentlyThanBlinky() {
        Level level = mapParser.parseMap(List.of("B I  P          "));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Inky inky = (Inky) Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();

        Optional<Direction> move = inky.nextAiMove();
        assertThat(move).isPresent();
        // Inky's destination is far EAST of the player — he should move EAST
        assertThat(move.get()).isEqualTo(Direction.EAST);
    }
}