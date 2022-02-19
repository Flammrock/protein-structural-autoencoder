package bio.pdb;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/* documentation : https://ftp.wwpdb.org/pub/pdb/doc/format_descriptions/Format_v33_A4.pdf */

/* en cours de construction */

public enum Record {
	
	HEADER(new PDBField.Builder()
		.add(1,6,"Record name","HEADER")
		.add(11,50,"String(40)","classification")
		.add(51,59,"Date","depDate")
		.add(63,66,"IDcode","idCode")
		.build()
	),
	
	OBSLTE(new PDBField.Builder()
		.add(1,6,"Record name","OBSLTE")
		.add(9,10,"Continuation","continuation")
		.add(12,20,"Date","repDate")
		.add(22,25,"IDcode","idCode")
		.add(32,35,"IDcode","rIdCode[0]")
		.add(37,40,"IDcode","rIdCode[1]")
		.add(42,45,"IDcode","rIdCode[2]")
		.add(47,50,"IDcode","rIdCode[3]")
		.add(52,55,"IDcode","rIdCode[4]")
		.add(57,60,"IDcode","rIdCode[5]")
		.add(62,65,"IDcode","rIdCode[6]")
		.add(67,70,"IDcode","rIdCode[7]")
		.add(72,75,"IDcode","rIdCode[8]")
		.build()
	),
	
	TITLE(new PDBField.Builder()
		.add(1,6,"Record name","TITLE")
		.add(9,10,"Continuation","continuation")
		.add(11,80,"String","title")
		.build()
	),
	
	SPLIT(new PDBField.Builder()
		.add(1,6,"Record name","SPLIT")
		.add(9,10,"Continuation","continuation")
		.add(12,15,"IDcode","idCode[0]")
		.add(17,20,"IDcode","idCode[1]")
		.add(22,25,"IDcode","idCode[2]")
		.add(32,35,"IDcode","idCode[3]")
		.add(37,40,"IDcode","idCode[4]")
		.add(42,45,"IDcode","idCode[5]")
		.add(47,50,"IDcode","idCode[6]")
		.add(52,55,"IDcode","idCode[7]")
		.add(57,60,"IDcode","idCode[8]")
		.add(62,65,"IDcode","idCode[9]")
		.add(67,70,"IDcode","idCode[10]")
		.add(72,75,"IDcode","idCode[11]")
		.add(77,80,"IDcode","idCode[12]")
		.build()
	),
	
	CAVEAT(new PDBField.Builder()
		.add(1,6,"Record name","CAVEAT")
		.add(9,10,"Continuation","continuation")
		.add(12,15,"IDcode","idCode")
		.add(20,79,"String","comment")
		.build()
	),
	
	COMPND(new PDBField.Builder()
		.add(1,6,"Record name","COMPND")
		.add(8,10,"Continuation","continuation")
		.add(11,80,"Specification list","compound")
		.build()
	),
	
	SOURCE(new PDBField.Builder()
		.add(1,6,"Record name","SOURCE")
		.add(8,10,"Continuation","continuation")
		.add(11,79,"Specification list","srcName")
		.build()
	),
	
	KEYWDS(new PDBField.Builder()
		.add(1,6,"Record name","KEYWDS")
		.add(9,10,"Continuation","continuation")
		.add(11,79,"Specification list","keywds")
		.build()
	),
	
	EXPDTA(new PDBField.Builder()
		.add(1,6,"Record name","EXPDTA")
		.add(9,10,"Continuation","continuation")
		.add(11,79,"SList","technique")
		.build()
	),
	
	NUMMDL(new PDBField.Builder()
		.add(1,6,"Record name","NUMMDL")
		.add(11,14,"Integer","modelNumber")
		.build()
	),
	
	MDLTYP(new PDBField.Builder()
		.add(1,6,"Record name","MDLTYP")
		.add(9,10,"Continuation","continuation")
		.add(11,80,"SList","comment")
		.build()
	),
	
	AUTHOR(new PDBField.Builder()
		.add(1,6,"Record name","AUTHOR")
		.add(9,10,"Continuation","continuation")
		.add(11,79,"List","authorList")
		.build()
	),
	
	

	ATOM(new PDBField
		.Builder()
		.add(1,6,"Record name","ATOM")
		.add(7,11,"Integer","serial")
		.add(13,16,"Atom","name")
		.add(17,17,"Character","altLoc")
		.add(18,20,"Residue name","resName")
		.add(22,22,"Character","chainID")
		.add(23,26,"Integer","resSeq")
		.add(27,27,"AChar","iCode")
		.add(31,38,"Real(8.3)","x")
		.add(39,46,"Real(8.3)","y")
		.add(47,54,"Real(8.3)","z")
		.add(55,60,"Real(6.2)","occupancy")
		.add(61,66,"Real(6.2)","tempFactor")
		.add(77,78,"LString(2)","element")
		.add(79,80,"LString(2)","charge")
		.build()
	),
	
