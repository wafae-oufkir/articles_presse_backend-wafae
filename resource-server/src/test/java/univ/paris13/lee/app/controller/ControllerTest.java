package univ.paris13.lee.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import univ.paris13.lee.app.persistence.ArticleRepository;
import univ.paris13.lee.app.persistence.MongoArticle;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {
    private static final ObjectMapper MAPPER = createMapper();

    private static ObjectMapper createMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper;
    }

    public static String serialize(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Mock
    private ArticleRepository repository;
    @InjectMocks
    private ArticleController controller;
    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getArticles() throws Exception {
        final List<MongoArticle> articles = Collections.singletonList(new MongoArticle().author("foo").title("bar").content("toto"));

        Mockito.when(repository.findAll()).thenReturn(articles);

        mockMvc.perform(MockMvcRequestBuilders.get(new URI("/articles")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(serialize(articles.stream()
                        .map(MongoArticle::toArticle).collect(Collectors.toList()))));
    }

    @Test
    public void getArticle() throws Exception {
        final MongoArticle article = new MongoArticle().author("foo").title("bar").content("toto");

        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(article));

        mockMvc.perform(MockMvcRequestBuilders.get(new URI("/articles/id")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(serialize(article.toArticle())));
    }

    @Test
    public void createArticle() throws Exception {
        final MongoArticle article = new MongoArticle().id("id").author("foo").title("bar").content("toto");

        Mockito.when(repository.save(Mockito.any())).thenReturn(article);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(new URI("/articles"))
                .headers(headers)
                .content(serialize(article));
        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(header().string("Location", article.getId()))
                .andExpect(MockMvcResultMatchers.content().json(serialize(article.toArticle())));
    }


}
