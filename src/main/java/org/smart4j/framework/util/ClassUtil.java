package org.smart4j.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @author jinwei
 *
 */
public final class ClassUtil {
	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

	/**
	 * 获得类加载器
	 * 
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 加载类
	 * 
	 * @param className
	 * @param isInitialized
	 * @return
	 */
	public static Class<?> loadClass(String className, boolean isInitialized) {
		Class<?> cls;
		try {
			cls = Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			logger.error("加载类出错！", e);
			throw new RuntimeException(e);
		}
		return cls;
	}

	/**
	 * 加载类（将自动初始化）
	 */
	public static Class<?> loadClass(String className) {
		return loadClass(className, true);
	}

	/**
	 * 是否为 int 类型（包括 Integer 类型）
	 */
	public static boolean isInt(Class<?> type) {
		return type.equals(int.class) || type.equals(Integer.class);
	}

	/**
	 * 是否为 long 类型（包括 Long 类型）
	 */
	public static boolean isLong(Class<?> type) {
		return type.equals(long.class) || type.equals(Long.class);
	}

	/**
	 * 是否为 double 类型（包括 Double 类型）
	 */
	public static boolean isDouble(Class<?> type) {
		return type.equals(double.class) || type.equals(Double.class);
	}

	/**
	 * 是否为 String 类型
	 */
	public static boolean isString(Class<?> type) {
		return type.equals(String.class);
	}

	/**
	 * 找到所有基础包下的class
	 * 
	 * @param packageName
	 * @return
	 */
	public static Set<Class<?>> getClassSet(String packageName) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll(".", "/"));
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String protocol = url.getProtocol();
					if (protocol.equals("file")) {
						String packagePath = url.getPath().replaceAll("%20", "");
						addClass(classSet, packagePath, packageName);
					} else if (protocol.equals("jar")) {
						JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
						if (jarURLConnection != null) {
							JarFile jarFile = jarURLConnection.getJarFile();
							if (jarFile != null) {
								Enumeration<JarEntry> jarEntries = jarFile.entries();
								while (jarEntries.hasMoreElements()) {
									JarEntry jarEntry = jarEntries.nextElement();
									String jarEntryName = jarEntry.getName();
									if (jarEntryName.equals(".class")) {
										String className = jarEntryName.substring(0, jarEntryName.lastIndexOf("."))
												.replaceAll("/", ".");
										doAddClass(classSet, className);
									}
								}
							}
						}
					}

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("get classSet failure", e);
			throw new RuntimeException();
		}
		return classSet;

	}

	/**
	 * 
	 * @param classSet
	 * @param packagePath
	 * @param packageName
	 */
	private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				return file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
			}
		});
		for (File file : files) {
			String fileName = file.getName();
			if (file.isFile()) {
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (StringUtil.isNotEmpty(packageName)) {
					className = packageName + "." + className;
				}
				doAddClass(classSet, className);
			} else {
				String subPackagePath = fileName;
				if (StringUtil.isNotEmpty(packagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath;
				}
				String subPackageName = fileName;
				if (StringUtil.isNotEmpty(packageName)) {
					subPackageName = packageName + "/" + subPackageName;
				}
				addClass(classSet, subPackagePath, subPackageName);
			}
		}

	}

	/**
	 * 
	 * @param classSet
	 * @param className
	 */
	private static void doAddClass(Set<Class<?>> classSet, String className) {
		Class<?> clazz = loadClass(className, false);
		classSet.add(clazz);

	}
}
