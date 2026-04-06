package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class GhostTest {
    private GhostMapParser mapParser;
    private Player player;

    /**
     * Get the automatically created player. Can then be registered in the level.
     * @return The created player object.
     */
    protected Player getPlayer() {
        return player;
    }

    /**
     * Sets up the map parser and player for use in the tests.
     */
    @BeforeEach
    void setup() {

        PacManSprites sprites = new PacManSprites();

        PlayerFactory playerFactory = new PlayerFactory(sprites);
        player = playerFactory.createPacMan();

        GhostFactory ghostFactory = new GhostFactory(sprites);
        PointCalculator pointCalculator = new DefaultPointCalculator();
        LevelFactory levelFactory = new LevelFactory(sprites, ghostFactory, pointCalculator);
        BoardFactory boardFactory = new BoardFactory(sprites);

        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    /**
     * Loads a level given a test map resource name.
     * @param mapName The name of the map test resource.
     * @return The level constructed from the map.
     */
    protected Level getLevelFromMapResource(String mapName) {

        try {
            return mapParser.parseMap(mapName);
        } catch (IOException ioe) {
            fail("Could not load test map resource", ioe);
            return null;
        }
    }
}