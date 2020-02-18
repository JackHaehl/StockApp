import org.patriques.output.timeseries.data.StockData;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;


//This class writes stock data to files when a stock is created and when the file is refreshed adding new data to the file.
public class FetchStock {
    static FileOutputStream fileOut;
    static ObjectOutputStream objectOut;
    static String symbol;
    static String apiKey = "2487HC79MF8DQJP9";
    static List<StockData> stockData;

    public static void fetch(String ticker){
        symbol = ticker;

    }
}
