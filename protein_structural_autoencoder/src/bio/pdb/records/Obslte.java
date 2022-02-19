package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Obslte extends Base {

	public Obslte(FieldData data) {
		super(data);
		rIdCode = new String[9];
		for (int i = 0; i < 9; i++) {
			rIdCode[i] = (String)data.getValue("rIdCode["+i+"]");
		}
	}
	
	public Integer continuation;
	public String repDate;
	public String idCode;
	public String[] rIdCode;
	
}
