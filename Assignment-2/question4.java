interface PaymentStrategy {
    boolean processPayment(double amount);
}

class CreditCardStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment of " + amount);
        return true;
    }
}

class CryptoStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing cryptocurrency transaction of " + amount);
        return true;
    }
}

class TransactionProcessor {
    private PaymentStrategy strategy;

    public TransactionProcessor(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeTransaction(double amount) {
        boolean success = strategy.processPayment(amount);
        if (success) {
            System.out.println("Transaction successfully executed via the loaded strategy.");
        } else {
            System.out.println("Transaction failed.");
        }
    }
}
