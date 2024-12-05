import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodChain {
    /**
     * Flattens a map to a list of <code>String</code>s, where each element in the list is formatted
     * as "key -> value" (i.e., each key-value pair is converted to a string in this specific format).
     *
     * @param aMap the specified input map.
     * @param <K> the type parameter of keys in <code>aMap</code>.
     * @param <V> the type parameter of values in <code>aMap</code>.
     * @return the flattened list representation of <code>aMap</code>.
     */
    public static <K, V> List<String> flatten(Map<K, V> aMap) {
        return aMap
                .entrySet()
                .stream()
                .flatMap(e -> Stream.of(e.getKey().toString() + " -> " + e.getValue().toString()))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        System.out.println(map);

        System.out.println(flatten(map));

    }
}
