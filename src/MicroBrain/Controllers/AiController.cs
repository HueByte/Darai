using Microsoft.AspNetCore.Mvc;
using OpenAI;
using OpenAI.Chat;
using OpenAI.Models;

namespace MicroBrain.Controllers;

public record class AiMessage(string Role, string Message);
public record class TalkExchange(List<AiMessage> Messages);
public record class AiAnswer(List<AiMessage> Messages);

public class AiController : BaseApiController
{
    private readonly OpenAIClient _aiClient;

    public AiController(OpenAIClient aiClient)
    {
        _aiClient = aiClient;
    }

    [HttpPost]
    [ProducesResponseType<AiAnswer>(200)]
    public async Task<ActionResult<AiAnswer>> Talk([FromBody] TalkExchange request)
    {
        List<Message> messages = new();
        foreach (var message in request.Messages)
        {
            messages.Add(new Message(GetMessageRole(message.Role), message.Message));
        }

        var chatRequest = new ChatRequest(messages, Model.GPT3_5_Turbo, maxTokens: 2000);
        var response = await _aiClient.ChatEndpoint.GetCompletionAsync(chatRequest);
        var choice = response.FirstChoice;

        request.Messages.Add(new AiMessage("AI", choice));

        return Ok(request);
    }

    private Role GetMessageRole(string value) => value switch
    {
        "User" => Role.User,
        "System" => Role.System,
        "AI" => Role.Assistant,
        _ => throw new ArgumentException($"Invalid value {value}", nameof(value))
    };
}