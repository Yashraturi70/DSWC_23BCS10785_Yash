// Coding Problem 1: The "EcoSmart" Home Hub Controller

// Scenario Background:
// You are the lead architect for EcoSmart, a company that manufactures a central hub to manage various smart home devices. Your hub needs to control lights, security cameras, and smart locks.
// Some of these devices are wired directly into the house's electricity, while others rely on internal batteries. Every night at 2:00 AM, the central hub runs a "System Diagnostics Routine." During this routine, the hub must trigger every device to perform its primary action to ensure it is working, and it must also check the battery levels of any device that relies on battery power, triggering a recharge alert if the battery is dangerously low.
// Implementation Constraints & Requirements:
// 1. Inheritance Structure (The "Is-A" Relationship):
// Create an abstract base class called SmartDevice. It must contain:
// A protected String deviceId.
// A protected String deviceName.
// A constructor to initialize these fields.
// An abstract method public abstract void runDiagnostic().
// Create three concrete classes that extends SmartDevice:
// SmartLight
// SmartCamera
// SmartLock
// 2. Interface Definition (The "Can-Do" Behavior):
// Create an interface called BatteryOperated. It must contain:
// int getBatteryLevel()
// void triggerRechargeAlert()
// The SmartLight is hardwired into the ceiling. It does not use batteries.
// The SmartCamera and SmartLock are wireless. You must explicitly make these two classes implements BatteryOperated. (Assume you can pass a battery level into their constructors for testing purposes).

// 3. Polymorphism & The Hub Manager:
// Create a class called HomeHub.
// Write a method with the exact signature: public void executeNightlyRoutine(SmartDevice[] devices).
// The Polymorphism Constraint: Inside this method, you must iterate through the array. For every device, you must call its polymorphic runDiagnostic() method.
// However, if the device in the loop happens to be BatteryOperated, you must also check its battery level. If the battery is strictly less than 20%, you must call triggerRechargeAlert(). You must achieve this using safe type-checking (instanceof) and downcasting.

abstract class SmartDevice{
    protected String deviceId;
    protected String deviceName;
    public SmartDevice(String deviceId,String deviceName)
    {
        this.deviceId=deviceId;
        this.deviceName=deviceName;
    }
    public abstract void runDiagnostic();
}
class SmartLight extends SmartDevice{
    public SmartLight(String deviceId,String deviceName)
    {
        super(deviceId,deviceName);
    }
    public void runDiagnostic()
    {
        System.out.println("Running diagonistic for SmartLight");
    }
}
interface BatteryOperated {
    int getBatteryLevel();
    void triggerRechargeAlert();
}
class SmartCamera extends SmartDevice implements BatteryOperated{
    int batteryLevel;
    public SmartCamera(String deviceId,String deviceName,int batteryLevel)
    {
        super(deviceId,deviceName);
        this.batteryLevel=batteryLevel;
    }
    public void runDiagnostic()
    {
        System.out.println("Running diagonistic for SmartCamera");
    }
    public int getBatteryLevel()
    {
        return batteryLevel;
    }
    public void triggerRechargeAlert()
    {
        System.out.println("Trigger recharge from Smart Camera");
    }
}
class SmartLock extends SmartDevice implements BatteryOperated{
    int batteryLevel;
    public SmartLock(String deviceId,String deviceName,int batteryLevel)
    {
        super(deviceId,deviceName);
        this.batteryLevel=batteryLevel;
    }
    public void runDiagnostic()
    {
        System.out.println("Running diagonistic for Smart Lock");
    }
    public int getBatteryLevel()
    {
        return batteryLevel;
    }
    public void triggerRechargeAlert()
    {
        System.out.println("Trigger recharge from SmartLock");
    }
}
class HomeHub{
    public void executeNightlyRoutine(SmartDevice[] devices)
    {
        for(SmartDevice i:devices)
        {
            i.runDiagnostic();
            if(i instanceof BatteryOperated)
            {
                BatteryOperated newi=(BatteryOperated) i;
                int level=newi.getBatteryLevel();
                if(level<20)
                {
                    newi.triggerRechargeAlert();
                }
            }
        }
    }
}