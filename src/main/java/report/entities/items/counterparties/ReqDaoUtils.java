package report.entities.items.counterparties;

import report.entities.items.propertySheet__TEST.ObjectPSI;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ReqDaoUtils {
    public static Map<String, ObjectPSI> getEmptyItems(ObjectPSI ... items) {
        Map<String, ObjectPSI> map = new LinkedHashMap<>();
        Stream.of(items).forEach(item -> map.put(item.getSqlName(),item));
        return map;
    }
    public static void setID(long id, Map<String, ObjectPSI> map) {
        for (ObjectPSI item : map.values()){
            item.setId(id);
        }
    }
    public static String buildSqlString(List<ObjectPSI> items) throws SQLException {
        String sql = null;
        if(items != null && !items.isEmpty()) {
            int i = 0;
            StringBuilder sqlStringBuilder = new StringBuilder("INSERT INTO ")
                    .append(items.get(0).getSqlTableName())
                    .append("(");
            for (ObjectPSI item : items) {
                if (i == 0) {
                    sqlStringBuilder.append(item.getSqlName());
                } else {
                    sqlStringBuilder.append(",").append(item.getSqlName());
                }
                i++;
            }
            sqlStringBuilder.append(") VALUES(");
            for (; i > 0; i--) {
                if (i != 1) {
                    sqlStringBuilder.append("?,");
                } else {
                    sqlStringBuilder.append("? )");
                }
            }
            sql = sqlStringBuilder.toString();
        }else{
            throw new SQLException(ReqDaoUtils.class + " строка SQL запроса не создана");
        }
        return sql;
    }

}
