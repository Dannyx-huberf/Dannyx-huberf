import java.util.ArrayList;
import java.util.List;

public class WordDefinition {
    private final String definition;
    private final List<String> examples;
    private final List<String> synonyms;

    public WordDefinition(String definition) {
        this.definition = definition;
        this.examples = new ArrayList<>();
        this.synonyms = new ArrayList<>();
    }

    public String getDefinition() {
        return definition;
    }

    public void addExample(String example) {
        examples.add(example);
    }

    public void addSynonym(String synonym) {
        synonyms.add(synonym);
    }

    public List<String> getExamples() {
        return examples;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }
}