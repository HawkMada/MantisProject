<?php

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
  width: 25%;
  padding: 10px;
  height: 10000; 
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

#toomuch {
	
	color: #FE0101;	
	background-color: #F4F487;
}
</style>
</head>
<body>

	<form action="DeleteFiles.php" method="post" enctype="multipart/form-data">
        <b>Return To Main Screen</b>
        <input type="submit" name="Return" value="Return">
    </form>

<h2>STUDENT RESULTS</h2>

<div class="row">
  <div class="column">
    <h2>Student ID/ File Name: </h2>
            <p>
			<?php
			$size = count($Stu);
			 for($i = 0; $i < $size;$i += 2){
		       // $stunumber1 = $Stu[0];
			     
					echo '<p>';
					echo $Stu[$i];
					//echo $i;
					echo '</p>';
			 }
			 
			 
			 //$stunumber1 = $Stu[0];
			 //echo $stunumber1
			?>
            </p>	
  </div>
  <div class="column" >
    <h2>Student ID/ File Name:</h2>
			<?php
			 for($i = 1; $i < $size;$i += 2){
		       // $stunumber1 = $Stu[0];
			     
					echo '<p>';
					echo $Stu[$i];
					//echo $i;
					echo '</p>';
			 }			 
			 
			 //$stunumber1 = $Stu[0];
			 //echo $stunumber1
			?>
            </p>
  </div>
  <div class="column" >
    <h2>Similarity Percentage</h2>
			<?php
			 foreach($perc as $item2){
		       // $stunumber1 = $Stu[0];
			   $tempdouble = 0.25;
			     if($item2 > $tempdouble){
					$percent = round((float)$item2 * 100 ) . '%';
					echo '<p id ="toomuch">';		
					echo $percent;
					echo '</p>'; 
				 }else{ 
					echo '<p>';
					$percent = round((float)$item2 * 100 ) . '%';
					echo $percent;
					echo '</p>';
				 }
			 }
			 
			 
			 //$stunumber1 = $Stu[0];
			 //echo $stunumber1
			?>
            </p>
  </div>
    <div class="column" >
    <h2>View Results:</h2>
			<?php
			 $Com = array_map('trim',$Stu);
			 for($i = 0, $j = 1; $i < $size;$i += 2, $j+=2){
		       // $stunumber1 = $Stu[0];
			        $filename1 = $Com[$i];
			        $filename2 = $Com[$j];
					echo "<p><a href= combine.php?filename1=$filename1&filename2=$filename2> Compare </a></p>";
			 }			 
			 
			 //$stunumber1 = $Stu[0];
			 //echo $stunumber1
			?>
            </p>
  </div>
</div>

</body>
</html>
