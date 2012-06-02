<?php
include '../data/jpword-setting.php';
$setting = new JwSettings;
echo "JwSettings json string:";
echo json_encode($setting);
echo "<br/>";
echo "JwSettings json string2:";
echo $setting->toJsonString();

$storage = new SaeStorage();
$i = 1;
$filename = $i.".zip";
echo '<br/>';
print_r($storage -> getAttr('jpwordsj',$filename));
echo '<br/>';
echo '1.zip attributes:'.$temp;
$attrs = $storage -> getAttr('jpwordsj',$filename);
echo '<br/>';
echo '1.zip length:'.$attrs['length'];
?>