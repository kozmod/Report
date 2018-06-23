package report.models.counterpaties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import report.entities.abstraction.Transformable;
import report.entities.items.site.PreviewTIV;
import report.entities.items.site.SiteEntity;
import report.usage_strings.SQL;

import java.math.BigDecimal;

public class SiteWrapper implements Transformable<PreviewTIV> {
    private final SiteEntity siteEntity;
    private BigDecimal allTaxes;

    public SiteWrapper(SiteEntity siteEntity) {
        this.siteEntity = siteEntity;
    }
    public SiteWrapper(SiteEntity siteEntity,BigDecimal allTaxes) {
        this.siteEntity = siteEntity;
        this.allTaxes = allTaxes;
    }

    public SiteEntity getSiteEntity() {
        return siteEntity;
    }

    public BigDecimal getAllTaxes() {
        return allTaxes;
    }

    public void setAllTaxes(BigDecimal allTaxes) {
        this.allTaxes = allTaxes;
    }

    @Override
    public ObservableList<PreviewTIV> reverse() {
        ObservableList<PreviewTIV> list = FXCollections.observableArrayList(PreviewTIV.extractor());
        list.addAll(//0
                new PreviewTIV(siteEntity.getId(), SQL.Site.SITE_NUMBER, "Номер участка", siteEntity.getSiteNumber()),
                //1
                new PreviewTIV(siteEntity.getId(), SQL.Site.TYPE_HOME, "Тип дома", siteEntity.getTypeHome()),
                //2
                new PreviewTIV(siteEntity.getId(), SQL.Site.CONTRACTOR, "Субподрядчик", siteEntity.getCountAgentName()),
                //3
                new PreviewTIV(siteEntity.getId(), SQL.Site.SITE_TYPE_ID, "Класс Дома", siteEntity.getTypeHome()),
                //4
                new PreviewTIV(siteEntity.getId(), SQL.Site.DATE_CONTRACT, "Дата договора", siteEntity.getDateOfContract()),
                //5
                new PreviewTIV(siteEntity.getId(), SQL.Site.FINISH_BUILDING, "Окончание строительства", siteEntity.getFinishBuilding()),
                //6
                new PreviewTIV(siteEntity.getId(), SQL.Site.STATUS_JOBS, "Статус строительства", siteEntity.getStatusJobs()),
                //7
                new PreviewTIV(siteEntity.getId(), SQL.Site.STATUS_PAYMENT, "Статус оплаты", siteEntity.getStatusPayment()),
                //8
                new PreviewTIV(siteEntity.getId(), SQL.Site.SALE_CLIENTS, "Оплачено клиентом", siteEntity.getSaleClient()),
                //9
                new PreviewTIV(siteEntity.getId(), SQL.Site.DEBT_CLIENTS, "Долг клиента", siteEntity.getDebtClient()),
                //10
                new PreviewTIV(siteEntity.getId(), SQL.Site.SMET_COST, "Сметная стоимость", siteEntity.getSmetCost()),
                //11
                new PreviewTIV(siteEntity.getId(), SQL.Site.COST_HOUSE, "Стоимость дома", siteEntity.getCostHouse()), //Продажная cебестоимость / CostHouse == SmetCost
                //12
                new PreviewTIV(siteEntity.getId(), SQL.Site.SALE_HOUSE, "Цена продажи дома", siteEntity.getSaleHouse()), // + SaleHouse "Цена продажи дома"
                //13
                new PreviewTIV(siteEntity.getId(), SQL.Site.COST_SITE, "Стоимость земли", siteEntity.getCostSite()),
                //14
                new PreviewTIV(siteEntity.getId(), SQL.Site.SUM_COST, "Сумма затрат", siteEntity.getSumCost()),// >>>>>>>>>>> Delete
                //18
                new PreviewTIV(siteEntity.getId(), SQL.Site.TAXES_ALL, "Выплаченные налоги", 0),
                //15
                new PreviewTIV(siteEntity.getId(), SQL.Site.QUEUE_BUILDING, "Очередь Строительства", siteEntity.getQueueBuildings()),
                //16
                new PreviewTIV(siteEntity.getId(), SQL.Site.N_CONTRACT, "№ Контракта", siteEntity.getNumberOfContract()),
                //17 k  - коэффициент умножения
                new PreviewTIV(siteEntity.getId(), SQL.Site.COEFFICIENT, "Коэффициент", siteEntity.getCoefficient())
        );
        return list;
    }
}
