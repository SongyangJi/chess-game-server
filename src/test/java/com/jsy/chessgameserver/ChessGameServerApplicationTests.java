package com.jsy.chessgameserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jsy.chessgameserver.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.Serializable;

@SpringBootTest
class ChessGameServerApplicationTests {


    static class Student {
        int id;
        String name;

        public Student(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    void contextLoads() throws IOException {
//        Student student = new Student(1, "张三");
//        String s;
//        System.out.println(s =JsonUtil.stringfy(student));
//        System.out.println(JsonUtil.parse(s));

    }

}
