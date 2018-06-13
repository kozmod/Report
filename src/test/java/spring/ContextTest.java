package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import report.entities.items.site.SiteEntityDao;
import report.spring.spring.configuration.RootFxConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
public class ContextTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    public void NamedParameterJdbcTemplateTest() {

        String sql = "SELECT " +
                "S.[id]," +
                "S.[SiteNumber]," +
                "S.[id_Count]," +
                "S.[SiteTypeID]," +
                "S.[TypeHome]," +
                "S.[NContract]," +
                "S.[DateContract]," +
                "S.[SmetCost]," +
                "S.[SumCost]," +
                "S.[FinishBuilding]," +
                "S.[CostHouse]," +
                "S.[CostSite]," +
                "S.[SaleClients]," +
                "S.[DebtClients]," +
                "S.[StatusPayment]," +
                "S.[StatusJobs]," +
                "S.[QueueBuilding]," +
                "S.[SaleHouse]," +
                "S.[NumberSession]," +
                "S.[k]," +
                "S.[dell]," +
                "S.[DateCreate], " +
                " CAN.[Name] as [CountAgentName] " +
                " FROM [dbo].[Site_new] S " +
                " INNER JOIN [dbo].[DIC_Count_Name] CAN ON CAN.[id_Count] = S.[id_Count] " +
                " ";

    }
}


