package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PinkyTest {
    private GhostMapParser mapParser;
    private PlayerFactory playerFactory;
    private GhostFactory ghostFactory;

    @BeforeEach
    void setup() {
        PacManSprites sprites = new PacManSprites();
        ghostFactory = new GhostFactory(sprites);
        BoardFactory boardFactory = new BoardFactory(sprites);
        PointCalculator pointCalculator = mock(PointCalculator.class);
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);

        // Extend GhostMapParser to support 'Y' -> Pinky
        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory) {
            @Override
            protected void addSquare(Square[][] grid, List<Ghost> ghosts,
                                     List<Square> startPositions, int x, int y, char c) {
                if (c == 'Y') {
                    grid[x][y] = makeGhostSquare(ghosts, ghostFactory.createPinky());
                } else {
                    super.addSquare(grid, ghosts, startPositions, x, y, c);
                }
            }
        };

        playerFactory = new PlayerFactory(sprites);
    }

    /**
     * When a player is on the board and a path to the spot 4 ahead exists,
     * Pinky should return a direction (not empty).
     *
     * Pinky is to the left of the player. Player faces EAST by default.
     * Pinky should move EAST toward the spot 4 ahead of the player.
     */
    @Test
    void pinkyMovesTowardSpotAheadOfPlayer() {
        // Pinky on left, player in middle, plenty of open space to the right
        Level level = mapParser.parseMap(List.of("Y  P        "));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Pinky pinky = (Pinky) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertThat(pinky).isNotNull();

        Optional<Direction> move = pinky.nextAiMove();
        assertThat(move).isPresent();
        // Pinky should move EAST toward the target 4 ahead of the player
        assertThat(move.get()).isEqualTo(Direction.EAST);
    }

    /**
     * When there is no player, Pinky cannot compute a target
     * and should return Optional.empty().
     */
    @Test
    void pinkyReturnsEmptyWithNoPlayer() {
        Level level = mapParser.parseMap(List.of("Y    "));

        Pinky pinky = (Pinky) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertThat(pinky).isNotNull();

        Optional<Direction> move = pinky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * When a wall blocks the path between Pinky and the player's ahead-target,
     * Pinky should return Optional.empty().
     */
    @Test
    void pinkyReturnsEmptyWhenNoPath() {
        Level level = mapParser.parseMap(List.of("Y#P    "));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Pinky pinky = (Pinky) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertThat(pinky).isNotNull();

        Optional<Direction> move = pinky.nextAiMove();
        assertThat(move).isEmpty();
    }

    /**
     * Pinky above the player — should still produce a valid move
     * since she targets 4 squares ahead of the player, not the player directly.
     */
    @Test
    void pinkyMovesCorrectlyFromAbovePlayer() {
        Level level = mapParser.parseMap(List.of(
                "Y        ",
                "P        "
        ));

        Player player = playerFactory.createPacMan();
        level.registerPlayer(player);

        Pinky pinky = (Pinky) Navigation.findUnitInBoard(Pinky.class, level.getBoard());
        assertThat(pinky).isNotNull();

        Optional<Direction> move = pinky.nextAiMove();
        assertThat(move).isPresent();
    }
}