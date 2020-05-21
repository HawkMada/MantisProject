
package similarity;

import Token.Unzip;
import Token.Zip;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import mantis.Mantis;
import static similarity.Similarity.zipFiles;

public class ourmainfunction {
    public static void main(String[] args) throws IOException, Exception {
        System.out.println("Doing Mantis");

        Date date = new Date();
        String strDateFormat = "hh:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        System.out.println("Current time: " + formattedDate);
        
        new Mantis();
        
        System.out.println("Unzip finished");
        
        date = new Date();
        strDateFormat = "hh:mm:ss a";
        dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate= dateFormat.format(date);
        System.out.println("Current time: " + formattedDate);
        //Unzip.unzip("coreyworkplace/out.zip", "tokens");
        //Zip.zipDirectory("tokens", "xinanworkplace/tokens.zip");
        Similarity.zipFiles("xinanworkplace/tokens.zip","resourcelibrary/tokenizedFiles");
        date = new Date();
        
        strDateFormat = "hh:mm:ss a";
        dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate= dateFormat.format(date);
        System.out.println("Current time: " + formattedDate);
        
        System.out.println("Starting compare");
        
        Similarity s=new Similarity();
        
        zipFiles("zipout\\out.zip","outcompare");
        System.out.println("All finished");

        System.out.println("Delete works");
        File file = new File("outcompare");      
        String[] myFiles;    
      
        if(file.isDirectory()){
            myFiles = file.list();
            for (int i=0; i<myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]); 
                myFile.delete();
            }
        }
        file = new File("xinanworkplace");          
        if(file.isDirectory()){
            myFiles = file.list();
            for (int i=0; i<myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]); 
                myFile.delete();
            }
        }
        
        //delete ismails work
        Path directory = Paths.get("resourcelibrary/TestSet");
        Files.walk(directory).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        
        Path directory1 = Paths.get("resourcelibrary/tokenizedFiles");
        Files.walk(directory1).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        /*
        Path directory = Paths.get("ismailunzip/400");
        Files.walk(directory).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        *///and zipfile
        date = new Date();
        
        strDateFormat = "hh:mm:ss a";
        dateFormat = new SimpleDateFormat(strDateFormat);
        formattedDate= dateFormat.format(date);
        System.out.println("Current time: " + formattedDate);

    }
}
