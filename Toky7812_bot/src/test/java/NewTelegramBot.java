import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewTelegramBot extends TelegramLongPollingBot {
    private static final String botUserName = "Toky7812_bot";
    private static final String token = "6189408505:AAFwY3hOJw66eiMvFhOXLN7fjBnFZEX-0bU";
    public int lang_i = 0; // interface language as int / 0 - Ru, 1 - En
    public static int level_i = -1; // menu level
    private String msg_s;
    private String que_s;
    private Boolean ok_b = false;
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new NewTelegramBot());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()) {
            msg_s = update.getMessage().getText();
        }
        if(update.hasCallbackQuery()) {
            que_s = update.getCallbackQuery().getData().toString();
        }
        switch (level_i) {
            case 0: /** level 0 */
                if(que_s.equals("Ru")) { /** Russian language */
                    lang_i = 0;
                    ok_b = true;
                }
                if(que_s.equals("En")) { /** English language */
                    lang_i = 1;
                    ok_b = true;
                }
                if(que_s.equals("00")) { /** About, in Russian */
                   lang_i = 0;
                   ok_b = false;
                }
                if(que_s.equals("01")) { /** About, in English */
                    lang_i = 1;
                    ok_b = false;
                }
                if(ok_b) {
                    sendInLineKeyboard(update.getMessage().getChatId().toString(),3);
                } else {
                    /** to be added About message in Ru and En */
                }
            case 1:
                sendInLineKeyboard(update.getMessage().getChatId().toString(),3);
                break;
            case 2:

                break;
            default:
                sendCustomKeyboard(update.getMessage().getChatId().toString());

        }

        // We check if the update has a message and the message has text
        /**if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());

            try {
                execute(message); // Call method to send the message
                sendCustomKeyboard(message.setChatId(update.getMessage().getChatId().toString()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } */
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return token;
    }
    public void sendInLineKeyboard(String chatId, int rows_n) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Custom message text");

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setOneTimeKeyboard(true);
        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();
        // Set each button, you can also use KeyboardButton objects if you need something else than text
        row.add("Row 1 Button 1");
        row.add("Row 1 Button 2");
        row.add("Row 1 Button 3");
        // Add the first row to the keyboard
        keyboard.add(row);
        if (rows_n > 1) {
            // Create another keyboard row
            row = new KeyboardRow();
            // Set each button for the second line
            row.add("Row 2 Button 1");
            row.add("Row 2 Button 2");
            row.add("Row 2 Button 3");
            // Add the second row to the keyboard
            keyboard.add(row);
            if (rows_n > 2) {
                // Create another keyboard row
                row = new KeyboardRow();
                // Set each button for the second line
                row.add("Row 3 Button 1");
                row.add("Row 3 Button 2");
                row.add("Row 3 Button 3");
                // Add the second row to the keyboard
                keyboard.add(row);
            }
        }

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(keyboardMarkup);

        try {
            // Send the message
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    public void sendCustomKeyboard(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите язык общения \n" + "Select language");

        // Create InlineKeyboardMarkup object
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        // Create the keyboard (list of InlineKeyboardButton list)
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        // Create a list for buttons
        List<InlineKeyboardButton> Buttons = new ArrayList<InlineKeyboardButton>();
        // Initialize each button, the text must be written
        InlineKeyboardButton button1 = new InlineKeyboardButton("Русский");
        // Also must use exactly one of the optional fields,it can edit  by set method
        button1.setCallbackData("Ru");             //.setText("Русский");
        // Add button to the list
        Buttons.add(button1);
        // Initialize each button, the text must be written
        InlineKeyboardButton button2 = new InlineKeyboardButton("English");
        // Also must use exactly one of the optional fields,it can edit  by set method
        button2.setCallbackData("En");
        // hash2 = button2.hashCode();
        // Add button to the list
        Buttons.add(button2);
        keyboard.add(Buttons);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        // Add it to the message
        message.setReplyMarkup(inlineKeyboardMarkup);
        level_i = 0;
        try {
            // Send the message
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
