public class ImportStockFromFile {
    ReadFromFile reader = new ReadFromFile("stocks\\importStocks.txt");
    public void importStocks() {
        while(reader.hasNext()){
            Stock stock = new Stock(reader.next());
        }
        System.out.println("----Stock import complete!!!----");
    }
}
