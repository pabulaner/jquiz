package de.crs.jquiz;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Quiz {

    public static final String QUESTION_SEPARATOR = "\\$question\\$";
    public static final String ANSWER_SEPARATOR = "\\$answer\\$";

    private final List<Question> questions;

    public static Quiz fromFile(String path) {
        Quiz result = new Quiz();

        try (FileInputStream fis = new FileInputStream(path)) {
            String content = new String(fis.readAllBytes());
            String[] questions = content.split(QUESTION_SEPARATOR);

            // i starts at 1 to skip the first empty string as
            // every quiz starts with the QUESTION_SEPARATOR
            for (int i = 1; i < questions.length; i++) {
                String[] question = questions[i].split(ANSWER_SEPARATOR);

                if (question.length != 2) {
                    throw new RuntimeException("Invalid formed question in file '" + path  + "' at line " + (i + 1));
                }

                result.questions.add(new Question(question[0].trim(), question[1].trim()));
            }

            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Quiz() {
        questions = new ArrayList<>();
    }

    public void shuffle() {
        Collections.shuffle(questions);
    }

    public Quiz childQuiz(int maxQuestionCount) {
        Quiz result = new Quiz();

        for (int i = 0; i < questions.size() && i < maxQuestionCount; i++) {
            result.questions.add(questions.get(i));
        }

        return result;
    }

    public boolean hasNextQuestion() {
        return !questions.isEmpty();
    }

    public Question nextQuestion() {
        Question question = questions.get(0);
        questions.remove(0);

        return question;
    }
}
