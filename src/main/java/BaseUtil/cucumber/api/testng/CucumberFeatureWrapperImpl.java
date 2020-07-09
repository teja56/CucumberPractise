package BaseUtil.cucumber.api.testng;

import BaseUtil.cucumber.runtime.model.CucumberFeature;

class CucumberFeatureWrapperImpl implements CucumberFeatureWrapper {
    private final CucumberFeature cucumberFeature;

    CucumberFeatureWrapperImpl(CucumberFeature cucumberFeature) {
        this.cucumberFeature = cucumberFeature;
    }

    @Override
    public String toString() {
        return "\"" + cucumberFeature.getGherkinFeature().getFeature().getName() + "\"";
    }
}