
package report.usage_strings;


/**
 * This Interface is AbstractJavaFxApplication Mirror of <b>SQLTables</b> Columns.
 */
public interface SQL extends ServiceStrings {


    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>SQLTables</b> Names.
     */
    interface Tables {
        String SITE = "Site";
        String FIN_PLAN = "FinPlan";
        String CONTRACTORS = "Contractors";
        String ESTIMATE = "Estimate";
        String SITE_EXPENSES = "SiteExpenses";
        String KS = "KS";
        String SITE_OSR = "SiteOSR";
        String SITE_JOB_PERIOD = "SiteJobPeriod";
        String COUNTERPARTIES = "Counterparties";
        String COUNT_REQ_BANK = "Count_Req_Bank";

    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>Common SQLTables</b> Columns.
     */
    interface Common {

        String ID = "id";
        String DATE_CREATE = "DateCreate";
        String DEL = "dell";

        String SITE_NUMBER = "SiteNumber";
        String TYPE_HOME = "TypeHome";
        String CONTRACTOR = "Contractor";
        String CONTRACTOR_ID = "id_Count";

        String TEXT = "Text";
        String TYPE = "Type";
        String VALUE = "Value";


    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>Site SQLTable</b> Columns.
     */
    interface Site {
        String SITE_NUMBER = Common.SITE_NUMBER;
        String TYPE_HOME = Common.TYPE_HOME;
        String CONTRACTOR = Common.CONTRACTOR;
        String CONTRACTOR_ID = Common.CONTRACTOR_ID;
        String DATE_CONTRACT = "DateContract";
        String FINISH_BUILDING = "FinishBuilding";
        String STATUS_JOBS = "StatusJobs";
        String STATUS_PAYMENT = "StatusPayment";
        String SALE_CLIENTS = "SaleClients";
        String DEBT_CLIENTS = "DebtClients";
        String SMET_COST = "SmetCost";
        String COST_HOUSE = "CostHouse";
        String SALE_HOUSE = "SaleHouse";
        String COST_SITE = "CostSite";
        String SUM_COST = "SumCost";
        String QUEUE_BUILDING = "QueueBuilding";
        String N_CONTRACT = "NContract";
        String COEFFICIENT = "k";
        String TAXES_ALL = "Taxes_all";
        String SITE_TYPE_ID = "SiteTypeID";


    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>KS SQLTable</b> Columns.
     */
    interface KS {
        String SITE_NUMBER = Common.SITE_NUMBER;
        String TYPE_HOME = Common.TYPE_HOME;
        String CONTRACTOR = Common.CONTRACTOR;

        String NUMBER = "KS_Number";
        String DATE = "KS_Date";


    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>Estimate SQLTable</b> Columns.
     */
    interface Estimate {
        String SITE_NUMBER = Common.SITE_NUMBER;
        String TYPE_HOME = Common.TYPE_HOME;
        String CONTRACTOR = Common.CONTRACTOR;
        String JM_NAME = "JM_name";
        String JOB_MATERIAL = "JobsOrMaterials";
        String BINDED_JOB = "bindedJob";
        String UNIT = "Unit";
        String VALUE = Common.VALUE;
        String PRICE_ONE = "Price_one";
        String PRICE_SUM = "Price_sum";
        String BUILDING_PART = "BuildingPart";
        String TABLE_TYPE = "TableType";
        String EXIST = "Exist";

    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>Contractors SQLTable</b> Columns.
     */
    interface Contractors {
        String DIRECTOR = "Director";
        String ADRESS = "Adress";
        String COMMENTS = "Comments";
        String CONTRACTOR = "Contractor";

    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>COR SQLTable</b> Columns.
     */
    interface Cor {
        String NAME_COR = "Name_Cor";


    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>Plan SQLTable</b> Columns.
     */
    interface Plan {
        String TYPE_ID = "TypeID";
        String TYPE_NAME = "TypeName";
        String QUANTITY = "Quantity";
        String REST = "Rest";
        String SMET_COST = "SmetCost";
        String SMET_COST_SUM = "SmetCostSUM";
        String SALE_COST = "SaleCost";
        String SALE_COST_SUM = "SaleCostSUM";
        String COST_HOUSE_SUM = "CostHouseSUM";


    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>Period SQLTable</b> Columns.
     */
    interface Period {
        String DATE_FROM = "DateFrom";
        String DATE_TO = "DateTo";

    }

    /**
     * This Nested Interface is AbstractJavaFxApplication Mirror of <b>Period SQLTable</b> Columns.
     */
    interface Formula {
        String SITE_EXPESES = "SiteExpeses";
        String OSR = "OSR";
        String QUANTITY = "Quantity";
        String SALE_HOUSE_SUM_ALL = "SaleHouseSumALL";
        String SMET_COST_SUM_ALL = "SmetCostSumALL";
        String SALE_COSTSUM_FROM_FINPLAN = "SaleCostSumFromFinPlan";

    }


}

