package bio.pdb.records;

import bio.pdb.Record.FieldData;

public class Anisou extends Base {
	
	public Anisou(FieldData data) {
		super(data);
		u = new Integer[3][3];
		u[0][0] = (Integer)data.getValue("u[0][0]");
		u[1][1] = (Integer)data.getValue("u[1][1]");
		u[2][2] = (Integer)data.getValue("u[2][2]");
		u[0][1] = (Integer)data.getValue("u[0][1]");
		u[0][2] = (Integer)data.getValue("u[0][2]");
		u[1][2] = (Integer)data.getValue("u[1][2]");
	}
	
	public Integer serial;
	public String name;
	public String altLoc;
	public String resName;
	public String chainID;
	public Integer resSeq;
	public String iCode;
	public Integer[][] u;
	public String element;
	public String charge;
	
}
