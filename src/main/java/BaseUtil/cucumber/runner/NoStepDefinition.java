package BaseUtil.cucumber.runner;

import java.util.List;

import cucumber.runtime.StepDefinition;
import gherkin.pickles.PickleStep;
import io.cucumber.stepexpression.Argument;

final class NoStepDefinition implements StepDefinition {

    @Override
    public List<Argument> matchedArguments(PickleStep step) {
        return null;
    }

    @Override
    public String getLocation(boolean detail) {
        return null;
    }

    @Override
    public Integer getParameterCount() {
        return 0;
    }

    @Override
    public void execute(Object[] args) {
    }

    @Override
    public boolean isDefinedAt(StackTraceElement stackTraceElement) {
        return false;
    }

    @Override
    public String getPattern() {
        return null;
    }

    @Override
    public boolean isScenarioScoped() {
        return false;
    }

}
