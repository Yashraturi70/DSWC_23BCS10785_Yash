import java.util.List;

abstract class EngineLog {
    protected String timestamp;
    protected double coreTemperature;
    protected boolean isAnomaly;

    public EngineLog(String timestamp, double coreTemperature, boolean isAnomaly) {
        this.timestamp = timestamp;
        this.coreTemperature = coreTemperature;
        this.isAnomaly = isAnomaly;
    }

    public String getTimestamp() { return timestamp; }
    public double getCoreTemperature() { return coreTemperature; }
    public boolean isAnomaly() { return isAnomaly; }
}

class NominalLog extends EngineLog {
    public NominalLog(String timestamp, double coreTemperature, boolean isAnomaly) {
        super(timestamp, coreTemperature, isAnomaly);
    }
}

class CriticalLog extends EngineLog {
    private String errorCode;

    public CriticalLog(String timestamp, double coreTemperature, boolean isAnomaly, String errorCode) {
        super(timestamp, coreTemperature, isAnomaly);
        this.errorCode = errorCode;
    }

    public String getErrorCode() { return errorCode; }
}

@FunctionalInterface
interface LogAuditor {
    boolean isCritical(EngineLog log);
}

@FunctionalInterface
interface HeatExtractor {
    double extractTemp(EngineLog log);
}

class TelemetryProcessor {
    public double getPeakCriticalTemp(List<EngineLog> logs, LogAuditor auditor, HeatExtractor extractor) {
        return logs.stream()
                .filter(auditor::isCritical)
                .mapToDouble(extractor::extractTemp)
                .max()
                .orElse(0.0);
    }
}

class AstroDyneMain {
    public static void main(String[] args) {
        LogAuditor anomalyAuditor = log -> {
            if (log.isAnomaly()) return true;
            if (log instanceof CriticalLog) {
                return "OVERHEAT".equals(((CriticalLog) log).getErrorCode());
            }
            return false;
        };

        HeatExtractor coreTempExtractor = EngineLog::getCoreTemperature;
    }
}