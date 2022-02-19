package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Atom extends Base {
	
	public Atom(FieldData data) {
		super(data);
	}
	
	public Integer serial;
	public String name;
	public String altLoc;
	public String resName;
	public String chainID;
	public Integer resSeq;
	public String iCode;
	public Float x;
	public Float y;
	public Float z;
	public Float occupancy;
	public Float tempFactor;
	public String element;
	public String charge;
	
}
