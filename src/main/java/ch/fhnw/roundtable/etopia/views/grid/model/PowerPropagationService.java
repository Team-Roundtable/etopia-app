package ch.fhnw.roundtable.etopia.views.grid.model;

public final class PowerPropagationService {

    private PowerPropagationService() {

    }

    public static void shutdown(Pipe pipe) {
        pipe.setPowered(false);

        pipe.getNeighbours().forEach((direction, neighbour) -> shutdown(neighbour));
    }

    public static void power(Pipe pipe) {
        pipe.setPowered(true);

        pipe.getNeighbours().entrySet().stream()
                .filter(entry -> PipeConnectionRule.connected(pipe, entry.getKey(), entry.getValue()))
                .forEach(entry -> power(entry.getValue()));
    }

    public static boolean isConsumerPowered(Pipe pipe) {
        if (pipe.getType() == PipeType.CONSUMER) {
            return pipe.isPowered();
        }

        return pipe.getNeighbours().entrySet().stream()
                .anyMatch(entry -> isConsumerPowered(entry.getValue()));
    }
}
