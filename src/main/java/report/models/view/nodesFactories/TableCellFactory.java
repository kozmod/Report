
package report.models.view.nodesFactories;

import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import report.entities.items.AbstractEstimateTVI;
import report.entities.items.Clone;
import report.entities.items.estimate.EstimateDao;
import report.entities.items.estimate.EstimateTVI;
import report.entities.items.osr.OSR_TIV;
import report.entities.items.plan.PlanDao;
import report.entities.items.site.PreviewTIV;
import report.entities.items.site.SiteDao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import report.models.converters.numberStringConverters.DoubleStringConverter;
import report.usage_strings.SQL;

import report.layout.controllers.root.RootLayoutController;
import report.layout.controllers.estimate.EstimateController_old.Est;
import report.entities.items.cb.AddEstTIV;
import report.usage_strings.ServiceStrings;


public class TableCellFactory {

    private TableCellFactory() {
    }

    /**
     * Cell to Estimate, Account Tables
     * @return DecimalTableCell
     */
//    public static TableCell getDecimalCell(){
//        return new TableCellFactory().new DecimalTableCell();
//    }

    /**
     * Cell to Estimate, Account Tables
     * <br>[cell set date from Epoch]
     * @return EpochDateCell
     */
//    public static TableCell getEpochDateCell(){
//        return new TableCellFactory().new EpochDateCell();
//    }
//

    /**
     * Cell to expenses
     * <br>[set type of currency]
     *
     * @return ExpensesCell
     */
    public static TableCell getExpenseesCell() {
        return new TableCellFactory().new ExpensesCell();
    }

    /**
     * Cell to "Add Row Estimate" TableWrapper (AddEstimateRowController)
     *
     * @return CheckValueCell
     */
    public static TableCell getCheckValueCell() {
        return new TableCellFactory().new CheckValueCell();
    }

    /**
     * Cell to EDIT Site TableWrapper
     * <br>[set type of currency]
     *
     * @return EditCell
     */
    public static TableCell getEditSiteCell() {
        return new TableCellFactory().new EditSiteCell(Est.Common);
    }

    /**
     * Cell to inKS (EST)
     * <br>(true - RED / false - GREEN).
     * <br>[set text quantity color]
     *
     * @return InKsColoredCell
     */
    @Deprecated
    public static TableCell getInKsColoredCell() {
        return new TableCellFactory().new inKsColoredCell();
    }

    /**
     * This Cell Listen Mouse Entering to this one and set "DelTable" Items.
     * <br>
     *
     * @return DelElementTableCell
     */
    public static TableCell getOnMouseEnteredTableCell(Est enumEst) {
        return new TableCellFactory().new OnMouseEnteredTableCell(enumEst);
    }

    /**
     * This Cell Listen Double Mouse click.
     * Get text of this cell and find same in JM_Mane(Est), then
     * <br>
     *
     * @return DelElementTableCell
     */
    public static TableCell getOnDoubleMouseClickMoveToTextCell() {
        return new TableCellFactory().new OnDoubleMouseClickMoveToCell();
    }

    /**
     * Cell to show list of deleted elements of table.
     * <br>
     *
     * @return ChageTableCell
     */
    public static TableCell getEqualsToAboveCell() {
        return new TableCellFactory().new EqualToAboveTableCell();
    }

    /**
     * Cell which change Object data to String.
     * <br>
     *
     * @return ObjectCell
     */
    public static TableCell getPreviewCell() {
        return new TableCellFactory().new ObjectCell();
    }

    /**
     * TEST ID OSR
     * <br>
     *
     * @return ObjectCell
     */
    public static TableCell getTestIdOSR() {
        return new TableCellFactory().new TestID();
    }


//   
//inner cell class --------------------------------------------------------------------------
//    

