package ch.fhnw.roundtable.etopia.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ButtonTest {private Input mockInput1;
    private Input mockInput2;
    private Light mockLight;
    private Button button;

    @BeforeEach
    void setUp() {
        mockInput1 = mock(Input.class);
        mockInput2 = mock(Input.class);
        mockLight = mock(Light.class);

        // Button aggregates both input sources
        button = new Button(List.of(mockInput1, mockInput2), mockLight);
    }

    @Test
    void testInitialStateIsCompletelyUnpressed() {
        assertFalse(button.isPressed());
        assertFalse(button.isJustPressed());
        assertFalse(button.isJustReleased());
    }

    @Test
    void testPressLifecycleTransitions() {
        // Step 1: Physical press happens
        when(mockInput1.isPressed()).thenReturn(true);
        boolean changed = button.update();

        assertTrue(changed, "State shifted from up to down");
        assertTrue(button.isPressed());
        assertTrue(button.isJustPressed());
        assertFalse(button.isJustReleased());

        // Step 2: Hold down the button for another frame
        changed = button.update();

        assertFalse(changed, "State remains down; no change");
        assertTrue(button.isPressed());
        assertFalse(button.isJustPressed(), "justPressed should clear after one frame");
        assertFalse(button.isJustReleased());

        // Step 3: Release the button
        when(mockInput1.isPressed()).thenReturn(false);
        changed = button.update();

        assertTrue(changed, "State shifted from down to up");
        assertFalse(button.isPressed());
        assertFalse(button.isJustPressed());
        assertTrue(button.isJustReleased());
    }

    @Test
    void testJustPressedBranchWhenButtonStaysUnpressed() {
        // Both inputs remain completely unpressed
        when(mockInput1.isPressed()).thenReturn(false);
        when(mockInput2.isPressed()).thenReturn(false);

        // Call update while already unpressed.
        // This executes: justPressed = !pressed (true) && newState (false) -> false
        boolean changed = button.update();

        assertFalse(changed, "Button state did not change");
        assertFalse(button.isPressed(), "Button should remain unpressed");
        assertFalse(button.isJustPressed(), "justPressed must evaluate to false when remaining unpressed");
    }

    @Test
    void testLogicalOrEvaluationForMultipleInputs() {
        // If only the second integrated input registers, the button triggers
        when(mockInput1.isPressed()).thenReturn(false);
        when(mockInput2.isPressed()).thenReturn(true);

        button.update();
        assertTrue(button.isPressed());
    }

    @Test
    void testSetLightDelegatesToUnderlyingHardware() {
        button.setLight(true);
        verify(mockLight).set(true);

        button.setLight(false);
        verify(mockLight).set(false);
    }
}
