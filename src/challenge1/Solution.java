package challenge1;

import java.io.BufferedReader;
import java.io.FileReader;

public class Solution {
	
	private static int[][] offsets = {{0,1}, {0,-1}, {1,0}, {-1,0}};
	
	public static void main(String[] args) throws Exception {
		//args = new String[]{"test_len_5_drop_8.txt"};
		args = new String[]{"redmart_input.txt"};
		
		// Assume input is good
		BufferedReader br = new BufferedReader(new FileReader(args[0]));
		String[] tokens = br.readLine().split(" ");
		int rows = Integer.parseInt(tokens[0]);
		int cols = Integer.parseInt(tokens[1]);
		
		int[][] matrix = new int[rows][cols];
		for(int i = 0; i < rows; i++) {
			String[] numbers = br.readLine().split(" ");
			for(int j = 0; j < cols; j ++) {
				matrix[i][j] = Integer.parseInt(numbers[j]);
			}
		}

		br.close();
		
		// Done reading input
		Solution solution = new Solution();
		solution.solve(matrix);
		//solution.printMatrix(matrix);
	}
	
	public void solve(int[][] matrix) {
        int rLimit = matrix.length;
        if(rLimit == 0)
            return;
        int cLimit = matrix[0].length;
        if(cLimit == 0)
            return;
        
        // decreasing path dp matrix
        int[][] dpMatrix = new int[rLimit][cLimit];
        // drop dp matrix
        int[][] dropMatrix = new int[rLimit][cLimit];
        
        // Compute
        for(int i = 0; i < dpMatrix.length; i ++) {
            for(int j = 0; j < dpMatrix[i].length; j++) {
                compute(matrix, dpMatrix, dropMatrix, rLimit, cLimit, i, j);
            }
        }
        
        int maxLength = 1;
        int maxDrop = 0;
        for(int i = 0; i < dpMatrix.length; i ++) {
            for(int j = 0; j < dpMatrix[i].length; j++) {
                if(dpMatrix[i][j] > maxLength) {
                	maxLength = dpMatrix[i][j];
                	maxDrop = dropMatrix[i][j];
                } else if (dpMatrix[i][j] == maxLength) {
                	maxDrop = (dropMatrix[i][j] > maxDrop) ? dropMatrix[i][j] : maxDrop;
                }
            }
        }
        
        System.out.println(maxLength);
        System.out.println(maxDrop);
        return ;
    }
    
    private int compute(int[][] matrix, int[][] dpMatrix, int[][] dropMatrix, int rLimit, int cLimit, int r, int c) {
        if(dpMatrix[r][c] == 0) {
            int max = 0;
            int dropOfMax = 0;
            for(int[] offset : offsets) {
                int rOffset = offset[0];
                int cOffset = offset[1];
                if(isInBound(rLimit, cLimit, r + rOffset, c + cOffset) && matrix[r + rOffset][c + cOffset] < matrix[r][c]) {
                    int temp = compute(matrix, dpMatrix, dropMatrix, rLimit, cLimit, r + rOffset, c + cOffset);
                    if(temp > max) {
                    	max = temp;
                    	dropOfMax = matrix[r][c] - matrix[r + rOffset][c + cOffset] + dropMatrix[r + rOffset][c + cOffset];
                    }
                }
            }
            dpMatrix[r][c] = max + 1;
            dropMatrix[r][c] = dropOfMax;
            //System.out.println("" + up + "," + down + "," + left + "," + right);
            //printMatrix(dpMatrix);
            //printMatrix(dropMatrix);
        }
        return dpMatrix[r][c];
    }
    
    private boolean isInBound(int rLimit, int cLimit, int r, int c) {
        return r >= 0 && r < rLimit && c >= 0 && c < cLimit;
    }
    
    private void printMatrix(int[][] matrix) {
        for (int[] x : matrix) {
           for (int y : x)
                System.out.print(y + " ");
           System.out.println();
        }
        System.out.println("---------");
    }
}
