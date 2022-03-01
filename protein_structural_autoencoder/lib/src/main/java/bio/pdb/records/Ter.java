package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Ter extends Base {

	public Ter(FieldData data) {
		super(data);
	}
	
	public Integer serial;
	public String resName;
	public String chainID;
	public Integer resSeq;
	public String iCode;
	
}
