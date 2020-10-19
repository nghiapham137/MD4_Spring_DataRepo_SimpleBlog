package controller;

import model.Blog;
import model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;
import service.BlogService;
import service.CategoryService;
import service.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

//    @GetMapping("")
//    public String listBlog(Model model, @RequestParam("s")Optional<String> s) {
//        Page<Blog> blogs;
//        Sort sort = new Sort(new Sort.Order(Direction.ASC, "title") );
//        Pageable pageable = new PageRequest(0,10 , sort);
//        if (s.isPresent()) {
//            blogs = blogService.findAllByTitleContaining(s.get(), pageable);
//        }else {
//            blogs = blogService.findAll(pageable);
//        }
//        model.addAttribute("blogs", blogs);
//        return "blog/list";
//    }
    @GetMapping("")
    public ResponseEntity<Iterable<Blog>> listBlog() {
        List<Blog> blogs = (List<Blog>) blogService.findAll();
        if (blogs.isEmpty()) {
            return new ResponseEntity<Iterable<Blog>>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Iterable<Blog>>(blogs, HttpStatus.OK);
        }

    }

    @GetMapping(value = "/view/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Blog> getBlogById(@PathVariable("id") Long id) throws NotFoundException {
        Blog blog = blogService.findById(id);
        if (blog == null) {
            System.out.println("Blog with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Blog>(blog, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog, UriComponentsBuilder urBuilder) {
        blogService.save(blog);
        return new ResponseEntity<Blog>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Blog> updateCustomer(@PathVariable("id") Long id, @RequestBody Blog blog) throws NotFoundException {
        System.out.println("Updating Blog " + id);

        Blog currentBlog = blogService.findById(id);

        if (currentBlog == null) {
            System.out.println("Blog with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }

        currentBlog.setTitle(blog.getTitle());
        currentBlog.setContent(blog.getContent());
        currentBlog.setCategory(blog.getCategory());
        currentBlog.setId(blog.getId());

        blogService.save(currentBlog);
        return new ResponseEntity<Blog>(currentBlog, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Blog> deleteCustomer(@PathVariable("id") Long id) throws NotFoundException {
        System.out.println("Fetching & Deleting Blog with id " + id);

        Blog blog = blogService.findById(id);
        if (blog == null) {
            System.out.println("Unable to delete. Customer with id " + id + " not found");
            return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
        }

        blogService.remove(id);
        return new ResponseEntity<Blog>(HttpStatus.NO_CONTENT);
    }


//    @GetMapping("/create")
//    public String showAddForm(Model model) {
//        model.addAttribute("blog", new Blog());
//        return "blog/create";
//    }
//
//    @PostMapping("/create")
//    public String createBlog(Blog blog) {
//        blogService.save(blog);
//        return "redirect:/blogs";
//    }
//
//    @GetMapping("/view/{id}")
//    public String viewBlog(@PathVariable("id") Long id,Model model) throws NotFoundException {
//        Blog blog = blogService.findById(id) ;
//        model.addAttribute("blog", blog);
//        return "blog/view";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable("id") Long id,Model model) throws NotFoundException {
//        Blog blog = blogService.findById(id);
//        model.addAttribute("blog", blog);
//        return "blog/edit";
//    }
//
//    @PostMapping("/edit")
//    public String updateBlog(Blog blog, Model model, Pageable pageable) {
//        blogService.save(blog);
//        Page<Blog> blogs = blogService.findAll(pageable);
//        model.addAttribute("blogs", blogs);
//        return "redirect:/blogs";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String showDeleteForm(@PathVariable("id") Long id,Model model) throws NotFoundException {
//        Blog blog = blogService.findById(id);
//        model.addAttribute("blog", blog);
//        return "blog/delete";
//    }
//
//    @PostMapping("/delete/{id}")
//    public String deleteBlog(@PathVariable("id") Long id){
//        blogService.remove(id);
//        return "redirect:/blogs";
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ModelAndView showNotFound() {
//        return new ModelAndView("blog/not-found");
//    }
}
