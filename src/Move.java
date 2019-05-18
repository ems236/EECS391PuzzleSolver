public interface Move 
{
	//Allows moves to be held in an array.
	public State move(State state);
	public boolean isValid(State state);
	public String getName();
}
