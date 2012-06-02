<?php
$appname = strip_tags( $_REQUEST['appname'] );
$mdn= strip_tags( $_REQUEST['mdn'] );
$imei= strip_tags( $_REQUEST['imei'] );
//echo "appname:".$_REQUEST['appname']."<br/>userid:".$_REQUEST['userid'];

$mysql = new SaeMysql();
$sql = "SELECT `id`,`appname`,`userid`,`visitcount`,`lastaccess`,`firstaccess` FROM `app_use` WHERE `appname`='".$mysql->escape( $appname).
  "' AND `userid`='".$mysql->escape($userid)."' ";

$mysql->runSql( $sql );
if( $mysql->errno() != 0 ){
	die( "Error:" . $mysql->errmsg() );
}

include '../data/jpword-setting.php';
$setting = new JwSettings;
echo $setting->toJsonString();

$mysql->closeDb();
?>