SecurityGamesLimitSurveil:

Folders:
src - code is in this folder
scripts - script files that need to be copied to teamserver.usc.edu
dist - location of the jar file (this might change depending on the IDE that you use)

Main.java contains the different types of tests.  In main(String[] args), this contains calls to different
function calls to run different types of tests.

Steps to run:
1. Decide which test you want to run.
2. Compile the project (this should create a new SecurityGamesLimitSurveil.jar file in the dist folder)
3. Create a new folder in teamserver.usc.edu.  The username: teamcore, password: Te@mc0re.  Preferably first
create a new folder with your name.  Note that there is a file called Eric, which is where I store all my files.
4. Transfer all the files in the scripts folder to the new folder created within your subfolder.
5. In that same folder, transfer SecurityGamesLimitSurveil.jar.
6. Run the jar file by typing: java -jar SecurityGamesLimitSurveil.jar
7. The results should be in a csv file, something like outputFile*.csv