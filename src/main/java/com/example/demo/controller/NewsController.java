package com.example.demo.controller;

import com.example.demo.dto.NewsDTO;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<NewsDTO> getAllNews() {
        return newsService.getAllNews();
    }

    @GetMapping("/{id}")
    public NewsDTO getNewsById(@PathVariable Integer id) {
        return newsService.getNewsById(id);
    }

    @PostMapping
    public ResponseEntity<String> saveNews(@RequestBody NewsDTO newsDTO) {
        newsService.saveNews(newsDTO);
        return ResponseEntity.ok("News created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateNews(
            @PathVariable("id") int id,
            @RequestBody NewsDTO newsDTO) {
        newsService.updateNews(id, newsDTO);
        return ResponseEntity.ok("News updated successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Integer id) {
        newsService.deleteNews(id);
    }
}
