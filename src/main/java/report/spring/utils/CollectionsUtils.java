package report.spring.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class CollectionsUtils {

    public static boolean notBlank(Collection collection){
        return collection !=null && !collection.isEmpty();
    }

//    public static <T> Collection<T> intersection(final Collection<T> baseList,final Collection<T> editedList) {
//        return baseList.stream()
//                .filter((T item) -> !editedList.contains(item))
//                .collect(Collectors.toList());
//    }

    public static <T> Collection<T> baseCollectionNotContain(final Collection<T> base, final Collection<T> edited) {
        Objects.requireNonNull(base);
        Objects.requireNonNull(edited);
        return base.stream()
                .filter(item -> !Objects.requireNonNull(edited).contains(item))
                .collect(Collectors.toList());
    }

    public static <T> Collection<T> changeCollectionNotContain(final Collection<T> base, final Collection<T> edited) {
        Objects.requireNonNull(base);
        Objects.requireNonNull(edited);
        return edited.stream()
                .filter(item -> !base.contains(item))
                .collect(Collectors.toList());
    }
}
