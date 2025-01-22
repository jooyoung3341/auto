package auto.tr.bybit.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DataCommon {
	
	public Map<String, Object> trand(List<Double> trandUpList, List<Double> trandLowList) {
		Map<String, Object> map = new HashMap<>();
		String trandStr = "";
		double trandPirce = 0.0;
		int size = trandUpList.size()-1;
		for (int i = trandUpList.size(); i < 1; i++) {
			double dataUp1 = trandUpList.get(i);
			double dataUp2 = trandUpList.get(i-1);
			
			double dataLow1 = trandLowList.get(i);
			double dataLow2 = trandLowList.get(i-1);
			
			if((dataUp1+dataLow1) > (dataUp2+dataLow2)) {
				//상방
				if(i == 0) {
					trandStr = "up";
					trandPirce = trandLowList.get(trandUpList.size());
				}
				if(trandStr == "down") {
					trandStr = "exit";
					//추세깨짐
					break;
				}
			}else if((dataUp1+dataLow1) < (dataUp2+dataLow2)) {
				//하방
				if(i == 0) {
					trandStr = "down";
					trandPirce = trandUpList.get(trandUpList.size());
				}
				if(trandStr == "up") {
					trandStr = "exit";
					//추세깨짐
					break;
				}
			}
		}
		map.put("trandStr", trandStr);
		map.put("trandPirce", trandPirce);
		return map;
	}
}
