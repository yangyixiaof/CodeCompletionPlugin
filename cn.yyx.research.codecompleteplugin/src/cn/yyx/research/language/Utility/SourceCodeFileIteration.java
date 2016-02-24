package cn.yyx.research.language.Utility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import cn.yyx.research.language.JDTHelper.ProgramProcessor;
import cn.yyx.research.language.JDTHelper.SystemParameter;
import cn.yyx.research.language.simplified.JDTManager.ConflictASTNodeHashCodeError;

public class SourceCodeFileIteration {
	
	private static boolean canrun = false;
	
	private static int level = 0;
	private static int num = 0;
	private static int dnum = 0;
	
	public static void Initial() {
		canrun = true;
	}

	public static void IterateAllFilesAndWriteToOneBigFile(File f) {
		if (!canrun)
		{
			return;
		}
		level++;
		if (level >= 80)
		{
			System.err.println("What the fuck, the depth of directory is more than 80ï¼?");
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return;
		}
		if (f.exists()) {
			if (f.isDirectory()) {
				File[] files = f.listFiles();
				for (File a : files)
				{
					IterateAllFilesAndWriteToOneBigFile(a);
				}
			} else {
				String fname = f.getName();
				//  || fname.endsWith(".c") || fname.endsWith(".cpp") || fname.endsWith(".cc")
				if (fname.endsWith(".java"))
				{
					num++;
					System.out.println("Handling file : " + f.getAbsolutePath() + ";CurrentNum:"+num);
					try {
						long begin = System.currentTimeMillis();
						ArrayList<CorpusContentPair> corpus = null;
						int trytime = 0;
						boolean cancontinue = false;
						while (!cancontinue)
						{
							trytime++;
							try {
								corpus = ProgramProcessor.ProcessOneJavaFile(f);
								cancontinue = true;
							} catch (ConflictASTNodeHashCodeError e) {
								System.err.println("Conflict! try " + trytime + "st time. wait for 20s. Doing system gc in this period.");
								try {
									System.gc();
									Thread.sleep(20000 + (trytime-1)*10000);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								if (trytime >= SystemParameter.MaxTry)
								{
									System.err.println("Conflict! Have tried " + SystemParameter.MaxTry + " times. try stops and ignore this file :" + f.getAbsolutePath());
									throw e;
								}
							}
						}
						long end = System.currentTimeMillis();
						try {
							SleepTimeController.TImeSleep(begin, end);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						BigDirectoryManager.WriteCorpus(corpus);
						corpus = null;
					} catch (Exception e) {
						System.err.println("Not Parsed File or wrong parsed file, ignore it. It is exception.");
						e.printStackTrace();
						RecordWrongFile(f.getAbsolutePath());
						try {
							Thread.sleep(15000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					} catch (Error e) {
						System.err.println("Not Parsed File or wrong parsed file, ignore it. It is error.");
						e.printStackTrace();
						RecordWrongFile(f.getAbsolutePath());
						try {
							Thread.sleep(15000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		}
		level--;
	}
	
	private static void RecordWrongFile(String filepath)
	{
		File errorfile = new File("errorJavaFile.txt");
		if (!errorfile.exists())
		{
			try {
				errorfile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(errorfile, true));
			bw.write("Wrong File Path:" + filepath);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (IOException e2) {
			e2.printStackTrace();
			System.exit(1);
		}
	}

	public static void StopIterate() {
		canrun = false;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		SourceCodeFileIteration.level = level;
	}

	public static int getNum() {
		return num;
	}

	public static void setNum(int num) {
		SourceCodeFileIteration.num = num;
	}

	public static int getDnum() {
		return dnum;
	}

	public static void setDnum(int dnum) {
		SourceCodeFileIteration.dnum = dnum;
	}

}