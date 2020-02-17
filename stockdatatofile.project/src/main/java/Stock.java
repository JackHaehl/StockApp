import java.util.ArrayList;

public class Stock {
    String symbol;
    FetchStock stock;
    ReadFromFile reader;
    ArrayList<PerStockDataPoint> dataPoints;
    PerStockDataCalculator calculator;
    Stock(String tickerSymbol) {
        symbol = tickerSymbol;
        stock = new FetchStock(symbol);
        reader = new ReadFromFile("stocks\\current\\" + symbol + ".txt");
        constructDataArray();
        calculator = new PerStockDataCalculator(dataPoints);

    }
    public String getSymbol(){
        return symbol;
    }
    public void constructDataArray(){
        //Constructs the set of data that is then used for leaderboards and stock analysis in an easier to work with format.
        dataPoints = new ArrayList<PerStockDataPoint>();
        int doy,y,m,d;
        double p,div,vol;
        while(reader.hasNext()) {
            reader.next();
            doy = Integer.parseInt(reader.next());
            reader.next();
            y = Integer.parseInt(reader.next());
            reader.next();
            m = Integer.parseInt(reader.next());
            reader.next();
            d = Integer.parseInt(reader.next());
            reader.next();
            p = Double.parseDouble(reader.next());
            reader.next();
            div = Double.parseDouble(reader.next());
            reader.next();
            vol = Double.parseDouble(reader.next());
            dataPoints.add(new PerStockDataPoint(doy,y,m,d,p,div,vol));
        }
        reader.reset();
    }
    public void constructSimulationDataArray(int startYear, int startMonth){
        int doy,y,m,d,points=0;
        double p,div,vol;
        boolean passStart = false,passEnd = false;
        reader = new ReadFromFile("stocks\\full\\"+symbol+".txt");
        dataPoints = new ArrayList<PerStockDataPoint>();
        dataPoints.clear();
        while(reader.hasNext()){
            reader.next();
            doy = Integer.parseInt(reader.next());
            reader.next();
            y = Integer.parseInt(reader.next());
            reader.next();
            m = Integer.parseInt(reader.next());
            reader.next();
            d = Integer.parseInt(reader.next());
            reader.next();
            p = Double.parseDouble(reader.next());
            reader.next();
            div = Double.parseDouble(reader.next());
            reader.next();
            vol = Double.parseDouble(reader.next());
            if(startYear == y && startMonth == m && !passStart){
                passStart = true;
            }
            if(passStart){
                points++;
            }
            if(!passEnd && points >= 1250){
                passEnd = true;
            }
            if(passStart && !passEnd) {
                dataPoints.add(new PerStockDataPoint(doy, y, m, d, p, div, vol));
            }
        }
    }
    public PerStockDataPoint getDataPoint(int w){
       return dataPoints.get(w);
    }
    public double allTimeHigh(){
        return calculator.allTimeHigh();
    }
    public double percentChangeToday(){
        return calculator.percentChangeToday();
    }
    public double averageChange(){
        return calculator.averageDailyPercentChange();
    }
    public double stability() {
        return calculator.stabilityCoefficient();
    }
    public double payoutRatio() {
        return calculator.dividendPayoutPercentage();
    }
    public double weightedAverageChange() {
        return calculator.weightedAverageDailyPercentChange();
    }
    public double weightedStability() {
        return calculator.weightedStabilityCoefficient();
    }
    public double yearlyROI(){
        return calculator.yearlyROI();
    }
    public double consistency(){
        return calculator.consistencyRatio();
    }
    public double HSI(){
        return calculator.HSIScore();
    }
    public double totalChange(){
        return calculator.totalChange();
    }
    public double volatility(){
        return calculator.volatility();
    }
    public double roundedPoint(double point){
        return Math.floor(point * 100) / 100;
    }

}
