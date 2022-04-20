package com.system.management.library.librarymanagementsystem.views;

import com.system.management.library.librarymanagementsystem.MainView;
import com.system.management.library.librarymanagementsystem.dto.BookDto;
import com.system.management.library.librarymanagementsystem.service.BookService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Library")
@Route(value = "library", layout = MainView.class)
public class LibraryView extends HorizontalLayout {

    final BookService bookService;
    final Grid<BookDto> grid;

    public LibraryView(final BookService bookService) {
        this.grid = new Grid<>(BookDto.class);
        this.bookService = bookService;

        setMargin(true);

        if (bookService.getAllBooks().isEmpty()) {

            final Div div = new Div();
            div
                    .getElement()
                    .setProperty("innerHTML",
                            "<h2>No records</h2><br><p>Use <i><b>Add new record</i></b> to fill this space.</p>");

            add(div);
            setSizeFull();
            setJustifyContentMode(JustifyContentMode.CENTER);
            getStyle().set("text-align", "center");
        } else {
            add(grid);
            listBooks();
        }
    }

    private void listBooks() {
        grid.setItems(bookService.getAllBooks());
    }
}
