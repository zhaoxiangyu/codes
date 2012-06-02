<?php
echo "<a href='/ui/jpword-userlist.php?cond=latest100'>jpword latest 100</a>";
echo "<br/><a href='/ui/jpword-userlist.php?cond=vcgreater&vc=9'>visit count greater than 10</a>";
echo "<br/><a href='/ui/jpword-userlist.php?cond=vc_between&vc_min=0&vc_max=0'>visit count between 0 and 0</a>";

$cond = strip_tags( $_REQUEST['cond'] );
$an = 'JpWordReader';
$mysql = new SaeMysql();
if($cond == 'latest100'){
	echo '<br/><strong>Web word list project, User statistics</strong>';
	$sql = "SELECT `id`,`appname`,`userid`,`visitcount`,`lastaccess`,`firstaccess` FROM `app_use` WHERE `appname`='".$an."' ORDER BY `lastaccess` desc  LIMIT 100";
	$data = $mysql->getData( $sql );
	$size = sizeof( $data );
}else if($cond == 'vcgreater'){
	$vc = strip_tags( $_REQUEST['vc'] );
	echo "<br/><strong>User whose visicout greater than $vc</strong>";
	$sql = "SELECT `id`,`appname`,`userid`,`visitcount`,`lastaccess`,`firstaccess` FROM `app_use` WHERE `appname`='".$an."' AND `visitcount`>= $vc ORDER BY `lastaccess` desc";
	$data = $mysql->getData( $sql );
	$size = sizeof( $data );
}else if($cond == 'vc_between'){
	$vc_min = strip_tags( $_REQUEST['vc_min'] );
	$vc_max = strip_tags( $_REQUEST['vc_max'] );
	echo "<br/><strong>User whose visicout greater than $vc</strong>";
	$sql = "SELECT `id`,`appname`,`userid`,`visitcount`,`lastaccess`,`firstaccess` FROM `app_use` WHERE `appname`='".$an."' AND `visitcount`>= $vc_min AND `visitcount`<=$vc_max ORDER BY `lastaccess` desc";
	$data = $mysql->getData( $sql );
	$size = sizeof( $data );
}

echo "<br/>Count of Users matched: <font color=red>$size</font>";
echo "<table border=1>";
echo "<th>id</th><th>appname</th><th>userid</th><th>visitcount</th><th>lastaccess</th><th>firstaccess</th>";
foreach($data as $row){
  echo "<tr>";
  echo "<td>".$row["id"]."</td>";
  echo "<td>".$row["appname"]."</td>";
  echo "<td>".$row["userid"]."</td>";
  echo "<td>".$row["visitcount"]."</td>";
  echo "<td>".$row["lastaccess"]."</td>";
  echo "<td>".$row["firstaccess"]."</td>";
  echo "</tr>";
}
echo "</table>";
$mysql->closeDb();
?>