package nl.tudelft.jpacman.level;

import static org.mockito.Mockito.*;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PlayerCollisionsTest {

    private PlayerCollisions collisions;

    private PointCalculator calculator;
    private Player player;
    private Ghost ghost;
    private Pellet pellet;

    @BeforeEach
    void setUp() {
        calculator = mock(PointCalculator.class);
        collisions = new PlayerCollisions(calculator);

        player = mock(Player.class);
        ghost = mock(Ghost.class);
        pellet = mock(Pellet.class);
    }

    //Test player colliding with ghost: player should die
    @Test
    void collidePlayerGhost() {
        collisions.collide(player, ghost);

        verify(calculator).collidedWithAGhost(player, ghost);
        verify(player).setAlive(false);
        verify(player).setKiller(ghost);
    }

    //Test ghost colliding with player: player should die
    @Test
    void collideGhostPlayer() {
        collisions.collide(ghost, player);

        verify(calculator).collidedWithAGhost(player, ghost);
        verify(player).setAlive(false);
        verify(player).setKiller(ghost);
    }

    //Test player collide with pellet: pellet consumed and pellet despawns
    @Test
    void collidePlayerPellet() {
        collisions.collide(player, pellet);

        verify(calculator).consumedAPellet(player, pellet);
        verify(pellet).leaveSquare();
    }

    //Test ghost collide with pellet: nothing should happen
    @Test
    void collideGhostPellet() {
        collisions.collide(ghost, pellet);

        verifyNoInteractions(calculator);
        verify(pellet, never()).leaveSquare();
        verify(player, never()).setAlive(anyBoolean());
    }
}