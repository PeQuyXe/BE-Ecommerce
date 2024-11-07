package com.example.demo.service;

import com.example.demo.dto.NewsDTO;
import com.example.demo.model.News;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import java.time.ZoneId;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream().map(news -> {
            NewsDTO newsDTO = new NewsDTO(news);
            userRepository.findById(news.getUserId())
                    .ifPresent(user -> newsDTO.setAuthorName(user.getFullname()));
            return newsDTO;
        }).collect(Collectors.toList());
    }

    public NewsDTO getNewsById(Integer id) {
        News news = newsRepository.findById(id).orElse(null);
        if (news == null) return null;

        NewsDTO newsDTO = new NewsDTO(news);
        userRepository.findById(news.getUserId())
                .ifPresent(user -> newsDTO.setAuthorName(user.getFullname()));
        return newsDTO;
    }

    public News saveNews(NewsDTO newsDTO) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setThumb(newsDTO.getThumb());
        news.setSlug(newsDTO.getSlug());
        news.setView(newsDTO.getView());
        news.setStatus(newsDTO.getStatus());  // Status là String ("true" hoặc "false")

        // Chuyển đổi từ Date sang LocalDateTime trước khi lưu
        news.setCreateAt(newsDTO.getCreateAt());
        news.setUpdateAt(newsDTO.getUpdateAt());

        news.setUserId(newsDTO.getUserId());
        System.out.println("Received news: " + newsDTO);
        return newsRepository.save(news);
    }

    public News updateNews(Integer id, NewsDTO newsDTO) {
        News news = newsRepository.findById(id).orElse(null);
        if (news == null) return null;

        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setThumb(newsDTO.getThumb());
        news.setSlug(newsDTO.getSlug());
        news.setView(newsDTO.getView());
        news.setStatus(newsDTO.getStatus());  // Status là String ("true" hoặc "false")

        // Cập nhật thông tin updateAt
        news.setUpdateAt(newsDTO.getUpdateAt());
        return newsRepository.save(news);
    }

    public void deleteNews(Integer id) {
        newsRepository.deleteById(id);
    }

    // Helper method to convert Date to LocalDateTime
    private LocalDateTime toLocalDateTime(Date date) {
        if (date != null) {
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        }
        return null;
    }
}
