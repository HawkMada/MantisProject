package similarity;
import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import static java.util.Locale.filter;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import sun.misc.IOUtils;


public class Similarity { 
    ArrayList<ArrayList> students=new ArrayList();
    ArrayList<Double> marks=new ArrayList();
    ArrayList<String> highmatch=new ArrayList();
    ArrayList<String> aid=new ArrayList();
    FileWriter fw;
    BufferedWriter bf;
    public void readfile() throws IOException{
        ZipFile zip = new ZipFile("xinanworkplace/tokens.zip");
        Enumeration<? extends ZipEntry> entries = zip.entries();
        String line;
        double total=0;
        ArrayList<Integer> emptylines=new ArrayList();
        while(entries.hasMoreElements()){//match assignment id//weight and test?
            ZipEntry entry = entries.nextElement();
            ArrayList<String> studentswork=new ArrayList();
            InputStream stream = zip.getInputStream(entry);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            int emptyline=0;
            while(reader.ready()){
                line = reader.readLine();
                String tempp=line;
                if(tempp.equals("")){
                    //System.out.println(reader.readLine());
                    emptyline++;   
                } 
                studentswork.add(line);
            }
            if(!studentswork.isEmpty())
            {
                students.add(studentswork);
                emptylines.add(emptyline);
            }
            if(!entry.isDirectory()){
                String tempfileid=entry.getName().substring(0,entry.getName().lastIndexOf("."));
                aid.add(tempfileid);
            }
        }
        if(students.size()>1){
            for(int loopout=0;loopout<students.size();loopout++){
                for(int loopin=0;loopin<students.size();loopin++){
                    double totalpercentage=0;
                    marks.clear();
                    highmatch.clear();
                    HashSet testinglines=new HashSet();
                    for(int alines=0;alines<students.get(loopin).size();alines++){
                        for(int blines=0;blines<students.get(loopout).size();blines++){
                            double tem=0;
                            int a=alines;
                            int b=blines;
                            tem=simplecheck((String)students.get(loopin).get(alines),(String)students.get(loopout).get(blines),aid.get(loopout));
                            marks.add(tem);
                            if(tem>0.95){//check higher than 90% and highlight include words changes and all lind meets
                                String linetoline=null;
                                linetoline=Integer.toString(a)+"->"+Integer.toString(b);
                                highmatch.add(linetoline);
                                testinglines.add(a);
                            }
                        }
                    }
                    //System.out.println(emptylines);
                    double testing=(double)testinglines.size()/(students.get(loopin).size()-emptylines.get(loopin));
                    //System.out.println("abc"+emptylines.get(loopin));
                    /*for(Double in:marks){
                        if (in >=0 && in <=1){
                            totalpercentage=totalpercentage+(double)in;
                        }
                    }
                    total=totalpercentage/(students.get(loopin).size()*students.get(loopout).size());*/
                    
                    //System.out.println("total "+totalpercentage);
                    //System.out.println("lines "+(students.get(loopin).size()*students.get(loopout).size()));
                    //System.out.println(marks);
                    //System.out.println(highmatch);
                    //System.out.println(total);
                    System.out.println(aid.get(loopin)+" -> "+aid.get(loopout));
                    writeoutput(testing,highmatch,aid.get(loopin),aid.get(loopout));
                }
            }
        }
    }
    public void writeoutput(double perc,ArrayList<String> same,String aid,String bid) throws IOException{
        if(!aid.equals(bid)){
            File f=new File("outcompare\\"+aid+" to "+bid+".txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            fw = new FileWriter(f);
            bf= new BufferedWriter(fw);
            bf.write(aid);
            bf.newLine();
            bf.write(bid);
            bf.newLine();
            String tempp=Double.toString(perc);
            bf.write(tempp);
            for(int i=0;i<same.size();i++){
                bf.newLine();
                bf.write(same.get(i));
            }
            bf.close();
            fw.close();
        }
    }
    public double simplecheck(String test,String test2,String asid){
        simplecheck a=new simplecheck();
        HashMap<String,Double> amap=new HashMap<>();
        String fid=asid;//line53 aid.get(loopout-1) is fid
        int ngrams=4;//may change ngrams to 2->3,4,5
        Set<String> templine=new HashSet();
        
        for(int i=0;i<test.length()-(ngrams-1);i++){
            String ngram = test.substring(i, i + ngrams);//eg:0-3,1-4,2-5 to the end
            templine.add(ngram);
        }
        for(String le:templine){
            amap.put(le,1.0);
        }
        //System.out.println("library="+amap);
        a.setinput(amap, ngrams, fid);
        double temptest=a.getSimilarity(test,test2);
        //System.out.println("result for test "+temptest);
        return temptest;
    }
    public double wccheck(String test,String test2){//didn't finish testing
        WordContainsCheck wcc=new WordContainsCheck();
        wcc.setinput("that a new aaa", "this a new aaa hahah that","0");
        double te=wcc.getSimilarity();
        return te;
    }
    public Similarity() throws IOException{
        readfile();
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
