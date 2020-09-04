/*

Given a 2D grid, each cell is either a zombie 1 or a human 0. Zombies can turn adjacent (up/down/left/right) human beings into zombies every hour. Find out how many hours does it take to infect all humans?

Example:

Input:
[[0, 1, 1, 0, 1],
 [0, 1, 0, 1, 0],
 [0, 0, 0, 0, 1],
 [0, 1, 0, 0, 0]]

Output: 2

Explanation:
At the end of the 1st hour, the status of the grid:
[[1, 1, 1, 1, 1],
 [1, 1, 1, 1, 1],
 [0, 1, 0, 1, 1],
 [1, 1, 1, 0, 1]]

At the end of the 2nd hour, the status of the grid:
[[1, 1, 1, 1, 1],
 [1, 1, 1, 1, 1],
 [1, 1, 1, 1, 1],
 [1, 1, 1, 1, 1]]
int minHours(int rows, int columns, List<List<Integer>> grid) {
	// todo
}

Rotten Oranges
https://leetcode.com/problems/rotting-oranges/

In a given grid, each cell can have one of three values:

the value 0 representing an empty cell;
the value 1 representing a fresh orange;
the value 2 representing a rotten orange.
Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible, return -1 instead.

 

Example 1:



Input: [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
Example 2:

Input: [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
Example 3:

Input: [[0,2]]
Output: 0
Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
 

Note:

1 <= grid.length <= 10
1 <= grid[0].length <= 10
grid[i][j] is only 0, 1, or 2.

https://leetcode.com/discuss/interview-question/411357/

Walls and Gates

https://leetcode.com/discuss/interview-question/411357/

https://leetcode.com/problems/walls-and-gates/

https://leetcode.com/problems/rotting-oranges/

*/


import java.util.*;

class ZombieSpread {
    public static void main(String[] args) {
        ArrayList<List> list = new ArrayList<List>();
        list.add(Arrays.asList(0, 1, 1, 0, 1));
        list.add(Arrays.asList(0, 1, 0, 1, 0));
        list.add(Arrays.asList(0, 0, 0, 0, 1));
        list.add(Arrays.asList(0, 1, 0, 0, 0));
        ZombieSpread z = new ZombieSpread();
        int result = z.calculateHours(list);
        System.out.println(result);
    }

    public int calculateHours(List<List> grid) {
        int m = grid.size();
        int n = grid.get(0).size();
        int zombies = 0;
        int hours = 0;
        int total = m * n;
        int[][] dirs = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    queue.offer(new int[] { i, j });
                    zombies++;
                }
            }
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (zombies == total)
                return hours;
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                for (int[] dir : dirs) {
                    int newRow = current[0] + dir[0];
                    int newCol = current[1] + dir[1];
                    if (newRow < 0 || newRow >= m || newCol < 0 || newCol >= n || grid.get(newRow).get(newCol) == 1)
                        continue;

                    queue.offer(new int[] { newRow, newCol });
                    zombies++;
                    grid.get(newRow).set(newCol, 1);
                }
            }
            hours++;
        }
        return -1;
    }
}




public class ZombieSpread {
    public static void main(String[] args) {
        ArrayList<List<Integer>> list = new ArrayList<List<Integer>>();
        list.add(Arrays.asList(0, 1, 1, 0, 1));
        list.add(Arrays.asList(0, 1, 0, 1, 0));
        list.add(Arrays.asList(0, 0, 0, 0, 1));
        list.add(Arrays.asList(0, 1, 0, 0, 0));
        ZombieSpread z = new ZombieSpread();
        int result = z.calculateHours(list);
        System.out.println(result);
    }

    public int calculateHours(List<List<Integer>> grid) {
        int m = grid.size();
        int n = grid.get(0).size();
        int[][] dirs = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

        Queue<int[]> q = new LinkedList<>();

        int target = m * n; // toal no of zombies
        int zombies = 0, hours = 0;

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                if (grid.get(i).get(j) == 1) {
                    q.offer(new int[] { i, j });
                    zombies++;
                }

        while (!q.isEmpty()) {
            int size = q.size();
            if (zombies == target)
                return hours; // when all are zombies

            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for (int[] d : dirs) {
                    // check neighbours
                    int newRow = cur[0] + d[0];
                    int newCol = cur[1] + d[1];
                    if (newRow < 0 || newRow >= m || newCol < 0 || newCol >= n || grid.get(newRow).get(newCol) == 1)
                        continue;

                    zombies++;
                    q.offer(new int[] { newRow, newCol });
                    grid.get(newRow).set(newCol, 1);
                }
            }
            hours++;
        }
        return -1;
    }

}

    // https://leetcode.com/playground/cibcSFae

    public int HumanToZombie(List<List> grid) {

        int column = grid.get(0).size();
        int row = grid.size();
        int humans = 0;
        int hours = 0;
        Queue<int[]> q = new LinkedList<int[]>();
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++) {
                if (grid.get(i).get(j) == 1) {
                    q.add(new int[] { i, j });
                }
                if (grid.get(i).get(j) == 0) {
                    humans++;
                }
            }
        int[][] directions = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

        while (!q.isEmpty() && humans > 0) {
            hours++;
            int qSize = q.size();
            for (int i = 0; i < qSize; i++) {
                int[] zombiegrid = q.poll();

                for (int[] direction : directions) {
                    int x = zombiegrid[0] + direction[0];
                    int y = zombiegrid[1] + direction[1];

                    if (!(x < 0 || y < 0 || x >= row || y >= column || grid.get(x).get(y) == 1)) {
                        humans--;
                        grid.get(x).remove(y);
                        grid.get(x).add(y, 1);
                        q.add(new int[] { x, y });
                    }
                } // end of directions
            } // end of qsize
        } // end of while

        if (humans == 0)
            return hours;

        return -1;
}
