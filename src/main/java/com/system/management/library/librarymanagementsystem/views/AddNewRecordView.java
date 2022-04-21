package com.system.management.library.librarymanagementsystem.views;

import com.system.management.library.librarymanagementsystem.MainView;
import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.service.BookService;
import com.system.management.library.librarymanagementsystem.validator.BookDtoValidator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.StatusChangeListener;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.validation.ValidationException;
import java.util.function.Consumer;

@PageTitle("Add new record")
@Route(value = "add-new-record", layout = MainView.class)
public class AddNewRecordView extends Div {

    private final BookService bookService;
    private final TextField author = new TextField("Author");
    private final TextField title = new TextField("Title");
    private final TextField isbn = new TextField("ISBN");
    private final Button add = new Button("Add");
    private final Binder<BookDto> binder = new Binder<>(BookDto.class);

    public AddNewRecordView(final BookService bookService, final BookDtoValidator bookDtoValidator) {
        this.bookService = bookService;

        addClassName("addnewrecord-view");
        add(createFormLayout());
        add(createButtonLayout());

        binder.forField(author)
                .withValidator(fieldValidator(bookDtoValidator::authorValidator))
                .bind(BookDto::getAuthor, BookDto::setAuthor);

        binder.forField(title)
                .withValidator(fieldValidator(bookDtoValidator::titleValidator))
                .bind(BookDto::getTitle, BookDto::setTitle);

        binder.forField(isbn)
                .withValidator(fieldValidator(bookDtoValidator::isbnValidator))
                .bind(BookDto::getIsbn, BookDto::setIsbn);

        binder.addStatusChangeListener((StatusChangeListener) event -> add.setEnabled(binder.isValid()));

        add.addClickListener(e -> {
            addButtonListener();
            add.getUI().ifPresent(u -> u.navigate(LibraryView.class));
        });
    }

    private Validator<String> fieldValidator(final Consumer<String> validatorFunction) {
        return (s, ctx) -> {
            try {
                validatorFunction.accept(s);
                return ValidationResult.ok();
            } catch (final ValidationException e) {
                return ValidationResult.error(e.getMessage());
            }
        };
    }

    private void addButtonListener() {
        try {
            final BookDto formDto = new BookDto();
            binder.writeBean(formDto);
            bookService.addBook(formDto, true);
            Notification.show("Book " + formDto.getTitle() + " stored.");
        } catch (final com.vaadin.flow.data.binder.ValidationException e) {
            Notification.show("Validation error when saving the book.");
            e.printStackTrace();
        } finally {
            clearForm();
        }
    }

    private Component createButtonLayout() {
        final HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        buttonLayout.add(add);
        return buttonLayout;
    }

    private Component createFormLayout() {
        final FormLayout formLayout = new FormLayout();
        formLayout.add(author, title, isbn);
        return formLayout;
    }

    private void clearForm() {
        binder.readBean(new BookDto());
    }
}
