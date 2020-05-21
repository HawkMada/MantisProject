
package Token;//corey

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Unzip {


	    private String zipFileDir  = null;
	    private String zipFileName = null;
	    private String unzipDir    = null;

	    public void FileUnzipper(String zipFileDir, String zipFileName, String unzipDir) {
	        this.zipFileDir  = zipFileDir;
	        this.zipFileName = zipFileName;
	        this.unzipDir    = unzipDir;
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
	                    try {
	            			Tokenizer.tokenize(true, destPath, destPath + ".txt");
	            		} catch (Exception e) {
	            			// TODO Auto-generated catch block
	            			e.printStackTrace();
	            		}
	                    File file = new File(destPath);
	                    if(file.delete()){
	                        System.out.println( "File deleted");
	                    }else System.out.println("doesn't exist");
	                }
	            }
	        } catch(IOException e){
	            throw new RuntimeException("Error unzipping file " + zipFileName, e);
	        }
	    }
	}

