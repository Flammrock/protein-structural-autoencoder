package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Header extends Base {

	public Header(FieldData data) {
		super(data);
	}
	
	public String classification;
	public String depDate;
	public String IDcode;
	
}
