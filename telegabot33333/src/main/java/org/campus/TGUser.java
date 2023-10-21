package org.campus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TGUser {
    private Long userid;
    private List<Question> questions =  new ArrayList<>();

    private Integer backendPoing = 0;
    private Integer frontendPoing = 0;
    private Integer qaPoing = 0;

    public TGUser(Long userid) {
        this.userid = userid;
        fillQuestions();
    }

    private void fillQuestions(){
        questions.add(new Question(Texts.Q1, Arrays.asList(
                new Answer(LanguageType.FRONTEND, Texts.Q1_A1),
                new Answer(LanguageType.FRONTEND, Texts.Q1_A2),
                new Answer(LanguageType.BACKEND, Texts.Q1_A3),
                new Answer(LanguageType.QA, Texts.Q1_A4)
        )));
        questions.add(new Question(Texts.Q2, Arrays.asList(
                new Answer(LanguageType.FRONTEND, Texts.Q2_A1),
                new Answer(LanguageType.BACKEND, Texts.Q2_A2),
                new Answer(LanguageType.QA, Texts.Q2_A3),
                new Answer(LanguageType.QA, Texts.Q2_A4)
        )));

        questions.add(new Question(Texts.Q3, Arrays.asList(
                new Answer(LanguageType.QA, Texts.Q3_A1),
                new Answer(LanguageType.FRONTEND, Texts.Q3_A2),
                new Answer(LanguageType.FRONTEND, Texts.Q3_A3),
                new Answer(LanguageType.BACKEND, Texts.Q3_A4)
        )));




        questions.add(new Question(Texts.Q4, Arrays.asList(
                new Answer(LanguageType.FRONTEND, Texts.Q4_A1),
                new Answer(LanguageType.FRONTEND, Texts.Q4_A2),
                new Answer(LanguageType.QA, Texts.Q4_A3),
                new Answer(LanguageType.BACKEND, Texts.Q4_A4)
        )));



        questions.add(new Question(Texts.Q5, Arrays.asList(
                new Answer(LanguageType.FRONTEND, Texts.Q5_A1),
                new Answer(LanguageType.QA, Texts.Q5_A2),
                new Answer(LanguageType.FRONTEND, Texts.Q5_A3),
                new Answer(LanguageType.QA, Texts.Q5_A4)
        )));
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getBackendPoint() {
        return backendPoing;
    }

    public void setBackendPoint(Integer backendPoing) {
        this.backendPoing = backendPoing;
    }

    public Integer getFrontendPoint() {
        return frontendPoing;
    }

    public void setFrontendPoint(Integer frontendPoing) {
        this.frontendPoing = frontendPoing;
    }

    public Integer getQaPoint() {
        return qaPoing;
    }

    public void setQaPoint(Integer qaPoing) {
        this.qaPoing = qaPoing;
    }

    public void put(Long userid, TGUser tgUser) {
    }
}
