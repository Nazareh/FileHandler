/*
Class to search for a string in text files within a folder or its subfolders.
 */
package filehandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; 
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Nazareh Created on 30-JAN-2018
 */
public class FileHandler {
    private int counter;
    private String allFilesPattern = "*.*";
    
    public void findFiles(String path) throws IOException {
        findFiles(path, allFilesPattern);
    }
        public void findFiles(String path,String pattern) throws IOException {
        findFiles(path, pattern , null);
    }
    public void findFiles(String path, String pattern, String stringToBeFound) throws IOException {
        List files;
        Path startingDir = Paths.get(path);
        FindFiles f = new FindFiles(pattern);
        
         try {
             Files.walkFileTree(startingDir, f);
         } catch (IOException ex) {
            System.out.println("Error during walkFileTree.Directory:  " + startingDir .toString() + "." + ex.getMessage());
         }
         files = f.getMatchedFiles();
         counter =  0;
         for(Object p:files){
             
             if (stringToBeFound != null){
                 findString(p.toString(),stringToBeFound);
             }
             else {
                 System.out.println(p);
             }
         }
         if (stringToBeFound == null) {
             System.out.println("Encoutered files: "+ files.size());
         }
         else {
             System.out.println("Files containing '"+ stringToBeFound +"' : " + counter);
         }
    }
    //Method find a String within a text file
    public void findString(String file, String str) throws IOException {
        BufferedReader inputStream = null;
        PrintWriter outputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader(file));

            String l;
            while ((l = inputStream.readLine()) != null) {
                if (l.contains(str)) {
                    counter++;
                    System.out.println("File: " + file);
                    System.out.println("Line: "+ l.trim() );
                    System.out.println();
                    
                };
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Please inform the folder: ");
        String path = reader.readLine() ;
        System.out.print("Please inform the file name pattern: ");
        String pattern = reader.readLine() ;
        System.out.print("Please inform the String you are looking for: ");
        String string = reader.readLine() ;
        // String path = "C:\\Users\\nazar\\Google Drive\\Programming\\Open-Source-Projects\\teammates";
        
        FileHandler fh = new FileHandler();
        fh.findFiles(path, pattern,string);
        

    }
    public void setAllFilesPattern (String allFilesPattern) {
        this.allFilesPattern = allFilesPattern;
    }
     public String getAllFilesPattern () {
        return allFilesPattern;
    }
}
