package report.entities.abstraction;

public interface ParametrizedDao<E,P> extends CommonDao<E> {

    E getData(P params);
}
