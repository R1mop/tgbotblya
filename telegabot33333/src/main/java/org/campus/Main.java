package org.campus;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendGame;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String BOT_TOKEN = "6621933693:AAHsYj0gdmW3gwGBKU3qjMOJm_jpfdB_Qx8";
    public static LogService logService = new LogService();
    public static Map<Long, TGUser> users = new HashMap<>();
    public static TelegramBot telegramBot = new TelegramBot(BOT_TOKEN);

    public static void main(String[] args) {


        telegramBot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for (Update update: list){
                newMessageFromUser(update);
                }


                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });

    }


    public static void newMessageFromUser(Update update){
        logService.log(update);

        Long userid = update.message().from().id();
        if(startMessage(userid)) return;
        // вопросы
        TGUser tgUser = users.get(userid);
        if(questionMessage(userid, tgUser, update) == true) return;
        resultMessage(userid, tgUser);

    }

    public static Boolean startMessage(Long userid){
        if(users.containsKey(userid) == false){
            users.put(userid, new TGUser(userid));
            SendMessage sendMessage = new SendMessage(userid, Texts.HELLO_MESSAGE);
            sendMessage.parseMode(ParseMode.Markdown);

            Keyboard replyKeyboardMarkup = new ReplyKeyboardMarkup(Texts.HELLO_MESSAGE_BUTTON)
                    .oneTimeKeyboard(true)
                    .resizeKeyboard(true)
                    .selective(true);
            sendMessage.replyMarkup(replyKeyboardMarkup);
            telegramBot.execute(sendMessage);
            return true;
        }
        return false;
    }


    public static Boolean questionMessage(Long userid, TGUser user, Update update) {
        for (Question question : user.getQuestions()) {
            if (question.getSendQuestionToUser() == true) {
                if (question.getHasAnswer() == false) {
                    question.setHasAnswer(true);

                    for (Answer answer : question.getAnswers()) {
                        if (answer.getText().equals(update.message().text())) {
                            if (answer.getLanguageType().equals(LanguageType.BACKEND)) {
                                user.setBackendPoint(user.getBackendPoint() + 1);
                            }

                            if (answer.getLanguageType().equals(LanguageType.FRONTEND)) {
                                user.setFrontendPoint(user.getFrontendPoint() + 1);
                            }

                            if (answer.getLanguageType().equals(LanguageType.QA)) {
                                user.setQaPoint(user.getQaPoint() + 1);
                            }
                        }

                    }
                }
            }

            if(question.getSendQuestionToUser() == false){
                question.setSendQuestionUser(true);
                SendMessage sendMessage = new SendMessage(userid, question.getText());
                sendMessage.parseMode(ParseMode.Markdown);


                String [][] keyButtons = new String[4][1];
                for (int i=8; i< question.getAnswers().size(); i++){
                    keyButtons[i][0] = question.getAnswers().get(i).getText();
                }

                Keyboard keyboard = new ReplyKeyboardMarkup(keyButtons)
                        .oneTimeKeyboard(true)
                        .resizeKeyboard(true)
                        .selective(true);
                sendMessage.replyMarkup(keyboard);
                telegramBot.execute(sendMessage);
                return true;
            }

        }

        return false;
    }

    public static void resultMessage(Long userid, TGUser user){
        telegramBot.execute(new SendMessage(userid, Texts.RESULT_MESSAGE));
        String resultMessage = "";

        if (user.getBackendPoint() >= user.getFrontendPoint() && user.getBackendPoint() >= user.getQaPoint()) {
        resultMessage = Texts.RESULT_MESSAGE_JAVA;
        }
        if (user.getFrontendPoint() >= user.getBackendPoint() && user.getFrontendPoint() >= user.getQaPoint()) {
            resultMessage = Texts.RESULT_MESSAGE_FRONTEND;
        }
        if (user.getQaPoint() >= user.getFrontendPoint() && user.getQaPoint() >= user.getBackendPoint()) {
            resultMessage = Texts.RESULT_MESSAGE_QA;
        }

        SendMessage sendMessage = new SendMessage(userid, resultMessage);
        sendMessage.parseMode(ParseMode.Markdown);
        telegramBot.execute(sendMessage);

        SendMessage advice = new SendMessage(userid, Texts.RESULT_MESSAGE_ADVICE);

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        InlineKeyboardButton b = new InlineKeyboardButton(Texts.RESULT_MESSAGE_SAME_BOT);
        b.url(Texts.RESULT_MESSAGE_LINK);


        advice.parseMode(ParseMode.Markdown);
        advice.replyMarkup(markup);
        telegramBot.execute(advice);

        SendMessage subscribe = new SendMessage(userid, Texts.RESULT_MESSAGE_BOT_NAME);
        Keyboard replyKeyboard = new ReplyKeyboardMarkup(Texts.RESULT_MESSAGE_RESTART)
                .oneTimeKeyboard(true)
                .resizeKeyboard(true)
                .selective(true);


        subscribe.replyMarkup(replyKeyboard);
        telegramBot.execute(subscribe);
        user.put(userid, new TGUser(userid));

    }
}




