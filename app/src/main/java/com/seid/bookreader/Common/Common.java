package com.seid.bookreader.Common;

import com.seid.bookreader.Model.Book;
import com.seid.bookreader.Model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static List<Book> bookList =new ArrayList<>();
    public static Book bookSelected;
    public static List<Chapter> chapterList;
    public static Chapter chapterSelected;
    public static int chapterIndex=-1;

    public static String formatString(String name){

        return (name.length() > 15 ? name.substring(0, 15) + "..." : name);

    }
    public static String[] categories = {
            "Action",
            "Adult",
            "Adventure",
            "Comedy",
            "Cooking",
            "Science",
            "Drama"



    };

}
