import math
import os
import pylab
import glob
import sys

inFile = open(sys.argv[1])
maxDepth = -1

line1 = inFile.readline()

for line2 in inFile:
	v1 = line1.rstrip('\n').split(' ')
	v2 = line2.rstrip('\n').split(' ')

	print line1.rstrip('\n') + " 1"

	if v1[5] != v2[5] and float(v1[10]) < 600.0:
		depth = int(v1[4]) + 1

		if int(v1[3]) == 5:
			maxDepth = 25
		elif int(v1[3]) == 10:
			maxDepth = 11
		elif int(v1[3]) == 15:
			maxDepth = 8
		elif int(v1[3]) == 20:
			maxDepth = 7
		
		while (depth <= maxDepth):
			print v1[0] + " " + v1[1] + " " + v1[2] + " " + v1[3] + " " + str(depth) + " " + v1[5] + " " + v1[6] + " " + v1[7] + " " + v1[8] + " " + v1[9] + " " + v1[10] + " 0"
			depth = depth + 1
	
	line1 = line2
inFile.close()