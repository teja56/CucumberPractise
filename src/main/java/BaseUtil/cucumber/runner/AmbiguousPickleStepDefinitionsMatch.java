package BaseUtil.cucumber.runner;

import java.util.Collections;

import cucumber.api.Scenario;
import gherkin.pickles.PickleStep;
import io.cucumber.stepexpression.Argument;

final class AmbiguousPickleStepDefinitionsMatch extends PickleStepDefinitionMatch {
    private AmbiguousStepDefinitionsException exception;

    AmbiguousPickleStepDefinitionsMatch(String uri, PickleStep step, AmbiguousStepDefinitionsException e) {
        super(Collections.<Argument>emptyList(), new NoStepDefinition(), uri, step);
        this.exception = e;
    }

    @Override
    public void runStep(Scenario scenario) {
        throw exception;
    }

    @Override
    public void dryRunStep(Scenario scenario) {
        runStep(scenario);
    }

    @Override
    public Match getMatch() {
        return exception.getMatches().get(0);
    }
}
