package BaseUtil.cucumber.runtime;

import java.util.List;

import BaseUtil.cucumber.runtime.model.CucumberFeature;

public interface FeatureSupplier {
    List<CucumberFeature> get();
}
