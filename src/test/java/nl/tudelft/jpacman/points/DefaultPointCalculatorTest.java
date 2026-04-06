package nl.tudelft.jpacman.points;

import static org.mockito.Mockito.*;

import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.Test;

class DefaultPointCalculatorTest {

    private DefaultPointCalculator calculator = new DefaultPointCalculator();
    private Player player = mock(Player.class);
    private Ghost ghost = mock(Ghost.class);
    private Pellet pellet = mock(Pellet.class);


    //No points for colliding with Ghost
    @Test
    void collidedWithAGhost() {
        int points = 0;

        calculator.collidedWithAGhost(player, ghost);

        verifyNoInteractions(player);
    }

    //Check points added for colliding with pellet
    @Test
    void consumedAPellet() {
        when(pellet.getValue()).thenReturn(10);
        int points = 10;

        calculator.consumedAPellet(player, pellet);

        verify(player).addPoints(points);
    }

    //No points for pacman moving
    @Test
    void pacmanMoved() {
        int points = 0;

        calculator.pacmanMoved(player, Direction.NORTH);

        verifyNoInteractions(player);
    }
}