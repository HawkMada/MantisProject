#!/usr/bin/php-cgi
<h3>Running Mantis Now.....</h3>


<?php

//shell_exec("cd javatest\src\javatest");
ini_set('max_execution_time', 600); 
exec('java -jar similarity.jar', $output);
print_r($output);
//echo shell_exec("cd..");
//echo shell_exec("java javatest.Javatest");

//include 'Routput/index.php';
?>

    <form action="Report.php" method="post" enctype="multipart/form-data">
        <h2>View Report</h2>
        <input type="submit" name="run" value="View">
    </form>