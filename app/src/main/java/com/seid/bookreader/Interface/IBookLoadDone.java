package com.seid.bookreader.Interface;

import com.seid.bookreader.Model.Book;

import java.util.List;

public interface IBookLoadDone {
    void onBookLoadDoneListener(List<Book> bookList);
}
