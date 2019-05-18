public enum Color 
{
	//0=white
	//1=red
	//2=blue
	//3=yellow
	//4=orange
	//5=green
	WHITE 
	, RED
	, BLUE
	, YELLOW
	, ORANGE
	, GREEN;
	
	public String toString()
	{
		if(this == WHITE)
		{
			return "w";
		}
		else if(this == RED)
		{
			return "r";
		}
		else if(this == BLUE)
		{
			return "b";
		}
		else if(this == YELLOW)
		{
			return "y";
		}
		else if(this == ORANGE)
		{
			return "o";
		}
		else
		{
			//green
			return "g";
		}
	}
}
