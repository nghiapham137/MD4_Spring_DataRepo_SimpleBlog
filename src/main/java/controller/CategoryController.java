package controller;

import model.Blog;
import model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.BlogService;
import service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<Iterable<Category>> listCategories() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<Iterable<Category>>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Iterable<Category>>(categories, HttpStatus.OK);
        }
    }
//    public ModelAndView listCategory() {
//        Iterable<Category> categories = categoryService.findAll();
//        ModelAndView modelAndView = new ModelAndView("/category/list");
//        modelAndView.addObject("categories", categories);
//        return modelAndView;
//    }

//    @GetMapping("/create")
//    public ModelAndView showCreateForm() {
//        ModelAndView modelAndView = new ModelAndView("/category/create");
//        modelAndView.addObject("category", new Category());
//        return modelAndView;
//    }
//
//    @PostMapping("/create")
//    public ModelAndView createCategory(@ModelAttribute("category") Category category) {
//        categoryService.save(category);
//        ModelAndView modelAndView = new ModelAndView("/category/create");
//        modelAndView.addObject("category", new Category());
//        modelAndView.addObject("message", "New category created successfully");
//        return modelAndView;
//    }
//
//    @GetMapping("/edit/{id}")
//    public ModelAndView showEditForm(@PathVariable("id") Long id) {
//        Category category = categoryService.findById(id);
//        if (category != null) {
//            ModelAndView modelAndView = new ModelAndView("/category/edit");
//            modelAndView.addObject("category", category);
//            return modelAndView;
//        }else {
//            ModelAndView modelAndView = new ModelAndView("/error404");
//            return modelAndView;
//        }
//    }
//
//    @PostMapping("/edit")
//    public ModelAndView updateCategory(@ModelAttribute("category") Category category) {
//        categoryService.save(category);
//        ModelAndView modelAndView = new ModelAndView("/category/edit");
//        modelAndView.addObject("category", category);
//        modelAndView.addObject("message", "category updated successfully");
//        return modelAndView;
//    }
//
//    @GetMapping("/delete/{id}")
//    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
//        Category category = categoryService.findById(id);
//        if (category != null) {
//            ModelAndView modelAndView = new ModelAndView("/category/delete");
//            modelAndView.addObject("category", category);
//            return modelAndView;
//        }else {
//            ModelAndView modelAndView = new ModelAndView("/error404");
//            return modelAndView;
//        }
//    }
//
//    @PostMapping("/delete")
//    public String deleteCategory(@ModelAttribute("category")Category category) {
//        categoryService.delete(category.getId());
//        return "redirect:/categories";
//    }
//
//    @GetMapping("/view/{id}")
//    public ModelAndView viewCategory(@PathVariable("id") Long id) {
//        Category category = categoryService.findById(id);
//        if (category == null) {
//            return new ModelAndView("/error404");
//        }
//        Iterable<Blog> blogs = blogService.findAllByCategory(category);
//        ModelAndView modelAndView = new ModelAndView("/category/view");
//        modelAndView.addObject("category", category);
//        modelAndView.addObject("blogs", blogs);
//        return modelAndView;
//    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<Category>(category, HttpStatus.OK);
        }
    }


}
