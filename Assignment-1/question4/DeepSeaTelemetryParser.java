class HardwareLockException extends Exception {
    public HardwareLockException(String message) {
        super(message);
    }
}

class SensorCorruptionException extends RuntimeException {
    public SensorCorruptionException(String message) {
        super(message);
    }
}

class TelemetryStream implements AutoCloseable {
    @Override
    public void close() {}
}

public class DeepSeaTelemetryParser {
    public void parseTelemetry() throws HardwareLockException {
        try (TelemetryStream stream = new TelemetryStream()) {
            
        }
    }
}
