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
    public void readData() throws HardwareLockException {
        boolean fileLocked = true; 
        if (fileLocked) {
            throw new HardwareLockException("OS file lock detected.");
        }
    }

    public void processSensorData(int temperature) {
        if (temperature > 100) {
            throw new SensorCorruptionException("Impossible sensor values detected.");
        }
    }

    @Override
    public void close() {
        System.out.println("TelemetryStream closed automatically.");
    }
}

public class DeepSeaTelemetryParser {
    public void parseTelemetry() throws HardwareLockException {
        try (TelemetryStream stream = new TelemetryStream()) {
            stream.readData();
            stream.processSensorData(500);
        } catch (SensorCorruptionException e) {
            System.out.println("Logged recoverable error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DeepSeaTelemetryParser parser = new DeepSeaTelemetryParser();
        try {
            parser.parseTelemetry();
        } catch (HardwareLockException e) {
            System.err.println("Process crashed due to fatal hardware failure: " + e.getMessage());
        }
    }
}
