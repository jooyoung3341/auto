package auto.tr.bybit.service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auto.tr.bybit.common.BybitCommon;

@Service
public class Trading {
    final static String BASE_URL = "https://api.bybit.com";
	@Autowired
	static BybitCommon common;
	
	//포지션 열기
	public static String positionOpen(String symbol, String side, String qty) throws InvalidKeyException, NoSuchAlgorithmException, IOException, InterruptedException {
		String endpoint = BASE_URL+"/v5/order/create";
		
		Map<String, Object> map = new HashMap<>();
		map.put("category", "linear");
		map.put("symbol", symbol);
		//buy : 롱 / sell : 숏
		map.put("side", side);
		map.put("orderType", "Market");
		//주문 수량
		map.put("qty", qty);
		map.put("timeInForce", "GTC");

		return common.postBybit(map, endpoint);
	}
	
	public static boolean isPosition(String symbol) throws IOException, InterruptedException {
		String endpoint = "v5/position/list";
		String url = BASE_URL+endpoint+"?category=linear&symbol="+symbol;
		String response = common.getPosition(url);
		
		try {
			org.json.JSONObject jsonResponse = new org.json.JSONObject(response);
	        org.json.JSONObject result = jsonResponse.getJSONObject("result");
	        org.json.JSONArray positions = result.getJSONArray("list");

	        for (int i = 0; i < positions.length(); i++) {
	            org.json.JSONObject position = positions.getJSONObject(i);
	            double size = position.getDouble("size");
	            if (size > 0) {
	                return true;
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

		//return common.getPosition(url);
	}
	
}
