import java.util.List;
import java.util.stream.Collectors;

abstract class MemoryEngram {
    protected String engramId;
    protected double clarityScore;
    protected boolean isCorrupted;

    public MemoryEngram(String engramId, double clarityScore, boolean isCorrupted) {
        this.engramId = engramId;
        this.clarityScore = clarityScore;
        this.isCorrupted = isCorrupted;
    }

    public String getEngramId() { return engramId; }
    public double getClarityScore() { return clarityScore; }
    public boolean isCorrupted() { return isCorrupted; }
}

class StandardEngram extends MemoryEngram {
    public StandardEngram(String engramId, double clarityScore, boolean isCorrupted) {
        super(engramId, clarityScore, isCorrupted);
    }
}

class ClassifiedEngram extends MemoryEngram {
    private int securityClearanceLevel;

    public ClassifiedEngram(String engramId, double clarityScore, boolean isCorrupted, int securityClearanceLevel) {
        super(engramId, clarityScore, isCorrupted);
        this.securityClearanceLevel = securityClearanceLevel;
    }

    public int getSecurityClearanceLevel() { return securityClearanceLevel; }
}

@FunctionalInterface
interface EngramValidator {
    boolean isValid(MemoryEngram e);
}

@FunctionalInterface
interface EngramTranslator {
    String translate(MemoryEngram e);
}

class CortexProcessor {
    public List<String> processMemories(List<MemoryEngram> engrams, EngramValidator validator, EngramTranslator translator) {
        return engrams.stream()
                .filter(validator::isValid)
                .filter(e -> e.getClarityScore() >= 50.0)
                .map(translator::translate)
                .collect(Collectors.toList());
    }
}

class NeuroLinkMain {
    public static void main(String[] args) {
        EngramValidator securityValidator = e -> {
            if (e.isCorrupted()) return false;
            if (e instanceof ClassifiedEngram) {
                return ((ClassifiedEngram) e).getSecurityClearanceLevel() <= 3;
            }
            return true;
        };

        EngramTranslator digitalTranslator = e -> 
            "ENGRAM-" + e.getEngramId() + " | CLARITY: " + (int)e.getClarityScore() + "%";
    }
}