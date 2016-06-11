package HTM;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WildCardTest {
	
	public static void main(String[] args) {
		Map<? extends Attribute,?> attributes = new TreeMap<Attribute, Attribute>();
		Set<? extends Attribute> keys = attributes.keySet();
		
		/*Iterator<? extends Attribute> itr = keys.iterator();
		Attribute sd = itr.next();
		System.out.println(sd);*/
	}
	
}
