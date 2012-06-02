<?php
class JwSettings {
	const SAE_URL = "http://wd4web.sinaapp.com";
	//public $appuse_qurl = "http://wd4web.sinaapp.com/ws/jpword-httpquery.php?q=app-use";
	public $appuse_url = "http://wd4web.sinaapp.com/ws/new-appuse.php";
	public $jpcourse_qurl = "http://wd4web.sinaapp.com/ws/jpword-httpquery.php";
	public $use_ad = "youmi_points";//"youmi";//admob,youmi_banner

	public $bonus_offer = 100;
	public $course_points = 50;
	public $writing_points = 1;
	
	public $version_upgrade_enable = false;
	public $force_fee = false;
	
	public function toJsonString() {
		//$this->appuse_url = "http://wd4web.sinaapp.com/ws/new-appuse.php?appname=abc";
		return json_encode($this);
	}
}
?>