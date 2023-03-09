package ru.hogwarts.school.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.entities.Avatar;
import ru.hogwarts.school.services.AvatarService;

import java.util.Collection;

@RestController
@RequestMapping("avatar")
public class AvatarController {

    private AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping
    public ResponseEntity<Collection<Avatar>> getAvatars(@RequestParam("page") Integer page,
                                                         @RequestParam("size") Integer size) {
        return ResponseEntity.of(avatarService.getAvatars(page, size));
    }
}
