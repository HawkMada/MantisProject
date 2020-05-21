#!/usr/bin/php-cgi
<?php
$zip = new ZipArchive;
$file = $zip->open('zipout/out.zip');
if($file===TRUE){
    $zip ->extractTo('out/');
    $file = $zip ->close('zipout/out.zip');
    }
else
{
    echo 'Problem UnZipping';
}
//extract the student file in the Zip
//echo "This is where i will put the Reporting file for Stage 2";

//$fh= fopen('out/test/input.txt','r');

//$perc = fgets($fh);
//$assign = fgets($fh);
//fclose($fh);

//fclose($fh);

    $files = glob("out/*.txt");
    $output = "result.txt";
    $Stu = array();
    $perc = array();
    foreach($files as $file) {
        $fh= fopen($file,'r');
        //while(!feof($file)){
            
            $Stu[] = fgets($fh);
            $Stu[] = fgets($fh);
            $perc[] = fgets($fh);
            
            
        //}
       // $content = file_get_contents($file). "\n";
       // file_put_contents($output, $content, FILE_APPEND);
    }
        fclose($fh);




//include 'Report.php';
//include 'mailProf.php';

?>