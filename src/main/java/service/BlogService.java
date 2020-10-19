package service;

import model.Blog;
import model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import service.exception.NotFoundException;

import java.util.List;

public interface BlogService {
    Iterable<Blog> findAll();
    Blog findById(Long id) throws NotFoundException;
    void save(Blog blog);
    void remove(Long id);
    Iterable<Blog> findAllByCategory(Category category);
    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
}
