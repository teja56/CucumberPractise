package BaseUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class StepData {
	
	public static Map<String, Object> storedValuesMap;

    public StepData() {
        storedValuesMap = new LinkedHashMap<String, Object>();
    }

	@SuppressWarnings("unchecked")
	public LinkedHashMap<String, Object> getStoredValues() {
        String threadId = Thread.currentThread().getName();
        if (storedValuesMap.containsKey(threadId)) {
            return (LinkedHashMap<String, Object>)storedValuesMap.get(threadId);
        }
        else {
            synchronized (storedValuesMap) {
                storedValuesMap.put(threadId, new LinkedHashMap<String, Object>());
                return (LinkedHashMap<String, Object>) storedValuesMap.get(threadId);
            }
        }
    }
    
    public void storeValue(String key, Object value) {
        getStoredValues().put(key, value);
    }
    
    public Object getStoredValue(String key) {
        return getStoredValues().get(key);
    }  

}