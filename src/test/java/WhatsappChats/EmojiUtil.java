package WhatsappChats;

import java.util.HashMap;
import java.util.Map;

public class EmojiUtil {

	private static final Map<String, String> emojiMap = new HashMap<>();

    static {
        // Basic set of emojis (add more as you need)
        emojiMap.put("😊", "\uD83D\uDE0A"); // Smiling face
        emojiMap.put("😂", "\uD83D\uDE02"); // Face with tears of joy
        emojiMap.put("😍", "\uD83D\uDE0D"); // Heart eyes
        emojiMap.put("👍", "\uD83D\uDC4D"); // Thumbs up
        emojiMap.put("❤️", "\u2764\uFE0F"); // Red heart
        emojiMap.put("💬", "\uD83D\uDCAC"); // Speech balloon
        emojiMap.put("😎", "\uD83D\uDE0E"); // Cool face
        emojiMap.put("🤔", "\uD83E\uDD14"); // Thinking face
        emojiMap.put("😢", "\uD83D\uDE22"); // Crying face
        emojiMap.put("🎉", "\uD83C\uDF89"); // Party popper
        emojiMap.put("🔥", "\uD83D\uDD25"); // Fire
        emojiMap.put("⭐", "\u2B50");       // Star
        emojiMap.put("😡", "\uD83D\uDE21"); // Angry face
        emojiMap.put("🙏", "\uD83D\uDE4F"); // Folded hands
        emojiMap.put("🥳", "\uD83E\uDD73"); // Partying face
        emojiMap.put("😴", "\uD83D\uDE34"); // Sleeping face
        emojiMap.put("🤗", "\uD83E\uDD17"); // Hugging face
        emojiMap.put("😇", "\uD83D\uDE07"); // Smiling face with halo
        emojiMap.put("😅", "\uD83D\uDE05"); // Smiling face sweat
        emojiMap.put("🤩", "\uD83E\uDD29"); // Starry eyes
    }

    /**
     * Converts a text with emojis into safe Unicode escapes
     * usable in Selenium/ChromeDriver.
     */
    public static String safeEmoji(String text) {
        for (Map.Entry<String, String> entry : emojiMap.entrySet()) {
            text = text.replace(entry.getValue(), entry.getKey());
        }
        return text;
    }
    
    public static void main(String[] args) {
		String msg = "Hey there! 😊  Just hangin' out, ready to chat with you! What's up? 💬";
    	String msg1 = EmojiUtil.safeEmoji(msg);
    	
    	System.out.println(msg1);
	}
}
