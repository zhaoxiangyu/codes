<?php
class PayInfo {
	public $isPayed = FALSE;
	public $amountPayed;
	public function toJsonString() {
		return json_encode($this);
	}
}
?>