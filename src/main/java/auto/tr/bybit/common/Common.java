package auto.tr.bybit.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Common {

	@SuppressWarnings("unchecked")
	public List<List<Object>> resultList(Map<String, Object> map){
		Map<String, Object> result = (Map<String, Object>) map.get("result");
		return  (List<List<Object>>) result.get("list");
	}
}
