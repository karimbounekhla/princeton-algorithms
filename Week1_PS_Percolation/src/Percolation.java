import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
		private WeightedQuickUnionUF grid;
		private WeightedQuickUnionUF gridFullCheck;
		private int N;
		private int numOpenSites;
		private int topIndex;
		private int bottomIndex;
		
		private boolean[] openGrid;
		
		public Percolation(int N) {
			this.N = N;
			this.grid = new WeightedQuickUnionUF(N*N+2);
			this.gridFullCheck = new WeightedQuickUnionUF(N*N+1);
			this.openGrid = new boolean[N*N];
			this.setNumOpenSites(0);
			
			this.topIndex = N*N;
			this.bottomIndex = N*N+1;
		}
		
		public void open(int row, int col) {
			if (!isValid(row, col) || isOpen(row, col)) {
				return;
			}
			
			int idx = getIndex(row, col);
			this.openGrid[idx] = true;
			this.setNumOpenSites(this.getNumOpenSites() + 1);
			
			// Check if in first and last row
			if (row == 1) {
				this.grid.union(idx, topIndex);
				this.gridFullCheck.union(idx, topIndex);
			}
			if (row == this.N) {
				this.grid.union(idx, bottomIndex);
			}
			
			// Check left right down up
			if (isValid(row, col - 1) && isOpen(row, col - 1)) {
				int toConnect = getIndex(row, col - 1);
				this.grid.union(idx, toConnect);
				this.gridFullCheck.union(idx, toConnect);
			}
			if (isValid(row, col + 1) && isOpen(row, col + 1)) {
				int toConnect = getIndex(row, col + 1);
				this.grid.union(idx, toConnect);
				this.gridFullCheck.union(idx, toConnect);
			}
			if (isValid(row - 1, col) && isOpen(row - 1, col)) {
				int toConnect = getIndex(row - 1, col);
				this.grid.union(idx, toConnect);
				this.gridFullCheck.union(idx, toConnect);
			}
			if (isValid(row + 1, col) && isOpen(row + 1, col)) {
				int toConnect = getIndex(row + 1, col);
				this.grid.union(idx, toConnect);
				this.gridFullCheck.union(idx, toConnect);
			}
		}
		
		public boolean isOpen(int row, int col) {
			if (!isValid(row, col)) {
				return false;
			}
			int idx = getIndex(row, col);
			return this.openGrid[idx];
		}
		
		public boolean isFull(int row, int col) {
			if (!isValid(row, col)) {
				return false;
			}
			int idx = getIndex(row, col);
			return this.gridFullCheck.connected(idx, topIndex);
		}
		
		public boolean percolates() {
			return this.grid.connected(topIndex, bottomIndex);
		}
		
		private int getIndex(int row, int col) {
			return (N*(row - 1) + col) - 1;
		}
		
		private boolean isValid(int row, int col) {
			return row > 0 && row <= N && col > 0 && col <= N;
		}
		
		public static void main(String [] args) {
			Percolation p = new Percolation(5);
			p.open(1, 1);
			p.open(2, 1);
			p.open(3, 1);
			p.open(4, 1);
			p.open(4, 2);
			p.open(4, 3);
			p.open(5, 3);
			System.out.println(p.percolates());
		}

		public int getNumOpenSites() {
			return numOpenSites;
		}

		public void setNumOpenSites(int numOpenSites) {
			this.numOpenSites = numOpenSites;
		}
		
}
