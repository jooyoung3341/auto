package auto.tr;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import auto.tr.bybit.common.Common;
import auto.tr.bybit.common.DataCommon;
import auto.tr.bybit.service.Data;
import auto.tr.bybit.service.Indicators;
import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class AutoApplication {
    @Autowired
    private Common common;

    @Autowired
    private Data data;

    @Autowired
    private Indicators indicators;

    @Autowired
    private DataCommon dataCommon;
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		SpringApplication.run(AutoApplication.class, args);
		//atStart();
		//atTest();
	}
	
	@PostConstruct
	private void atTest() throws InvalidKeyException, NoSuchAlgorithmException {
		String symbol = "BTC";
		String inter = "15";
		
		//15분봉
		Map<String, Object> data15Map = data.getKline("BTCUSDT", "15", 100);
		List<List<Object>> data15List = common.resultList(data15Map);
		
		List<Double> trandUpList = new ArrayList<>();
		List<Double> trandLowList = new ArrayList<>();
				
		//현재캔들 가격 
		double high = 0.0;
		double low = 0.0;
		double close = 0.0;
		Map<String, Object> priceMap = data.getMarkKline("BTCUSDT", "15");
		List<List<Object>> priceList = common.resultList(priceMap);
		
		for(List<Object> item : priceList) {
			high = Double.parseDouble((String) item.get(2));
			low = Double.parseDouble((String) item.get(3));
			close = Double.parseDouble((String) item.get(4));
		}
		System.out.println(symbol + " / " + inter + " : high : " + high + "/ low : " + low + "/ close : " + close);
		//ssl n개데이터를 뽑아서 우상향인지 우하향인지  파악해서 추세 확인
		for(int i = 1; i <= 7; i++) {
			int j = 0;
			List<Double> sslList = new ArrayList<>();
			for(List<Object> item : data15List) {
				if(j < i) {
					j += 1;
					break;
				}
				sslList.add(Double.parseDouble((String) item.get(4)));
			}
			System.out.println("---------------------");
			System.out.println("i : " + i);
			System.out.println(symbol + " / " + inter + " : sslUp : " + indicators.sslUpperk(sslList, 60, high, low, close));
			System.out.println(symbol + " / " + inter + " : sslLow : " + indicators.sslLowerk(sslList, 60, high, low, close));
			
			trandUpList.add(indicators.sslUpperk(sslList, 60, high, low, close));
			trandLowList.add(indicators.sslLowerk(sslList, 60, high, low, close));
		}
		System.out.println("---------------------");
		Map<String, Object> trandMap = dataCommon.trand(trandUpList, trandLowList);
		
		System.out.println(symbol + " / " + inter + " : ssl추세 : " + trandMap.get("trandStr"));
		System.out.println(symbol + " / " + inter + " : ssl마지막 가격 : " + trandMap.get("trandPrice"));
		int count = 0;
		List<Double> emaList = new ArrayList<>();
		for(List<Object> item : data15List) {
			//현재 진행중인 캔들은 제외
			if(count == 0) {
				count = 1;
				break;
			}
			emaList.add(Double.parseDouble((String) item.get(4)));
		}
		System.out.println(symbol + " / " + inter + " : ema9 가격 : " + indicators.ema(emaList, 9));
		System.out.println(symbol + " / " + inter + " : ema25 가격 : " + indicators.ema(emaList, 25));
		
		
		//trandPirce ssl 최근가격으로 현가격이랑 차이가 많이 날 경우에는 포지션 X(급등 급락 일수도있어서)

		
		Map<String, Object> data5Map = data.getKline("BTC", "15", 100);
		List<List<Object>> data5List = common.resultList(data5Map);
		
		for(int i = 1; i <= 7; i++) {
			int j = 0;
			List<Double> sslList = new ArrayList<>();
			for(List<Object> item : data5List) {
				if(j < i) {
					j += 1;
					continue;
				}
				sslList.add(Double.parseDouble((String) item.get(4)));
			}
			trandUpList.add(indicators.sslUpperk(sslList, 60, high, low, close));
			trandLowList.add(indicators.sslUpperk(sslList, 60, high, low, close));
		}
	}
	
	private void atStart() throws InvalidKeyException, NoSuchAlgorithmException {
		//bybit 포지션 연동 해서 포지션 여부 확인 후 호출하기
		boolean position = false;
		while(true) {
			if(position == true) {
				notNoPosition();
			}else {
				noPosition();
			}
		    // 일정 시간 대기
		    try {
		        Thread.sleep(1000); // 1초 대기
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	private void noPosition() throws InvalidKeyException, NoSuchAlgorithmException {
		String symbol = "BTCUSDT";
		//15분봉
		Map<String, Object> data15Map = data.getKline(symbol, "15", 100);
		List<List<Object>> data15List = common.resultList(data15Map);
		
		List<Double> trandUpList = new ArrayList<>();
		List<Double> trandLowList = new ArrayList<>();
		
		//현재캔들 가격 
		double high = 0.0;
		double low = 0.0;
		double close = 0.0;
		Map<String, Object> priceMap = data.getMarkKline(symbol, "15");
		List<List<Object>> priceList = common.resultList(priceMap);
		
		for(List<Object> item : priceList) {
			high = Double.parseDouble((String) item.get(2));
			low = Double.parseDouble((String) item.get(3));
			close = Double.parseDouble((String) item.get(4));
		}
		//ssl n개데이터를 뽑아서 우상향인지 우하향인지  파악해서 추세 확인
		for(int i = 1; i <= 7; i++) {
			int j = 0;
			List<Double> sslList = new ArrayList<>();
			for(List<Object> item : data15List) {
				if(j < i) {
					j += 1;
					break;
				}
				sslList.add(Double.parseDouble((String) item.get(4)));
			}
			trandUpList.add(indicators.sslUpperk(sslList, 60, high, low, close));
			trandLowList.add(indicators.sslLowerk(sslList, 60, high, low, close));
		}
		Map<String, Object> trandMap = dataCommon.trand(trandUpList, trandLowList);
		String trandStr = (String) trandMap.get("trandStr");
		
		
		//exit일 경우 다음 15분봉 갱신될떄까지 대기로직 추가(10분대기)
		if(trandStr == "exit") {
			try {
	            // 10분 대기 (600,000 밀리초)
	            Thread.sleep(600000);
	            return;
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	            return;
	        }
		}
		

		int count = 0;
		List<Double> emaList = new ArrayList<>();
		for(List<Object> item : data15List) {
			//현재 진행중인 캔들은 제외
			if(count == 0) {
				count = 1;
				break;
			}
			emaList.add(Double.parseDouble((String) item.get(4)));
		}
		//trandPirce ssl 최근가격으로 현가격이랑 차이가 많이 날 경우에는 포지션 X(급등 급락 일수도있어서)
		double trandPirce = (double) trandMap.get("trandPrice");
		
		Map<String, Object> data5Map = data.getKline(symbol, "15", 100);
		List<List<Object>> data5List = common.resultList(data5Map);
		
		for(int i = 1; i <= 7; i++) {
			int j = 0;
			List<Double> sslList = new ArrayList<>();
			for(List<Object> item : data5List) {
				if(j < i) {
					j += 1;
					continue;
				}
				sslList.add(Double.parseDouble((String) item.get(4)));
			}
			trandUpList.add(indicators.sslUpperk(sslList, 60, high, low, close));
			trandLowList.add(indicators.sslLowerk(sslList, 60, high, low, close));
		}
		
		//현가격이랑 몇퍼센트 차이가 나는지 구해야함
		if(trandStr == "up") {
			//상방 추세
			//여기에 포지션 잡을만한 조건 넣어서 조건이 맞을경우 포지션잡기
			//5분봉 추세 확인하여서 같을경우에만 포지션 진입? / 1or5 이평 정배열일 경우에만?
		}else if(trandStr == "down") {
			//하방 추세
		}
	}
	
	private void notNoPosition() {
		//1분봉 ssl 추세 바뀔때 포종?
		
	}
}
