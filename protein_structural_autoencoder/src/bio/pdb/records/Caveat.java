package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Caveat extends Base {
	
	public Caveat(FieldData data) {
		super(data);
	}
	
	public Integer continuation;
	public String idCode;
	public String comment;
	
}
