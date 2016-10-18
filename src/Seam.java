
/**
 * @author John Doe
 */
public final class Seam {

	/**
	 * Compute shortest path between {@code from} and {@code to}
	 * @param successors : adjacency list for all vertices
	 * @param costs : weight for all vertices
	 * @param from : first vertex
	 * @param to : last vertex
	 * @return a sequence of vertices, or {@code null} if no path exists
	 */
	public static int[] path(int[][] successors, float[] costs, int from, int to) {
		
		float[] distance = new float[successors.length];
		int[] predecesseur = new int[successors.length];

		for (int v = 0; v < successors.length; v++) {
			distance[v] = Float.POSITIVE_INFINITY;
			predecesseur[v] = -1;
		}
		
		distance[from] = costs[from];
		boolean modified = true;

		while (modified) {
			modified = false;
			
			for (int v = 0; v < successors.length; v++) {
				for (int n = 0; n < successors[v].length; n++) {
					if (distance[successors[v][n]] > (distance[v] + costs[successors[v][n]])) {
						distance[successors[v][n]] = (distance[v] + costs[successors[v][n]]);
						modified = true;
						predecesseur[successors[v][n]] = v;
					}
				}
			}
		}
		int fin = to;
		int compteur = 1;
		
		// recuperation de la taille du tableau predecesseur
		while (fin != from) {
			fin = predecesseur[fin];
			compteur++;
		}

		int[] path = new int[compteur];
		path[compteur - 1] = to;
		
		//remontee du chemin pour obtenir les successeurs
		for (int i = 1; i < compteur; i++) {
			path[compteur - i - 1] = predecesseur[to];
			to = predecesseur[to];
		}
		return path;
	}

	/**
	 * Find best seam
	 * @param energy : weight for all pixels
	 * @return a sequence of x-coordinates (the y-coordinate is the index)
	 */
	public static int[] find(float[][] energy) {

		int width = energy[0].length;
		int height = energy.length;
		int nbPixel = width * height;

		int[][] successor = new int[nbPixel + 2][];
		float[] costs = new float[nbPixel + 2];

		successor[nbPixel + 1] = new int[] {};
		successor[nbPixel] = new int[width];

		for (int i = 0; i < energy.length; i++) {
			successor[nbPixel][i] = i;
		} // on attribue les predecesseur de la premiere ligne

		for (int i = 0; i < height; i++) {

			for (int j = 0; j < width; j++) {

				if (i == height - 1) // on attribue les successeurs de la derniere ligne
					successor[i * width + j] = new int[] { nbPixel + 1 };

				else if (j == 0) // on attribue les successeurs du bord gauche
					successor[i * width + j] = new int[] { ((i + 1) * width) + j, ((i + 1) * width) + (j + 1) };

				else if (j == width - 1) // on attribue les successeurs du bord droit
					successor[i * width + j] = new int[] { ((i + 1) * width) + (j - 1), ((i + 1) * width) + j };

				else 
					successor[i * width + j] = new int[] { ((i + 1) * width) + (j - 1), ((i + 1) * width) + j, ((i + 1) * width) + (j + 1) };

			}
		}
		costs[nbPixel + 1] = 0;
		costs[nbPixel] = 0;
		for (int i = 0; i < energy.length; i++) {
			for (int j = 0; j < energy[0].length; j++) {
				costs[(i * width) + j] = energy[i][j];
			}
		}

		int[] indice = path(successor, costs, width * height, width * height + 1);

		int[] chemin = new int[indice.length - 2];
		for (int j = 0; j < indice.length - 2; j++) {
			chemin[j] = indice[j + 1] - (j * width);
		}
		return chemin;
	}

	/**
	 * Draw a seam on an image
	 * @param image original image
	 * @param seam a seam on this image
	 * @return a new image with the seam in blue
	 */
	public static int[][] merge(int[][] image, int[] seam) {

		// Copy image
		int width = image[0].length;
		int height = image.length;
		int[][] copy = new int[height][width];
		for (int row = 0; row < height; ++row)
			for (int col = 0; col < width; ++col)
				copy[row][col] = image[row][col];

		// Paint seam in blue
		for (int row = 0; row < height; ++row)
			copy[row][seam[row]] = 0x0000ff;

		return copy;
	}

	/**
	 * Remove specified seam
	 * 
	 * @param image original image
	 * @param seam a seam on this image
	 * @return the new image (width is decreased by 1)
	 */
	public static int[][] shrink(int[][] image, int[] seam) {
		int width = image[0].length;
		int height = image.length;
		int[][] result = new int[height][width - 1];
		for (int row = 0; row < height; ++row) {
			for (int col = 0; col < seam[row]; ++col)
				result[row][col] = image[row][col];
			for (int col = seam[row] + 1; col < width; ++col)
				result[row][col - 1] = image[row][col];
		}
		return result;
	}

}
