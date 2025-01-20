package auto.tr.bybit.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import auto.tr.bybit.common.BybitCommon;

@Component
public class Data {
	@Autowired
	BybitCommon common;
	
	public Map<String, Object> getKline(String symbol, String interval, int limit) throws InvalidKeyException, NoSuchAlgorithmException{
		Map<String, Object> map = new HashMap<>();
		map.put("category", "linear");
		map.put("symbol", symbol);
		//분봉
		map.put("interval", interval);
		//정수
		map.put("limit", limit);
		String url = "https://api.bybit.com/v5/market/kline?";
		return common.getBybit(map, url);
	}
	
	public Map<String, Object> getMarkKline(String symbol, String interval) throws InvalidKeyException, NoSuchAlgorithmException{
		Map<String, Object> map = new HashMap<>();
		map.put("symbol", symbol);
		//분봉
		map.put("interval", interval);
		String url = "https://api.bybit.com/v5/market/mark-price-kline?";
		return common.getBybit(map, url);
	}
}
