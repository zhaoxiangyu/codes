package org.sharp.db;

import com.orientechnologies.orient.core.db.object.ODatabaseObject;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;

public class OrientDB {

	private static ODatabaseObject db = null;
	
	public static ODatabaseObject db(){
		if(db==null){
			db = new ODatabaseObjectTx("memory:worddict").create();
		}else
			;
		return db;
	}
}
