package com.system.management.library.librarymanagementsystem.views;

import com.system.management.library.librarymanagementsystem.model.Book;
import com.system.management.library.librarymanagementsystem.repository.BookRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Library")
@Route(value = "library", layout = MainLayout.class)
public class LibraryView extends HorizontalLayout {

    private final BookRepository bookRepository;
    final Grid<Book> grid;

    public LibraryView(final BookRepository bookRepository) {

        this.grid = new Grid<>(Book.class);
        this.bookRepository = bookRepository;

        setMargin(true);

        if (bookRepository.findAll().isEmpty()) {

            Div div = new Div();
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
        grid.setItems(bookRepository.findAll());
    }
}
