package cc.redpen.validator.sentence;

import cc.redpen.RedPen;
import cc.redpen.RedPenException;
import cc.redpen.ValidationError;
import cc.redpen.config.Configuration;
import cc.redpen.config.ValidatorConfiguration;
import cc.redpen.distributor.FakeResultDistributor;
import cc.redpen.model.DocumentCollection;
import cc.redpen.model.Sentence;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EndOfSentenceValidatorTest {
    @Test
    public void testInvalidEndOfSentence() {
        EndOfSentenceValidator validator = new EndOfSentenceValidator();
        List<ValidationError> errors = validator.validate(
                new Sentence("He said \"that is right\".", 0));
        assertEquals(1, errors.size());
    }

    @Test
    public void testValidEndOfSentence() {
        EndOfSentenceValidator validator = new EndOfSentenceValidator();
        List<ValidationError> errors = validator.validate(
                new Sentence("He said \"that is right.\"", 0));
        assertEquals(0, errors.size());
    }

    @Test
    public void testInValidEndOfSentenceWithQuestionMark() {
        EndOfSentenceValidator validator = new EndOfSentenceValidator();
        List<ValidationError> errors = validator.validate(
                new Sentence("He said \"Is it right\"?", 0));
        assertEquals(1, errors.size());
    }

    @Test
    public void testVoid() {
        EndOfSentenceValidator validator = new EndOfSentenceValidator();
        List<ValidationError> errors = validator.validate(
                new Sentence("", 0));
        assertEquals(0, errors.size());
    }

    @Test
    public void testJapaneseInvalidEndOfSentence() throws RedPenException {
        Configuration config = new Configuration.Builder()
                .addValidatorConfig(new ValidatorConfiguration("EndOfSentence"))
                .setSymbolTable("ja").build();

        DocumentCollection documents = new DocumentCollection.Builder()
                .addDocument("")
                .addSection(1)
                .addParagraph()
                .addSentence(
                        "彼は言った，“今日は誕生日”。",
                        1)
                .build();

        RedPen validator = new RedPen.Builder()
                .setConfiguration(config)
                .setResultDistributor(new FakeResultDistributor())
                .build();

        List<ValidationError> errors = validator.check(documents);
        assertEquals(1, errors.size());
    }
}
