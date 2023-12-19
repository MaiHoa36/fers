package fpt.edu.eresourcessystem.service;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import fpt.edu.eresourcessystem.model.Topic;
import fpt.edu.eresourcessystem.repository.TopicRepository;
import fpt.edu.eresourcessystem.service.DocumentService;
import fpt.edu.eresourcessystem.service.TopicServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private DocumentService documentService;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private TopicServiceImpl topicService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        Topic topic1 = new Topic(); // Assume Topic is a valid entity class
        Topic topic2 = new Topic();
        when(topicRepository.findAll()).thenReturn(Arrays.asList(topic1, topic2));

        List<Topic> topics = topicService.findAll();

        assertNotNull(topics);
        assertEquals(2, topics.size());
        verify(topicRepository, times(1)).findAll();
    }

//    @Test
//    public void testAddTopic_NewTopic() {
//        Topic newTopic = new Topic();
//        when(topicRepository.save(newTopic)).thenReturn(newTopic);
//
//        Topic result = topicService.addTopic(newTopic);
//
//        assertNotNull(result);
//        verify(topicRepository).save(newTopic);
//    }
//
//    @Test
//    public void testAddTopic_ExistingTopic() {
//        Topic existingTopic = new Topic();
//        existingTopic.setId("013456891");
//        when(topicRepository.findById("013456891")).thenReturn(Optional.of(existingTopic));
//
//        Topic result = topicService.addTopic(existingTopic);
//
//        assertNull(result);
//        verify(topicRepository, never()).save(existingTopic);
//    }



}
