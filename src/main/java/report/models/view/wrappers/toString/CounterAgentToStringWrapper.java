package report.models.view.wrappers.toString;

import report.entities.items.counterparties.AgentTVI.CountAgentTVI;

public final  class CounterAgentToStringWrapper implements ToStringWrapper<CountAgentTVI> {
    private final CountAgentTVI countAgent;

    public CounterAgentToStringWrapper(CountAgentTVI countAgent) {
        this.countAgent = countAgent;
    }

    @Override
    public CountAgentTVI getItem() {
        return countAgent;
    }

    @Override
    public String toString() {
        return countAgent.getForm()
                .concat(" ")
                .concat(countAgent.getName());
    }
}
