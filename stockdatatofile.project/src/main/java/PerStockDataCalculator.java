import java.util.ArrayList;

public class PerStockDataCalculator {
    String symbol;
    int tradingDays = 253;
    static double topChange=0,topStability=0,topPayout=0,topROI=0,topConsistency=0,topTotalChange=0, topVolatility = 0, minVolatility = 0;
    static boolean firstRun = true;
    ArrayList<PerStockDataPoint> dataSet;
    StockList stockList;
    PerStockDataCalculator(ArrayList<PerStockDataPoint> perStockDataPointSet){
        dataSet = perStockDataPointSet;
    }
    public double allTimeHigh(){
        double high = 0;
        for(PerStockDataPoint data : dataSet){
            if(data.getPrice() > high){
                high = data.getPrice();
            }
        }
        return high;
    }
    public double percentChangeToday(){
        double a = dataSet.get(0).getPrice();
        double b = dataSet.get(1).getPrice();
        return ((a - b)/b) * 100;
    }
    public double averageDailyPercentChange(){
        double a = 0;
        double b = 0;
        double change = 0;
        double dataSize = 0;
        boolean firstRun = true;
        for(PerStockDataPoint data: dataSet){
            if(firstRun){
                a = data.getPrice();
                firstRun = false;
            }else{
                b = data.getPrice();
                dataSize++;
                //change += (((a-b)/b) * 100);
                change += (((b-a)/a) * 100);
                a = data.getPrice();
            }
        }
        return -change / dataSize;
    }
    public double weightedAverageDailyPercentChange(){
        double a = 0;
        double b = 0;
        double change = 0;
        double dataSize = 0;
        double dataConstant = 1 + (.01 * dataSet.size());
        boolean firstRun = true;
        for(PerStockDataPoint data: dataSet){
            if(firstRun){
                a = data.getPrice() * dataConstant;
                firstRun = false;
                dataConstant -= .01;
            }else{
                b = data.getPrice() * dataConstant;
                dataSize += dataConstant;
                //change += (((a-b)/b) * 100);
                change += (((b-a)/a) * 100);
                dataConstant -= .01;
                a = data.getPrice() * dataConstant;
            }
        }
        return -change / dataSize;
    }
    public double averageDividendsPerDollarPerYear(){
        double dataSize = 0;
        double divPerDollar = 0;
        for(PerStockDataPoint data : dataSet){
            if(data.getDividend() != 0.0){
                divPerDollar += data.getDividend()/data.getPrice();

            }
            dataSize++;
        }
        return (divPerDollar / dataSize) * tradingDays;
    }

    public double stabilityCoefficient(){
        //X is equal to the amount of days or place in the array! Y is equal to the price on that day.
        //Formula found on https://study.com/academy/lesson/pearson-correlation-coefficient-formula-example-significance.html
        double dataPoints = 0,sumX = 0,sumY = 0,sumXY = 0,sumXSquared = 0,sumYSquared = 0;
        for(PerStockDataPoint data : dataSet){
            sumX+=dataPoints;
            dataPoints++;
            sumY += data.getPrice();
            sumXY += (dataPoints * data.getPrice());
            sumXSquared += (dataPoints * dataPoints);
            sumYSquared += (data.getPrice() * data.getPrice());
        }
        double r = 0;
        r = ((dataPoints * sumXY) - (sumX * sumY))/(Math.sqrt(((dataPoints * sumXSquared) - (sumX * sumX)) * ((dataPoints * sumYSquared) - (sumY * sumY))));
                return -r;
    }
    public double weightedStabilityCoefficient(){
        //X is equal to the amount of days or place in the array! Y is equal to the price on that day.
        //Formula found on https://study.com/academy/lesson/pearson-correlation-coefficient-formula-example-significance.html
        double dataPoints = 0,sumX = 0,sumY = 0,sumXY = 0,sumXSquared = 0,sumYSquared = 0;
        double dataConstant = 1 + (.01 * dataSet.size());
        for(PerStockDataPoint data : dataSet){
            sumX+=dataPoints * dataConstant;
            dataPoints+= dataConstant;
            sumY += data.getPrice() * dataConstant;
            sumXY += (dataPoints * data.getPrice()) * dataConstant;
            sumXSquared += (dataPoints * dataPoints) * dataConstant;
            sumYSquared += (data.getPrice() * data.getPrice()) * dataConstant;
            dataConstant -= .01;
        }
        double r = 0;
        r = ((dataPoints * sumXY) - (sumX * sumY))/(Math.sqrt(((dataPoints * sumXSquared) - (sumX * sumX)) * ((dataPoints * sumYSquared) - (sumY * sumY))));
        return -r;
    }

