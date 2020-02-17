import org.patriques.AlphaVantageConnector;
import org.patriques.TimeSeries;
import org.patriques.input.ApiParameter;
import org.patriques.input.timeseries.OutputSize;
import org.patriques.output.AlphaVantageException;
import org.patriques.output.timeseries.Daily;
import org.patriques.output.timeseries.DailyAdjusted;
import org.patriques.output.timeseries.data.StockData;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/*
 The purpose of this class is to be the only class that calls the AlphaVantage API, this class deals
 with all instances in which the API is called to write to a file that is read by the rest of the program.
 */
public class FetchStock {
    String apiKey = "2487HC79MF8DQJP9";
    int timeout = 12000;
    String symbol ="";
    List<StockData> stockData;
    WriteToFile writer;
    ReadFromFile reader;
    Refresher refresher;
    int i = 0;
    //Constructor that is called to create a new Alpha Vantage stock object ready to have its data points read.
    FetchStock(String ts) {
        symbol = ts;
        reader = new ReadFromFile("stocks\\full\\" + symbol + ".txt");
        refresher = new Refresher();
        //First checks if the "full" file exists at all
        if(!reader.exists()) {
            try {
                System.out.println("Stock not in system, creating file...");
                AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
                TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
                DailyAdjusted response = stockTimeSeries.dailyAdjusted(symbol, OutputSize.FULL);
                Map<String, String> metaData = response.getMetaData();
                stockData = response.getStockData();
                //Creates an object to write to the file for the respective stock.
                writer = new WriteToFile( "stocks\\full\\" + symbol + ".txt", false);
                constructDataSet();
                writer = new WriteToFile("stocks\\allstocks.txt", true);
                writer.openFile();
                writer.writeFile(symbol);
                writer.closeFile();
                writer = new WriteToFile("stocks\\current\\"+symbol+".txt",false);
                writer.openFile();
                constructCurrentData();
                writer.closeFile();
            } catch (AlphaVantageException e) {
                System.out.println("Error Code FetchStock 1");
            }
        }else{

        }
    }
    //Creates the "Full" data set for the entire history of a stock, this is only done when a new stock is added to the
    //System
    public void constructDataSet(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Does a for-each loop printing the date and closing price of the stock on every day recorded by Alpha Vantage.
        try {

            writer.openFile();
            stockData.forEach(Stock -> {
                    writer.writeFile("DayOfYear: " + Stock.getDateTime().getDayOfYear() + " Year: " + Stock.getDateTime().getYear() + " Month: " + Stock.getDateTime().getMonthValue() + " Day: " + Stock.getDateTime().getDayOfMonth() + " Price: " + Stock.getClose() + " Dividend: " + Stock.getDividendAmount() + " Volume: " + Stock.getVolume());
            });

            writer.closeFile();
        }catch(AlphaVantageException e){
            System.out.println("Error Code FetchStock 2");
        }
    }
    //Constructs the most recent 5 years of data for the "Current" version of a stock.
    public void constructCurrentData(){
        //Does a for-each loop printing the date and closing price of the stock on every day recorded by Alpha Vantage for the last 5 years.
        writer = new WriteToFile("stocks\\current\\"+symbol+".txt",false);
        try {
            int i = 0;
            writer.openFile();
            for(StockData stock : stockData){
                if(i < 1250){
                    writer.writeFile("DayOfYear: " + stock.getDateTime().getDayOfYear() + " Year: " + stock.getDateTime().getYear() + " Month: " + stock.getDateTime().getMonthValue() + " Day: " + stock.getDateTime().getDayOfMonth() + " Price: " + stock.getClose() + " Dividend: " + stock.getDividendAmount() + " Volume: " + stock.getVolume());
                    i++;
                }
            }

            writer.closeFile();
        }catch(AlphaVantageException e){
            System.out.println("Error Code FetchStock 4");
        }
    }
    public void constructMissingData() {
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            int mostRecentDay = reader.mostRecentDayOfYear();
            refresh();
            i = 0;
            writer = new WriteToFile("stocks\\full\\" + symbol + ".txt", false);
            writer.openFile();
            while (stockData.get(i).getDateTime().getDayOfYear() != mostRecentDay) {
                writer.writeFile("DayOfYear: " + stockData.get(i).getDateTime().getDayOfYear() + " Year: " + stockData.get(i).getDateTime().getYear() + " Month: " + stockData.get(i).getDateTime().getMonthValue() + " Day: " + stockData.get(i).getDateTime().getDayOfMonth() + " Price: " + stockData.get(i).getClose() + " Dividend: " + stockData.get(i).getDividendAmount());
                //writer.writeFile("DayOfYear: " + stockData.get(i).getDateTime().getDayOfYear() + " Date: " + stockData.get(i).getDateTime() + " Price: " + stockData.get(i).getClose() + " Dividend: " + stockData.get(i).getDividendAmount());
                i++;
            }
            writer.closeFile();
        }catch(AlphaVantageException e){
            System.out.println("Refresh failed!");
        }

    }

    public void refresh(){
        try {
            AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
            TimeSeries stockTimeSeries = new TimeSeries(apiConnector);
            DailyAdjusted response = stockTimeSeries.dailyAdjusted(symbol, OutputSize.COMPACT);
            Map<String, String> metaData = response.getMetaData();
            stockData = response.getStockData();
        } catch (AlphaVantageException e) {
            System.out.println("Error Code FetchStock 5");
        }
    }

    public boolean isRecent(){
        refresh();
        return stockData.get(0).getDateTime().getDayOfYear() == reader.mostRecentDayOfYear();
    }
}