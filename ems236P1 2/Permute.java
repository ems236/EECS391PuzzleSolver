import java.util.*;
public class Permute 
{
	/*
	public static void main(String[] args)
	{
		List<Integer> initial = new ArrayList<Integer>();
		initial.add(0);
		initial.add(1);
		initial.add(2);
		initial.add(3);
		initial.add(4);
		initial.add(5);
		initial.add(6);
		initial.add(7);
		initial.add(8);
		List<List<Integer>> permutations = Permute.generate(initial);
		
		for(int i = 0; i < 100; i++)
		{
			String out = "";
			for(Integer j : permutations.get(i))
			{
				out += j;
			}
			System.out.println(out);
		}
		System.out.println(permutations.size());
	}
	*/
	
	public static List<List<Integer>> generate(List<Integer> values)
	{
		List<List<Integer>> output = new ArrayList<List<Integer>>();
		permute(values, 0, output);
		return output;
	}
	
	//This was copied from https://stackoverflow.com/questions/2920315/permutation-of-array
	//Generates every permutation of an array of Strings, flattens every permutation to a single string and populates outlist.
	private static void permute(List<Integer> arr, int k, List<List<Integer>> outList)
	{
        for(int i = k; i < arr.size(); i++)
        {
            Collections.swap(arr, i, k);
            permute(arr, k+1, outList);
            Collections.swap(arr, k, i);
        }
        if (k == arr.size() -1)
        {
        	outList.add(new ArrayList<Integer>(arr));
            /*
             * //A permutation is generated
             
        	StringBuilder value = new StringBuilder();
        	for(Integer character : arr)
        	{
        		value.append(character);
        	}
        	outList.add(Integer.parseInt(value.toString()));
        	*/
        }
    }
}
