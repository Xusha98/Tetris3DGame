package game;

import maths.Vector3f;
import models.ModelEntity;
import models.TexturedModel;

public class Help extends Game {

	static ModelEntity help_view;

	public static void init() {

		TexturedModel helpModel = new TexturedModel(new float[] { -10.0f, 10.0f, 0, // TOP LEFT V0
				10.0f, 10.0f, 0, // TOP RIGHT V1
				10.0f, -10.0f, 0, // BOTTOM RIGHT V2
				-10.0f, -10.0f, 0 },
				new float[] { 0, 0, // TOP LEFT V0
						1, 0, // TOP RIGHT V1
						1, 1, // BOTTOM RIGHT V2
						0, 1 },
				new int[] { 0, 1, 2, // Triangle 1
						2, 3, 0 // Triangle 2
				}, "helpBackground.png");

		help_view = new ModelEntity(helpModel, new Vector3f(7.5f, 22, 3.0f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)); // 7.5,
																															// 20,
																															// -1
																															// //9.0f,
																															// 25,
																															// -8.0f

	}

	public static void render() {

		renderer.renderModelEntity(help_view);

	}
}
