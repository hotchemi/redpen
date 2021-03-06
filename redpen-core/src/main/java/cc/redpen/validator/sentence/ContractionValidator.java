/**
 * redpen: a text inspection tool
 * Copyright (C) 2014 Recruit Technologies Co., Ltd. and contributors
 * (see CONTRIBUTORS.md)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cc.redpen.validator.sentence;

import cc.redpen.ValidationError;
import cc.redpen.model.Sentence;
import cc.redpen.validator.PreProcessor;
import cc.redpen.validator.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validate English contraction in the input document.
 * NOTE: this validator works only for English documents.
 */
public class ContractionValidator extends Validator<Sentence> implements PreProcessor<Sentence> {
    private int foundContractionNum = 0;
    private int foundNonContractionNum = 0;

    private static final Set<String> contractions;
    private static final Set<String> nonContractions;

    static {
        contractions = new HashSet<>();
        contractions.add("aren't");
        contractions.add("can't");
        contractions.add("couldn't");
        contractions.add("didn't");
        contractions.add("doesn't");
        contractions.add("don't");
        contractions.add("hadn't");
        contractions.add("hasn't");
        contractions.add("haven't");
        contractions.add("he'd");
        contractions.add("he'll");
        contractions.add("he's");
        contractions.add("i'd");
        contractions.add("i'll");
        contractions.add("i'm");
        contractions.add("i've");
        contractions.add("isn't");
        contractions.add("it's");
        contractions.add("let's");
        contractions.add("mightn't");
        contractions.add("mustn't");
        contractions.add("shan't");
        contractions.add("she'd");
        contractions.add("she'll");
        contractions.add("she's");
        contractions.add("shouldn't");
        contractions.add("that's");
        contractions.add("there's");
        contractions.add("they'd");
        contractions.add("they'll");
        contractions.add("they're");
        contractions.add("they've");
        contractions.add("we'd");
        contractions.add("we're");
        contractions.add("we've");
        contractions.add("weren't");
        contractions.add("what'll");
        contractions.add("what're");
        contractions.add("what's");
        contractions.add("what've");
        contractions.add("where's");
        contractions.add("who'd");
        contractions.add("who'll");
        contractions.add("who're");
        contractions.add("who's");
        contractions.add("who've");
        contractions.add("won't");
        contractions.add("wouldn't");
        contractions.add("you'd");
        contractions.add("you'll");
        contractions.add("you're");
        contractions.add("you've");
    }

    static {
        nonContractions = new HashSet<>();
        nonContractions.add("are");
        nonContractions.add("cannot");
        nonContractions.add("could");
        nonContractions.add("did");
        nonContractions.add("do");
        nonContractions.add("had");
        nonContractions.add("has");
        nonContractions.add("have");
        nonContractions.add("he");
        nonContractions.add("i");
        nonContractions.add("it");
        nonContractions.add("let");
        nonContractions.add("might");
        nonContractions.add("must");
        nonContractions.add("shall");
        nonContractions.add("she");
        nonContractions.add("that");
        nonContractions.add("there");
        nonContractions.add("we");
        nonContractions.add("what");
        nonContractions.add("who");
        nonContractions.add("will");
        nonContractions.add("would");
        nonContractions.add("you");
    }

    @Override
    public List<ValidationError> validate(Sentence block) {
        List<ValidationError> errors = new ArrayList<>();
        String [] words = block.content.toLowerCase().split(" ");
        for (String word : words) {
            if (foundNonContractionNum >= foundContractionNum
                    && contractions.contains(word)) {
                errors.add(new ValidationError(
                        this.getClass(),
                        "Found contraction: \"" + word + "\"",
                        block));
            }
        }
        return errors;
    }

    @Override
    public void preprocess(Sentence block) {
        String [] words = block.content.toLowerCase().split(" ");
        for (String word : words) {
            if (contractions.contains(word)) {
                foundContractionNum += 1;
            } else if (nonContractions.contains(word)) {
                foundNonContractionNum += 1;
            }
        }
    }
}
