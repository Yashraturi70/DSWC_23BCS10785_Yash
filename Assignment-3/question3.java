import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

class Order {
}

class ExchangeManager {
    private ConcurrentHashMap<String, CopyOnWriteArrayList<Order>> orderBook = new ConcurrentHashMap<>();
    public void placeOrder(String ticker, Order order) {
        orderBook.computeIfAbsent(ticker, k -> new CopyOnWriteArrayList<>()).add(order);
    }
}