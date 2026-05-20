package ch.fhnw.roundtable.etopia.views.grid.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PipeConnectionRuleTest {

    private Pipe pipe(PipeType type, int rotation) {
        var p = mock(Pipe.class);
        when(p.getType()).thenReturn(type);
        when(p.getRotation()).thenReturn(rotation);
        return p;
    }

    record Case(
            PipeType aType,
            int aRot,
            Direction dir,
            PipeType bType,
            int bRot,
            boolean expected
    ) {}

    static Stream<Case> connectionCases() {
        return Stream.of(

                // =========================
                // PRODUCER / CONSUMER / CROSS (always true individually)
                // =========================
                new Case(PipeType.PRODUCER, 0, Direction.UP, PipeType.CONSUMER, 0, true),
                new Case(PipeType.CONSUMER, 0, Direction.DOWN, PipeType.PRODUCER, 0, true),
                new Case(PipeType.CROSS_IMMUTABLE, 0, Direction.LEFT, PipeType.CROSS_IMMUTABLE, 0, true),

                // =========================
                // STRAIGHT alignment
                // vertical (UP/DOWN require odd rotation)
                // =========================
                new Case(PipeType.STRAIGHT_EDITABLE, 1, Direction.UP, PipeType.STRAIGHT_EDITABLE, 1, true),
                new Case(PipeType.STRAIGHT_EDITABLE, 1, Direction.DOWN, PipeType.STRAIGHT_EDITABLE, 1, true),

                new Case(PipeType.STRAIGHT_EDITABLE, 0, Direction.UP, PipeType.STRAIGHT_EDITABLE, 0, false),
                new Case(PipeType.STRAIGHT_EDITABLE, 0, Direction.DOWN, PipeType.STRAIGHT_EDITABLE, 0, false),

                // horizontal (LEFT/RIGHT require even rotation)
                new Case(PipeType.STRAIGHT_EDITABLE, 0, Direction.LEFT, PipeType.STRAIGHT_EDITABLE, 0, true),
                new Case(PipeType.STRAIGHT_EDITABLE, 2, Direction.RIGHT, PipeType.STRAIGHT_EDITABLE, 2, true),

                new Case(PipeType.STRAIGHT_EDITABLE, 1, Direction.LEFT, PipeType.STRAIGHT_EDITABLE, 1, false),

                // =========================
                // CORNER cases
                // =========================
                new Case(PipeType.CORNER_EDITABLE, 0, Direction.DOWN, PipeType.CORNER_EDITABLE, 2, true),
                new Case(PipeType.CORNER_EDITABLE, 1, Direction.DOWN, PipeType.CORNER_EDITABLE, 3, true),

                new Case(PipeType.CORNER_EDITABLE, 2, Direction.UP, PipeType.CORNER_EDITABLE, 0, true),
                new Case(PipeType.CORNER_EDITABLE, 3, Direction.UP, PipeType.CORNER_EDITABLE, 1, true),

                new Case(PipeType.CORNER_EDITABLE, 0, Direction.UP, PipeType.CORNER_EDITABLE, 1, false),
                new Case(PipeType.CORNER_EDITABLE, 0, Direction.LEFT, PipeType.CORNER_EDITABLE, 1, true),
                new Case(PipeType.CORNER_EDITABLE, 2, Direction.RIGHT, PipeType.CORNER_EDITABLE, 3, true),

                // =========================
                // THREE-WAY (rotation exclusion logic)
                // =========================
                new Case(PipeType.THREE_WAY_EDITABLE, 1, Direction.UP, PipeType.THREE_WAY_EDITABLE, 1, true),
                new Case(PipeType.THREE_WAY_EDITABLE, 2, Direction.DOWN, PipeType.THREE_WAY_EDITABLE, 2, false),

                new Case(PipeType.THREE_WAY_EDITABLE, 3, Direction.RIGHT, PipeType.THREE_WAY_EDITABLE, 3, false),
                new Case(PipeType.THREE_WAY_EDITABLE, 0, Direction.LEFT, PipeType.THREE_WAY_EDITABLE, 0, true),

                // =========================
                // IMMUTABLE types behave same as editable in rule
                // =========================
                new Case(PipeType.STRAIGHT_IMMUTABLE, 1, Direction.UP, PipeType.STRAIGHT_IMMUTABLE, 1, true),
                new Case(PipeType.CORNER_IMMUTABLE, 0, Direction.DOWN, PipeType.CORNER_IMMUTABLE, 2, true),
                new Case(PipeType.THREE_WAY_IMMUTABLE, 1, Direction.UP, PipeType.THREE_WAY_IMMUTABLE, 1, true)
        );
    }

    @ParameterizedTest
    @MethodSource("connectionCases")
    void shouldEvaluateConnectionRulesCorrectly(Case c) {
        var a = pipe(c.aType(), c.aRot());
        var b = pipe(c.bType(), c.bRot());

        boolean result = PipeConnectionRule.connected(a, c.dir(), b);

        assertEquals(c.expected(), result);
    }

    // =========================
    // Opposite-direction consistency (important regression test)
    // =========================
    @Test
    void shouldBeSymmetricWhenBothSidesMatch() {
        var a = pipe(PipeType.CORNER_EDITABLE, 0);
        var b = pipe(PipeType.CORNER_EDITABLE, 1);

        boolean ab = PipeConnectionRule.connected(a, Direction.DOWN, b);
        boolean ba = PipeConnectionRule.connected(b, Direction.UP, a);

        assertEquals(ab, ba);
    }

    // =========================
    // Rotation normalization (mod 4 safety)
    // =========================
    @Test
    void shouldHandleRotationGreaterThanFour() {
        var a = pipe(PipeType.STRAIGHT_EDITABLE, 5); // 5 % 4 = 1
        var b = pipe(PipeType.STRAIGHT_EDITABLE, 1);

        assertTrue(PipeConnectionRule.connected(a, Direction.UP, b));
    }

    // =========================
    // Direction inversion correctness
    // =========================
    @Test
    void shouldRespectOppositeDirectionLogic() {
        var a = pipe(PipeType.CROSS_IMMUTABLE, 0);
        var b = pipe(PipeType.CROSS_IMMUTABLE, 0);

        assertTrue(PipeConnectionRule.connected(a, Direction.LEFT, b));
        assertTrue(PipeConnectionRule.connected(a, Direction.RIGHT, b));
    }
}
