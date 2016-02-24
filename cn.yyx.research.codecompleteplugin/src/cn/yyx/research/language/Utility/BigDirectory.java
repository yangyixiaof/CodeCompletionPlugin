package cn.yyx.research.language.Utility;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigDirectory {

	File mBigFileDirectory = null;
	int nowmax = -1;
	
	public static String PrefixDirectory  = "BigDirectory";
	
	public static final int OneFileMaxMB = 100;

	public BigDirectory(String bigfiledir) throws Exception {
		mBigFileDirectory = new File(PrefixDirectory.equals("")?"":(PrefixDirectory + "/" + bigfiledir));
		if (!mBigFileDirectory.exists()) {
			mBigFileDirectory.mkdirs();
		}
		File[] files = mBigFileDirectory.listFiles();
		int rescheck = CheackAllFileName(files);
		if (rescheck == -1)
		{
			throw new Exception("The directory which is used to put BigFiles is not well formatted. It has some files not with correct filename format.");
		}
		nowmax = rescheck;
	}

	private int CheackAllFileName(File[] files) {
		if (files == null || files.length == 0)
		{
			return 1;
		}
		Pattern pattern = Pattern.compile("([0-9]+).txt");
		int max = -1;
		for (File file : files) {
			Matcher matcher = pattern.matcher(file.getName());
			boolean b = matcher.matches();
			if (!b)
			{
				return -1;
			}
			int seq = Integer.parseInt(matcher.group(1));
			if (max < seq)
			{
				max = seq;
			}
		}
		return max;
	}
	
	public void AppendOneContentToTheBigFile(String content) {
		String separator = File.separator;
		File currfile = new File(mBigFileDirectory.getAbsolutePath() + separator + nowmax+".txt");
		if (!currfile.exists())
		{
			try {
				currfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		long tfbytes = content.length()*2;
		double tfMB = tfbytes/1048576.0;
		long bytes = currfile.length();
		double MB = bytes/1048576.0;
		BigFile bf = new BigFile(currfile);
		bf.AppendOneContentToTheBigFile(content);
		if (MB+tfMB > OneFileMaxMB)
		{
			nowmax++;
			File f = new File(mBigFileDirectory.getAbsolutePath() + separator + nowmax+".txt");
			if (!f.exists())
			{
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void AppendOneFileToTheBigFile(File tf) {
		String separator = File.separator;
		File currfile = new File(mBigFileDirectory.getAbsolutePath() + separator + nowmax+".txt");
		if (!currfile.exists())
		{
			try {
				currfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		long tfbytes = tf.length();
		double tfMB = tfbytes/1048576.0;
		long bytes = currfile.length();
		double MB = bytes/1048576.0;
		BigFile bf = new BigFile(currfile);
		bf.AppendOneFileToTheBigFile(tf);
		if (MB+tfMB > OneFileMaxMB)
		{
			nowmax++;
			File f = new File(mBigFileDirectory.getAbsolutePath() + separator + nowmax+".txt");
			if (!f.exists())
			{
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
	}

}