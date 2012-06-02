<?php
$cmd = strip_tags( $_REQUEST['q'] );
$storage = new SaeStorage();
$domainName = 'jpwordsj';
if($cmd == 'jp-course'){
	$courseNo = strip_tags( $_REQUEST['courseNo'] );
	//if($courseNo > 0 && $courseNo <= 48)
		//echo $storage -> getUrl($domainName,$courseNo.'.zip');
	//else
		echo "http://web-word-list.googlecode.com/files/$courseNo.zip";	
}else if($cmd == 'jp-courses-manifest'){
	include '../data/course-manifest.php';
	$courses = array();
	for($i = 1;$i <= 48;$i++){
		$cm = new CourseManifest;
		$cm -> courseNo = $i;
		$cm -> status = 0;
		$attrs = $storage -> getAttr($domainName,$i.'.zip');
		$cm -> zipSize = $attrs['length'];
		$courses[$i] = $cm;
	}
	echo json_encode(array_values($courses));
}else if($cmd == 'payInfo'){
	$mdn = strip_tags( $_REQUEST['mdn'] );
	$imei = strip_tags( $_REQUEST['imei'] );
	$userid = strip_tags( $_REQUEST['userid'] );
	$amount = strip_tags( $_REQUEST['amount'] );
	$type = strip_tags( $_REQUEST['type'] );
	include '../utils/db-utils.php';
	include '../data/pay-info.php';
	$payinfo = new PayInfo();
	$totalAmount = DbUtils::singleValue("SELECT sum(`amount`) AS amount FROM `payment` 
		GROUP BY `userid` 
		HAVING `userid`=$userid
		ORDER BY `createtime` desc");
	$payinfo -> isPayed = $totalAmount >= $amount;
	$payinfo -> amountPayed = $totalAmount;
	echo $payinfo -> toJsonString();
}
?>