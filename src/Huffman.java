import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
	private String orgStr, encodedStr, decodedStr;
	public HashMap<Character, Integer> hmapWC;  // for occurrence count
	public HashMap<Character, String> hmapCode; // for code(character/code)
	public HashMap<String, Character> hmapCodeR; // for code(code/character)
	private PriorityQueue<node> pq;  // for MinHeap
	private int counter;  // Unique id assigned to each node
	private int treeSize;  // # of total nodes in the tree
	private node root;

	// Inner class
	private class node{
		int uid, weight;
		char ch;
		node left, right;
		
		// Constructor for class node
		private node(char ch, Integer weight, node left, node right){
			uid = ++counter;
			this.weight = weight;
			this.ch = ch;
			this.left = left;
			this.right = right;
		}	
	}
	
	// Constructor for class Huffman
	public Huffman(String orgStr, boolean show, String dotfilename){
		this.counter = 0;
		this.treeSize = 0;
		this.orgStr = orgStr;
		hmapWC = new HashMap<>();
		hmapCode = new HashMap<>();
		hmapCodeR = new HashMap<>();
		pq = new PriorityQueue<>(1, (n1, n2) -> {
			if (n1.weight < n2.weight)
				return -1;
			else if (n1.weight > n2.weight)
				return 1;
			return 0;
		});
		
		countWord();  // STEP 1: Count frequency of word
		buildTree();  // STEP 2: Build Huffman Tree
		buildCodeTable();  // STEP 3: Build Huffman Code Table
	}
		
	private void buildCodeTable(){
		String code = "";
		node n = root;
		buildCodeRecursion(n, code);  // Recursion
	}
	
	private void buildCodeRecursion(node n, String code){
		if (n != null){
			if (! isLeaf(n)){  // n = internal node
				buildCodeRecursion(n.left, code + '0');
				buildCodeRecursion(n.right, code + '1');
			}
			else{  // n = Leaf node
				hmapCode.put(n.ch, code); // for {character:code}
				hmapCodeR.put(code, n.ch); // for {code:character}
			}
		}
	}

		
	private void buildTree(){
		buildMinHeap();  // Set all leaf nodes into MinHeap
		node left, right;
		while (! pq.isEmpty()){
			left = pq.poll(); treeSize++;
			if (pq.peek() != null){
				right = pq.poll();  treeSize++;
				root = new node('\0', left.weight + right.weight, left, right);
			}
			else{  // only left child. right=null
				root = new node('\0', left.weight, left, null);
			}
			
			if (pq.peek() != null){
				pq.offer(root);
			}
			else{  // = Top root. Finished building the tree.
				treeSize++;
				break;
			}
		}
	}
	
	private void buildMinHeap(){
		for (Map.Entry<Character, Integer> entry: hmapWC.entrySet()){
			Character ch = entry.getKey();
	        Integer weight = entry.getValue();
	        node n = new node(ch, weight, null, null);
	        pq.offer(n);
		}		
	}
	
	private void countWord(){
		char ch;
		for(int i=0;i<orgStr.length();i++)
		{
			ch = orgStr.charAt(i);
			hmapWC.put(ch,hmapWC.getOrDefault(ch,0)+1);
		}
	}
	
	private boolean isLeaf(node n) {
        return (n.left == null) && (n.right == null);
    }
	
	public String encode(){
		StringBuilder sb = new StringBuilder();
		char ch;
		for(int i=0; i<orgStr.length(); i++){
			ch = orgStr.charAt(i);
			sb.append(hmapCode.get(ch));
		}
		encodedStr = sb.toString();
		return encodedStr;
	}
	
	public String decode(){
		StringBuilder sb = new StringBuilder();
		String t = "";
		
		for(int i=0; i<encodedStr.length(); i++){
			t += encodedStr.charAt(i);
			if (hmapCodeR.containsKey(t)){
				sb.append(hmapCodeR.get(t));
				t = "";
			}
		}
		decodedStr = sb.toString();
		return decodedStr;
	}
	
}
