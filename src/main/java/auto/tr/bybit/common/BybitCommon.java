package auto.tr.bybit.common;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class BybitCommon {

    final static String API_KEY  = "fLfrnDiVOeEqwXus6y";
    final static String API_SECRET  = "dCVX8PJYCwAIMv6QCitDaF1CtfTjL7iIR6m1";
    final static String TIMESTAMP = Long.toString(ZonedDateTime.now().toInstant().toEpochMilli());
    final static String RECV_WINDOW = "5000";

    
    
    public Map<String, Object> getBybit(Map<String, Object> map, String url) throws InvalidKeyException, NoSuchAlgorithmException{
		String signature = genGetSign(map);
		StringBuilder sb = genQueryStr(map);

		 OkHttpClient client = new OkHttpClient().newBuilder().build();
	        Request request = new Request.Builder()
	                .url(url + sb)
	                .get()
	                .addHeader("X-BAPI-API-KEY", API_KEY)
	                .addHeader("X-BAPI-SIGN", signature)
	                .addHeader("X-BAPI-SIGN-TYPE", "2")
	                .addHeader("X-BAPI-TIMESTAMP", TIMESTAMP)
	                .addHeader("X-BAPI-RECV-WINDOW", RECV_WINDOW)
	                .build();
	        Call call = client.newCall(request);
	        try {
	            Response response = call.execute(); 
	            assert response.body() != null;
	            
	            String responseBody = response.body().string();
	            
	            ObjectMapper objectMapper = new ObjectMapper();
	            return objectMapper.readValue(responseBody, Map.class);
	        }catch (IOException e){
	            e.printStackTrace();
	            return new HashMap<>();
	        }
	}
    
    public String postBybit(Map<String, Object> map, String url) throws InvalidKeyException, NoSuchAlgorithmException, IOException, InterruptedException{
		String body = map.entrySet().stream()
                .map(entry -> "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"")
                .collect(Collectors.joining(",", "{", "}"));
		String signature = genGetSign(map);
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("X-BAPI-API-KEY", API_KEY)
                .header("X-BAPI-TIMESTAMP", TIMESTAMP)
                .header("X-BAPI-SIGN", signature)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
		
		HttpClient client = HttpClient.newHttpClient();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		
		return response.body();
		
	}
    
    public String getPosition(String url) throws IOException, InterruptedException {
    	HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-BAPI-API-KEY", API_KEY)
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    	
    }
	  private static String genGetSign(Map<String, Object> params) throws NoSuchAlgorithmException, InvalidKeyException {
	        StringBuilder sb = genQueryStr(params);
	        String queryStr = TIMESTAMP + API_KEY + RECV_WINDOW + sb;

	        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
	        sha256_HMAC.init(secret_key);
	        return bytesToHex(sha256_HMAC.doFinal(queryStr.getBytes()));
	    }
	   
	    private static StringBuilder genQueryStr(Map<String, Object> map) {
	        Set<String> keySet = map.keySet();
	        Iterator<String> iter = keySet.iterator();
	        StringBuilder sb = new StringBuilder();
	        while (iter.hasNext()) {
	            String key = iter.next();
	            sb.append(key)
	                    .append("=")
	                    .append(map.get(key))
	                    .append("&");
	        }
	        sb.deleteCharAt(sb.length() - 1);
	        return sb;
	    }
	    
	    private static String bytesToHex(byte[] hash) {
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : hash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    }
}
