package org.sharp.vocreader.beans;

import org.sharp.android.orm.annotation.Column;
import org.sharp.android.orm.annotation.Table;

@Table
public class AudioInfoV2 {
	@Column
	public Integer level = 0;
	
	@Column(primary=true)
	public String name;
	
	@Column
	public String mp3Path;
	
	@Column
	public Integer courseNo;
	
	@Column
	public Integer unitNo;

	@Column
	public String pronun;

	@Column
	public String writing;

	@Column
	public String partOfSpeech;

	@Column
	public String expl;

	/*@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(o, this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}*/
	
}
