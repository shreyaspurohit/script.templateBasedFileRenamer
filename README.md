Template based file renamer
===========================

This scala script can be used to rename files in batch based on input and output templates. This can be used to rename files for Plex in batch. Plex expects file names in a particular format. If the real file names are not as expected, then this script can be used to rename them.

Usage
-----

Lets say file names are in format "Friends Season 03 Episode 01 - The One with the Princess Leia Fantasy.avi" and we want this to be converted to the Plex format "Friends - S03E01 - The One with the Princess Leia Fantasy.avi".

The input file names transform to a common input template: 
	
	#SHOW#DELIM#SEASON#DELIM#SHOWNUM#DELIM#EPISODE#DELIM#EPNUM#DELIM#HYPHEN#DELIM#OTHER
	
Note that, #OTHER matches everything at the end. The template holders used above like "#SHOW", "#SEASON" etc can be anything but must start with '#'. 
The output file name transformation template will be:

	#SHOW#DELIM%-#DELIM%S#SHOWNUM%E#EPNUM#DELIM#HYPHEN#DELIM#OTHER

Note that I am reusing the holders from the input as necessary. If you need to insert new tokens or strings then use the '%' prefix. In the above example %E inserts the character 'E' at the specified location. 

The script takes input template, output transformation template, delimiter in template to real delimiter mapping, and directory to scan in that order. For the example above, the invocation will be:

	scala renamer.scala #SHOW#DELIM#SEASON#DELIM#SHOWNUM#DELIM#EPISODE#DELIM#EPNUM#DELIM#HYPHEN#DELIM#OTHER #SHOW#DELIM%-#DELIM%S#SHOWNUM%E#EPNUM#DELIM#HYPHEN#DELIM#OTHER #DELIM=" " G:/Shows/Friends/Season01/