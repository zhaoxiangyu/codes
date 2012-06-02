<?php
class StorageUtils {	
	public static function downFile($file_dir,$file_name){
	    $file_dir = chop($file_dir);
	    if($file_dir != '')
	    {
	        $file_path = $file_dir;
	        if(substr($file_dir,strlen($file_dir)-1,strlen($file_dir)) != '/')
	            $file_path .= '/';
	        $file_path .= $file_name;
	    }           
	    else
	        $file_path = $file_name;   
	   
	    if(!file_exists($file_path))
	    {
	        echo '对不起,你要下载的文件不存在。';
	        return false;
	    }
	
	    $file_size = filesize($file_path);
	 
	    header("Content-type: application/octet-stream");
	    header("Accept-Ranges: bytes");
	    header("Accept-Length: $file_size");
	    header("Content-Disposition: attachment; filename=".$file_name);
	   
	    $fp = fopen($file_path,"r");
	    $buffer_size = 1024;
	    $cur_pos = 0;
	   
	    while(!feof($fp)&&$file_size-$cur_pos>$buffer_size)
	    {
	        $buffer = fread($fp,$buffer_size);
	        echo $buffer;
	        $cur_pos += $buffer_size;
	    }
	   
	    $buffer = fread($fp,$file_size-$cur_pos);
	    echo $buffer;
	    fclose($fp);
	    return true;
	}
}
?>