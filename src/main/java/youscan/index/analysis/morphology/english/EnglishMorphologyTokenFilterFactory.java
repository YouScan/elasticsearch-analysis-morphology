/*
 * Copyright 2012 Igor Motov
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

package youscan.index.analysis.morphology.english;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.elasticsearch.ElasticsearchIllegalArgumentException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import youscan.index.analysis.morphology.common.KeywordPreserveMorphologyFilter;
import org.elasticsearch.index.settings.IndexSettings;

import java.io.IOException;

/**
 *
 */
public class EnglishMorphologyTokenFilterFactory extends AbstractTokenFilterFactory {

    private final LuceneMorphology luceneMorph;

    @Inject
    public EnglishMorphologyTokenFilterFactory(Index index, @IndexSettings Settings indexSettings, String name, Settings settings) {
        super(index, indexSettings, name, settings);
        try {
            luceneMorph = new EnglishLuceneMorphology();
        } catch (IOException ex) {
            throw new ElasticsearchIllegalArgumentException("Unable to load English morphology analyzer", ex);
        }
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new KeywordPreserveMorphologyFilter(tokenStream, luceneMorph);
    }
}
