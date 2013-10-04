package ff14repbouyomi;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;

public class JsonChat {
	private static final Gson gson = new GsonBuilder()
			.excludeFieldsWithoutExposeAnnotation().create();

	public static JsonChat build(String str) throws JsonSyntaxException {
		return gson.fromJson(str, JsonChat.class);
	}

    public static String toJson(JsonChat chat) throws JsonSyntaxException {
        return gson.toJson(chat, JsonChat.class);
    }

	@Expose
	public String text;
	@Expose
	public String translate;
	@Expose
	public List<String> using;
}
