package report.entities.items.counterparties;

import report.entities.items.propertySheet__TEST.ObjectPSI;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ReqDaoUtils {
    public static Map<String, ObjectPSI> getEmptyItems(ObjectPSI ... items) {
        Map<String, ObjectPSI> map = new LinkedHashMap<>();
        Stream.of(items).forEach(item -> map.put(item.getSqlName(),item));
        return map;
    }
}
