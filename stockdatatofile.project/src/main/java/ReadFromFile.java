import java.io.File;
import java.util.Scanner;

public class ReadFromFile {
    private Scanner reader;
    private String name;
    File file;
    private int day;
    //Creates the scanner by which we can read from a text file containing all of a stock's data.
    ReadFromFile(String filePath){
        //name = "stocks\\" + fileName + ".txt";
        name = filePath;
        try {
            file = new File(name);

            reader = new Scanner(file);
        }catch(Exception e){
            if(file.exists()) {
                System.out.println("Error Code ReadFromFile 1");
            }else{
                System.out.println("File " + name + " Created!");
            }
        }
        System.out.println("YOU ARE NOW READING FROM -------------"+name+"----");
    }
    public boolean isThere(String input){
        if(reader.findInLine(input) == null){
            return false;
        }else{
            return true;
        }
    }
    public boolean exists(){
        return file.exists();
    }
    public int mostRecentDayOfYear(){
       reader.next();
       day = Integer.parseInt(reader.next());
       reader.reset();
       return day;
    }
    public boolean hasNext(){
        return reader.hasNext();
    }
    public String next(){
        return reader.next();
    }
    public void reset(){
        reader.reset();
    }

}
