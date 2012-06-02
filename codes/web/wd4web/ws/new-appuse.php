<?php
$appname = strip_tags( $_REQUEST['appname'] );
$userid= strip_tags( $_REQUEST['userid'] );
//echo "appname:".$_REQUEST['appname']."<br/>userid:".$_REQUEST['userid'];

$mysql = new SaeMysql();
$sql = "SELECT `id`,`appname`,`userid`,`visitcount`,`lastaccess`,`firstaccess` FROM `app_use` WHERE `appname`='".$mysql->escape( $appname).
  "' AND `userid`='".$mysql->escape($userid)."' ";
$data = $mysql->getData( $sql );
$size = sizeof( $data );
if( $size == 1 ){
//	echo "<br/>size == 1";
	$rid = $data[0]['id'];
	$rvisitcount = $data[0]['visitcount'];
	if( is_null($rvisitcount) )
	$rvisitcount = 0;

	$rvisitcount = $rvisitcount + 1;

	$sql = " UPDATE `app_use` set `visitcount` = $rvisitcount, `lastaccess` = NOW() WHERE `id`=$rid";
}else{
	$sql = "INSERT  INTO `app_use` ( `appname` ,`userid` ,`visitcount`, `lastaccess`, `firstaccess` )
    VALUES ( '".$mysql->escape( $appname )."' , '".$mysql->escape ( $userid )."' ,0, NOW() , NOW() ) ";
}

$mysql->runSql( $sql );
if( $mysql->errno() != 0 ){
	die( "Error:" . $mysql->errmsg() );
}

include '../data/jpword-setting.php';
$setting = new JwSettings;
echo $setting->toJsonString();

$mysql->closeDb();
?>