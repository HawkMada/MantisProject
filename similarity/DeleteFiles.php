#!/usr/bin/php-cgi
<?php

$files = glob('resourcelibrary/TestSet/*'); 
foreach($files as $file){ 
  if(is_file($file)){
    unlink($file); 
    }
}

$files2 = glob('resourcelibrary/processedFiles/*'); 
foreach($files2 as $file2){ 
  if(is_file($file2)){
    unlink($file2); 
    }
}



$files3 = glob('out/*'); 
foreach($files3 as $file3){ 
  if(is_file($file3)){
    unlink($file3);
    } 
}

$files4 = glob('zipout/*'); 
foreach($files4 as $file4){ 
  if(is_file($file4)){
    unlink($file4); 
    }
}



include('../index.php');
         
?>