import java.util.ArrayList;

public class StockList {
    ArrayList<Stock> stockList = new ArrayList<>();
    ReadFromFile reader = new ReadFromFile("stocks\\allstocks.txt");
    public void buildList(){
        stockList.clear();
        while(reader.hasNext()){
            stockList.add(new Stock(reader.next()));

        }
    }
    //Creates a stock list of exclusively stocks that existed during the simulation period
    public void buildSimulationStockList(int year, int month){
        boolean viable = false;
        Stock stock;
        stockList.clear();
        while(reader.hasNext()){
            viable = false;
            stock = new Stock(reader.next());
            for(PerStockDataPoint point : stock.dataPoints){
                if(point.getYear() == year && point.getMonth() == month){
                    viable = true;
                }
            }
            if(viable) {
                stock.constructSimulationDataArray(year,month);
                stockList.add(stock);
            }
        }
    }
    public ArrayList<Stock> getStockList(){
        return stockList;
    }


}
