import java.util.*;
class Passenger {
    private String passportNumber;
    private String fullName;
    private String nationality;

    public Passenger(String passportNumber, String fullName, String nationality) {
        this.passportNumber = passportNumber;
        this.fullName = fullName;
        this.nationality = nationality;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(passportNumber, passenger.passportNumber) && 
               Objects.equals(nationality, passenger.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passportNumber, nationality);
    }
}
class ManifestManager {
    private Set<Passenger> globalNoFlyList = new HashSet<>();
    private Map<Passenger, String> globalPassengerDirectory = new HashMap<>();
    private Map<String, List<Passenger>> flightRosters = new HashMap<>();
    public void addToNoFlyList(Passenger p) {
        globalNoFlyList.add(p);
    }
    public boolean processCheckIn(String flightNumber, Passenger p) {
        if (globalNoFlyList.contains(p)) {
            return false;
        }
        flightRosters.computeIfAbsent(flightNumber, k -> new ArrayList<>()).add(p);
        globalPassengerDirectory.put(p, flightNumber);
        return true;
    }
    public String locatePassengerFlight(Passenger p) {
        return globalPassengerDirectory.get(p);
    }
}