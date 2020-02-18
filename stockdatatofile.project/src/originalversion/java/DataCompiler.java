public class DataCompiler {
    Stock stock;
    WriteToFile writer;
    String symbol;
    DataCompiler(Stock activeStock){
     stock = activeStock;
     symbol = stock.getSymbol();
    }
    public void compileData(){
        writer = new WriteToFile("stocks\\data\\" + symbol + ".txt",false);
        writer.openFile();
        writer.writeFile(" Avg d%/day: " + stock.averageChange());
        writer.closeFile();
    }
}
