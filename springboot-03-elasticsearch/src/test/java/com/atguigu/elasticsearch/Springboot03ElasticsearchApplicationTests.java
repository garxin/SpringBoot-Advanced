package com.atguigu.elasticsearch;

import com.atguigu.elasticsearch.bean.Article;
import com.atguigu.elasticsearch.bean.Book;
import com.atguigu.elasticsearch.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03ElasticsearchApplicationTests {

    @Autowired
    JestClient jestClient;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void contextLoads() {
        //1.给ES的某个索引中保存一个文档
        Article article = new Article();
        article.setId(1);
        article.setTitle("好消息");
        article.setAuthor("张三");
        article.setContent("Hello World");

        //构建一个索引
        Index index = new Index.Builder(article).index("atguigu").type("news").build();
        try {
            //执行
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //测试搜索
    @Test
    public void search() {
        //查询表达式
        String json = "{\n" +
                "    \"query\" : {\n" +
                "    \t\"match\" : {\n" +
                "    \t\t\"content\" : \"Hello\"\n" +
                "    \t}\n" +
                "    }\n" +
                "}";

        //构建搜索
        Search search = new Search.Builder(json).addIndex("atguigu").addType("news").build();
        try {
            //执行
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        Book book = new Book();
        book.setId(1);
        book.setBookName("西游记");
        book.setAuthor("吴承恩");
        bookRepository.index(book);

        List<Book> list = bookRepository.findByBookNameLike("游");
        list.forEach(System.out::println);
    }

}