    /**
     * Cell to Estimate, Account Tables
     */
    private class DecimalTableCell extends TableCell<TableView, Number> {
        @Override
        protected void updateItem(Number item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(new DoubleStringConverter().toString(item));
            }
        }

    }

    /**
     * Cell to Estimate, Account Tables
     * <br>[cell set date from Epoch]
     */
    private class EpochDateCell extends TableCell {
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (item instanceof Integer)
                    setText(LocalDate.ofEpochDay((int) item).toString());
                if (item instanceof String)
                    setText(item.toString());
            }
        }

    }

    /**
     * Cell to expenses
     * <br>[set type of currency]
     */
    public class ExpensesCell extends TableCell {
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
                if (item.equals(0)) {
                    setText(" % ");
                } else if (item.equals(1)) {
                    setText(" Руб. ");
                    ;
                } else {
                    setText("N/A");
                    setGraphic(null);
                }
            } else {
                setText(null);
                setGraphic(null);
            }
        }

        ;
    }

    /**
     * Cell to EDIT TableWrapper
     * <br>[set type of currency]
     */
    private class EditSiteCell extends TableCell<PreviewTIV, Object> {

        private Est enumPreTable;

        public EditSiteCell(Est enumEst) {
            super();
            this.enumPreTable = enumEst;
        }


        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            int rowIndex = this.getIndex();
            PreviewTIV t = null;
            if (this.getItem() != null) t = this.getTableView().getItems().get(rowIndex);

            if (!isEmpty()) {
                switch (t.getSqlColumn()) {
                    case SQL.Site.DATE_CONTRACT:
                    case SQL.Site.FINISH_BUILDING:
                        setGraphic(createDatePicker(t));
                        break;
                    case SQL.Site.SITE_TYPE_ID:
                    case SQL.Site.STATUS_JOBS:
                    case SQL.Site.STATUS_PAYMENT:
                        setGraphic(createComboBox(t));
                        break;
                    case SQL.Site.SALE_CLIENTS:
                    case SQL.Site.DEBT_CLIENTS:
                    case SQL.Site.SALE_HOUSE:
                    case SQL.Site.N_CONTRACT:
                        setGraphic(createTextField(t));
                        break;
                    case SQL.Site.CONTRACTOR:
                    case SQL.Site.TYPE_HOME:
                        if (!t.getSecondValue().equals(ServiceStrings.Line)) {
                            setText(t.getSecondValue().toString());
                            setGraphic(null);
                        } else setGraphic(createComboBox(t));
                        break;
                    default:
                        if (item instanceof String) {
                            setText(item.toString());
                        } else if (item instanceof Long) {
                            setText(LocalDate.ofEpochDay((long) item).toString());
                        } else if (item instanceof Float) {
                            setText(new DoubleStringConverter().toString((Float) item));
                        } else if (item instanceof Double) {
                            setText(new DoubleStringConverter().toString((Double) item));
                        } else {
                            setText("N/A");
                            setGraphic(null);
                        }
//                        setText(t.getSiteSecondValue().formatNumber());
                        setGraphic(null);
                        break;
                }
            } else {
                setText(null);
                setGraphic(null);
            }
        }

        ;

        private DatePicker createDatePicker(PreviewTIV item) {
            DatePicker datePicker = new DatePicker();
            datePicker.setPrefWidth(150);
            datePicker.setEditable(false);
            datePicker.setValue(LocalDate.ofEpochDay((long) item.getSecondValue()));
            datePicker.setOnAction(e -> {
                item.setSecondValue(datePicker.getValue().toEpochDay());
            });

            datePicker.setConverter(new StringConverter<LocalDate>() {
                final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            });
            return datePicker;
        }

        private ComboBox createComboBox(PreviewTIV item) {
            ComboBox comboBox = new ComboBox();
            comboBox.setPrefWidth(150);
            switch (item.getSqlColumn()) {
                case SQL.Site.CONTRACTOR:
                    comboBox.setItems(new SiteDao().getDistinct(SQL.Common.CONTRACTOR));
//                                comboBox.setItems(new CommonQuery().getObsDISTINCT(SQL.Tables.C, SQL.Plan.TYPE_NAME, this));
                    break;
                case SQL.Site.TYPE_HOME:
                    comboBox.setItems(new EstimateDao().getDistinct(SQL.Common.TYPE_HOME));

                    break;
                case SQL.Site.SITE_TYPE_ID:
                    comboBox.setItems(new PlanDao().getDistinct(SQL.Plan.TYPE_NAME));
                    break;
                case SQL.Site.STATUS_JOBS:
                    comboBox.setItems(FXCollections.observableArrayList("закончен", "не начат", "в работе"));
                    break;
                case SQL.Site.STATUS_PAYMENT:
                    comboBox.setItems(FXCollections.observableArrayList("оплачен", "не оплачен"));
                    break;
            }
            comboBox.setValue(item.getSecondValue());
            comboBox.valueProperty().addListener(e -> {
                System.out.println("CB");
                item.setSecondValue(comboBox.getValue());
            });

            return comboBox;
        }

        private TextField createTextField(PreviewTIV item) {
            TextField textField = new TextField(item.getSecondValue().toString());
            textField.setMaxWidth(150);
            textField.textProperty().addListener(e -> {
                System.out.println("TF");
                switch (item.getSqlColumn()) {
                    case SQL.Site.N_CONTRACT:
                        item.setSecondValue(textField.getText());
                        break;
                    case SQL.Site.SALE_CLIENTS:
                    case SQL.Site.DEBT_CLIENTS:
                    case SQL.Site.SALE_HOUSE:
                        item.setSecondValue(new DoubleStringConverter().fromString(textField.getText()));
                        break;
                }

            });

            return textField;
        }

    }

    /**
     * Cell to "Add Row Estimate" TableWrapper (addSiteRowlayoutController)
     */
    private class CheckValueCell extends TableCell<AddEstTIV, Boolean> {
        private CheckBox checkBox;

        public CheckValueCell() {
        }


        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                if (item instanceof Boolean) {
                    createCheckBoxCell();
                    checkBox.setSelected((boolean) item);
                    setGraphic(checkBox);
                } else {
                    setGraphic(null);
                }
            } else {
                setText(null);
                setGraphic(null);
            }
            setAlignment(Pos.CENTER);
        }

        void createCheckBoxCell() {
            checkBox = new CheckBox();
            checkBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                getTableView().getItems().get(getIndex()).setCheck(newValue);
//                ((AddEstTIV)treeTableView().getObservableItems().saveEst(getIndex())).setCheck(newValue);
            });
        }
    }

    @Deprecated
    public class inKsColoredCell extends TableCell<EstimateTVI, Boolean> {

        @Override
        public void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEmpty()) {
//                        this.setTextFill(Color.BLACK);
                if (item.equals(true)) this.setTextFill(Color.RED);
                if (item.equals(false)) this.setTextFill(Color.GREEN);
                setText(Boolean.toString(item));
            }
        }

    }

    class EqualToAboveTableCell<T extends Number & Comparable> extends TableCell<Clone, T> {

        @Override
        public void updateItem(T item, boolean empty) {

            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);

            } else {

                setText(new DoubleStringConverter().toString(item));

                Comparable aboveCellValue
                        = super.getTableColumn().getCellData(this.getIndex() - 1);
                if (aboveCellValue != null)
                    switch (item.compareTo(aboveCellValue)) {
                        case 1:
                            this.setTextFill(Color.RED);
                            ;
                            break;
                        case 0:
                            this.setTextFill(Color.BLACK);
                            break;
                        case -1:
                            this.setTextFill(Color.GREEN);
                            ;
                            break;
                    }

            }
        }

    }


    @Deprecated
    private class OnMouseEnteredTableCell extends TableCell<AbstractEstimateTVI, Object> {
        private Est enumEst;

        private OnMouseEnteredTableCell(Est enumEst) {
            super();
            this.enumEst = enumEst;
        }

        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
                this.setOnMouseEntered(null);
            } else {
                setText(item.toString());
                setOnMouseEntered(value -> {
                    RootLayoutController.update_changeTable(
                            enumEst.findItemsList_DL(
                                    this.getTableView().getItems().get(this.getIndex())
                            )
                    );
                    System.out.println("ID EST =" + this.getTableView().getItems().get(this.getIndex()).getId());

                });
            }
        }
    }

    private class OnDoubleMouseClickMoveToCell extends TableCell<AbstractEstimateTVI, Object> {

        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
                this.setOnMouseEntered(null);
            } else {
                setText(item.toString());

                setOnMouseClicked(mouseEvent -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY) & mouseEvent.getClickCount() == 2) {
                        String text = this.getText();
//                            AbstractEstimateTVI itemW = this.treeTableView().getObservableItems()
//                                    .stream()
//                                    .filter(i -> i.getJM_name().equals(text))
//                                    .findFirst()
//                                    .get();
                        List items = this.getTableView().getItems().filtered(i -> i.getJM_name().equals(text));
                        if (!items.isEmpty()) {
                            int index = this.getTableView().getItems().indexOf(items.get(0));
                            this.getTableView().getSelectionModel().select(index);
                            this.getTableView().scrollTo(index);
                        }
                    }
                });
            }
        }
    }


    private class ObjectCell extends TableCell {
        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);

            if (!isEmpty()) {
                if (item instanceof String) {
                    setText(item.toString());
                } else if (item instanceof Long) {
                    setText(LocalDate.ofEpochDay((long) item).toString());
                } else if (item instanceof Integer) {
                    setText(item.toString());
                } else if (item instanceof Float) {
                    setText(new DoubleStringConverter().toString((Float) item));
                } else if (item instanceof Double) {
                    setText(new DoubleStringConverter().toString((Double) item));
                } else {
                    setText("N/A");
                    setGraphic(null);
                }
            } else {
                setText(null);
                setGraphic(null);
            }
        }

        ;
    }
