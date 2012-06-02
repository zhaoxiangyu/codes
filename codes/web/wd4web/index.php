<?php
include 'fragments/head.php';

echo "<a href='ui/jpword-userlist.php?cond=latest100'>jpword user statistics</a>";
echo "<br/><a href='ui/test.php'>test</a>";
echo "<br/><a href='ui/cbpay.php'>标日单词背诵网银支付2</a>";
echo "<br/><a href='3rd-payment/cbpay-android.html'>android支付测试页面</a>";

echo "<br/><a href='data/jpword-setting.php'>jpword-setting</a>";
echo "<br/><a href='ws/jpword-httpquery.php'>jpword-setting-query</a>";
echo "<br/><a href='ws/new-appuse.php?appname=abc&userid=abcuser'/>add app use</a>";
echo "<br/><a href='http://wd4web-jpwordsj.stor.sinaapp.com/1.zip'/>1.zip</a>";
echo "<br/><a href='ws/jpword-httpquery.php?q=jp-course&courseNo=1'/>1.zip url</a>";
echo "<br/><a href='ws/jpword-httpquery.php?q=jp-courses-manifest'/>jpwords manifest</a>";
echo "<br/><a href='ws/jpword-httpquery.php?q=payInfo&mdn=13641338053&imei=0000000000&amount=10&type=unknown'/>pay info</a>";
echo "<br/><a href='http://pay3.chinabank.com.cn/Payto?v_mid=22357785&v_id=152558&v_url=http://wd4web.sinaapp.com/jpword-httpquery.php?q=payInfo&mdn=&imei=&amount=3&prdesc=标准日本语词汇背诵&quantity=1&v_amount=3&productname=标准日本语单词'>标日单词背诵网银支付</a>";

include 'fragments/foot.php';
?>
