# This program shows a number of the longest sequence of zeros in binary representation of the integer entered at the console.
# For example, number 9 has binary representation 1001 and contains a binary gap of length 2.
# The number 529 has binary representation 1000010001 and contains two binary gaps: one of length 4 and one of length 3.
# The number 20 has binary representation 10100 and contains one binary gap of length 1.
# The number 15 has binary representation 1111 and has no binary gaps. The number 32 has binary representation 100000
# and has no binary gaps.
print("Enter an integer number here:")
a=input()
print(a)
a=bin(int(a))[2:]
print(a)
gap=0
max=0
one=False
for x in range(0, len(a)):
	print(a[x])
	if a[x]=="1":
		print("found 1")
		if gap>max and not one:
			max=gap
		one=True
		gap=0
	else:
		gap=gap+1
		print("Gap is "+str(gap))
		if(one):
			one=False
print(a)
print("Max Gap is " +str(max))

	
