/*
Refreshes the list of stocks each day to ensure data is up to date while minimizing unnecessary API calls.
 */
public class Refresher {
    FetchStock currentStock;
    ReadFromFile readerAll;
    ReadFromFile readerBase;
    ReadFromFile readerTemp;
    WriteToFile writerTemp;
    WriteToFile writerBase;
    StockList stockList = new StockList();
    String workingStock = "";
    DataCompiler dataCompiler;
    int day = 0;
    //Checks if the most recent data point is equal to today's data point.
    public boolean isRefreshed(){
       currentStock = new FetchStock("AAPL");
       return currentStock.isRecent();
    }
    public void refresh(){
        readerAll = new ReadFromFile("stocks\\allstocks.txt");
        while(readerAll.hasNext()){
            workingStock = readerAll.next();
            currentStock = new FetchStock(workingStock);
            readerBase = new ReadFromFile("stocks\\full\\" + workingStock + ".txt");
            writerTemp = new WriteToFile("stocks\\temp\\" + workingStock + ".txt",false);
            writerTemp.openFile();
            while(readerBase.hasNext()){
                writerTemp.writeFile(readerBase.next());
            }
            writerTemp.closeFile();
            currentStock.constructMissingData();
            writerBase = new WriteToFile("stocks\\full\\" + workingStock + ".txt",true);
            writerBase.openFile();
            readerTemp = new ReadFromFile("stocks\\temp\\" + workingStock + ".txt");
            while(readerTemp.hasNext()){
                writerBase.writeFile(readerTemp.next());
            }
            writerBase.closeFile();
        }
        stockList.buildList();
        stockList.getStockList().forEach((activeStock) -> {
                dataCompiler = new DataCompiler(activeStock);
                dataCompiler.compileData();
        });

        }
    }
