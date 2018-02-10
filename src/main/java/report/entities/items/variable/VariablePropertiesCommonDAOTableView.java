//
//package report.entities.items.variable;
//
//
//import java.util.Collection;
//import java.util.Properties;
//import java.util.stream.Collector;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import report.entities.TableViewItemDAO;
//import report.models.numberStringConverters.numberStringConverters.DoubleStringConverter;
//import report.models.view.wrappers.table_wrappers.AbstractTableWrapper;
//import report.usage_strings.PathStrings;
//import report.models.view.wrappers.table_wrappers.TableWrapper;
//
//
//public class VariablePropertiesCommonDAOTableView implements TableViewItemDAO<VariableTIV> {
//
//
//
//    private final DoubleStringConverter doubleStringConverter;
//
//
//    /*!******************************************************************************************************************
//    *                                                                                                         CONSTRUCTOR
//    /********************************************************************************************************************/
//
//    public VariablePropertiesCommonDAOTableView(DoubleStringConverter doubleStringConverter) {
//        this.doubleStringConverter = doubleStringConverter;
//    }
//
//    public VariablePropertiesCommonDAOTableView() {
//        this.doubleStringConverter = new DoubleStringConverter("###,##0.000");
//    }
//
//
//    /*!******************************************************************************************************************
//    *                                                                                                            Methods
//    /********************************************************************************************************************/
//
//    /**
//    * Get String - path to file (formula.properties).
//    *
//    * @return  String
//    */
//    @Override
//    public String sqlTableName() {return PathStrings.Files.VARIABLE_PROPERTIES;}
//
//
//
//    /**
//    * Get variable Properties.
//    * @return quantity (converted string to Double quantity)
//    */
//    public Properties getProperties(){return  PropertiesFile.get(sqlTableName());}
////    public Properties getProperties(){return  PropertiesFactory.getUsePath(sqlTableName());}
//
//
//    /**
//     * Get List of Variable Items from SQL (SiteOSR)
//    * @return  ObservableList of VariableTIV
//    */
//    @Override
//    public ObservableList<VariableTIV> getData() {
//        ObservableList<VariableTIV> listAllOSR
//                = PropertiesFile.get(PathStrings.Files.VARIABLE_PROPERTIES)
//                .entrySet()
//                .stream()
//                .map(temp ->
//                        {
//                            VariableTIV tiv = new VariableTIV(
//                                    0,
//                                    temp.getKey().toString()
////                            ,DecimalFormatter.formatString(temp.getQuantity()));
////                            ,DecimalFormatter.stringToDouble_threeZeroes(temp.getQuantity()));
//                                    ,doubleStringConverter.fromString(temp.getQuantity().toString())
//                            );
//                            return tiv;
//                        }
//                ).collect(Collector.of(
//                        () -> FXCollections.observableArrayList(VariableTIV.extractor()),
//                        ObservableList::add,
//                        (l1, l2) -> { l1.addAll(l2); return l1; }
//                        )
//                );
//
//        return  listAllOSR;
//    }
//
//
//    /**
//     * Delete VariableTIV Entities from Properties File (formula.properties)
//    * @param items (Collection of VariableTIV)
//    */
//    @Override
//    public void delete(Collection<VariableTIV> items) {
//
//    }
//
//
//    /**
//     * Insert VariableTIV Entities to Properties File (formula.properties)
//    * @param items (Collection of VariableTIV)
//    */
//    @Override
//    public void insert(Collection<VariableTIV> items) {
//
//        Properties property = items.stream()
//                .collect(Collector.of(
//                    Properties::new,
//                    (prop, item) -> prop.put(
//                        item.getText(),
////                        DecimalFormatter.toString_threeZeroes(item.getQuantity())
//                            doubleStringConverter.fromString(item.getQuantity().toString())
////                        DecimalFormatter.formatNumber(item.getQuantity())
//                    ),
//                    (p1, p2) -> { p1.putAll(p2); return p1; })
//                );
//        PropertiesFile.update(property, this.sqlTableName());
//
//    }
//
//
//    /**
//    * Update Properties File (formula.properties)
//    */
//    @Override
//    public  void dellAndInsert(AbstractTableWrapper<Collection<VariableTIV>> tableWrapper) {
//        insert(tableWrapper.getItems());
//    }
//
//
//
//
//
//}
