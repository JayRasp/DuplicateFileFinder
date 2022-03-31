# DuplicateFileFinder
## ⚠️Important Note
This is a little personal project I started that helps find duplicate files across directories/drives.
<br/><br/>
It's still __WIP__ and advise to :warning:__NOT USE__:warning: it in its current state since it only compares file size and file names to determine wether a file is a duplicate or not, so it could very likely show files as duplicate that are in fact not!
<br/><br/>
I plan to improve the comparision of files so that it only shows duplicates by checking the files content.
<br/>
## Features
### Implemented:
 - Find duplicate files
 - Opening files by selecting an item, then double clicking or pressing enter.
 - Delete files (able to choose which to delete - original or duplicate)
### Planned:
 - White-/Blacklist file formats
 - White-/Blacklist drives and/or folders
 - Store scanned file properties in a BST for faster duplicate detection
 - Progressbar (__current file index / filecount__)
 - Estimate time remaining (__average time per file * remaining files__)
 - More accurate file comparison
