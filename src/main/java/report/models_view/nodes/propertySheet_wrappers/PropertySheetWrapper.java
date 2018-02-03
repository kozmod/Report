package report.models_view.nodes.propertySheet_wrappers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.PropertySheet;
import report.entities.items.propertySheet__TEST.ObjectPSI;
import report.models.mementos.SheetMemento;
import report.models_view.nodes.table_wrappers.BindBase;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PropertySheetWrapper implements BindBase{
    private PropertySheet sheet;
    private Map<String, List<ObjectPSI>> itemMap;
    private Map<String, SheetMemento> mementoMap;
    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/
    /**
     * Private Constructor.
     */
    private PropertySheetWrapper() {
    }
    public PropertySheetWrapper(PropertySheet sheet) {
        this.sheet = sheet;
    }
    /***************************************************************************
     *                                                                         *
     * Base                                                                    *
     *                                                                         *
     **************************************************************************/
    @Override
    public void toBase() {
        System.out.println(this.mementoMap.values());

    }
    @Override
    public void setFromBase() {
        //TODO: write DAO and add this one here
        ObservableList<ObjectPSI> list = FXCollections.observableArrayList(ObjectPSI.extractor());
        list.add(new ObjectPSI<>("ОГРН",
                "Общие реквизиты",
                new BigInteger("123")).setSqlName("A"));
        list.add(new ObjectPSI<>("Дата присвоения ОГРН",
                "Общие реквизиты",
                LocalDate.ofEpochDay(12345)).setSqlName("A"));
        list.add(new ObjectPSI<>("ИНН",
                "Общие реквизиты",
                new BigInteger("123")).setSqlName("A"));
        list.add(new ObjectPSI<>("Юридический Адрес",
                "Общие реквизиты",
                "Москва").setSqlName("A"));
        list.add(new ObjectPSI<>("Фактический Адрес",
                "Общие реквизиты",
                "Тула").setSqlName("A"));
        list.add(new ObjectPSI<>("Адрес (Post)",
                "Общие реквизиты",
                "Караганда").setSqlName("A"));
        list.add(new ObjectPSI<>("Наименование банка",
                "Банковские реквизиты",
                "Супер-банк").setSqlName("A"));
        list.add(new ObjectPSI<>("БИК",
                "Банковские реквизиты",
                new BigInteger("123")).setSqlName("A"));
        list.add(new ObjectPSI<>("Номер Счета",
                "Банковские реквизиты",
                new BigInteger("123")).setSqlName("A"));
        list.add(new ObjectPSI<>("Креспондентский счет",
                "Банковские реквизиты",
                new BigInteger("123")).setSqlName("A"));
        list.add(new ObjectPSI<>("ИО Наименование",
                "Исполнительный орган",
                "ХХХХХХХХХХХХХХХХ").setSqlName("A"));
        list.add(new ObjectPSI<>("ИО Фамилия",
                "Исполнительный орган",
                "Васи").setSqlName("A"));
        list.add(new ObjectPSI<>("ИО Имя",
                "Исполнительный орган",
                "Вася").setSqlName("A"));
        list.add(new ObjectPSI<>("ИО Отчество",
                "Исполнительный орган",
                "Васильев").setSqlName("A"));
        list.add(new ObjectPSI<>("ИО Основания",
                "Исполнительный орган",
                "ЧЧЧЧЧЧЧЧ").setSqlName("A"));
        list.add(new ObjectPSI<>("Главный Бухгалтер:Фамилия",
                "Исполнительный орган",
                "Зеков").setSqlName("A"));
        list.add(new ObjectPSI<>("Главный Бухгалтер:Имя",
                "Исполнительный орган",
                "Сява").setSqlName("A"));
        list.add(new ObjectPSI<>("Главный Бухгалтер:Отчество",
                "Исполнительный орган",
                "Ахмедович").setSqlName("A"));
        list.add(new ObjectPSI<>("Паспорт: Серия/Номе",
                "Исполнительный орган",
                new BigInteger("123")).setSqlName("A"));
        list.add(new ObjectPSI<>("Паспорт:Кем выдан",
                "Исполнительный орган",
                "Г Г г Г").setSqlName("A"));
        list.add(new ObjectPSI<>("Паспорт:Дата выдачи",
                "Исполнительный орган",
                LocalDate.ofEpochDay(12345)).setSqlName("A"));
        list.add(new ObjectPSI<>("Паспорт:Код падразделения",
                "Исполнительный орган",
                new BigInteger("123")).setSqlName("A"));
        this.setItems(list);
    }
    /***************************************************************************
     *                                                                         *
     * Memento                                                                 *
     *                                                                         *
     **************************************************************************/
    public void undoChangeItems() {
        this.mementoMap.values().forEach(SheetMemento::clearChanges);
    }
    public  void saveChanges(){
        this.itemMap.keySet()
                .forEach(key -> mementoMap.put(key,new SheetMemento(itemMap.get(key))));
    }
    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/
    public void setItems(List<ObjectPSI> items){
        this.itemMap = items
                .stream()
                .collect(Collectors.groupingBy(
                        ObjectPSI::getSqlName,
                        Collectors.mapping(
                                i ->i,
                                Collectors.toList()
                        )
                )
        );
//        this.mementoMap = new HashMap<>();
//        this.saveChanges();
        sheet.getItems().setAll(items);
    }

    public PropertySheet getSheet() {
        return sheet;
    }
}
