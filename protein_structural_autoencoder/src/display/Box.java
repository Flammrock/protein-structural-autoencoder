package display;

public interface Box {

	public void addChildren(Component component);
	
	public boolean hasChildren(Component component);
	
	public void removeChildren(Component component);
	
}
