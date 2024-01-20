package Darai.Models;

/**
 * GptResponse
 */
public class GptResponse {
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String text) {
        this.answer = text;
    }

    public GptResponse(String text) {
        this.answer = text;
    }
}