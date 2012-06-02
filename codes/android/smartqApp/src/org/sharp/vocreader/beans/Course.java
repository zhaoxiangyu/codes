package org.sharp.vocreader.beans;

import org.sharp.vocreader.intf.Constants;


public class Course {
	public int courseNo;
	public int status;
	public int zipSize;
	
	public Course(){
	}
	
	public Course(int courseNo){
		this.courseNo = courseNo;
		this.status = Constants.COURSE_STATUS_NOTEXIST;
	}
	
	public Course(Course course){
		this.courseNo = course.courseNo;
		this.status = course.status;
		this.zipSize = course.zipSize;
	}
	
	public String toString(){
		if(status == Constants.COURSE_STATUS_NOTEXIST)
			return "Download Course "+courseNo;
		else if(status == Constants.COURSE_STATUS_DOWNLOADED)
			return "Open Course "+courseNo;
		else
			return "Course "+courseNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
