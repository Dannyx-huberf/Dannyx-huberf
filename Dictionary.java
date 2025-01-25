import java.util.HashMap;
import java.util.Map;
public class Dictionary {
    private final Map<String, WordDefinition> dictionaryMap;
    public Dictionary() {
        dictionaryMap = new HashMap<>();
    }
    public void addWord(String word, String definition) {
        WordDefinition wordDef = new WordDefinition(definition);
        dictionaryMap.put(word, wordDef);
    }
    public void addExample(String word, String example) {
        WordDefinition wordDef = getWordDefinition(word);
        if (wordDef != null) {
            wordDef.addExample(example);
        }
    }
    public void addSynonym(String word, String synonym) {
        WordDefinition wordDef = getWordDefinition(word);
        if (wordDef != null) {
            wordDef.addSynonym(synonym);
        }
    }
    public WordDefinition getWordDefinition(String word) {
        // Perform a case-insensitive search
        for (String key : dictionaryMap.keySet()) {
            if (key.equalsIgnoreCase(word)) {
                return dictionaryMap.get(key); // Return the definition for the matching key
            }
        }
        return null; // Return null if the word is not found
    }
    public boolean containsWord(String word) {
        // Use equalsIgnoreCase for case-insensitive check
        for (String key : dictionaryMap.keySet()) {
            if (key.equalsIgnoreCase(word)) {
                return true; // Return true if the word is found
            }
        }
        return false; // Return false if the word is not found
    }
}