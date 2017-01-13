package com.book.library.web.controller;

import com.book.library.model.Category;
import com.book.library.service.CategoryService;
import com.book.library.service.BookService;
import com.book.library.web.FlashMessage;
import com.book.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    // Home page - index of all Books
    @RequestMapping("/")
    public String listbooks(Model model) {
        // TODO: Get all books
        List<Book> books = bookService.findAll();

        model.addAttribute("books", books);
        return "book/index";
    }

    // Single book page
    @RequestMapping("/books/{bookId}")
    public String bookDetails(@PathVariable Long bookId, Model model) {
        // TODO: Get book whose id is bookId
        Book book = bookService.findById(bookId);

        model.addAttribute("book", book);
        return "book/details";
    }

    // Favorites - index of all books marked favorite
    @RequestMapping("/favorites")
    public String favorites(Model model) {
        // TODO: Get list of all books marked as favorite
        List<Book> faves = new ArrayList<>();

        model.addAttribute("books",faves);
        return "book/favorites";
    }

    // Upload a new book
    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public String addbook(Book book, RedirectAttributes redirectAttribute) {
        // TODO: Upload new book if data is valid
        bookService.save(book);

        //add a flash message for success
        redirectAttribute.addFlashAttribute("flash", new FlashMessage("Book successfully uploaded", FlashMessage.Status.SUCCESS));
        // TODO: Redirect browser to new book's detail view
        return String.format("redirect:/books/%s", book.getId());
    }

    // Form for uploading a new book
    @RequestMapping("/upload")
    public String formNewbook(Model model) {
        // TODO: Add model attributes needed for new book upload form
        if(!model.containsAttribute("book")){
            model.addAttribute("book",new Book());
        }

        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("action", "/books");
        model.addAttribute("heading", "Add");
        model.addAttribute("submit", "Add");

        return "book/form";
    }

    // Form for editing an existing book
    @RequestMapping(value = "/books/{bookId}/edit")
    public String formEditbook(@PathVariable Long bookId, Model model) {
        if(!model.containsAttribute("book")){
            model.addAttribute("book", bookService.findById(bookId));
        }

        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("action", String.format("/books/%s", bookId));
        model.addAttribute("heading", "Edit book");
        model.addAttribute("submit", "Update");

        return "book/form";
    }

    // Update an existing book
    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.POST)
    public String updatebook(@Valid Book book, BindingResult result, RedirectAttributes redirectAttributes) {
        // TODO: Update category if valid data was received
        if (result.hasErrors()) {
            // Include validation errors upon redirect
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", result);

            // Add  category if invalid was received
            redirectAttributes.addFlashAttribute("book", book);

            // Redirect back to the form
            return String.format("redirect:/categories/%s/edit", book.getId());
        }

        bookService.save(book);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Category successfully updated!", FlashMessage.Status.SUCCESS));

        return "redirect:/";
    }
    // Delete an existing book
    @RequestMapping(value = "/books/{bookId}/delete", method = RequestMethod.POST)
    public String deletebook(@PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        Book book = bookService.findById(bookId);

        // TODO: Delete the book whose id is bookId
        bookService.delete(book);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Book deleted!", FlashMessage.Status.SUCCESS));

        // TODO: Redirect to app root
        return "redirect:/";
    }

    // Mark/unmark an existing book as a favorite
    @RequestMapping(value = "/books/{bookId}/favorite", method = RequestMethod.POST)
    public String toggleFavorite(@PathVariable Long bookId, HttpServletRequest request) {
        // TODO: With book whose id is bookId, toggle the favorite field
        Book book = bookService.findById(bookId);
        bookService.toggleFavorite(book);
        // TODO: Redirect to book's detail view

        return String.format("redirect:%s", request.getHeader("referrer"));
    }

    // Search results
    @RequestMapping("/search")
    public String searchResults(@RequestParam String q, Model model) {
        // TODO: Get list of books whose description contains value specified by q
        List<Book> books = new ArrayList<>();

        model.addAttribute("books", books);
        return "book/index";
    }
}