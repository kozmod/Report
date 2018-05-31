package report.layout.controllers.root;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import report.entities.items.site.PreviewTIV;
import report.entities.items.site.SiteDao;
import report.entities.items.site.SiteEntity;
import report.layout.controllers.DeleteController;
import report.layout.controllers.estimate.EstimateController;
import report.layout.controllers.expences.ExpensesController;
import report.models.coefficient.Formula;
import report.models.coefficient.FormulaQuery;
import report.models.view.nodesFactories.TableCellFactory;
import report.models.view.nodesFactories.TableFactory;
import report.models.view.nodesHelpers.FxmlStage;
import report.models.view.wrappers.toString.CounterAgentToStringWrapper;
import report.models.view.wrappers.toString.ToStringWrapper;
import report.services.common.CounterAgentWrapper;
import report.usage_strings.PathStrings;
import report.usage_strings.SQL;

class RootControllerNodeUtils implements TableFactory {

    private RootControllerNodeUtils() {
    }

    /**
     * Create TableWrapper(Preview TableWrapper) with 2 columns /Object/.
     *
     * @return TableWrapper(Preview TableWrapper)
     */
    static TableView getSite() {
        TableView table = new TableView();

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPlaceholder(new Label("необходимо выбрать участок и подрядчика"));
        table.setPrefHeight(450);

        TableColumn titleCol = new TableColumn("Параметр");
        titleCol.setCellValueFactory(new PropertyValueFactory("firstValue"));

        TableColumn valueCol = new TableColumn("Значение");
        valueCol.setCellValueFactory(new PropertyValueFactory("secondValue"));
        valueCol.setCellFactory(param -> TableCellFactory.getPreviewCell());


        table.getColumns().addAll(titleCol, valueCol);

        return table;
    }


    static ObservableList<PreviewTIV> getPreviewTIV() {

        final CounterAgentWrapper counterAgentWrapper = RootControllerService
                .getCounterAgentWrapper()
                .orElseThrow(
                        () -> new IllegalArgumentException("CounterAgentWrapper is NULL")
                );

        final String counterAgetNameWithForm = counterAgentWrapper.getSelectedCounterAgent()
                .getForm()
                .concat(" ")
                .concat(counterAgentWrapper.getSelectedCounterAgent().getName());

        final long siteId = counterAgentWrapper.getSiteEntity().getId();
        final SiteEntity siteEntity = counterAgentWrapper.getSiteEntity();

        final ObservableList<PreviewTIV> list = FXCollections.observableArrayList(PreviewTIV.extractor());

        list.addAll(
                //0
                new PreviewTIV(siteId, SQL.Site.SITE_NUMBER, "Номер участка", siteEntity.getSiteNumber()),
                //1
                new PreviewTIV(siteId, SQL.Site.TYPE_HOME, "Тип дома", siteEntity.getTypeHome()),
                //2
                new PreviewTIV(siteId, SQL.Site.CONTRACTOR, "Субподрядчик", counterAgetNameWithForm),
                //3
                new PreviewTIV(siteId, SQL.Site.SITE_TYPE_ID, "Класс Дома", siteEntity.getSiteTypeId()),
                //4
                new PreviewTIV(siteId, SQL.Site.DATE_CONTRACT, "Дата договора", siteEntity.getDateOfContract()),
                //5
                new PreviewTIV(siteId, SQL.Site.FINISH_BUILDING, "Окончание строительства", siteEntity.getFinishBuilding()),
                //6
                new PreviewTIV(siteId, SQL.Site.STATUS_JOBS, "Статус строительства", siteEntity.getStatusJobs()),
                //7
                new PreviewTIV(siteId, SQL.Site.STATUS_PAYMENT, "Статус оплаты", siteEntity.getStatusPayment()),
                //8
                new PreviewTIV(siteId, SQL.Site.SALE_CLIENTS, "Оплачено клиентом", siteEntity.getSaleClient()),
                //9
                new PreviewTIV(siteId, SQL.Site.DEBT_CLIENTS, "Долг клиента", siteEntity.getDebtClient()),
                //10
                new PreviewTIV(siteId, SQL.Site.SMET_COST, "Сметная стоимость", siteEntity.getSmetCost()),
                //11
                new PreviewTIV(siteId, SQL.Site.COST_HOUSE, "Стоимость дома", siteEntity.getCostHouse()), //Продажная cебестоимость / CostHouse == SmetCost
                //12
                new PreviewTIV(siteId, SQL.Site.SALE_HOUSE, "Цена продажи дома", siteEntity.getSaleHouse()), // + SaleHouse "Цена продажи дома"
                //13
                new PreviewTIV(siteId, SQL.Site.COST_SITE, "Стоимость земли", siteEntity.getCostSite()),
                //14
                new PreviewTIV(siteId, SQL.Site.SUM_COST, "Сумма затрат", siteEntity.getSumCost()),// >>>>>>>>>>> Delete
                //18
                new PreviewTIV(siteId, SQL.Site.TAXES_ALL, "Выплаченные налоги", 0),
                //15
                new PreviewTIV(siteId, SQL.Site.QUEUE_BUILDING, "Очередь Строительства", siteEntity.getQueueBuildings()),
                //16
                new PreviewTIV(siteId, SQL.Site.N_CONTRACT, "№ Контракта", siteEntity.getNumberOfContract()),
                //17 k  - коэффициент умножения
                new PreviewTIV(siteId, SQL.Site.COEFFICIENT, "Коэффициент", siteEntity.getCoefficient())
        );
        return list;
    }

