<?php
session_start();
require 'index.php';
?>

<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
* {
  box-sizing: border-box;
}

/* Create three equal columns that floats next to each other */
.column {
  float: left;
  width: 33.33%;
  padding: 10px;
  height: 10000; 
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}
</style>
</head>


<body>
<div class="row">
	<div class="column">
	<?php
	  echo '<p>';    
    $ar=array_map('trim',$Stu);
    echo nl2br(file_get_contents( "resourcelibrary/processedFiles/".$_GET['filename1'].".txt"));
	  //echo $trimmed_array[0]."txt";
	  echo '</p>';
	?>
    </div>
    <div class="column">
  
  <?php
    echo '<p>'; 
    //$filname = ;  
    echo nl2br(file_get_contents( "resourcelibrary/processedFiles/".$_GET['filename2'].".txt"));
    //echo $filname;
    echo '</p>';
  ?>
  
  	</div>
</div>

</body>
</html>