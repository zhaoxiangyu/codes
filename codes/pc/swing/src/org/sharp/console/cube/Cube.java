package org.sharp.console.cube;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Cube {

	public static void main(String[] argv){
		   SimpleUniverse universe = new SimpleUniverse();
		   BranchGroup group = new BranchGroup();
		   group.addChild(new ColorCube(0.3));

		   universe.getViewingPlatform().setNominalViewingTransform();
		   universe.addBranchGraph(group); 
	}
}