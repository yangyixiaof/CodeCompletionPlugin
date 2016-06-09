package cn.yyx.contentassist.specification;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.launching.JavaRuntime;

public class OmnipotentClassLoader {

	private static List<IJavaProject> javaProjects = null;
	private static List<URLClassLoader> loaders = null;
	
	public static final int MaxTryTime = 3;
	
	public static Class<?> LoadClass(String classfullname) throws Exception {
		Class<?> cls = null;
		int tt = 0;
		StringBuilder sb = new StringBuilder(classfullname);
		int lastdotidx = classfullname.length();
		while (tt < MaxTryTime && cls == null)
		{
			cls = LoadClassDetail(classfullname);
			lastdotidx--;
			lastdotidx = classfullname.lastIndexOf('.', lastdotidx);
			if (lastdotidx < 0)
			{
				break;
			}
			sb.setCharAt(lastdotidx, '$');
			classfullname = sb.toString();
		}
		return cls;
	}

	private static Class<?> LoadClassDetail(String classfullname) throws Exception {
		Class<?> cls = null;
		cls = LoadPrimitiveClassAndVoidClass(classfullname);
		if (cls != null)
		{
			return cls;
		}
		try {
			cls = Class.forName(classfullname);
		} catch (ClassNotFoundException e) {
			try {
				cls = ComplexLoadClass(classfullname);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw e1;
			}
		}
		return cls;
	}
	
	private static Class<?> LoadPrimitiveClassAndVoidClass(String clsname)
	{
		if (clsname.equals("void"))
		{
			return void.class;
		}
		if (clsname.equals("float"))
		{
			return float.class;
		}
		if (clsname.equals("double"))
		{
			return double.class;
		}
		if (clsname.equals("boolean"))
		{
			return boolean.class;
		}
		if (clsname.equals("byte"))
		{
			return byte.class;
		}
		if (clsname.equals("short"))
		{
			return short.class;
		}
		if (clsname.equals("int"))
		{
			return int.class;
		}
		if (clsname.equals("long"))
		{
			return long.class;
		}
		if (clsname.equals("char"))
		{
			return char.class;
		}
		return null;
	}

	private static Class<?> ComplexLoadClass(String classfullname) throws Exception {
		FirstInitialzie();
		return MultipleLoadClass(classfullname);
	}

	private static Class<?> MultipleLoadClass(String classfullname) {
		for (URLClassLoader loader : loaders) {
			try {
				Class<?> clazz = loader.loadClass(classfullname);
				return clazz;
			} catch (ClassNotFoundException e) {
			}
		}
		return null;
	}

	private static void FirstInitialzie() throws Exception {
		if (javaProjects == null) { // || projects.length != javaProjects.size()
			javaProjects = new LinkedList<IJavaProject>();
			IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			for (IProject project : projects) {
				try {
					project.open(null /* IProgressMonitor */);
				} catch (CoreException e) {
					System.err.println("Project open false.");
					e.printStackTrace();
					System.exit(1);
				}
				IJavaProject javaProject = JavaCore.create(project);
				javaProjects.add(javaProject);
			}
			GenerateAllLoaders(true);
		}
	}

	private static void GenerateAllLoaders(boolean regenerate) throws Exception {
		if (regenerate || loaders == null) {
			if (javaProjects == null)
			{
				throw new Exception("there is no java projects in this workspace.");
			}
			loaders = new ArrayList<URLClassLoader>();
			for (IJavaProject javaProject : javaProjects) {
				loaders.add(GetProjectClassLoader(javaProject));
			}
		}
	}

	private static URLClassLoader GetProjectClassLoader(IJavaProject project)
			throws CoreException, MalformedURLException {
		String[] classPathEntries = JavaRuntime.computeDefaultRuntimeClassPath(project);
		List<URL> urlList = new ArrayList<URL>();
		for (int i = 0; i < classPathEntries.length; i++) {
			String entry = classPathEntries[i];
			IPath path = new Path(entry);
			URL url = path.toFile().toURI().toURL();
			urlList.add(url);
		}
		ClassLoader parentClassLoader = project.getClass().getClassLoader();
		URL[] urls = (URL[]) urlList.toArray(new URL[urlList.size()]);
		URLClassLoader classLoader = new URLClassLoader(urls, parentClassLoader);
		return classLoader;
	}

}