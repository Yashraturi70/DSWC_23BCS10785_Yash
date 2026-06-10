import java.util.*;
enum TriageLevel {
    CRITICAL, URGENT, STABLE
}
class Patient implements Comparable<Patient> {
    private String name;
    private TriageLevel severity;
    private long arrivalTime;
    public Patient(String name, TriageLevel severity, long arrivalTime) {
        this.name = name;
        this.severity = severity;
        this.arrivalTime = arrivalTime;
    }
    @Override
    public int compareTo(Patient other) {
        int severityCompare = this.severity.compareTo(other.severity);
        if (severityCompare != 0) {
            return severityCompare;
        }
        return Long.compare(this.arrivalTime, other.arrivalTime);
    }
}
class TriageManager {
    private PriorityQueue<Patient> waitingRoom = new PriorityQueue<>();
    public void admitPatient(Patient p) {
        waitingRoom.add(p);
    }
    public Patient getNextPatient() {
        return waitingRoom.poll();
    }
}