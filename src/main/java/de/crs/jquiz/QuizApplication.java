package de.crs.jquiz;

import java.util.Scanner;

public class QuizApplication {

    private enum State {
        BEGIN,
        QUESTION,
        ANSWER,
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("No quiz file specified!");
            return;
        } else if (args.length > 1) {
            System.out.println("Too many arguments!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        Quiz quiz = Quiz.fromFile(args[0]);
        State state = State.BEGIN;
        Question question = null;

        System.out.println("Press enter to start the quiz!");

        while (true) {
            scanner.nextLine();

            switch (state) {
                case BEGIN, ANSWER -> {
                    state = State.QUESTION;

                    if (quiz.hasNextQuestion()) {
                        question = quiz.nextQuestion();
                        System.out.println("Question: " + question.getQuestion() + "\n");
                        System.out.println("Press enter to show the answer...");
                    } else {
                        return;
                    }
                }
                case QUESTION -> {
                    state = State.ANSWER;
                    System.out.println("Answer: " + question.getAnswer() + "\n");

                    if (quiz.hasNextQuestion()) {
                        System.out.println("Press enter to show the next question...");
                    } else {
                        System.out.println("You completed the quiz! Press enter to exit...");
                    }
                }
            }
        }
    }
}