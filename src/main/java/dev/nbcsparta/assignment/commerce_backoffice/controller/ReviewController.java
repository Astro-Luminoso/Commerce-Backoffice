package dev.nbcsparta.assignment.commerce_backoffice.controller;

import dev.nbcsparta.assignment.commerce_backoffice.service.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

}
