import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;

/**
 * SimpleChatbot
 * A console-based Java chatbot that uses rule-based Natural Language Processing (NLP)
 * techniques for interactive communication and answering frequently asked questions.
 */
public class SimpleChatbot {

    // The knowledge base: A map storing keywords/phrases and corresponding responses.
    private static final Map<String, String> knowledgeBase = new HashMap<>();

    // Fallback responses when no pattern is matched
    private static final String[] FALLBACK_RESPONSES = {
        "That's interesting. Tell me more about that.",
        "I see. Can you elaborate on that topic?",
        "I'm not sure I understand. Could you rephrase your question?",
        "That's outside my current knowledge base. Try asking about my capabilities or the weather."
    };
    
    // Greeting and farewell responses
    private static final String[] GREETING_RESPONSES = {"Hello! How can I assist you today?", "Hi there! What's on your mind?", "Welcome! Ask me anything."};
    private static final String[] FAREWELL_RESPONSES = {"Goodbye! Have a great day.", "It was nice chatting with you. Farewell!", "See you later!"};

    private static final Random random = new Random();

    /**
     * Initializes the chatbot's knowledge base with trained FAQ and rules.
     */
    private static void initializeKnowledgeBase() {
        // --- FAQ Training (Specific Questions) ---
        knowledgeBase.put("capabilities", "I am a simple rule-based AI created in Java. I can answer questions based on my keyword patterns.");
        knowledgeBase.put("creator", "I was coded in Java using basic pattern matching logic.");
        knowledgeBase.put("java", "Java is a high-level, class-based, object-oriented programming language designed to have as few implementation dependencies as possible.");
        knowledgeBase.put("time", "I don't keep track of real-world time, but I am always here for you.");
        
        // --- Rule-Based NLP Simulation (Keyword Matching) ---
        knowledgeBase.put("hello", getRandomResponse(GREETING_RESPONSES));
        knowledgeBase.put("hi", getRandomResponse(GREETING_RESPONSES));
        knowledgeBase.put("how are you", "I am an AI, so I don't have feelings, but I'm operating perfectly!");
        knowledgeBase.put("weather", "I cannot check the live weather, but I hope it's sunny where you are!");
        knowledgeBase.put("name", "You can call me JavaBot.");
        knowledgeBase.put("help", "I can discuss Java, my basic functions, or just chat. Try 'What are your capabilities?'");
        knowledgeBase.put("thank you", "You're welcome! Do you have any other questions?");
    }

    /**
     * Finds a matching response from the knowledge base based on the user's input.
     * This simulates basic NLP by tokenizing and matching keywords/phrases.
     * * @param input The raw text input from the user.
     * @return A matching response string, or null if no match is found.
     */
    private static String getResponse(String input) {
        // 1. Preprocessing: Convert to lowercase and remove punctuation/special characters
        String cleanInput = input.toLowerCase().replaceAll("[^a-z0-9\\s]", "").trim();

        if (cleanInput.isEmpty()) {
            return "Please type something so we can chat!";
        }

        // 2. Exact Phrase Matching (for multi-word rules like "how are you")
        for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
            if (cleanInput.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        
        // 3. Keyword Matching (Tokenization)
        String[] words = cleanInput.split("\\s+");
        for (String word : words) {
            if (knowledgeBase.containsKey(word)) {
                return knowledgeBase.get(word);
            }
        }

        // 4. Check for exit commands
        if (cleanInput.contains("bye") || cleanInput.contains("exit") || cleanInput.contains("quit")) {
            return getRandomResponse(FAREWELL_RESPONSES);
        }

        // 5. No match found, use a fallback response
        return getRandomResponse(FALLBACK_RESPONSES);
    }
    
    /**
     * Retrieves a random response from a list of predefined options.
     * @param responses An array of possible response strings.
     * @return A randomly selected response.
     */
    private static String getRandomResponse(String[] responses) {
        return responses[random.nextInt(responses.length)];
    }

    /**
     * Main method to run the console-based interactive chatbot.
     */
    public static void main(String[] args) {
        initializeKnowledgeBase();
        Scanner scanner = new Scanner(System.in);
        String userInput;

        // Display welcome message and instructions
        System.out.println("=========================================");
        System.out.println("          JavaBot - AI Chatbot           ");
        System.out.println("=========================================");
        System.out.println("Hello! I am JavaBot. Ask me about Java, or try keywords like 'weather' or 'capabilities'.");
        System.out.println("Type 'bye' or 'exit' to end the conversation.\n");

        // Main interaction loop
        while (true) {
            System.out.print("You: ");
            userInput = scanner.nextLine();

            String response = getResponse(userInput);
            
            System.out.println("JavaBot: " + response);

            // Exit condition check (must match the responses defined in getResponse)
            boolean shouldExit = false;
            for (String farewell : FAREWELL_RESPONSES) {
                if (response.equals(farewell)) {
                    shouldExit = true;
                    break;
                }
            }
            if (shouldExit) {
                break;
            }
        }

        scanner.close();
    }
}
