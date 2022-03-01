package bio.pdb.records;

import java.util.List;

import bio.pdb.Record.FieldData;

public class Expdta extends Base {
	
	public Expdta(FieldData data) {
		super(data);
	}
	
	public Integer continuation;
	public String technique;
	
}
