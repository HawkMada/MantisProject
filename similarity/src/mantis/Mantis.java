package mantis;

import Token.Tokenizer;
import Token.Zip;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Mantis {

    String zipped_file = "resourcelibrary/TestSet.zip";

    String uncompressedDirectory = zipped_file.substring(0,zipped_file.lastIndexOf("/")+1);///Users/ismailmathkour/Desktop/
    String uncompressedDirectoryName = zipped_file.substring(zipped_file.lastIndexOf("/")+1,zipped_file.lastIndexOf(".zip"));//4F00Assignment
    
    public Mantis() throws IOException, Exception {
        //unzip_folder(zipped_file,uncompressedDirectory);
//        unzip(zipped_file,uncompressedDirectory);
        //check();
        combinAllFiles(uncompressedDirectory , uncompressedDirectoryName);
        
    }
    public void check(){
        String path = "resourcelibrary"; 

        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(); 

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) 
            {
                files = listOfFiles[i].getName();
                if (files.endsWith(".rar") || files.endsWith(".zip"))
                {
                    zipped_file=path+"/"+files;
                    break;
                }
            }
        }
    }
    public void unzip_folder(String zipped_file, String uncompressedDirectory) {

        //Open the file
        try (ZipFile file = new ZipFile(zipped_file)) {
            FileSystem fileSystem = FileSystems.getDefault();
            //Get file entries
            Enumeration<? extends ZipEntry> entries = file.entries();

            //Iterate over entries
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                //If directory then create a new directory in uncompressed folder
                if (entry.isDirectory()) {
//                    System.out.println("Creating Directory:" + uncompressedDirectory + entry.getName());
                    Files.createDirectories(fileSystem.getPath(uncompressedDirectory + entry.getName()));
                } //Else create the file
                else {
                    InputStream is = file.getInputStream(entry);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String uncompressedFileName = uncompressedDirectory + entry.getName();
                    Path uncompressedFilePath = fileSystem.getPath(uncompressedFileName);
                    Files.createFile(uncompressedFilePath);
                    FileOutputStream fileOutput = new FileOutputStream(uncompressedFileName);
                    while (bis.available() > 0) {
                        fileOutput.write(bis.read());
                    }
                    fileOutput.close();
//                    System.out.println("Written :" + entry.getName());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void unzip( String zipFileName, String unzipDir) {
	       // String zipFilePath = this.zipFileDir + File.separator + this.zipFileName;
	        try{
	            System.out.println("zipFilePath = " + zipFileName);
	            ZipFile zipFile = new ZipFile(zipFileName);
	            Enumeration<? extends ZipEntry> entries = zipFile.entries();
	            while(entries.hasMoreElements()){
	                ZipEntry entry = entries.nextElement();
	                if(entry.isDirectory()){
	                    System.out.print("dir  : " + entry.getName());
	                    String destPath = unzipDir + File.separator + entry.getName();
	                    System.out.println(" => " + destPath);
	                    File file = new File(destPath);
	                    file.mkdirs();
	                } else {
	                    String destPath = unzipDir + File.separator + entry.getName();

	                    try(InputStream inputStream = zipFile.getInputStream(entry);
	                        FileOutputStream outputStream = new FileOutputStream(destPath);
	                    ){
	                        int data = inputStream.read();
	                        while(data != -1){
	                            outputStream.write(data);
	                            data = inputStream.read();
	                        }
	                    }
	                    System.out.println("file : " + entry.getName() + " => " + destPath);

	                }
	            }
	        } catch(IOException e){
	            throw new RuntimeException("Error unzipping file " + zipFileName, e);
	        }
	    }

    public void combinAllFiles(String uncompressedDirectory,String uncompressedDirectoryName ) throws IOException, Exception {
        File dir = new File(uncompressedDirectory+uncompressedDirectoryName);   
        

        
        File[] directoryListing = dir.listFiles();
        FileSystem fileSystem = FileSystems.getDefault();
        Path processedFilesDirectory = fileSystem.getPath(uncompressedDirectory+"/processedFiles");
        Path tokenizdFilesDirectory = fileSystem.getPath(uncompressedDirectory+"/tokenizedFiles");
        Files.createDirectories(processedFilesDirectory);
        Files.createDirectories(tokenizdFilesDirectory);
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isDirectory()) {
                    String parseSTDNumber = child.toString();
                    String studentNumber = parseSTDNumber.substring(parseSTDNumber.lastIndexOf("\\")+1,parseSTDNumber.length());
        //System.out.println(studentNumber);

                    Stream<Path> walk = Files.walk(Paths.get(child.toString()));
                    List<String> result = walk.map(x -> x.toString())
                            .filter(f -> f.endsWith(".java")).collect(Collectors.toList());
                    
                    if (result.isEmpty()){
                        walk = Files.walk(Paths.get(child.toString()));
                        result = walk.map(x -> x.toString())
                            .filter(f -> f.endsWith(".cpp")).collect(Collectors.toList());
                    }

                    PrintWriter pw = new PrintWriter(processedFilesDirectory +"/"+ studentNumber + ".txt");
                    for (String string : result) {
                        // BufferedReader object for file1.txt 
                        BufferedReader br = new BufferedReader(new FileReader(string));
                        String line = br.readLine();
                        // loop to copy each line of  
                        // file1.txt to  file3.txt 
                        while (line != null) {
                            pw.println(line);
                            line = br.readLine();
                        }
                    }
                    pw.flush();
                    pw.close();
                    
                    Tokenizer.tokenize(true,processedFilesDirectory +"/"+ studentNumber + ".txt", tokenizdFilesDirectory +"/"+ studentNumber + ".txt" );
                }
            }
        }
    }
    public static void zipFiles(String zipFile, String srcDir) {
        try {
        File srcFile = new File(srcDir);
        File[] files = srcFile.listFiles();
        FileOutputStream fout = new FileOutputStream(zipFile);

        ZipOutputStream zout = new ZipOutputStream(fout);

        for (int i = 0; i < files.length; i++) {
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(files[i]);
            zout.putNextEntry(new ZipEntry(files[i].getName()));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zout.write(buffer, 0, length);
            }
            zout.closeEntry();
            fis.close();
        }
        zout.close();
        }
        catch (Exception e)
        {
            
        }
    }
  
}
