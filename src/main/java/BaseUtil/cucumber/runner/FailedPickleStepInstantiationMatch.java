package BaseUtil.cucumber.runner;

import java.util.Collections;

import cucumber.api.Scenario;
import gherkin.pickles.PickleStep;
import io.cucumber.stepexpression.Argument;

final class FailedPickleStepInstantiationMatch extends PickleStepDefinitionMatch {
    private final Throwable throwable;

    FailedPickleStepInstantiationMatch(String uri, PickleStep step, Throwable throwable) {
        super(Collections.<Argument>emptyList(), new NoStepDefinition(), uri, step);
        this.throwable = removeFrameworkFramesAndAppendStepLocation(throwable, getStepLocation());
    }

    @Override
    public void runStep(Scenario scenario) throws Throwable {
        throw throwable;
    }
    public void dryRunStep(Scenario scenario) throws Throwable {
        runStep(scenario);
    }

}
