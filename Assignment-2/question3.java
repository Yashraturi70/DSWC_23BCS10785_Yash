enum DoorState {
    OPEN, CLOSED, LOCKED
}

class IllegalStateTransitionException extends RuntimeException {
    public IllegalStateTransitionException(String message) {
        super(message);
    }
}

class VaultDoor {
    private DoorState currentState;

    public VaultDoor() {
        this.currentState = DoorState.CLOSED;
    }

    public DoorState getCurrentState() {
        return currentState;
    }

    public void closeDoor() {
        if (currentState == DoorState.OPEN) {
            currentState = DoorState.CLOSED;
            System.out.println("Vault door successfully closed.");
        } else if (currentState == DoorState.LOCKED) {
            System.out.println("Cannot close the door. It is currently locked.");
        } else {
            System.out.println("Door is already closed.");
        }
    }

    public void lockDoor() {
        if (currentState == DoorState.OPEN) {
            throw new IllegalStateTransitionException("Security violation: Cannot transition directly from OPEN to LOCKED. The door must be closed first.");
        }
        if (currentState == DoorState.CLOSED) {
            currentState = DoorState.LOCKED;
            System.out.println("Vault door securely locked.");
        } else {
            System.out.println("Door is already locked.");
        }
    }

    public void unlockDoor() {
        if (currentState == DoorState.LOCKED) {
            currentState = DoorState.CLOSED;
            System.out.println("Vault door unlocked and is now closed.");
        } else {
            System.out.println("Door is not locked.");
        }
    }
}
