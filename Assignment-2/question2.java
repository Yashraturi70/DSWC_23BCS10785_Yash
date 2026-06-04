abstract class DeliveryDrone {
    protected String droneId;

    public DeliveryDrone(String droneId) {
        this.droneId = droneId;
    }

    public abstract void deliverPackage();
}

interface Airborne {
    void flyToDestination();
    default void requestAirTrafficClearance() {
        System.out.println("Airspace clearance requested from air traffic control.");
    }
}

interface GroundBased {
    void navigateSidewalks();
}

class Quadcopter extends DeliveryDrone implements Airborne {
    public Quadcopter(String droneId) {
        super(droneId);
    }

    @Override
    public void deliverPackage() {
        System.out.println("Quadcopter " + droneId + " is delivering a package via air.");
    }

    @Override
    public void flyToDestination() {
        System.out.println("Quadcopter " + droneId + " is flying to the destination.");
    }
}

class CityRover extends DeliveryDrone implements GroundBased {
    public CityRover(String droneId) {
        super(droneId);
    }

    @Override
    public void deliverPackage() {
        System.out.println("CityRover " + droneId + " is delivering a package via streets.");
    }

    @Override
    public void navigateSidewalks() {
        System.out.println("CityRover " + droneId + " is navigating the sidewalks.");
    }
}

class HybridVTOL extends DeliveryDrone implements Airborne, GroundBased {
    public HybridVTOL(String droneId) {
        super(droneId);
    }

    @Override
    public void deliverPackage() {
        System.out.println("HybridVTOL " + droneId + " is delivering a package using hybrid transit.");
    }

    @Override
    public void flyToDestination() {
        System.out.println("HybridVTOL " + droneId + " is transitioning into flight mode.");
    }

    @Override
    public void navigateSidewalks() {
        System.out.println("HybridVTOL " + droneId + " is driving on the ground paths.");
    }
}
