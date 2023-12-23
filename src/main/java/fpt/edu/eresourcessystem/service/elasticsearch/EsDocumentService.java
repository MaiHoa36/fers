package fpt.edu.eresourcessystem.service.elasticsearch;

import fpt.edu.eresourcessystem.model.elasticsearch.EsDocument;
import fpt.edu.eresourcessystem.repository.elasticsearch.EsDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class EsDocumentService {

    private final EsDocumentRepository documentRepository;
    private final EsDocumentRepository esDocumentRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    public Page<EsDocument> searchDocument(String searchTerm, int skip) {
        Pageable pageable = PageRequest.of(skip, 10);
        return esDocumentRepository.search(searchTerm, pageable);
    }

    public EsDocument save(EsDocument document) {
        return documentRepository.save(document);
    }

    public void delete(EsDocument document) {
        documentRepository.delete(document);
    }

    public Optional<EsDocument> findOne(String id) {
        return documentRepository.findById(id);
    }

    public Iterable<EsDocument> findAll() {
        return documentRepository.findAll();
    }

//    public Page<EsDocument> searchDocument(String searchTerm, int skip) {
//        Pageable pageable = PageRequest.of(skip, 10);
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.boolQuery()
//                        .must(QueryBuilders.matchQuery("courseStatus", "PUBLISH"))
//                        .should(QueryBuilders.matchPhraseQuery("title", searchTerm))
//                        .should(QueryBuilders.matchPhraseQuery("description", searchTerm))
//                        .should(QueryBuilders.matchPhraseQuery("docType", searchTerm))
//                        .should(QueryBuilders.matchPhraseQuery("content", searchTerm))
//                        .should(QueryBuilders.matchQuery("title", searchTerm))
//                        .should(QueryBuilders.matchQuery("description", searchTerm))
//                        .should(QueryBuilders.matchQuery("content", searchTerm))
//                        .should(QueryBuilders.fuzzyQuery("title", searchTerm))
//                        .should(QueryBuilders.fuzzyQuery("description", searchTerm))
//                        .should(QueryBuilders.fuzzyQuery("content", searchTerm))
//                        .should(QueryBuilders.regexpQuery("title", ".*" + searchTerm + ".*"))
//                        .should(QueryBuilders.regexpQuery("description", ".*" + searchTerm + ".*"))
//                        .should(QueryBuilders.regexpQuery("content", ".*" + searchTerm + ".*"))
//                        .minimumShouldMatch(1))
//                .withHighlightFields(
//                        new HighlightBuilder.Field("title").numOfFragments(3).fragmentSize(20),
//                        new HighlightBuilder.Field("description").numOfFragments(3).fragmentSize(20),
//                        new HighlightBuilder.Field("content").numOfFragments(3).fragmentSize(20)
//                )
//                .withPageable(pageable)
//                .build();
//        SearchPage<EsDocument> page = SearchHitSupport.searchPageFor(elasticsearchTemplate.search(searchQuery, EsDocument.class), pageable);
//        return (Page) SearchHitSupport.unwrapSearchHits(page);
//    }
}
