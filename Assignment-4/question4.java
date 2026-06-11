import java.util.List;
import java.util.stream.Collectors;

abstract class TemporalEntity {
    protected String entityName;
    protected int originYear;

    public TemporalEntity(String entityName, int originYear) {
        this.entityName = entityName;
        this.originYear = originYear;
    }

    public String getEntityName() { return entityName; }
    public int getOriginYear() { return originYear; }
}

class HumanEntity extends TemporalEntity {
    public HumanEntity(String entityName, int originYear) {
        super(entityName, originYear);
    }
}

class ArtifactEntity extends TemporalEntity {
    private boolean isRadioactive;

    public ArtifactEntity(String entityName, int originYear, boolean isRadioactive) {
        super(entityName, originYear);
        this.isRadioactive = isRadioactive;
    }

    public boolean isRadioactive() { return isRadioactive; }
}

class HistoricalEvent {
    private List<TemporalEntity> entities;
    private int eventYear;

    public HistoricalEvent(List<TemporalEntity> entities, int eventYear) {
        this.entities = entities;
        this.eventYear = eventYear;
    }

    public List<TemporalEntity> getEntities() { return entities; }
    public int getEventYear() { return eventYear; }
}

@FunctionalInterface
interface ParadoxChecker {
    boolean isParadox(TemporalEntity entity, int eventYear);
}

@FunctionalInterface
interface ThreatMapper {
    String mapThreat(TemporalEntity entity);
}

class ParadoxAnalyzer {
    public List<String> detectParadoxes(List<HistoricalEvent> timeline, ParadoxChecker checker, ThreatMapper mapper) {
        return timeline.stream()
                .flatMap(event -> event.getEntities().stream()
                        .filter(entity -> checker.isParadox(entity, event.getEventYear()))
                )
                .map(mapper::mapThreat)
                .collect(Collectors.toList());
    }
}

class ChronoCorpMain {
    public static void main(String[] args) {
        ParadoxChecker anomalyChecker = (entity, eventYear) -> entity.getOriginYear() > eventYear;

        ThreatMapper reportMapper = entity -> entity.getEntityName() + " detected out of time!";
    }
}