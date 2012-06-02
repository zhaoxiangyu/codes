<?php
class DbUtils {	
	public static function matchedCount($sql){
		$mysql = new SaeMysql();
		$data = $mysql->getData( $sql );
		$size = sizeof( $data );
		$mysql->closeDb();
		return $size;
	}

	public static function singleValue($sql){
		$mysql = new SaeMysql();
		$data = $mysql->getVar( $sql );
		$mysql->closeDb();
		return $data;
	}
}
?>