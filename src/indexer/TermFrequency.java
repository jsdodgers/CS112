// Daniel Yang		- 84311865
// Justin Saletta	- 38006614
// Connor Richards	- 54689185


package indexer;


public final class TermFrequency {
	private final int word;
	private int frequency;
	
	public TermFrequency(int word) {
		this.word = word;
		this.frequency = 0;
	}
	
	public TermFrequency(int word, int frequency) {
		this.word = word;
		this.frequency = frequency;
	}
	
	public int getText() {
		return word;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public void incrementFrequency() {
		frequency++;
	}
	
	@Override
	public String toString() {
		return word + ":" + frequency;
	}
}
