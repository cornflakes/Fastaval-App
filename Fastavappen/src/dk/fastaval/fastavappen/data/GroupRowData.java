package dk.fastaval.fastavappen.data;

public class GroupRowData implements Comparable<GroupRowData> {
	public String Title;
	public int day;
	public int hour;
	public long timestamp;

	@Override
	public int compareTo(GroupRowData GRD) {
		return Long.valueOf(timestamp).compareTo(Long.valueOf(GRD.timestamp));
	}
}
