package bio.pdb.records;

import java.util.List;

import bio.pdb.Record.FieldData;

public class Compnd extends Base {

	public Compnd(FieldData data) {
		super(data);
	}
	
	public Integer continuation;
	public String compound;
	
}
