package com.example.GastroProject.controller;


import com.example.GastroProject.dto.SymptomDto;

import com.example.GastroProject.entity.Patient;

import com.example.GastroProject.repository.PatientRepository;

import com.example.GastroProject.service.SymptomService;
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
public class SymptomController {

    private final SymptomService symptomService;
    private final PatientRepository patientRepository;

    @GetMapping("/home")
    public String userPage() {
        return "user";
    }


    @GetMapping("/all-symptoms")
    public String showAllSymptoms(Model model,
                                  @RequestParam(required = false) String keyword,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate selectedDate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Patient patient = patientRepository.findByEmail(userEmail);

        List<SymptomDto> userSymptoms = symptomService.findByPatientAndKeywordAndDate(patient, keyword,selectedDate);
        model.addAttribute("symptoms", Objects.requireNonNullElseGet(userSymptoms, ArrayList::new));

        return "all-symptoms";
    }


    @GetMapping("/add-symptom")
    public String showSymptomForm(Model model) {
        SymptomDto symptomDto = new SymptomDto();
        model.addAttribute("symptom", symptomDto);
        return "add-symptom";
    }

    @PostMapping("/add-symptom")
    public String addSymptom(@ModelAttribute("symptom") SymptomDto symptomDto, Principal principal) {
        symptomService.addSymptom(symptomDto, principal.getName());
        return "redirect:/all-symptoms";
    }


    @GetMapping("/edit-symptom/{id}")
    public String showEditSymptomForm(@PathVariable Long id, Model model) {
        SymptomDto symptom = symptomService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid symptom Id:" + id));
        model.addAttribute("symptom", symptom);
        return "edit-symptom";
    }


    @PostMapping("/edit-symptom/{id}")
    public String updateSymptom(@PathVariable Long id, @ModelAttribute("symptom") SymptomDto updatedSymptom) {
        symptomService.updateSymptom(id,updatedSymptom);
        return "redirect:/all-symptoms";
    }


    @GetMapping("/delete-symptom/{id}")
    public String deleteSymptom(@PathVariable Long id) {
        symptomService.deleteSymptom(id);
        return "redirect:/all-symptoms";
    }

}
