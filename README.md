# Algorithms Part 1 (Princeton)

[Introduction to Algorithms](https://coursera.org/learn/algorithms-part1), created by Princeton University.

This course covers algorithms and data structures, with emphasis on applications and performance analysis of Java implementations. 

Part I covers elementary data structures, sorting, and searching algorithms. Part II focuses on graph- and string-processing algorithms.

This repo contains my work in progress for Part 1 of the course - Part 2 coming soon.

## Assignment 1 - Percolation

This assignment explores the concept of percolation using a model composed of an n-by-n grid of sites. Each site being either `open` or `blocked`. A `full` site is an open site that can be connected to an open site in the top row. A system is said to **percolate** if there is a full site in the bottom row - i.e. it is connected to the top row.

![Percolation](img/percolation.png)

(From the problem statement)
```
Given a composite systems comprised of randomly distributed insulating and metallic materials: what fraction of the materials need to be metallic so that the composite system is an electrical conductor? Given a porous landscape with water on the surface (or oil below), under what conditions will the water be able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an abstract process known as percolation to model such situations.
```

The objective of this problem set was to determine the probability that a system will percolate if sites are independent and set to open with probability `p`. When `p` equals 0, the system does not percolate, and when `p` equals 1, the system percolates (all sites are open). When `n` is sufficiently large, there is a threshold probability `p*` at which the system will tend to percolate (around 0.593).  

![PercolationStats](img/percolation2.png)

In this program, I modelled the Percolation system (n-by-n grid) and ran a Monte Carlo simulation to estimate the percolation threshold using the Union-find algorithm. 

Results were consistent with the experimentally observed percolation threshold.

![Percolation Results](img/percolationresults.png)

