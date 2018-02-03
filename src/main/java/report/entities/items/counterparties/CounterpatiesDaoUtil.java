package report.entities.items.counterparties;

import report.entities.abstraction.DaoUtil;

import java.util.Map;

public class CounterpatiesDaoUtil implements DaoUtil<String,Integer> {
    public static Map<String,Integer> getForm(){
        String sql ="SELECT DISTINCT [id],[Name] FROM [dbo].[DIC_Count_Form] WHERE dell = 0";
        return new CounterpatiesDaoUtil().getPairValue("Name","id",sql);
    }
    public static Map<String,Integer> getType(){
        String sql ="SELECT DISTINCT [id],[Name] FROM [dbo].[DIC_Count_Type] WHERE dell = 0";
        return new CounterpatiesDaoUtil().getPairValue("Name","id",sql);
    }
}
