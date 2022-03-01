package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Conect extends Base {
	
	public Conect(FieldData data) {
		super(data);
		serial = new Integer[5];
		for (int i = 0; i < 5; i++) {
			serial[i] = (Integer)data.getValue("serial["+i+"]");
		}
	}

	public Integer[] serial;
	
}
