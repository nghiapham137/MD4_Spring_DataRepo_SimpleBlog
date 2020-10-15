package service;

import model.Blog;
import model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BlogService {
    Page<Blog> findAll(Pageable pageable);
    Blog findById(Long id);
    void save(Blog blog);
    void remove(Long id);
    Iterable<Blog> findAllByCategory(Category category);
    Page<Blog> findAllByTitleContaining(String title, Pageable pageable);
}
