package auto.tr;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
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
	public void test() throws InvalidKeyException, NoSuchAlgorithmException {
		//
		String testDataArr = "102999.9,102713.9,103589.5,103397.1,103093.8,102578.8,102599.9,103374.8,103714.7,103844.5,103649.6,103864.9,103960.4,103746.0,103693.3,103287.6,103235.1,103714.3,103928.8,103984.4,103750.3,103953.2,102978.4,100796.8,104185.2,104245.9,106703.9,106532.9,105637.1,104564.6,105000.0,106500.0,107061.6,107444.6,107926.0,107785.8,107535.3,107715.2,108005.9,107711.1,107373.8,106997.8,106947.6,106505.1,106664.3,107409.8,108271.9,108180.2,108451.1,108136.0,108000.1,108487.7,108534.1,108118.2,108178.5,108020.7,108276.9,108783.9,108348.4,108256.1,108052.6,107892.0,107172.9,108015.4,108781.2,109059.4,107542.7,104714.7,102640.7,102495.8,102333.3,102599.9,102458.0,102451.9,102200.1,102298.8,101741.0,101778.3,101880.0,101569.2,101647.4,101767.5,101911.0,101628.9,101687.5,100969.9,100671.6,100044.7,100266.3,100190.9,99757.6,100738.2,101067.8,101398.5,101310.7,101203.0,100517.3,101264.1,103400.0,103330.9";
		String testhDataArr = "103001.9,102913.9,103989.5,103897.1,103793.8,103578.8,102899.9,103874.8,104714.7,104844.5,104649.6,104864.9,104960.4,104746.0,104693.3,104287.6,104235.1,104714.3,104928.8,104984.4,104750.3,104953.2,103978.4,102796.8,105185.2,105245.9,106803.9,106932.9,106637.1,105564.6,105010.0,106700.0,107161.6,108444.6,108926.0,108785.8,108535.3,108715.2,108105.9,107911.1,107973.8,107997.8,107947.6,107505.1,107664.3,107909.8,108571.9,108680.2,108751.1,108836.0,108200.1,108987.7,108734.1,108618.2,108678.5,108420.7,108576.9,108983.9,108848.4,108556.1,108452.6,108892.0,107572.9,108315.4,108981.2,109159.4,108542.7,106714.7,104640.7,104495.8,103333.3,103599.9,103458.0,103451.9,104200.1,103298.8,103741.0,102778.3,102880.0,102569.2,102647.4,102767.5,103911.0,102628.9,103687.5,101969.9,101671.6,101044.7,101266.3,101190.9,99857.6,101738.2,102067.8,102398.5,102310.7,102203.0,101517.3,102264.1,104400.0,104330.9";
		String testlDataArr = "101999.9,101713.9,102589.5,102397.1,102093.8,101578.8,101599.9,102374.8,102714.7,102844.5,102649.6,102864.9,102960.4,102746.0,102693.3,102287.6,102235.1,102714.3,102928.8,102984.4,102750.3,102953.2,101978.4,100196.8,103185.2,103245.9,105703.9,105532.9,104637.1,103564.6,104000.0,105500.0,106061.6,106444.6,106926.0,106785.8,106535.3,106715.2,107005.9,106711.1,106373.8,105997.8,105947.6,105505.1,105664.3,106409.8,107271.9,107180.2,107451.1,107136.0,107000.1,107487.7,107534.1,107118.2,107178.5,107020.7,107276.9,107783.9,107348.4,107256.1,107052.6,106892.0,106172.9,106015.4,107781.2,108059.4,106542.7,103714.7,101640.7,101495.8,101333.3,101599.9,101458.0,101451.9,101200.1,101298.8,100741.0,100778.3,100880.0,100569.2,100647.4,100767.5,100911.0,100628.9,100687.5,100469.9,100171.6,100004.7,100066.3,100090.9,99157.6,100038.2,100067.8,100398.5,100310.7,100203.0,100017.3,100264.1,100400.0,100330.9";
						
		String str [] = testDataArr.split(",");
		String str1 [] = testhDataArr.split(",");
		String str2 [] = testlDataArr.split(",");
		double h = 103589.5;
		double l = 102713.9;
		double c = 102999.9;
		List<Double> list = new ArrayList<>();
		List<Double> list1 = new ArrayList<>();
		List<Double> list2 = new ArrayList<>();
		
		
		for (int i = 0; i < str.length; i++) {
			list.add(Double.parseDouble(str[i]));
			list1.add(Double.parseDouble(str1[i]));
			list2.add(Double.parseDouble(str2[i]));
			
		}
		 //: sslUp : 101329.7916698587
		// : sslUp0.2 : 100112.32050707239

		
		System.out.println(" : sslUp : " + indicators.sslUpperk(list,list1,list2,60));
		System.out.println(" : emp9 : " + indicators.ema(list, 9));
		System.out.println(" : emp25 : " + indicators.ema(list,25));
		System.out.println(" : emp99 : " + indicators.ema(list,99));

		List<Double> tList1 = new ArrayList<>();
		List<Double> tList2 = new ArrayList<>();
		
		List<Double> trandUpList = new ArrayList<>();
		List<Double> trandLowList = new ArrayList<>();
		System.out.println("str :: " + str.length);
		for(int i = 1; i <= 7; i++) {
			int j = 0;
			List<Double> sslList = new ArrayList<>();
			List<Double> sslList1 = new ArrayList<>();
			List<Double> sslList2 = new ArrayList<>();
			for (int k = 0; k < str.length; k++) {
				if(j < i) {
					j += 1;
					continue;
				}
				sslList.add(list.get(k));
				sslList1.add(list1.get(k));
				sslList2.add(list2.get(k));
				
			}
			
			trandUpList.add(indicators.sslUpperk(sslList,sslList1,sslList2, 60));
			trandLowList.add(indicators.sslLowerk(sslList,sslList1,sslList2, 60));
		}
		
		Map<String, Object> tMap = dataCommon.trand(trandUpList, trandLowList);
		System.out.println(tMap);
		
		//System.out.println(" : sslLow : " + indicators.sslLowerk("HMA", d, 60, h, l, c));
	}
	/*@PostConstruct
	public void atTest() throws InvalidKeyException, NoSuchAlgorithmException {
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
		
		//0이 진행중 캔들 1이 이전캔들  모든곳이 그런듯 0이 진행중 1부터 이전캔들 
		for(List<Object> item : priceList) {
			System.out.println("ii : " + item);
			high = Double.parseDouble((String) item.get(2));
			low = Double.parseDouble((String) item.get(3));
			close = Double.parseDouble((String) item.get(4));
			break;
		}
		List<Double> testDataArr = new ArrayList<>();
		for(List<Object> item : data15List) {

			testDataArr.add(Double.parseDouble((String) item.get(4)));
		}
		System.out.println("testDataArr : " + testDataArr);
		//testDataArr : [102999.9, 102713.9, 103589.5, 103397.1, 103093.8, 102578.8, 102599.9, 103374.8, 103714.7, 103844.5, 103649.6, 103864.9, 103960.4, 103746.0, 103693.3, 103287.6, 103235.1, 103714.3, 103928.8, 103984.4, 103750.3, 103953.2, 102978.4, 100796.8, 104185.2, 104245.9, 106703.9, 106532.9, 105637.1, 104564.6, 105000.0, 106500.0, 107061.6, 107444.6, 107926.0, 107785.8, 107535.3, 107715.2, 108005.9, 107711.1, 107373.8, 106997.8, 106947.6, 106505.1, 106664.3, 107409.8, 108271.9, 108180.2, 108451.1, 108136.0, 108000.1, 108487.7, 108534.1, 108118.2, 108178.5, 108020.7, 108276.9, 108783.9, 108348.4, 108256.1, 108052.6, 107892.0, 107172.9, 108015.4, 108781.2, 109059.4, 107542.7, 104714.7, 102640.7, 102495.8, 102333.3, 102599.9, 102458.0, 102451.9, 102200.1, 102298.8, 101741.0, 101778.3, 101880.0, 101569.2, 101647.4, 101767.5, 101911.0, 101628.9, 101687.5, 100969.9, 100671.6, 100044.7, 100266.3, 100190.9, 99757.6, 100738.2, 101067.8, 101398.5, 101310.7, 101203.0, 100517.3, 101264.1, 103400.0, 103330.9]

		System.out.println(symbol + " / " + inter + " : high : " + high + "/ low : " + low + "/ close : " + close);
		//ssl n개데이터를 뽑아서 우상향인지 우하향인지  파악해서 추세 확인
		for(int i = 1; i <= 7; i++) {
			int j = 0;
			List<Double> sslList = new ArrayList<>();
			for(List<Object> item : data15List) {
				System.out.println("data 15 : " + item);
				if(j < i) {
					j += 1;
					continue;
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
				continue;
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
		
	}*/
}
