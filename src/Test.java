import java.io.*;
import java.util.Map;

public class Test{
	public static void writeFile(String out,String dotfilename){
		try{
			FileWriter fw = new FileWriter(dotfilename);
			fw.write(out);
			fw.close();
			System.out.println("Added into file Successfully");

		}catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	public static void testHuffman(String orgStr, boolean show, String dotfilename){
		System.out.print("* Building Huffman Tree and Code Tables...");
	    Huffman h = new Huffman(orgStr,show,dotfilename);
	    System.out.println(" DONE");
	    
	    if (show){
			System.out.println("\n============= Word Frequency =============");
			for (Map.Entry<Character, Integer> entry: h.hmapWC.entrySet()){
				String key = entry.getKey().toString();
				int val = entry.getValue();
				if (key.equals("\n"))
					key = "\\n";
				System.out.println(key + " occurs " + val + " times");
			}
			
			System.out.println("\n========== Huffman Code for each character =============");
			for (Map.Entry<Character, String> entry: h.hmapCode.entrySet()){
				String key = entry.getKey().toString();
				String val = entry.getValue();
				if (key.equals("\n"))
					key = "\\n";
				System.out.println(key + ": " + val); 
			}
			System.out.println();
		}
	    
	    System.out.print("* Encoding the text...");
	    String e = h.encode();
	    System.out.println(" DONE");
	    
	    System.out.print("* Decoding the encoded text...");
	    String d = h.decode();
	    myassert(orgStr.equals(d)) ;   // Check if original text and decoded text is exactly same
	    System.out.println(" DONE");
	    
	    double sl = orgStr.length() * 7 ;
	    double el = e.length();
	    System.out.println("\n========== RESULT ==========");
	    System.out.println("Original string cost = " + (int)sl + " bits") ;
	    System.out.println("Encoded  string cost = " + (int)el + " bits") ;
	    double r = ((el - sl)/sl) * 100 ;
	    System.out.println("% reduction = " + (-r)) ;
		writeFile(d,dotfilename);
	}
	
	public static String readFile(String fname){
		StringBuilder sb = new StringBuilder();
		File filename = new File(fname);
		try (BufferedReader in = new BufferedReader(new FileReader(filename))){
			String line = in.readLine();
			while (line != null){
				sb.append(line).append("\n");
				line = in.readLine();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return sb.toString();
	}
	
  public static void myassert(boolean  x) {
	    if (!x) {
	    	throw new IllegalArgumentException("Assert fail") ;
	    }
  }
  
  public static void testbed(){
	  boolean show = true ;
	  String orgFile = "C:\\Users\\Harihara\\Desktop\\My-Projects\\Huffman-Coding-In-Java\\files\\original.txt";
	  String dotFile = "C:\\Users\\Harihara\\Desktop\\My-Projects\\Huffman-Coding-In-Java\\files\\output.txt";
	  
	  System.out.print("* Loading the file...");
	  String orgString = readFile(orgFile);
	  System.out.println("DONE");
	  
	  testHuffman(orgString, show, dotFile);
  }
  public static void main(String[] args) {
	  System.out.println("----- Test.java START -----");
	  testbed();
	  System.out.println("\n----- Test DONE ----- ");
  }
  
}