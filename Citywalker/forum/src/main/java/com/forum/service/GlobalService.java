package com.forum.service;

import com.forum.controller.request.ContactRequest;
import com.forum.controller.response.SentenceResponse;
import org.springframework.web.multipart.MultipartFile;

public interface GlobalService {

    String uploadImage(MultipartFile file);

    SentenceResponse getOneSentence(String type);

    Boolean contact(ContactRequest contactRequest);
}
