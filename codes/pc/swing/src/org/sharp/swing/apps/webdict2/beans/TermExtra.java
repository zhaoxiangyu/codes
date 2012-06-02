package org.sharp.swing.apps.webdict2.beans;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.sharp.jdkex.Utils;

public class TermExtra implements Comparable<TermExtra>{
	public static final int[] REVIEW_DAYS = {1,2,4,8,16,32,64,128,256,512};
	
	Date lookupt;
	int score = 0;
	int rvc = 0;
	Date lastrvt;

	public Date getLookupt() {
		return lookupt;
	}
	public void setLookupt(Date lookupt) {
		this.lookupt = lookupt;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getRvc() {
		return rvc;
	}
	public void setRvc(int rvc) {
		this.rvc = rvc;
	}
	public Date getLastrvt() {
		return lastrvt;
	}
	public void setLastrvt(Date lastrvt) {
		this.lastrvt = lastrvt;
	}
	public void oneMoreReview() {
		if(rvc+1 <REVIEW_DAYS.length){
			rvc++;
			lastrvt = new Date();
		}
	}
	public void oneLessReview() {
		if(rvc-1 >=0){
			rvc--;
			lastrvt = new Date();
		}
	}
	public int schRvc(){
		Date at = new Date();
		int days = (int) ((at.getTime()-lastrvt.getTime())/(24*3600*1000L));
		int ret = 0;
		if(days>0){
			for (int i = 0; i < REVIEW_DAYS.length; i++) {
				if(days<REVIEW_DAYS[i]){
					ret = i;
					break;
				}
			}
		}
		return ret;
	}
	public int shouldReviewAfter() {
		if(rvc<REVIEW_DAYS.length){
			long sche = lastrvt.getTime()+(REVIEW_DAYS[rvc]*24*3600*1000L);
			if(sche > System.currentTimeMillis()){
				return (int) ((sche-System.currentTimeMillis())/(24*3600*1000L)) + 1;
			}else if(sche == System.currentTimeMillis())
				return 0;
			else
				return -1;
		}else{
			return -1;
		}
	}
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	public void toDisk(String dir) {
		Utils.saveJox(this, FilenameUtils.concat(dir,"info.txt"));
	}
	
	public static TermExtra fromDisk(String dir) {
		Date lookuptime = new Date(new File(dir).lastModified());
		TermExtra info = (TermExtra)Utils.loadJox2(
				FilenameUtils.concat(dir,"info.txt"), 
				TermExtra.class);
		if(info!=null){
			if(info.lastrvt==null)
				info.setLastrvt(lookuptime);
		}else{
			info = new TermExtra();
			info.setLookupt(lookuptime);
			info.setLastrvt(lookuptime);
		}
		return info;
	}
	
	public int compareTo(TermExtra t) {
		int ret = -1;
		ret = rvc - t.rvc;
		if(ret == 0){
			if(lastrvt!=null){
				if(t.lastrvt!=null)
					ret = lastrvt.compareTo(t.lastrvt);
				else
					ret = 1;
			}else{
				if(t!=null)
					ret = -1;
				else
					ret = 0;
			}
		}
		if(ret == 0)
			ret = t.lookupt.compareTo(lookupt);
		return ret;
	}
	
	public void touch(Date lkt,Date rvt,int rvc,int score){
		setLookupt(lkt);
		setLastrvt(rvt);
		setRvc(rvc);
		setScore(score);
	}
}