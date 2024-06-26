package com.example.menu_planner.service.impl;

import com.example.menu_planner.exception.ForbiddenException;
import com.example.menu_planner.exception.NotFoundException;
import com.example.menu_planner.exception.UnauthorizedException;
import com.example.menu_planner.exception.WrongData;
import com.example.menu_planner.model.dtoInput.TagRequest;
import com.example.menu_planner.model.entity.Category;
import com.example.menu_planner.model.entity.Ingridient;
import com.example.menu_planner.model.entity.Tag;
import com.example.menu_planner.model.util.JwtTokenUtils;
import com.example.menu_planner.repository.DeletedTokensRepository;
import com.example.menu_planner.repository.TagRepository;
import com.example.menu_planner.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final DeletedTokensRepository deletedTokensRepository;

@SneakyThrows
    public Tag createTag(@Valid TagRequest tag, Authentication authentication,String token) {
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }
        if (tagRepository.findByName(tag.name()).isPresent()){
            throw new WrongData("This tag exists in DB");
        }
        Tag newTag = Tag.of(null, tag.name());
        return tagRepository.save(newTag);
    }

    @SneakyThrows
    public Tag getTagById(@Valid UUID tag_id, Authentication authentication, String token){
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }
        Tag tag = tagRepository.findById(tag_id).orElseThrow(() -> new NotFoundException("Tag not found"));
        return tag;
    }

    @SneakyThrows
    public List<Tag> getTagAll(Authentication authentication, String token){
        if (deletedTokensRepository.findById(token).isPresent()){
            throw new UnauthorizedException();
        }
        List<Tag> tagList = tagRepository.findAll();
        return tagList;
    }

}
