package com.example.GastroProject.controller;

import com.example.GastroProject.dto.MealDto;
import com.example.GastroProject.entity.Patient;
import com.example.GastroProject.repository.PatientRepository;
import com.example.GastroProject.service.MealService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;
    private final PatientRepository patientRepository;

    @GetMapping("/home1")
    public String userPage() {
        return "user";
    }

    @GetMapping("/all-meals")
    public String showAllMeals(Model model,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
       Patient patient = patientRepository.findByEmail(email);
        List<MealDto> meals = mealService.findByPatientAndKeywordAndDate(patient, keyword,selectedDate);
        model.addAttribute("meals", Objects.requireNonNullElseGet(meals, ArrayList::new));
        return "all-meals";
    }


    @GetMapping("/add-meal")
    public String showMealForm(Model model) {
        MealDto mealDto = new MealDto();
        model.addAttribute("meal", mealDto);
        return "add-meal";
    }


    @PostMapping("/add-meal")
    public String addMeal(@ModelAttribute("meal") MealDto mealDto, Principal principal) {
        mealService.addMeal(mealDto, principal.getName());
        return "redirect:/all-meals";
    }

    @GetMapping("/edit-meal/{id}")
    public String showEditMealForm(@PathVariable Long id, Model model) {
        model.addAttribute("meal", mealService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid meal Id:" + id)));
        return "edit-meal";
    }


    @PostMapping("/edit-meal/{id}")
    public String updateMeal(@PathVariable Long id, @ModelAttribute("meal") MealDto updatedMeal) {
        mealService.updateMeal(id,updatedMeal);
        return "redirect:/all-meals";
    }

    @GetMapping("/delete-meal/{id}")
    public String deleteMeal(@PathVariable Long id) {
        mealService.deleteMeal(id);
        return "redirect:/all-meals";
    }
}
