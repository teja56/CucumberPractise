package BaseUtil.cucumber.runner;

import java.util.Collections;

import cucumber.api.Scenario;
import gherkin.pickles.PickleStep;
import io.cucumber.stepexpression.Argument;

class UndefinedPickleStepDefinitionMatch extends PickleStepDefinitionMatch {

    UndefinedPickleStepDefinitionMatch(PickleStep step) {
        super(Collections.<Argument>emptyList(), new NoStepDefinition(), null, step);
    }

    @Override
    public void runStep(Scenario scenario) {
        throw new UndefinedStepDefinitionException();
    }

    @Override
    public void dryRunStep(Scenario scenario) throws Throwable {
        runStep(scenario);
    }

}
