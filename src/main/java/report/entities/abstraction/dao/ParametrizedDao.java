package report.entities.abstraction.dao;

public interface ParametrizedDao<E, P> extends CommonDao<E> {

    E getData(P params);
}
