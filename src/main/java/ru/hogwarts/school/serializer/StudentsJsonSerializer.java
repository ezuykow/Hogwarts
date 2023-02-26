package ru.hogwarts.school.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.hogwarts.school.models.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StudentsJsonSerializer extends StdSerializer<Set<Student>> {

    public StudentsJsonSerializer() {
        this(null);
    }

    protected StudentsJsonSerializer(Class<Set<Student>> t) {
        super(t);
    }

    @Override
    public void serialize(Set<Student> students, JsonGenerator gen, SerializerProvider provider) throws IOException {
        List<Long> ids = new ArrayList<>();
        for (Student s : students) {
            ids.add(s.getId());
        }
        gen.writeObject(ids);
    }
}
