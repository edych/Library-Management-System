package com.system.management.library.librarymanagementsystem.views;

import com.system.management.library.librarymanagementsystem.MainView;
import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.service.BookService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Library")
@Route(value = "library", layout = MainView.class)
public class LibraryView extends HorizontalLayout {

    private final BookService bookService;
    private final Grid<BookDto> grid;
    private final Button addNewRecord = new Button("Add new record");

    public LibraryView(final BookService bookService) {
        this.grid = new Grid<>(BookDto.class);
        this.bookService = bookService;
        addClassName("addnewrecord-view");

        if (bookService.getAllBooks().isEmpty()) {
            final VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setAlignItems(Alignment.CENTER);
            verticalLayout.add(new H2("No records"), createButtonLayout());
            add(verticalLayout);
        } else {
            add(grid);
            add(createButtonLayout());
            listBooks();
        }

        addNewRecord.addClickListener(e -> addNewRecord.getUI().ifPresent(u -> u.navigate("add-new-record")));
    }

    private Component createButtonLayout() {
        final HorizontalLayout buttonLayout = new HorizontalLayout();
        addNewRecord.setIcon(new MainView.MenuItemInfo.LineAwesomeIcon("la la-plus"));
        buttonLayout.add(addNewRecord);
        return buttonLayout;
    }

    private void listBooks() {
        grid.setItems(bookService.getAllBooks());
    }
}
