package report.entities.items.counterparties;

import report.entities.items.propertySheet__TEST.ObjectPSI;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ReqDaoUtils {
    public static Map<String, ObjectPSI> getEmptyItems(ObjectPSI... items) {
        Map<String, ObjectPSI> map = new LinkedHashMap<>();
        Stream.of(items).forEach(item -> map.put(item.getSqlName(), item));
        return map;
    }

    public static void setID(long id, Map<String, ObjectPSI> map) {
        for (ObjectPSI item : map.values()) {
            item.setId(id);
        }
    }

    public static String buildSqlString(String idColumnName, List<ObjectPSI> items) throws SQLException {
        StringBuilder insertPart = new StringBuilder("INSERT INTO ");
        insertPart.append(items.get(0).getSqlTableName()).append('(');
        insertPart.append(idColumnName).append(',');
        StringBuilder valuesPart = new StringBuilder(" VALUES( ");
        valuesPart.append("?,");
        int i = 0;
        for (ObjectPSI item : items) {
            if (i != items.size() - 1) {
                insertPart.append(item.getSqlName()).append(',');
                valuesPart.append("?,");
            } else {
                insertPart.append(item.getSqlName());
                valuesPart.append("? ");
            }
            i++;
        }
        insertPart.append(')');
        valuesPart.append(')');
        return insertPart.append(valuesPart).toString();
    }


}
