#!/usr/bin/php-cgi
<?php
$files = glob('similarity/resourcelibrary/TestSet/*'); 
foreach($files as $file){ 
  if(is_file($file))
    unlink($file); 
    
}

$files2 = glob('similarity/resourcelibrary/processedFiles/*'); 
foreach($files2 as $file2){ 
  if(is_file($file2))
    unlink($file2); 
    
}


$files3 = glob('similarity/out/*'); 
foreach($files3 as $file3){ 
  if(is_file($file3))
    unlink($file3);
    
}

$files4 = glob('similarity/zipout/*'); 
foreach($files4 as $file4){ 
  if(is_file($file4))
    unlink($file4); 
    
}
         
?>


    <form action="upload-manager.php" method="post" enctype="multipart/form-data">
        <h2>Upload Student Zip</h2>
        <label>Filename:</label>
        <input type="file" name="file" id="fileSelect">
        <input type="submit" name="submit" value="Submit file">
    </form>




              