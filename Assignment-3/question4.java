import java.util.LinkedHashMap;
import java.util.Map;
class VideoCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public VideoCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > this.capacity;
    }
}