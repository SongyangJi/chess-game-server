package com.jsy.chessgameserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jsy.chessgameserver.dto.ChatMessage;
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
    void contextLoads() {

    }

    @Test
    void testMessage() throws JsonProcessingException {
//        ChatMessage message = new ChatMessage("1","2");
//        System.out.println(JsonUtil.stringfy(message));
    }

}
