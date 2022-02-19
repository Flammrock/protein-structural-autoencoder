package bio.pdb.records;

import java.util.List;

import bio.pdb.Record.FieldData;

public class Source extends Base {
	
	public Source(FieldData data) {
		super(data);
	}
	
	public Integer continuation;
	public String srcName;
	
}
