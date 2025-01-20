package auto.tr.bybit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class Indicators {

	 // Exponential Moving Average (EMA)
    public static double ema(List<Double> data, int period) {
        if (data.isEmpty() || period <= 0) {
            throw new IllegalArgumentException("Invalid data or period");
        }
        double alpha = 2.0 / (period + 1.0);
        double emaValue = data.get(0); // 초기값 설정
        for (int i = 1; i < data.size(); i++) {
            emaValue = alpha * data.get(i) + (1 - alpha) * emaValue;
        }
        return emaValue;
    }

    // Weighted Moving Average (WMA)
    public static double wma(List<Double> data, int period) {
        if (data.size() < period || period <= 0) {
            throw new IllegalArgumentException("Invalid data or period");
        }
        double weightedSum = 0.0;
        int weight = 0;
        for (int i = data.size() - period, w = 1; i < data.size(); i++, w++) {
            weightedSum += data.get(i) * w;
            weight += w;
        }
        return weightedSum / weight;
    }

    // Hull Moving Average (HMA)
    public static double hma(List<Double> data, int period) {
        if (data.size() < period || period <= 0) {
            throw new IllegalArgumentException("Invalid data or period");
        }
        List<Double> halfWMA = new ArrayList<>();
        for (int i = data.size() - period; i < data.size(); i++) {
            halfWMA.add(data.get(i));
        }

        double wmaHalf = wma(halfWMA, period / 2);
        double wmaFull = wma(halfWMA, period);

        List<Double> hmaData = new ArrayList<>();
        hmaData.add(2 * wmaHalf - wmaFull);

        return wma(hmaData, (int) Math.sqrt(period));
    }

    // True Range
    public static double trueRange(double high, double low, double close) {
        return Math.max(Math.max(Math.abs(high - low), Math.abs(high - close)), Math.abs(low - close));
    }

    // SSL Upper Band
    public static double sslUpperk(List<Double> data, int period, double high, double low, double close) {
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
