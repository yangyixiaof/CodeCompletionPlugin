# CodeCompletionPlugin

The video link:  https://youtu.be/3p1mJWNX124

We assume that users have the codes waited to be trained. The codes which consists of Java files in different sub-directories are put in a folder. we call the path to that folder  "PathToSourceRepository".

We also assume that users create a empty folder to put the IR files, we call the path to that folder "PathToWhereIRIsPut".

Then users must download https://github.com/yangyixiaof/gitcrawler/tree/master/programprocessor. Users can either put the project into eclipse, or export it to jar in eclipse.

The normal way to use it is using the following command:
"java -jar programprocessor.jar"
After the program is up, you type the command in command line:
"start PathToSourceRepository PathToWhereIRIsPut"
Then the java files in PathToSourceRepository will be translated to IR which will be put in PathToWhereIRIsPut.

We recommend to use the project in eclipse directly. After importing the project into eclipse, find the main class cn.yyx.research.language.programprocessor.ProgramHandle, right click it to run as Java application.
type the command in console of eclipse:
"start PathToSourceRepository PathToWhereIRIsPut"
where "PathToSourceRepository" and "PathToWhereIRIsPut" have been defined before.

After IR is generated, we need to train the model. We use Srilm to train the model. To install Srilm, please refer to http://www.speech.sri.com/projects/srilm/. Do not forget to add the bin folder and the subfolder of bin folder of Srilm to your PATH environment.

Download the ngram-train-scripts folder in https://github.com/yangyixiaof/gitcrawler/tree/master/programprocessor/ngram-train-scripts

The script ngramCountSpanLineBreaks.sh is important. The script must be run in linux. Then cd to the path where you put the IR which we call "PathToWhereIRIsPut".

Create an empty directory with exact name 'ClassWorkSpace' in directory PathToWhereIRIsPut/BigClassDetail. Note that the directory "BigClassDetail" will be surely generated if all previous steps are ok. Copy the ngramCountSpanLineBreaks.sh to the newly created directory 'ClassWorkSpace'. You may need to add execution priviledge to that bash file using the following command: "chmod a+x ngramCountSpanLineBreaks.sh". Cd to the 'ClassWorkSpace' directory. Create a text file named with exact name 'path.txt'. In path.txt, you need to specify the files you want to analyse in directory PathToWhereIRIsPut/BigClassDetail. Then run command './ngramCountSpanLineBreaks.sh'.

Then a new folder named "results" will be generated in which the n-gram model is put. The path of directory 'results' will be needed in the program which is used to put the model into AeroSpike database. We call the path of directory "results" the name "PathToResults".

Before put the model into AeroSpike, an AeroSpike database must be running! To install and configure the AeroSpike database, pelease refer to www.aerospike.com. Note that a namespace (a database in my understanding) with name 'yyx' must be configured in /etc/aerospike/aerospike.conf.

Then dowkload the project from https://github.com/yangyixiaof/ModelIntoAeroSpike.
The normal way to use it is using the following command:
"java -jar ModelIntoAeroSpike.jar"
After the program is up, you type the command in command line:
"start PathToResults"
where "PathToResults" is the directory in which the m-gram model is put and has been defined before.

After that, everything is ok.

Download this plugin and install it as a common eclipse plugin. If you want a easy way, we recommand you import this project into Eclipse RCP and after the project is imported, select the project in Eclipse RCP, right click the mouse, select Run -> Run as Eclipse Platform. Then a new elipse with PCC installed will be running, you invoke the same hot key as the internal code completion to invoke PCC. The proposals returned by PCC will be at the end position of GUI of code completion. We do not influence the internal code completion, do not worry.
