import java.util.*;

public class RubikBlock 
{
	public static Set<RubikBlock> validBlocks;
	static
	{
		validBlocks = new HashSet<RubikBlock>();
		validBlocks.add(new RubikBlock(Color.GREEN, Color.WHITE, Color.RED, 0));
		validBlocks.add(new RubikBlock(Color.BLUE, Color.WHITE, Color.RED, 1));
		validBlocks.add(new RubikBlock(Color.GREEN, Color.YELLOW, Color.RED, 2));
		validBlocks.add(new RubikBlock(Color.BLUE, Color.YELLOW, Color.RED, 3));
		validBlocks.add(new RubikBlock(Color.GREEN, Color.WHITE, Color.ORANGE, 4));
		validBlocks.add(new RubikBlock(Color.BLUE, Color.WHITE, Color.ORANGE, 5));
		validBlocks.add(new RubikBlock(Color.GREEN, Color.YELLOW, Color.ORANGE, 6));
		validBlocks.add(new RubikBlock(Color.BLUE, Color.YELLOW, Color.ORANGE, 7));
	}
	
	public int id;
	public Color xFace;
	public Color yFace;
	public Color zFace;
	
	public RubikBlock(Color xFace, Color yFace, Color zFace, int id)
	{
		this.id = id;
		this.xFace = xFace;
		this.yFace = yFace;
		this.zFace = zFace;
	}
	
	public void rotateXY()
	{
		//rotate in xy planze, z direction doesn't change.
		//xy swap.  State handles pos or negative.
		Color temp = xFace;
		xFace = yFace;
		yFace = temp;
	}
	
	public void rotateYZ()
	{
		//rotate in yz planze, x direction doesn't change.
		//yz swap.  State handles pos or negative.
		Color temp = yFace;
		yFace = zFace;
		zFace = temp;
	}
	
	public void rotateXZ()
	{
		//rotate in XZ planze, Y direction doesn't change.
		//xz swap.  State handles pos or negative.
		Color temp = xFace;
		xFace = zFace;
		zFace = temp;
	}
	
	public boolean isLegal()
	{
		return validBlocks.contains(this);
	}
	
	public RubikBlock copy()
	{
		return new RubikBlock(xFace, yFace, zFace, id);
	}
	
	//equals where direction matters.
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		
		if(!(o instanceof RubikBlock))
		{
			return false;
		}
		
		RubikBlock block = (RubikBlock)o;
		return block.xFace == this.xFace && block.yFace == this.yFace && block.zFace == this.zFace;
	}
	
}
