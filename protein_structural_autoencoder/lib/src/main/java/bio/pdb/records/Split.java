package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Split extends Base {
	
	public Split(FieldData data) {
		super(data);
		idCode = new String[13];
		for (int i = 0; i < 13; i++) {
			idCode[i] = (String)data.getValue("idCode["+i+"]");
		}
	}
	
	public Integer continuation;
	public String[] idCode;
	
}
