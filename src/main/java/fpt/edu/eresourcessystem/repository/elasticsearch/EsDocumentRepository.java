package fpt.edu.eresourcessystem.repository.elasticsearch;

import fpt.edu.eresourcessystem.model.elasticsearch.EsDocument;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository("esDocumentRepository")
public interface EsDocumentRepository extends ElasticsearchRepository<EsDocument, String> {

    @Query("{\"bool\":{" +
            "\"must\":[" +
            "{\"match\": {\"courseStatus\": \"PUBLISH\"}}," +
            "{\"bool\": " +
            "{\"should\":[" +
            "{\"match_phrase\": {\"title\": \"*?0*\"}}," +
            "{\"match_phrase\": {\"description\": \"*?0*\"}}," +
            "{\"match_phrase\": {\"docType\": \"*?0*\"}}," +
            "{\"match_phrase\": {\"content\": \"*?0*\"}}," +
            "{\"match\": {\"title\": \"*?0*\"}}," +
            "{\"match\": {\"description\": \"*?0*\"}}," +
            "{\"match\": {\"content\": \"*?0*\"}}," +
            "{\"fuzzy\": {\"title\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"fuzzy\": {\"description\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"fuzzy\": {\"content\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}," +
            "{\"regexp\": {\"title\": \".*?0.*\"}}," +
            "{\"regexp\": {\"description\": \".*?0.*\"}}," +
            "{\"regexp\": {\"content\": \".*?0.*\"}}" +
            "]}}" +
            "]}}")
    Page<EsDocument> search(String search, Pageable pageable);

    default Page<EsDocument> findBySearchTerm(String searchTerm, Pageable pageable) {
        String[] words = searchTerm.toLowerCase().trim().split("\\s+");
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        for (String word : words) {
            queryBuilder.should(QueryBuilders.matchQuery("title", word))
                    .should(QueryBuilders.matchQuery("description", word))
                    .should(QueryBuilders.matchQuery("content", word));
        }
        return search(queryBuilder.toString(), pageable);
    }
}