	ANISOU(new PDBField
		.Builder()
		.add(1,6,"Record name","ANISOU")
		.add(7,11,"Integer","serial")
		.add(13,16,"Atom","name")
		.add(17,17,"Character","altLoc")
		.add(18,20,"Residue name","resName")
		.add(22,22,"Character","chainID")
		.add(23,26,"Integer","resSeq")
		.add(27,27,"AChar","iCode")
		.add(29,35,"Integer","u[0][0]")
		.add(36,42,"Integer","u[1][1]")
		.add(43,49,"Integer","u[2][2]")
		.add(50,56,"Integer","u[0][1]")
		.add(57,63,"Integer","u[0][2]")
		.add(64,70,"Integer","u[1][2]")
		.add(77,78,"LString(2)","element")
		.add(79,80,"LString(2)","charge")
		.build()
	),
	
	TER(new PDBField.Builder()
		.add(1,6,"Record name","TER")
		.add(7,11,"Integer","serial")
		.add(18,20,"Residue name","resName")
		.add(22,22,"Character","chainID")
		.add(23,26,"Integer","resSeq")
		.add(27,27,"AChar","iCode")
		.build()
	),
	
	ENDMDL(new PDBField.Builder()
		.add(1,6,"Record name","ENDMDL")
		.build()
	),
	
	CONECT(new PDBField.Builder()
		.add(1,6,"Record name","CONECT")
		.add(7,11,"Integer","serial[0]")
		.add(12,16,"Integer","serial[1]")
		.add(17,21,"Integer","serial[2]")
		.add(22,26,"Integer","serial[3]")
		.add(27,31,"Integer","serial[4]")
		.build()
	),
	
	MASTER(new PDBField.Builder()
		.add(1,6,"Record name","MASTER")
		.add(11,15,"Integer","numRemark")
		.add(16,20,"Integer","_0")
		.add(21,25,"Integer","numHet")
		.add(26,30,"Integer","numHelix")
		.add(31,35,"Integer","numSheet")
		.add(36,40,"Integer","numTurn")
		.add(41,45,"Integer","numSite")
		.add(46,50,"Integer","numXform")
		.add(51,55,"Integer","numCoord")
		.add(56,60,"Integer","numTer")
		.add(61,65,"Integer","numConect")
		.add(66,70,"Integer","numSeq")
		.build()
	),
	
	END(new PDBField.Builder()
		.add(1,6,"Record name","END")
		.build()
	)
	
	;
	
	private PDBField field;
	
	private Record(PDBField field) {
		this.field = field;
	}
	
	public FieldData parse(String line) {
		FieldData data = new FieldData();
		int length = line.length();
		for (Map.Entry<String,PDBField.Field> entry : field.subfields.entrySet()) {
			PDBField.Field field = entry.getValue();
			if (field.type.equals("Record name")) continue;
			if (length < field.endPos) throw new IllegalArgumentException("the String passed as argument is too short.");
			data.put(entry.getKey(), line.substring(field.startPos-1, field.endPos), field.type);
		}
		return data;
	}
	
	public static Predicate<? super String> is(Record record) {
		String name = record.name();
		Integer length = name.length();
		return (line) -> {
			if (line.length() < length) return false;
			return name.equals(line.substring(0,length));
		};
	}
	
	public static class FieldData {
		
		private Map<String,Object> data = new LinkedHashMap<>();
		
		private void put(String name, String value, String type) {
			if (type.equals("Continuation") || type.equals("Integer")) {
				data.put(name, value.isBlank()?0:Integer.valueOf(value.trim()));
			} else if (type.length() >= 4 && type.substring(0,4).equals("Real")) {
				data.put(name,value.isBlank()?0.0f:Float.valueOf(value.trim()));
			} else {
				data.put(name,value);
			}
		}
		
		public boolean hasKey(String name) {
			return data.containsKey(name);
		}
		
		public Object getValue(String name) {
			if (!hasKey(name)) throw new IllegalArgumentException("The key \""+name+"\" doesn't exist.");
			return data.get(name);
		}
		
		public Map<String,Object> getValues() {
			return data;
		}
		
	}
	
	private static class PDBField {
		
		public Map<String,Field> subfields;
		
		private PDBField(Map<String,Field> subfields) {
			this.subfields = subfields;
		}
		
		public static class Field {
			private int startPos;
			private int endPos;
			private String name;
			private String type;
			private Field(int startPos,int endPos,String name,String type) {
				this.startPos = startPos;
				this.endPos = endPos;
				this.name = name;
				this.type = type;
			}
		}
		
		public static class Builder {
			
			public Map<String,Field> subfields = new LinkedHashMap<>();
			
			public Builder add(int startPos, int endPos, String type, String name) {
				subfields.put(name, new Field(startPos,endPos,name,type));
				return this;
			}
			
			public PDBField build() {
				return new PDBField(subfields);
			}
			
		}
		
	}
	
	
	public static FieldData parseLine(String line) {
		return Record.valueOf(line.substring(0,5)).parse(line);
	}
}
