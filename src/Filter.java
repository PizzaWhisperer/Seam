
/**
 * @author Mathilde Aliénor RAYNAL
 */
public final class Filter {

	/**
	 * Get a pixel without accessing out of bounds
	 * @param gray a HxW float array
	 * @param row Y coordinate
	 * @param col X coordinate
	 * @return nearest valid pixel color
	 */
	public static float at(float[][] gray, int row, int col) {

		if (row < 0) row = 0;
		if (row > gray.length - 1) row = gray.length - 1;
		if (col < 0) col = 0;
		if (col > gray[0].length - 1) col = gray[0].length - 1;

		return (gray[row][col]);
	}

	/**
	 * Convolve a single-channel image with specified kernel.
	 * @param gray a HxW float array
	 * @param kernel a MxN float array, with M and N odd
	 * @return a HxW float array
	 */
	public static float[][] filter(float[][] gray, float[][] kernel) {

		float[][] array = new float [gray.length][gray[0].length];	

		float somme = 0f;
		for (int i = 0; i < gray.length; i++){
			for (int j = 0; j < gray[0].length; j ++){

				for (int a = 0;a < kernel.length; a++){
					for(int b = 0; b < kernel[0].length; b++){

						somme = somme + (at(kernel,a,b) * at(gray, i + a - 1, j + b - 1));
					}
				}
				array[i][j] = somme;
				somme = 0f;
			}
		}
		return array;
	}

	/**
	 * Smooth a single-channel image
	 * @param gray a HxW float array
	 * @return a HxW float array
	 */
	public static float[][] smooth(float[][] gray) {

		float[][] array = new float [gray.length][gray[0].length];	

		float[][]a = {
				{.1f, .1f, .1f},
				{.1f, .2f, .1f},
				{.1f, .1f, .1f}
		};
		array = filter(gray, a);

		return array;
	}

	/**
	 * Compute horizontal Sobel filter
	 * @param gray a HxW float array
	 * @return a HxW float array
	 */
	public static float[][] sobelX(float[][] gray) {

		float[][] sobelX = new float [gray.length][gray[0].length];	

		float[][]filtreX =  { {-1.0f, 0f, 1.0f}, 
							{-2.0f, 0f, 2.0f}, 
							{-1.0f, 0f, 1.0f} };
		
		sobelX = filter(gray, filtreX);

		return sobelX;
	}

	/**
	 * Compute vertical Sobel filter
	 * @param gray a HxW float array
	 * @return a HxW float array
	 */
	public static float[][] sobelY(float[][] gray) {

		float[][] sobelY = new float [gray.length][gray[0].length];	

		float [][]filtreY =  {{-1, -2, -1}, 
							 {0, 0, 0}, 
							 {1, 2, 1} };
		
		sobelY = filter(gray, filtreY);

		return sobelY;
	}

	/**
	 * Compute the magnitude of combined Sobel filters
	 * @param gray a HxW float array
	 * @return a HxW float array
	 */

	public static float[][] sobel(float[][] gray) {

		float[][] sobelx = new float [gray.length][gray[0].length];
    	sobelx = sobelX(gray);
    	
    	float[][] sobely = new float [gray.length][gray[0].length];
    	sobely = sobelY(gray);
    	
    	float[][] sobel = new float [gray.length][gray[0].length];
    	
    	for (int i = 0; i < gray.length; i++) {
    		for ( int j = 0; j < gray[0].length; j++) {
    			sobel[i][j] = (float)Math.sqrt((float) Math.pow(sobelx[i][j], 2.0) + (float) Math.pow(sobely[i][j], 2.0));
    		}
    	}
    	
        return sobel;
	}
}
