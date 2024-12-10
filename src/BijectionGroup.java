import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class BijectionGroup {
    public static <T> Group<Function<T, T>> bijectionGroup(Set<T> set){
        Group<Function<T, T>> group = new Group<Function<T, T>>() {
            @Override
            public Function<T, T> binaryOperation(Function<T, T> f, Function<T, T> g) {
                return x -> f.apply(g.apply(x));
            }

            @Override
            public Function<T, T> identity() {
                return x -> x;
            }

            @Override
            public Function<T, T> inverseOf(Function<T, T> f) {
                return f;
            }
        };

        return group;
    }

    public static <T> Set<Function<T, T>> bijectionsOf(Set<T> set) {
        List<List<T>> arrangements = new ArrayList<>();
        List<T> setList = new ArrayList<>(set);
        permute(setList, arrangements, new ArrayList<>());
        Set<Function<T, T>> bijections = new HashSet<>();

        for (List<T> bijection : arrangements){     //Adds each permutation to the set of bijections
            Map<T, T> map = new HashMap<>();
            for (int i = 0; i < bijection.size(); i++)
                map.put(setList.get(i), bijection.get(i));
            Function<T, T> f = map::get;
            bijections.add(f);
        }
        return bijections;
    }

    private static <E> void permute(List<E> elements, List<List<E>> arrangements, List<E> bijection){
        if (bijection.size() == elements.size())             //Adds bijection once full
            arrangements.add(bijection);
        else {
            for (int j = 0; j < elements.size(); j++){      //Iterate through elements
                if (bijection.contains(elements.get(j)))    //Ignore an element if it already exists in the bijection
                    continue;
                bijection.add(elements.get(j));
                permute(elements, arrangements, new ArrayList<>(bijection));    //Recursive call to fill bijection
                bijection.remove(bijection.size() - 1);                     //Removes the element added for the next iteration to start a new bijection
            }
        }
    }

    public static void main(String[] args) {
        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
        // you have to figure out the data type in the line below
        Set<Function<Integer, Integer>> bijections = bijectionsOf(a_few);
        bijections.forEach(aBijection -> {
            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
            System.out.println();
        });

        // you have to figure out the data types in the lines below
        // some of these data types are functional objects
        // so, look into java.util.function.Function
        Group<Function<Integer, Integer>> g = bijectionGroup(a_few);
        Function<Integer, Integer> f1 = bijectionsOf(a_few).stream().findFirst().get();
        Function<Integer, Integer> f2 = g.inverseOf(f1);
        Function<Integer, Integer> id = g.identity();
    }
}