package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PacManUiBuilderTest {

    private PacManUiBuilder pacManUiBuilder;
    private final Game game = mock(Game.class);
    private Level level = mock(Level.class);
    private Board board = mock(Board.class);

    @BeforeEach
    void setUp() {
        when(game.getPlayers()).thenReturn(List.of());
        when(game.getLevel()).thenReturn(level);
        when(level.getBoard()).thenReturn(board);

        pacManUiBuilder = new PacManUiBuilder();
    }


    @Test
    void addKey() {
        Action action = mock(Action.class);
        PacManUiBuilder res = pacManUiBuilder.addKey(KeyEvent.VK_UP, action);

        assertThat(res).isEqualTo(pacManUiBuilder);
    }

    @Test
    void addButton() {
        Action action = mock(Action.class);
        pacManUiBuilder.addButton("TestButton", action);

        PacManUI ui = pacManUiBuilder.build(game);
        assertThat(ui).isNotNull();
    }

    @Test
    void withDefaultButtons() {
        pacManUiBuilder.withDefaultButtons();
        PacManUI ui = pacManUiBuilder.build(game);

        assertThat(ui).isNotNull();
    }
}