//   private class TooltipTableCell extends TableCell<AbstractEstimateTVI, Object> {
//       TableWrapper table = new TableWrapper();
//       Tooltip tooltip = new Tooltip();
//         
//        public TooltipTableCell() {
//            super();
////            initTableView();
////            initTooltip();
//     
//        }
//
//        @Override
//        public void updateItem(Object item, boolean empty) {
//            super.updateItem(item, empty);
//             if (empty) {
//                setText(null);
//                setGraphic(null);
//                this.setOnMouseEntered(null);
//             } else {
//                setText(item.formatNumber());
//                setOnMouseEntered(quantity ->{
////                     
//                   
//                     RootLayoutController.update_changeTable((ObservableList) Est.Base.findItemsList_DL(this.treeTableView().getObservableItems().saveEst(this.getIndex())));
//                    
//                 });
//                 
//             }
//         } 
//        
//        private void initTableView(){
//           
//               table.setPrefWidth(900);
//               table.setPrefHeight(400);
//               table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//               
//               TableColumn tc1 = table.createColumn("Name", "JM_name");
//               TableColumn tc2 = table.createColumn("Кол-во", "quantity");
//               TableColumn Price_oneColumn = table.createColumn("Стоимость (за единицу)", "priceOne");
//               TableColumn Price_sumColumn = table.createColumn("Стоимость (общая)", "priceSum");
// 
//        }
//        
//        private void initTooltip(){
//            tooltip.setFont(new Font("Arial", 16));
//            tooltip.setGraphic(table);
//            
//            try {
//               Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
//               fieldBehavior.setAccessible(true);
//               Object objBehavior = fieldBehavior.saveEst(tooltip);
//               
//               Field fieldHideTimer = objBehavior.getClass().getDeclaredField("hideTimer");
////               Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
//               fieldHideTimer.setAccessible(true);
//               Timeline objTimer = (Timeline) fieldHideTimer.saveEst(objBehavior);
//               
//               objTimer.getKeyFrames().clear();
//               objTimer.getKeyFrames().add(new KeyFrame(new Duration(Double.MAX_VALUE)));
//               
//           } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
//               Logger.getLogger(TableCellFactory.class.getName()).log(Level.SEVERE, null, ex);
//           }
//        }
//   
//    }

    private class TestID extends TableCell<OSR_TIV, Object> {
        @Override
        public void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
                this.setOnMouseEntered(null);
            } else {
                setText(item.toString());
                setOnMouseEntered(value -> {
                    System.err.println("ID = " + this.getTableView().getItems().get(this.getIndex()).getId());

                });
            }
        }
    }
}
