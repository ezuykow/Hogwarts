package ru.hogwarts.school.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.models.Avatar;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;

    @Value("${path.to.avatars}")
    private String avatarsDir;

    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public Optional<Collection<Avatar>> getAvatars(Integer page, Integer size) {
        PageRequest pr = PageRequest.of(page-1, size);
        return Optional.of(avatarRepository.findAll(pr).getContent());
    }

    public boolean uploadStudentAvatar(long studentId, MultipartFile avatarFile) throws IOException {
        Optional<Student> studentOptional = studentService.getStudentById(studentId);

        if (studentOptional.isEmpty()) {
            return false;
        }
        Student student = studentOptional.get();

        Path filePath = Path.of(avatarsDir, studentId + "." + student.getName()
                + getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024))
        {
            bis.transferTo(bos);
        }

        Avatar avatar = findByStudentId(studentId);
        avatar.setStudent(student);
        avatar.setFileSize(avatarFile.getSize());
        avatar.setFilePath(filePath.toString());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        return true;
    }

    public Optional<Avatar> findAvatar(long id) {
        return avatarRepository.findAvatarByStudentId(id);
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Avatar findByStudentId(long studentId) {
        return avatarRepository.findAvatarByStudentId(studentId).orElse(new Avatar());
    }
}
