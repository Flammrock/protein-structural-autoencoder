package bio.pdb.records;

import java.util.List;

import bio.pdb.Record.FieldData;

public class Author extends Base {
	
	public Author(FieldData data) {
		super(data);
	}
	
	public Integer continuation;
	public String authorList;
	
}
