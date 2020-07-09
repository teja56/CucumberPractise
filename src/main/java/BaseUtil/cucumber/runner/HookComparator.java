package BaseUtil.cucumber.runner;

import java.util.Comparator;

import cucumber.runtime.HookDefinition;

class HookComparator implements Comparator<HookDefinition> {
    private final boolean ascending;

    HookComparator(boolean ascending) {
        this.ascending = ascending;
    }

    @Override
    public int compare(HookDefinition hook1, HookDefinition hook2) {
        int x = hook1.getOrder();
        int y = hook2.getOrder();
        return ascending ? Integer.compare(x, y) : Integer.compare(y, x);
    }
}
