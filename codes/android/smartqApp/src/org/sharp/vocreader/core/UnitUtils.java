package org.sharp.vocreader.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sharp.vocreader.beans.State;
import org.sharp.vocreader.core.JpWordsReader.EventListenr;

public class UnitUtils {
	@Deprecated
	public void goUnit(State state ,int unitNo, EventListenr listener) {
		loadUnit(state, unitNo);
		if (listener != null)
			listener.viewNeedsFresh();
	}

	@Deprecated
	private static void loadUnit(State state ,int unitNo) {
		/*loadMp3s(mAs.storagePath(IOUtils.fullPath(jpMp3Path,
				UnitUtils.unitDir(unitNo))));*/
		state.resetState(unitNo, false);
		//filterLevel(infoList(),levelList(),unitNo, mState.level);
	}

	public static String[] unitTitles() {
		List<String> tl = new ArrayList<String>();
		for (int i = 0; i < 12; i++) {
			tl.add("Unit " + (i + 1));
		}
		return tl.toArray(new String[0]);
	}

	public static int unitNoOf(String path) {
		// jp/jpwords/unit1/1/abc.mp3
		Pattern p = Pattern.compile("unit(\\d+)/");
		Matcher m = p.matcher(path);
		if (m.find())
			return Integer.parseInt(m.group(1));
		else
			return Integer.MIN_VALUE;
	}

	public static String unitDir(int unitNo) {
		return "unit" + unitNo;
	}

}