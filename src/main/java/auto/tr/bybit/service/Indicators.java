package auto.tr.bybit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Indicators {

	 // Exponential Moving Average (EMA)
    public static double ema(List<Double> data, int period) {

        double alpha = 2.0 / (period + 1.0);
        double emaValue = data.get(0); // 초기값 설정
        for (int i = 1; i < data.size(); i++) {
            emaValue = alpha * data.get(i) + (1 - alpha) * emaValue;
        }
        return emaValue;
    }

    // Weighted Moving Average (WMA)
    public static double wma(List<Double> data, int period) {
    	 int size = data.size();
         double weightedSum = 0.0;
         System.out.println("wma data : " + data);
         System.out.println("wma period : " + period);
         
         for (int i = 0; i < period; i++) {
             weightedSum += data.get(size - period + i) * (i + 1);
         }
         double sumOfWeights = (period * (period + 1)) / 2.0;
         return weightedSum / sumOfWeights;
    }

    // Hull Moving Average (HMA)
    public static double hma(List<Double> data, int period) {
    	System.out.println("data :: " + data);
    	System.out.println("period :: " + period);
    	

        List<Double> dataArrHma = new ArrayList<>();
        for (int i = 0; i < period; i++) {
            dataArrHma.add(data.get(data.size() - period + i));
        }

        double wmaHalfPeriod = wma(dataArrHma, period / 2);
        double wmaPeriod = wma(dataArrHma, period);

        List<Double> dataArr = new ArrayList<>();
        dataArr.add(2 * wmaHalfPeriod - wmaPeriod);

        return wma(dataArr, (int) Math.sqrt(period));
    }

    // True Range
    public static double trueRange(double high, double low, double close) {
        return Math.max(Math.max(Math.abs(high - low), Math.abs(high - close)), Math.abs(low - close));
    }

    // SSL Upper Band
    public static double sslUpperk(List<Double> data, int period, double high, double low, double close) {
    	System.out.println("Zzz"+ period);
        double keltma = hma(data, period);
        double range = trueRange(high, low, close);

        List<Double> rangeArr = new ArrayList<>();
        rangeArr.add(range);

        double rangema = ema(rangeArr, period);
        return keltma + rangema;
    }

    // SSL Lower Band
    public static double sslLowerk(List<Double> data, int period, double high, double low, double close) {
        double keltma = hma(data, period);
        double range = trueRange(high, low, close);

        List<Double> rangeArr = new ArrayList<>();
        rangeArr.add(range);

        double rangema = ema(rangeArr, period);
        return keltma - rangema;
    }
}
