package nl.tudelft.jpacman.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.BorderLayout;
import java.util.List;

import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PacManUITest {

    private PacManUI ui;
    private final Game game = mock(Game.class);
    private Level level = mock(Level.class);
    private Board board = mock(Board.class);
    private PacManUiBuilder builder;

    @BeforeEach
    void setUp() {
        when(game.getPlayers()).thenReturn(List.of());
        when(game.getLevel()).thenReturn(level);
        when(level.getBoard()).thenReturn(board);

        builder = new PacManUiBuilder();
        builder.withDefaultButtons();
        ui = builder.build(game);
    }

    @Test
    void testInitialSetup() {
        // Verify the title and default close operation
        assertThat(ui.getTitle()).isEqualTo("JPacman");
        assertThat(ui.getDefaultCloseOperation()).isEqualTo(PacManUI.EXIT_ON_CLOSE);
    }

    @Test
    void testComponentLayout() {
        assertThat(ui.getContentPane().getLayout()).isInstanceOf(BorderLayout.class);

        BorderLayout layout = (BorderLayout) ui.getContentPane().getLayout();

        // Check if panels are in the right spots
        assertThat(layout.getLayoutComponent(BorderLayout.NORTH)).isInstanceOf(ScorePanel.class);
        assertThat(layout.getLayoutComponent(BorderLayout.CENTER)).isInstanceOf(BoardPanel.class);
    }

    @Test
    void testKeyListenerAdded() {
        // Verify that at least one key listener was added
        assertThat(ui.getKeyListeners()).hasSize(1);
        assertThat(ui.getKeyListeners()[0]).isInstanceOf(PacKeyListener.class);
    }

    @Test
    void testStartVisibility() {

        assertThat(ui.isVisible()).isFalse();

        ui.start();

        assertThat(ui.isVisible()).isTrue();
    }
}