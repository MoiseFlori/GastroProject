package com.example.GastroProject.service.impl;

import com.example.GastroProject.entity.Document;
import com.example.GastroProject.repository.DocumentRepository;
import com.example.GastroProject.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

private final DocumentRepository documentRepository;
    @Override
    public List<Document> getDocumentsByPatientId(Long patientId) {
        return documentRepository.findByPatientId(patientId);
    }


}
