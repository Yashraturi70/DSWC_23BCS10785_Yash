import java.util.List;
import java.util.stream.Collectors;

abstract class Cargo {
    protected String containerId;
    protected double valueInCredits;
    protected boolean isHazardous;

    public Cargo(String containerId, double valueInCredits, boolean isHazardous) {
        this.containerId = containerId;
        this.valueInCredits = valueInCredits;
        this.isHazardous = isHazardous;
    }

    public String getContainerId() { return containerId; }
    public double getValueInCredits() { return valueInCredits; }
    public boolean isHazardous() { return isHazardous; }
}

class StandardCargo extends Cargo {
    public StandardCargo(String containerId, double valueInCredits, boolean isHazardous) {
        super(containerId, valueInCredits, isHazardous);
    }
}

class BiologicalCargo extends Cargo {
    private boolean isShielded;

    public BiologicalCargo(String containerId, double valueInCredits, boolean isHazardous, boolean isShielded) {
        super(containerId, valueInCredits, isHazardous);
        this.isShielded = isShielded;
    }

    public boolean isShielded() { return isShielded; }
}

@FunctionalInterface
interface CargoInspector {
    boolean isSafe(Cargo c);
}

@FunctionalInterface
interface CargoCompressor {
    String compress(Cargo c);
}

class ManifestProcessor {
    public List<String> processManifest(List<Cargo> manifest, CargoInspector inspector, CargoCompressor compressor) {
        return manifest.stream()
                .filter(inspector::isSafe)
                .filter(c -> c.getValueInCredits() >= 1000.0)
                .map(compressor::compress)
                .collect(Collectors.toList());
    }
}

class CargoMain {
    public static void main(String[] args) {
        CargoInspector safetyInspector = c -> {
            if (c.isHazardous() && c instanceof BiologicalCargo) {
                return ((BiologicalCargo) c).isShielded();
            }
            return true;
        };

        CargoCompressor telemetryCompressor = c -> {
            String idPart = c.getContainerId().substring(0, Math.min(4, c.getContainerId().length()));
            int intValue = (int) c.getValueInCredits();
            return idPart + "-" + intValue;
        };
    }
}