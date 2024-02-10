package com.example.reactiveweb.model;

import java.time.LocalDate;


public record BookRequest (String title,String author, LocalDate publishDate){
}
