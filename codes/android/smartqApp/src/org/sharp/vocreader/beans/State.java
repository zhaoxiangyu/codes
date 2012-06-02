package org.sharp.vocreader.beans;

public class State {
	public static final String LATEST_INDEX = "latestIndex";
	public static final String LATEST_LEVEL = "latestLevel";
	public static final String LATEST_COURSENO = "latestCourseNo";
	public static final String LATEST_UNITNO = "latestUnitNo";
	public static final String COURSE_OR_UNIT = "courseOrUnit";
	public static final String LEVELA_INDEX = "levelA.current";
	public static final String LEVELA_LAST = "levelA.last";
	public static final String LEVEL0_INDEX = "level0.current";
	public static final String LEVEL0_LAST = "level0.last";
	public static final String LEVEL1_INDEX = "level1.current";
	public static final String LEVEL1_LAST = "level1.last";
	public static final String LEVEL2_INDEX = "level2.current";
	public static final String LEVEL2_LAST = "level2.last";
	public static final String LEVEL3_INDEX = "level3.current";
	public static final String LEVEL3_LAST = "level3.last";
	public static final String LEVEL4_INDEX = "level4.current";
	public static final String LEVEL4_LAST = "level4.last";
	public static final String BONUS = "bonus";
	
    public int last = 0;
	public int current = 0;
	public int level = -1;
	public int lac = 0;
	public int lal = 0;
	public int l0c = 0;
	public int l0l = 0;
	public int l1c = 0;
	public int l1l = 0;
	public int l2c = 0;
	public int l2l = 0;
	public int l3c = 0;
	public int l3l = 0;
	public int l4c = 0;
	public int l4l = 0;
	public int courseNo = 0;
	public int unitNo = 0;
	public boolean courseOrUnit = true;
	public int bonus = 0;

	public State(){
	}
	
	public void resetState(int no, boolean courseOrUnit){
		if(courseOrUnit)
			this.courseNo = no;
		else
			this.unitNo = no;
		this.courseOrUnit = courseOrUnit;
		current = 0;
	    level = 0;
	    
	    lac = 0;
	    lal = 0;
	    l0c = 0;
	    l0l = 0;
	    l1c = 0;
	    l1l = 0;
	    l2c = 0;
	    l2l = 0;
	    l3c = 0;
	    l3l = 0;
	    l4c = 0;
	    l4l = 0;
	}

}
