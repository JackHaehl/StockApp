import java.io.FileWriter;

public class WriteToFile {
    private FileWriter stock;
    private String name = "";
    private boolean appendable;
    //Adds the necessary components to make a file path out of the simplistic word entered into the constructor.
    //Make the boolean true if you want to add to a file, i.e adding a new stock to the system, or make it false if you want to create a new file, i.e adding the new day's data to the stock.
    WriteToFile(String filePath, boolean editable){
        appendable = editable;
        name = filePath;
    }
    //opens the file for editing.
    public void openFile(){
        try{
            stock = new FileWriter(name, appendable);
        }catch(Exception e){
            System.out.println("Error Code WriteToFile 1");
        }

    }
    //Appends the input to the function to the end of the file.
    public void writeFile(String input){
        try{
            stock.write(" " + input);
        }catch(Exception e){
            System.out.println("Error Code WriteToFile 2");
        }

    }
    //Closes the file, must be done after every batch of editing.
    public void closeFile(){
        try{
            stock.close();
        }catch(Exception e){
            System.out.println("Error Code WriteToFile 3");
        }
    }
}
