import java.util.*;

public record Sample() {

    public static List<String> filterLongNames(List<String> names) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            if (name.length() > 5) {
                result.add(name.toUpperCase());
            }
        }
        return result;
    }
}
