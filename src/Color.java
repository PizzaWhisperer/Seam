/**
 * @author Mathilde Aliénor RAYNAL

 */
public final class Color {

	/**
	 * Verifie si la variable curr est dans l'intervalle [0.0 ; 1.0f]
	 * @param a float value
	 * @return a float between 0.0 and 1.0
	 */
	public static float intervalle(float curr) {

		if (curr < 0.0)
			curr = 0.0f;
		else 
			if (curr > 1.0) 
				curr = 1.0f;

		return curr;
	}

	/**
	 * Returns red component from given packed color.
	 * @param rgb 32-bits RGB color
	 * @return a float between 0.0 and 1.0
	 * @see #getGreen
	 * @see #getBlue
	 * @see #getRGB(float, float, float)
	 */
	public static float getRed(int rgb) {

		int y = rgb >> 16;
		int z = y & 0xff;
		float red = z / 255.0f;

		return red;
	}

	/**
	 * Returns green component from given packed color.
	 * @param rgb 32-bits RGB color
	 * @return a float between 0.0 and 1.0
	 * @see #getRed
	 * @see #getBlue
	 * @see #getRGB(float, float, float)
	 */
	public static float getGreen(int rgb) {

		int y = rgb >> 8;
		int z = y & 0b11111111;
		float green = z / 255.0f;

		return green;
	}

	/**
	 * Returns blue component from given packed color.
	 * @param rgb 32-bits RGB color
	 * @return a float between 0.0 and 1.0
	 * @see #getRed
	 * @see #getGreen
	 * @see #getRGB(float, float, float)
	 */
	public static float getBlue(int rgb) {

		int z = rgb & 0b11111111;
		float blue = z / 255.0f;

		return blue;
	}

	/**
	 * Returns the average of red, green and blue components from given packed color.
	 * @param rgb 32-bits RGB color
	 * @return a float between 0.0 and 1.0
	 * @see #getRed
	 * @see #getGreen
	 * @see #getBlue
	 * @see #getRGB(float)
	 */
	public static float getGray(int rgb) {
		float x = getBlue(rgb);
		float y = getRed(rgb);
		float z = getGreen(rgb);

		x = intervalle(x);
		y = intervalle(y);
		z = intervalle(z);

		float gray = (x + y + z) / 3;

		gray = intervalle(gray);

		return gray;
	}

	/**
	 * Convertit un float en un int en binaire et l'ajoute à une base
	 * @param un float color : une composante de la couleur
	 * @param rgb : int sur lequel on va greffer une composante de la couleur
	 * @return un binaire avec la nouvelle composante
	 */
	public static int Conv(float color, int rgb) {
		float a = (int) color * 255.0f;
		int c = rgb << 8;
		c += a;
		return c;
	}

	/**
	 * Returns packed RGB components from given red, green and blue components.
	 * @param red a float between 0.0 and 1.0
	 * @param green a float between 0.0 and 1.0
	 * @param blue a float between 0.0 and 1.0
	 * @return 32-bits RGB color
	 * @see #getRed
	 * @see #getGreen
	 * @see #getBlue
	 */
	public static int getRGB(float red, float green, float blue) {
		
		int base = 0b00000000;
		
		int R = Conv(intervalle(red), base);
		int RG = Conv(intervalle(green), R);
		int RGB = Conv(intervalle(blue), RG);

		return RGB;

	}

	/**
	 * Returns packed RGB components from given grayscale value.
	 * @param red a float between 0.0 and 1.0
	 * @param green a float between 0.0 and 1.0
	 * @param blue a float between 0.0 and 1.0
	 * @return 32-bits RGB color
	 * @see #getGray
	 */
	public static int getRGB(float gray) {

		int base = 0b00000000;
		float a = intervalle(gray) * 255.0f;
		int b = (int) a;
		for (int i = 0; i < 3; i++) {
			base = base << 8;
			base += b;
		}

		return base;
	}

	/**
	 * Converts packed RGB image to grayscale float image.
	 * @param image a HxW int array
	 * @return a HxW float array
	 * @see #toRGB
	 * @see #getGray
	 */
	public static float[][] toGray(int[][] image) {
	
		int width = image[0].length;
		int height = image.length;
		float[][] gray = new float[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; ++j) {
				gray[i][j] = getGray(image[i][j]);

			}
		}

		return gray;
	}

	/**
	 * Converts grayscale float image to packed RGB image.
	 * @param channels a HxW float array
	 * @return a HxW int array
	 * @see #toGray
	 * @see #getRGB(float)
	 */
	public static int[][] toRGB(float[][] gray) {
		
		int width = gray[0].length;
		int height = gray.length;
		int[][] array = new int[height][width];
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; ++j) {
				array[i][j] = getRGB(gray[i][j]);

			}
		}
		return array;
	}
}