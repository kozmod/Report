package report.spring.utils;

@FunctionalInterface
public interface BeanFunction <T, R> {

    R apply(T t) throws Exception;
}
