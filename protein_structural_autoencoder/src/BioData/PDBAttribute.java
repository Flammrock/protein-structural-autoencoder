package BioData;

/* documentation : https://ftp.wwpdb.org/pub/pdb/doc/format_descriptions/Format_v33_A4.pdf */

/* en cours de construction */

public enum PDBAttribute {

	ATOM(new PDBField
		.Builder()
		.add()
		.add()
		.build()
	);
	
	private PDBField field;
	
	private PDBAttribute(PDBField field) {
		this.field = field;
	}
	
	private static class PDBField {
		
		public static class Builder {
			
			public Builder add() {
				return this;
			}
			
			public PDBField build() {
				return new PDBField();
			}
			
		}
		
	}
}
