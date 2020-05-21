#!/usr/bin/php-cgi
<?php

$file = $_FILES["file"];

move_uploaded_file($file["tmp_name"], $file["name"]);

//header("Location: index.php");
?>

<?php
$extract_filname = str_replace(".zip","",$file["name"]);
$fil = 'TestSet'; 
$zip = new ZipArchive;
$file = $zip->open($file["name"]);
if($file===TRUE){
    $zip ->extractTo('similarity/resourcelibrary/'.$fil);
    $file = $zip ->close($file["name"]);
    }
else
{
    echo 'no1';
}




?>

	<form action="index.php" method="post" enctype="multipart/form-data">
        <input type="submit" name="Return" value="Return To Main Screen">
    </form>

    <form action="similarity/runMantis.php" method="post" enctype="multipart/form-data">
        <h2>RUN MANTIS</h2>
        <input type="submit" name="run" value="Run Mantis">
        <p> This may take a few minutes..... <p>
    </form>


