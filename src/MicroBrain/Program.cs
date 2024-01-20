using System.Text.Json.Serialization;
using OpenAI;

var builder = WebApplication.CreateBuilder(args);
var config = builder.Configuration;
var services = builder.Services;

config.SetBasePath(AppContext.BaseDirectory)
         .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
         .AddEnvironmentVariables();

services.AddEndpointsApiExplorer();
services.AddSwaggerGen();
services.AddHttpClient("GPTClient", c => { });
services.AddControllers()
        .AddJsonOptions(options => options.JsonSerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles);
services.AddEndpointsApiExplorer();
services.AddHttpContextAccessor();
services.AddCors(cors =>
{
    cors.AddDefaultPolicy(options =>
    {
        options.AllowAnyHeader()
            .AllowAnyOrigin()
            .AllowAnyMethod();
    });
});
services.AddScoped<OpenAIClient>((provider) =>
{
    var httpClient = provider.GetRequiredService<IHttpClientFactory>().CreateClient("GPTClient");

    string? openAIKey = config["AiToken"];
    if (string.IsNullOrEmpty(openAIKey))
    {
        throw new ArgumentNullException(nameof(openAIKey), "OpenAIKey is null or empty");
    }

    return new OpenAIClient(openAIKey, client: httpClient);
});

var app = builder.Build();

app.MapControllers();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.Run();
