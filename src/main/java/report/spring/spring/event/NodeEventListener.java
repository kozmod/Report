package report.spring.spring.event;

public interface NodeEventListener<T> {
    void actOnEvent(T event);
}
