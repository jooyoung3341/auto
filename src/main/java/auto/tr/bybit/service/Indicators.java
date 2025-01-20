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
    /*
     * qqe모드
     //@version=6

indicator("QQE MOD", overlay=false, max_lines_count=1, timeframe = '')

// === PRIMARY QQE SETTINGS ===
group_primary = "Primary QQE Settings"
rsiLengthPrimary = input.int(6, title="RSI Length", group=group_primary)
rsiSmoothingPrimary = input.int(5, title="RSI Smoothing", group=group_primary)
qqeFactorPrimary = input.float(3.0, title="QQE Factor", group=group_primary)
thresholdPrimary = input.float(3.0, title="Threshold", group=group_primary)
sourcePrimary = input.source(close, title="RSI Source", group=group_primary)

// === SECONDARY QQE SETTINGS ===
group_secondary = "Secondary QQE Settings"
rsiLengthSecondary = input.int(6, title="RSI Length", group=group_secondary)
rsiSmoothingSecondary = input.int(5, title="RSI Smoothing", group=group_secondary)
qqeFactorSecondary = input.float(1.61, title="QQE Factor", group=group_secondary)
thresholdSecondary = input.float(3.0, title="Threshold", group=group_secondary)
sourceSecondary = input.source(close, title="RSI Source", group=group_secondary)

// === BOLLINGER BANDS SETTINGS ===
group_bollinger = "Bollinger Bands Settings"
bollingerLength = input.int(50, minval=1, title="Length", group=group_bollinger, tooltip="The length of the Bollinger Bands calculation.")
bollingerMultiplier = input.float(0.35, step=0.1, minval=0.001, maxval=5, title="Multiplier", group=group_bollinger, tooltip="The multiplier used to calculate Bollinger Band width.")

// === FUNCTIONS ===
// Calculate QQE Bands
calculateQQE(rsiLength, smoothingFactor, qqeFactor, source) =>
    wildersLength = rsiLength * 2 - 1
    rsi = ta.rsi(source, rsiLength)
    smoothedRsi = ta.ema(rsi, smoothingFactor)
    atrRsi = math.abs(smoothedRsi[1] - smoothedRsi)
    smoothedAtrRsi = ta.ema(atrRsi, wildersLength)
    dynamicAtrRsi = smoothedAtrRsi * qqeFactor

    // Initialize variables
    longBand = 0.0
    shortBand = 0.0
    trendDirection = 0

    // Calculate longBand, shortBand, and trendDirection
    atrDelta = dynamicAtrRsi
    newShortBand = smoothedRsi + atrDelta
    newLongBand = smoothedRsi - atrDelta
    longBand := smoothedRsi[1] > longBand[1] and smoothedRsi > longBand[1] ? math.max(longBand[1], newLongBand) : newLongBand
    shortBand := smoothedRsi[1] < shortBand[1] and smoothedRsi < shortBand[1] ? math.min(shortBand[1], newShortBand) : newShortBand
    longBandCross = ta.cross(longBand[1], smoothedRsi)
    
    if ta.cross(smoothedRsi, shortBand[1])
        trendDirection := 1
    else if longBandCross
        trendDirection := -1
    else
        trendDirection := trendDirection[1]

    // Determine the trend line
    qqeTrendLine = trendDirection == 1 ? longBand : shortBand
    [qqeTrendLine, smoothedRsi]

// === MAIN CALCULATIONS ===
// Calculate Primary QQE
[primaryQQETrendLine, primaryRSI] = calculateQQE(rsiLengthPrimary, rsiSmoothingPrimary, qqeFactorPrimary, sourcePrimary)

// Calculate Secondary QQE
[secondaryQQETrendLine, secondaryRSI] = calculateQQE(rsiLengthSecondary, rsiSmoothingSecondary, qqeFactorSecondary, sourceSecondary)

// Calculate Bollinger Bands for the Primary QQE Trend Line
bollingerBasis = ta.sma(primaryQQETrendLine - 50, bollingerLength)
bollingerDeviation = bollingerMultiplier * ta.stdev(primaryQQETrendLine - 50, bollingerLength)
bollingerUpper = bollingerBasis + bollingerDeviation
bollingerLower = bollingerBasis - bollingerDeviation

// Color Conditions for Primary RSI
rsiColorPrimary = primaryRSI - 50 > bollingerUpper ? color.new(#00c3ff, 0) : primaryRSI - 50 < bollingerLower ? color.new(#ff0062, 0) : color.new(#707070, 20)

// Color Conditions for Secondary RSI
rsiColorSecondary = secondaryRSI - 50 > thresholdSecondary ? color.new(#707070, 20) : secondaryRSI - 50 < -thresholdSecondary ? color.new(#707070, 20) : na

// === PLOTTING ===
// Plot Primary and Secondary QQE Lines
plot(secondaryQQETrendLine - 50, title="Secondary QQE Trend Line", color=color.white, linewidth=2)
plot(secondaryRSI - 50, color=rsiColorSecondary, title="Secondary RSI Histogram", style=plot.style_columns)

// Plot Signal Highlights
plot(secondaryRSI - 50 > thresholdSecondary and primaryRSI - 50 > bollingerUpper ? secondaryRSI - 50 : na, title="QQE Up Signal", style=plot.style_columns, color=#00c3ff)
plot(secondaryRSI - 50 < -thresholdSecondary and primaryRSI - 50 < bollingerLower ? secondaryRSI - 50 : na, title="QQE Down Signal", style=plot.style_columns, color=#ff0062)

// Plot Zero Line
hline(0, title="Zero Line", color=color.white, linestyle=hline.style_dotted)

// === ALERTS ===
// Alerts for Zero Crosses
alertcondition(ta.crossover(primaryRSI, 50), title="Primary RSI Cross Above Zero", message="Primary RSI crossed above zero")
alertcondition(ta.crossunder(primaryRSI, 50), title="Primary RSI Cross Below Zero", message="Primary RSI crossed below zero")

     */ 
     
}