    public double dividendPayoutPercentage(){
        int yearlyDividentPayouts = 0;
        boolean hasValue = false;
        double dividendValue = 0;
        double recentPrice = 0.0;
        boolean hasPrice = false;
        for(PerStockDataPoint data : dataSet){
            if(!hasPrice){
                recentPrice = data.getPrice();
                hasPrice = true;
            }
            if(data.getYear() == 2018 && (data.getDividend() - 0.01) >= 0.0){
                yearlyDividentPayouts++;
            }
            if(!hasValue && (data.getDividend() - 0.01) >= 0.0){
                dividendValue = data.getDividend();
                hasValue = true;
            }
        }
        return ((dividendValue * yearlyDividentPayouts) / recentPrice * 100);
    }
    public double yearlyROI(){
        double startPrice = 0;
        double endPrice = 0;
        final int tradingDays = 253;
        double change = 0;
        double actualDays = 0;
        boolean firstRun = true;
        for(int i=0 ; i < dataSet.size() ; i++){
            if(firstRun){
                endPrice = dataSet.get(i).getPrice();
                firstRun = false;
            }else if(actualDays == tradingDays || i == dataSet.size() - 1){
                startPrice = dataSet.get(i).getPrice();
                change = (((startPrice-endPrice)/startPrice) * 100);
                return -(change / (253/actualDays));
            }
                actualDays++;
        }
        return 0;
        //return -(change * (253/actualDays));
    }
    public double averageROI(){
        int currentYear = 0;
        final int tradingDays = 253;
        int daysThisYear = 0;
        double startPrice = 0;
        double endPrice = 0;
        int yearCount = 0;
        double change = 0;
        boolean firstRun = true;
        for(int i=0 ; i < dataSet.size() ; i++){
            if(firstRun){
                endPrice = dataSet.get(i).getPrice();
                daysThisYear++;
                //yearCount++;
                currentYear = dataSet.get(i).getYear();
                firstRun = false;
            }else if(currentYear != dataSet.get(i).getYear()){
                startPrice = dataSet.get(i - 1).getPrice();
                change += (((startPrice-endPrice)/startPrice) * 100) * (253/daysThisYear);
                endPrice = dataSet.get(i).getPrice();
                daysThisYear++;
                yearCount++;
                currentYear = dataSet.get(i).getYear();
                daysThisYear = 0;

            }else{
                daysThisYear++;
                endPrice += dataSet.get(i).getDividend();
            }
        }
        startPrice = dataSet.get(dataSet.size() - 1).getPrice();
        daysThisYear++;
        yearCount++;
        change += (((startPrice-endPrice)/startPrice) * 100) * (253 / daysThisYear);
        //EXPERIMENTAL ADDITION HERE
        return -change / yearCount;
    }
    public double consistencyRatio(){
        double upDays = 0, downDays = 0;
        double a = 0, b = 0;
        for(PerStockDataPoint data : dataSet){
            if(a == 0){
                a = data.getPrice();
            }else{
                b = data.getPrice();
                if(b > a){
                    downDays++;
                }else{
                    upDays++;
                }
                a = data.getPrice();
            }
        }
        if(downDays != 0) {
            return upDays / downDays;
        }else{
            return 999;
        }
    }
    //This is a score from 0-5 judging a stock on several merits
    //Currently 5, ROI, consistency, weighted stability, dividend payout, and average daily percent change.
    //Each attribute can lower or highten a stock's score by an amount and adding these values will give the HSI(Haehl Stock Index) score.
    public double HSIScore(){
        double changePoint, stabilityPoint, payoutPoint, ROIPoint, consistencyPoint, totalChangePoint, volatilityPoint;
        if(firstRun) {
            stockList = new StockList();
            stockList.buildList();


            for (Stock stock : stockList.getStockList()) {
                if (stock.weightedAverageChange() > topChange) {
                    topChange = stock.weightedAverageChange();
                }
                if (stock.stability() > topStability) {
                    topStability = stock.stability();
                }
                if (stock.payoutRatio() > topPayout) {
                    topPayout = stock.payoutRatio();
                }
                if (stock.yearlyROI() > topROI) {
                    topROI = stock.yearlyROI();
                }
                if (stock.consistency() > topConsistency) {
                    topConsistency = stock.consistency();
                }
                if(stock.totalChange() > topTotalChange){
                    topTotalChange = stock.totalChange();
                }
                if(stock.volatility() > topVolatility){
                    topVolatility = stock.volatility();
                }
                if(stock.volatility() < minVolatility && stock.totalChange() > 0){
                    minVolatility = stock.volatility();
                }
            }
            firstRun = false;
        }
        //SUBJECT TO CHANGE AS I FEEL FIT
/*        changePoint =  0.4 * weightedAverageDailyPercentChange()/topChange;
        if(weightedStabilityCoefficient() < .7){
            stabilityPoint = -1;
        }else {
            stabilityPoint = 1.7 * weightedStabilityCoefficient() / topStability;
        }
        if(dividendPayoutPercentage() < 0.01){
            payoutPoint = -10;
        }else{
            payoutPoint = 1.2 * dividendPayoutPercentage()/topPayout;
        }
        ROIPoint = 0.85 * yearlyROI()/topROI;
        consistencyPoint = 1.2 * consistencyRatio()/topConsistency;
        totalChangePoint = 1.7 * totalChange()/topTotalChange;*/
        changePoint = 0;
        ROIPoint = 0;
        consistencyPoint = 0;
        stabilityPoint = 0;
        if(dividendPayoutPercentage() < 0.01){
            payoutPoint = -1000;
        }else{
            payoutPoint =  1.3 * dividendPayoutPercentage()/4;
        }
        if(volatility() < 90){
            volatilityPoint = -1000;
        }else {
            //volatilityPoint = volatility() / 100;
            volatilityPoint = ((topVolatility - volatility()) / (topVolatility - minVolatility));
        }
        stabilityPoint = weightedStabilityCoefficient()/topStability;
        ROIPoint = yearlyROI()/20;
        totalChangePoint = .7 * totalChange()/.18;
        //Doesn't let 1 attribute outweigh others
        if(changePoint >1)
            changePoint=1;
        if(payoutPoint >1.3)
            payoutPoint=1.3;
        if(ROIPoint >1)
            ROIPoint=1;
        if(consistencyPoint >1)
            consistencyPoint=1;
        if(totalChangePoint >.7)
            totalChangePoint=.7;
        if(volatilityPoint >1)
            volatilityPoint=1;
        if(stabilityPoint >1)
            stabilityPoint=1;
        return  changePoint+payoutPoint+ROIPoint+consistencyPoint+totalChangePoint+volatilityPoint+stabilityPoint;
    }
    public double roundedPoint(double point){
        return Math.floor(point * 100) / 100;
    }
    public double totalChange(){
        double startPrice = dataSet.get(dataSet.size() -1).getPrice();
        double endPrice = dataSet.get(0).getPrice();
        return -(((startPrice-endPrice)/startPrice) * 100) / dataSet.size();
    }

    //100 is best possible score, meaning the stock follows its start/finish line. lower number means it fluctuates more violently.
    //takes totalChange to find how much it SHOULD move in a day, and takes actual % change in a day then compares them.
    //returns average difference between totalchange and actualchange.
    public double volatility(){
        boolean firstRun = true;
        double dayChange = 0;
        double averageChange = totalChange();
        double totalDifference = 0;
        double b,a=0;
        for(PerStockDataPoint data: dataSet){
            if(firstRun){
                a = data.getPrice();
                firstRun = false;
            }else{
                b = data.getPrice();
                //change += (((a-b)/b) * 100);
                dayChange = (((b-a)/a) * 100);
                a = data.getPrice();
            }
            totalDifference += Math.abs(averageChange - dayChange);
        }
        //Making high good! also, making it so if it is a consistent downward trend then it is considered bad.
        if(totalChange() >= 0){
            return 100 - totalDifference / dataSet.size();
        }else{
            return totalDifference / dataSet.size();
        }
    }


}