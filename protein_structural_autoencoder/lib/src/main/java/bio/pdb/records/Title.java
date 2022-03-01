package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Title extends Base {

	public Title(FieldData data) {
		super(data);
	}
	
	public Integer continuation;
	public String title;
	
}
