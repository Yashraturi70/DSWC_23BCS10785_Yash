abstract class SpaceVessel {
    protected short shipID;
    protected boolean isMining;
    protected char fleetClass;

    public SpaceVessel(short shipID, boolean isMining, char fleetClass) {
        this.shipID = shipID;
        this.isMining = isMining;
        this.fleetClass = fleetClass;
    }
}

class MiningShip extends SpaceVessel {
    private float[][] cargoHold;

    public MiningShip(short shipID, boolean isMining, char fleetClass, byte bays, byte containersPerBay) {
        super(shipID, isMining, fleetClass);
        this.cargoHold = new float[bays][containersPerBay];
    }

    public float calculateTotalOreWeight() {
        float totalWeight = 0.0f;
        for (float[] bay : cargoHold) {
            for (float container : bay) {
                totalWeight += container;
            }
        }
        return totalWeight;
    }

    public float findHeaviestContainer() {
        float heaviest = 0.0f;
        for (float[] bay : cargoHold) {
            for (float container : bay) {
                if (container > heaviest) {
                    heaviest = container;
                }
            }
        }
        return heaviest;
    }
}

public class Main {
    public static void main(String[] args) {
        SpaceVessel[] fleet;
        long totalFleetValueCredits;
    }
}
