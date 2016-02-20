Instructions for The Parser
------------------------------

1. Put STUDENT.csv files into the data/reports folder.
You can create subdirectories in the data/reports folder for your own organisation, the program will read inside the directories as well.
However this also mean that no irrelevant files are inside the data/reports folder.

2. Double click the run.bat file. 
If properties.csv doesn’t exist, it will be generated.
results.csv will be generated in the same place as well.


Notes
------------------------------
You can ignore the ‘app’ folder unless you wish to view/change the code.



properties.csv
------------------------------
This is where you can edit the result.csv output.

COLUMN:
The column number. Best to ensure that it goes in order (eg. 1,2,3,4,5...)

HEADER NAME:
The header name that you wish will appear in the result.csv output.

INFO FROM?:
The section that the information is from. Sections are divided by the "______________" lines in the [STUDENT].csv source files.

VALUE KEY:
The name of value that you wish to retrieve. (e.g. "Full Name"). It is sensitive to caps and space. "full name" or "FullName" will not work.

SPECIAL METHOD:
Columns where special computational methods are required to generate output will have filled-in special method column.
For example, I want it to output "25-30" where Age is "26". This requires programming logic and is handled by a special method.
These special methods are hardcoded into the program.
If you enter a value into the special method field in properties.csv and the entered special method does not exist in the program, the field will print the special method value.
For example, entering "-" in the special method field will output "-" in the result.csv output.