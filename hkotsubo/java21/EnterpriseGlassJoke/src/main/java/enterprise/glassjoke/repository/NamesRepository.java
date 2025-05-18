package enterprise.glassjoke.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Repository that stores all possible office entities names.
 *
 * @author hkotsubo
 */
public class NamesRepository {
    private static final List<String> FIRST_NAMES = Arrays.asList("Adam", "Alice", "Alan", "Amanda", "Beatrix", "Bob",
                                                                  "Carol", "Carl", "Charlote", "Charles", "Daniel",
                                                                  "Daniela", "Eve", "Egon", "Emma", "Ernest", "Helen",
                                                                  "Hugh", "Mary", "Mike", "Paul", "Phoebe", "Rachel",
                                                                  "Richard", "Stephanie", "Steven", "Thomas", "Thereza");
    private static final List<String> LAST_NAMES = Arrays.asList("Lincoln", "Jordan", "Adams", "Simpson", "Thompson",
                                                                 "Williams", "Smith", "Jones", "Davis", "Jackson", "Moore",
                                                                 "Taylor", "Miller", "Garcia", "Rodriguez", "Martinez");

    private static final Random RAND = new Random();

    public static String getRandomName() {
        return String.format("%s %s", getRandomElement(FIRST_NAMES), getRandomElement(LAST_NAMES));
    }

    private static String getRandomElement(List<String> list) {
        return list.get(RAND.nextInt(list.size()));
    }
}
