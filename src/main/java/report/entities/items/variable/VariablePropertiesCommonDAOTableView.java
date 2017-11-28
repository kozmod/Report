
package report.entities.items.variable;


import java.util.Collection;
import java.util.Properties;
import java.util.stream.Collector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.TableViewItemDAO;
import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
import report.usage_strings.PathStrings;
import report.models_view.nodes.node_wrappers.TableWrapper;


public class VariablePropertiesCommonDAOTableView implements TableViewItemDAO<TableItemVariable, TableWrapper> {



    private final DoubleStringConverter doubleStringConverter;


    /*!******************************************************************************************************************
    *                                                                                                         CONSTRUCTOR
    /********************************************************************************************************************/

    public VariablePropertiesCommonDAOTableView(DoubleStringConverter doubleStringConverter) {
        this.doubleStringConverter = doubleStringConverter;
    }

    public VariablePropertiesCommonDAOTableView() {
        this.doubleStringConverter = new DoubleStringConverter("###,##0.000");
    }


    /*!******************************************************************************************************************
    *                                                                                                            Methods
    /********************************************************************************************************************/

    /**
    * Get String - path to file (formula.properties). 
    * 
    * @return  String 
    */
    @Override
    public String getTableString() {return PathStrings.Files.VARIABLE_PROPERTIES;}
    
    

    /**
    * Get variable Properties.
    * @return value (converted string to Double value)
    */
    public Properties getProperties(){return  PropertiesFile.get(getTableString());}
//    public Properties getProperties(){return  PropertiesFactory.getUsePath(getTableString());}


    /**
     * Get List of Variable Items from SQL (SiteOSR)
    * @return  ObservableList of TableItemVariable
    */
    @Override
    public ObservableList<TableItemVariable> getList() {
        ObservableList<TableItemVariable> listAllOSR 
                = PropertiesFile.get(PathStrings.Files.VARIABLE_PROPERTIES)
                .entrySet()
                .stream()
                .map(temp ->
                        {
                            TableItemVariable tiv = new TableItemVariable(
                                    0,
                                    temp.getKey().toString()
//                            ,DecimalFormatter.formatString(temp.getValue()));
//                            ,DecimalFormatter.stringToDouble_threeZeroes(temp.getValue()));
                                    ,doubleStringConverter.fromString(temp.getValue().toString())
                            );
                            return tiv;
                        }
                ).collect(Collector.of(
                        () -> FXCollections.observableArrayList(TableItemVariable.extractor()),
                        ObservableList::add,
                        (l1, l2) -> { l1.addAll(l2); return l1; }
                        )
                );
           
        return  listAllOSR;
    }

    
    /**
     * Delete TableItemVariable Entities from Properties File (formula.properties)
    * @param items (Collection of TableItemVariable) 
    */
    @Override
    public void delete(Collection<TableItemVariable> items) {

    }

    
    /**
     * Insert TableItemVariable Entities to Properties File (formula.properties)
    * @param items (Collection of TableItemVariable) 
    */
    @Override
    public void insert(Collection<TableItemVariable> items) {
        
        Properties property = items.stream()
                .collect(Collector.of(
                    Properties::new,
                    (prop, item) -> prop.put(
                        item.getText(),
//                        DecimalFormatter.toString_threeZeroes(item.getValue())
                            doubleStringConverter.fromString(item.getValue().toString())
//                        DecimalFormatter.formatNumber(item.getValue())
                    ),
                    (p1, p2) -> { p1.putAll(p2); return p1; })
                );
        PropertiesFile.update(property, this.getTableString());
       
    }

    
    /**
    * Update Properties File (formula.properties)
    */
    @Override
    public  void dellAndInsert(TableWrapper tableWrapper) {
        insert(tableWrapper.getItems());
    }
    
   



}
