package nl.tudelft.jpacman.ui;

import nl.tudelft.jpacman.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class PacManUiBuilderTest {

    private PacManUiBuilder pacManUiBuilder;
    private final Game game = mock(Game.class);

    @BeforeEach
    void setUp() {
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