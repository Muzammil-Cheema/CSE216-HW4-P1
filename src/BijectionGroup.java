import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class BijectionGroup {
    public static <T> List<Function<T, T>> bijectionsOf(Set<T> set) {
        List<List<T>> arrangements = new ArrayList<>();
        List<T> setList = new ArrayList<>(set);
        permute(setList, arrangements, new ArrayList<>(), 0);
        List<Function<T, T>> bijections = new ArrayList<>();

        for (List<T> bijection : arrangements){
            Map<T, T> map = new HashMap<>();
            for (int i = 0; i < bijection.size(); i++)
                map.put(setList.get(i), bijection.get(i));
            Function<T, T> f = map::get;
            bijections.add(f);
        }
        return bijections;
    }

    /*
    Add 1
        Add 2
            Add 3
        Add 3
            Add 2
    Remove 1 Add 2
        Add
     */
    private static <E> void permute(List<E> elements, List<List<E>> arrangements, List<E> bijection, int curr){
        if (bijection.size() == elements.size())             //Adds bijection once full
            arrangements.add(bijection);
        else {
            for (int j = 0; j < elements.size(); j++){      //Iterate through elements
                if (bijection.contains(elements.get(j)))    //Ignore an element if it already exists in the bijection
                    continue;
                bijection.add(curr, elements.get(j));
                permute(elements, arrangements, new ArrayList<>(bijection), curr+1);    //Recursive call to fill bijection
                bijection.remove(curr);                     //Removes the element added for the next iteration to start a new bijection
            }
        }
    }

    public static void main(String[] args) {
        Set<Integer> a_few = Stream.of(1, 2, 3).collect(Collectors.toSet());
        // you have to figure out the data type in the line below
        List<Function<Integer, Integer>> bijections = bijectionsOf(a_few);
        bijections.forEach(aBijection -> {
            a_few.forEach(n -> System.out.printf("%d --> %d; ", n, aBijection.apply(n)));
            System.out.println();
        });
    }
}