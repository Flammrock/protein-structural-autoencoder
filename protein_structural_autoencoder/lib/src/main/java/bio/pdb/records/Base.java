package bio.pdb.records;

import java.lang.reflect.Field;
import bio.pdb.Record;

public class Base {

	public Base(Record.FieldData data){
        for (String key : data.getValues().keySet()){
        	if (key.contains("[")) continue;
            setField(key,data.getValues().get(key));
        }
    }

    private void setField(String fieldName, Object value) {
        Field field;
        try {
            field = getClass().getDeclaredField(fieldName);
            field.set(this, value);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
	
}