    static RootTreeViewCellFactory getRootTreeViewCellFactory(RootLayoutController rootLayoutController) {
        return new RootTreeViewCellFactory(rootLayoutController);
    }

    static class RootTreeViewCellFactory implements Callback<TreeView<ToStringWrapper>, TreeCell<ToStringWrapper>> {
        private String selectedTreeElemParent;
        private String selectedTreeElem;
        private RootLayoutController rootLayoutControlle;


        RootTreeViewCellFactory(RootLayoutController rootLayoutControlle) {
            this.rootLayoutControlle = rootLayoutControlle;
        }

        @Override
        public TreeCell<ToStringWrapper> call(TreeView<ToStringWrapper> param) {
            TreeCell<ToStringWrapper> cell = new TreeCell<ToStringWrapper>() {
                @Override
                protected void updateItem(ToStringWrapper item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setText(null);
                    } else {
                        setText(getItem() == null ? "" : getItem().toString());
                    }
                    setGraphic(null);
                }

            };
            final ContextMenu contextMenu = new ContextMenu();

            final MenuItem siteEditItem = new MenuItem("Редактировать") {{
                setDisable(true);
            }};
            final MenuItem contAddItem = new MenuItem("Добавить субподрядчика") {{
                setDisable(true);
            }};
            final SeparatorMenuItem separator = new SeparatorMenuItem() {{
                setDisable(true);
            }};
            final MenuItem contDelItem = new MenuItem("Удалить") {{
                setDisable(true);
            }};

            siteEditItem.setOnAction(event -> {
                FxmlStage expensesLayout = new FxmlStage(
                        PathStrings.Layout.EXPENSES,
                        "Свойства Участка № ".concat(selectedTreeElemParent)
                                .concat(" / ").concat(selectedTreeElem)
                ).loadAndShowNewWindow();

                ExpensesController expensesController = expensesLayout.getController();
                rootLayoutControlle.setExpensesController(expensesController);
                expensesController.setControllerStage(expensesLayout.getStage());
                expensesLayout.getStage().setResizable(false);
//                    expensesLayout.getStage().show();
            });
            contAddItem.setOnAction(event ->
                    new FxmlStage(
                            PathStrings.Layout.CONT_ADD, "Добавить Субподрядчика"
                    ).loadAndShowNewWindow()
            );
            contDelItem.setOnAction(event -> {
                FxmlStage delLayout = new FxmlStage(PathStrings.Layout.DEL_SITE, "Удалить")
                        .loadAndShowNewWindow();
                DeleteController delController = delLayout.getController();
                rootLayoutControlle.setDelController(delController);
                delController.init_delLabels(selectedTreeElem, selectedTreeElemParent);
//                    delLayout.getStage().show();
            });


            contextMenu.getItems().addAll(siteEditItem, contAddItem, separator, contDelItem);

            cell.contextMenuProperty().bind(
                    Bindings.when(cell.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            cell.setOnMouseClicked((MouseEvent event) -> {
                if (((TreeView) cell.getTreeView()).getTreeItem(cell.getIndex()).isLeaf()) {
                    selectedTreeElemParent = ((TreeView) cell.getTreeView())
                            .getTreeItem(cell.getIndex())
                            .getParent()
                            .getValue().toString();

                    CounterAgentToStringWrapper selectedItem = (CounterAgentToStringWrapper) cell.getTreeView().getTreeItem(cell.getIndex()).getValue();
                    RootControllerService.initCounterAgentHolder(selectedTreeElemParent, selectedItem.getEntity());

                    selectedTreeElem = selectedItem.getEntity().getName();
                    EstimateController.Est.Common.setCountAgentTVI(selectedItem.getEntity());

                    siteEditItem.setDisable(false);
                    contAddItem.setDisable(false);
                    separator.setDisable(false);
                    contDelItem.setDisable(false);
//                        printEstToXmlMenuItem.setDisable(false); //todo : unkomment

                    //Update Preview TableWrapper OBS-LIST
                    EstimateController.Est.Common.setSiteObs(
                            new SiteDao(
                                    selectedTreeElemParent,
                                    selectedItem.getEntity().getIdCountConst()
                            ).getData()
                    );
                    rootLayoutControlle.getPreviewTable().setItems(EstimateController.Est.Common.getPreviewObservableList());

                    //create Coefficient
                    Formula formula = new FormulaQuery().getFormula();
                    //Set TAXES_ALL
                    EstimateController.Est.Common.getSiteItem(SQL.Site.TAXES_ALL).setSecondValue(formula.allTaxes());

                    FxmlStage estLayout = new FxmlStage(PathStrings.Layout.SITE_EST)
                            .loadIntoRootBorderPaneCenter();
                    rootLayoutControlle.setEstController(estLayout.getController());

                } else {
                    siteEditItem.setDisable(true);
                    contAddItem.setDisable(true);
                    separator.setDisable(true);
                    contDelItem.setDisable(true);

                }
            });
            return cell;
        }


    }
}