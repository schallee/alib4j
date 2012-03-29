package org.owasp.esapi.util;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Utilities to help with tests that involve files or directories.
 */
public class FileTestUtils
{
	private static final Class CLASS = FileTestUtils.class;
	private static final String CLASS_NAME = CLASS.getName();
	private static final String DEFAULT_PREFIX = CLASS_NAME + '.';
	private static final String DEFAULT_SUFFIX = ".tmp";
	private static final Random rand = new SecureRandom();
	private static final File[] EMPTY_FILE_ARRAY = new File[0];

	private String prefix;
	private String suffix;
	private File parent;
	private List<File> toDelete = null;

	/**
	 * Exception thrown when deletion of a temporary file fails.
	 */
	public static class DeletionFailedException extends IOException
	{
		private static final long serialVersionUID = 1l;
		private File[] failedFiles;

		/**
		 * @param failedFiles the files that failed to be deleted.
		 */
		public DeletionFailedException(File[] failedFiles)
		{
			super("Deletion of the following file"
				+ (failedFiles.length > 0 ? "s" : "")
				+ " failed: " + Arrays.toString(failedFiles));
			failedFiles = failedFiles;
		}

		/**
		 * @param failedFiles the files that failed to be deleted.
		 */
		public DeletionFailedException(Collection<File> failedFiles)
		{
			this(failedFiles.toArray(new File[failedFiles.size()]));
		}

		/**
		 * @param failedFile the file that failed to be deleted.
		 */
		public DeletionFailedException(File failedFile)
		{
			this(new File[]{failedFile});
		}

		/**
		 * Get an array of the files that failed to be deleted.
		 * @return Array of files that failed to be deleted.
		 */
		public File[] getFailedFiles()
		{
			return failedFiles;
		}
	}

	/**
	 * Equivalant to {@link #FileTestUtils(File,String,String)
	 * FileTestUtils(null,null,null)}
	 */
	public FileTestUtils()
	{
		this(null,null,null);
	}

	/**
	 * Create a FileTestUtils.
	 * @param parent The directory to create temp files. If this
	 * is null, {@link System.getProperties(String)
	 *	System.getProperties("java.io.tmpdir")} is used.
	 * @param prefix The file prefix for temp files. If this is null,
	 * 	"org.owasp.esapi.util.FileTestUtils" is used.
	 * @param suffix The suffix for temp files. If this is null, 
	 * 	"tmp" is used.
	 */
	public FileTestUtils(File parent, String prefix, String suffix)
	{
		if(parent == null)
			parent = new File(System.getProperty("java.io.tmpdir"));
		this.parent = parent;
		this.prefix = (prefix==null?DEFAULT_PREFIX:prefix);
		this.suffix = (suffix==null?DEFAULT_SUFFIX:suffix);
	}

	/**
	 * Equivalant to {@link #FileTestUtils(File,String,String)
	 * FileTestUtils(null,prefix,null)}
	 * @param prefix The file prefix for temp files. If this is null,
	 * 	"org.owasp.esapi.util.FileTestUtils" is used.
	 */
	public FileTestUtils(String prefix)
	{
		this(null,prefix, null);
	}

	/**
	 * Equivalant to {@link #FileTestUtils(File,String,String)
	 * FileTestUtils(null,cls.getName(),suffix)}
	 * @param cls The class whose name is used for the prefix.
	 * @param suffix The suffix for temp files. If this is null, 
	 * 	"tmp" is used.
	 * @throws NullPointerException if cls is null.
	 */
	public FileTestUtils(Class cls, String suffix)
	{
		this(null,cls.getName(),suffix);
	}

	/**
	 * Equivalant to {@link #FileTestUtils(File,String,String)
	 * FileTestUtils(null,cls.getName(),null)}
	 * @param cls The class whose name is used for the prefix.
	 * @throws NullPointerException if cls is null.
	 */
	public FileTestUtils(Class cls)
	{
		this(null,cls.getName(),null);
	}

	/**
	 * Add file to the list of files to be deleted when {@link
	 * #cleanup()} is called.
	 * @param file The file to add to the list
	 * @return file as a convinence
	 */
	public synchronized File addToDelete(File file)
	{
		if(toDelete == null)
			toDelete = new ArrayList<File>();
		toDelete.add(file);
		return file;
	}

	/**
	 * Remove all files created by this instance or added with {@link
	 * #addToDelete(File)}. Directories are recursively deleted.
	 * @throws IOException if listing directories and such does.
	 * @throws DeletionFailedException when deletion of a file
	 *	fails. The exception is thrown only after deletion of
	 * 	all files has been attempted.
	 */
	public synchronized void cleanup() throws IOException
	{
		try
		{
			deleteRecursivelyWithException(toDelete);
		}
		finally
		{
			toDelete = null;
		}
	}

	/**
	 * Create a temporary directory. The directory is also added to
	 * the list of files deleted by {@link #cleanup()}.
	 * @param parent The parent directory for the temporary
	 *	directory. If this is null, the system property
	 * 	"java.io.tmpdir" is used.
	 * @param prefix The prefix for the directory's name. If this
	 * 	is null, the full class name of this class is used.
	 * @param suffix The suffix for the directory's name. If this
	 * 	is null, ".tmp" is used.
	 * @return The newly created temporary directory.
	 * @throws IOException if directory creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createDirectory(File parent, String prefix, String suffix) throws IOException
	{
		return addToDelete(createTmpDirectory(parent, prefix, suffix));
	}

	/**
	 * Create a temporary directory. The directory is also added to
	 * the list of files deleted by {@link #cleanup()}. The directory
	 * is created in the directory specified in the constructor.
	 * @param prefix The prefix for the directory's name. If this
	 * 	is null, the full class name of this class is used.
	 * @param suffix The suffix for the directory's name. If this
	 * 	is null, ".tmp" is used.
	 * @return The newly created temporary directory.
	 * @throws IOException if directory creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createDirectory(String prefix, String suffix) throws IOException
	{
		return addToDelete(createTmpDirectory(parent, prefix, suffix));
	}

	/**
	 * Create a temporary directory. The directory is also added to
	 * the list of files deleted by {@link #cleanup()}. The directory
	 * is created in the directory specified in the constructor.
	 * @param cls The class whos name will be used for the prefix.
	 * @param suffix The suffix for the directory's name. If this
	 * 	is null, ".tmp" is used.
	 * @return The newly created temporary directory.
	 * @throws IOException if directory creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createDirectory(Class cls, String suffix) throws IOException
	{
		return addToDelete(createTmpDirectory(parent, cls.getName(), suffix));
	}

	/**
	 * Create a temporary directory. The directory is also added to
	 * the list of files deleted by {@link #cleanup()}. The directory
	 * is created in the directory specified in the
	 * constructor. Similarly, the suffix is the one specified there
	 * as well.
	 * @param cls The class whos name will be used for the prefix.
	 * @return The newly created temporary directory.
	 * @throws IOException if directory creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createDirectory(Class cls) throws IOException
	{
		return addToDelete(createTmpDirectory(parent, cls.getName(), suffix));
	}

	/**
	 * Create a temporary directory. The directory is also added to
	 * the list of files deleted by {@link #cleanup()}. The directory
	 * is created in the directory specified in the
	 * constructor. Similarly, the prefix and suffix are the ones
	 * specified there as well.
	 * @return The newly created temporary directory.
	 * @throws IOException if directory creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createDirectory() throws IOException
	{
		return addToDelete(createTmpDirectory(parent, prefix, suffix));
	}

	/**
	 * Create a temporary file. The file is also added to
	 * the list of files deleted by {@link #cleanup()}.
	 * @param parent The parent directory for the temporary
	 *	file. If this is null, the system property
	 * 	"java.io.tmpdir" is used.
	 * @param prefix The prefix for the file's name. If this
	 * 	is null, the full class name of this class is used.
	 * @param suffix The suffix for the file's name. If this
	 * 	is null, ".tmp" is used.
	 * @return The newly created temporary file.
	 * @throws IOException if file creation fails
	 * @throws SecurityException if {@link File#createTmpFile()}
	 * 	throws one.
	 */
	public static File createTmpFile(File parent, String prefix, String suffix) throws IOException
	{
		if(parent == null)
			parent = new File(System.getProperty("java.io.tmpdir"));
		if(prefix == null)
			prefix = DEFAULT_PREFIX;
		else if(prefix.charAt(prefix.length()-1) != '.')
			prefix += '.';
		if(suffix == null)
			suffix = DEFAULT_SUFFIX;
		else if(suffix.charAt(0) != '.')
			suffix = "." + suffix;
		return File.createTempFile(prefix,suffix,parent);
	}

	/**
	 * Create a temporary file. The file is also added to
	 * the list of files deleted by {@link #cleanup()}.
	 * @param parent The parent directory for the temporary
	 *	file. If this is null, the system property
	 * 	"java.io.tmpdir" is used.
	 * @param prefix The prefix for the file's name. If this
	 * 	is null, the full class name of this class is used.
	 * @param suffix The suffix for the file's name. If this
	 * 	is null, ".tmp" is used.
	 * @return The newly created temporary file.
	 * @throws IOException if file creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createFile(File parent, String prefix, String suffix) throws IOException
	{
		return addToDelete(createFile(parent,prefix,suffix));
	}

	/**
	 * Create a temporary file. The file is also added to
	 * the list of files deleted by {@link #cleanup()}. The
	 * parent directory for the temporary file is that specified
	 * at instantiation.
	 * @param prefix The prefix for the file's name. If this
	 * 	is null, the full class name of this class is used.
	 * @param suffix The suffix for the file's name. If this
	 * 	is null, ".tmp" is used.
	 * @return The newly created temporary file.
	 * @throws IOException if file creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createFile(String prefix, String suffix) throws IOException
	{
		return createFile(parent,prefix,suffix);
	}

	/**
	 * Create a temporary file. The file is also added to
	 * the list of files deleted by {@link #cleanup()}. The
	 * parent directory for the temporary file is that specified
	 * at instantiation.
	 * @param cls The class who's full name will be used for the
	 * 	temporary file's prefix.
	 * @param suffix The suffix for the file's name. If this
	 * 	is null, ".tmp" is used.
	 * @return The newly created temporary file.
	 * @throws IOException if file creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createFile(Class cls, String suffix) throws IOException
	{
		return createFile(parent,cls.getName(),suffix);
	}

	/**
	 * Create a temporary file. The file is also added to
	 * the list of files deleted by {@link #cleanup()}. The
	 * parent directory and suffix for the temporary file are that
	 * specified at instantiation.
	 * @param cls The class who's full name will be used for the
	 * 	temporary file's prefix.
	 * @return The newly created temporary file.
	 * @throws IOException if file creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createFile(Class cls) throws IOException
	{
		return createFile(cls.getName(),suffix);
	}

	/**
	 * Create a temporary file. The file is also added to
	 * the list of files deleted by {@link #cleanup()}. The
	 * parent directory, prefix and suffix for the temporary file
	 * are that specified at instantiation.
	 * @return The newly created temporary file.
	 * @throws IOException if file creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public File createFile() throws IOException
	{
		return createFile(parent,prefix,suffix);
	}

	/**
	 * Convert a long to it's hex representation. Unlike
	 * {@ Long#toHexString(long)} this always returns 16 digits.
	 * @param l The long to convert.
	 * @return l in hex.
	 */
	public static String toHexString(long l)
	{
		String initial;
		StringBuffer sb;

		initial = Long.toHexString(l);
		if(initial.length() == 16)
			return initial;
		sb = new StringBuffer(16);
		sb.append(initial);
		while(sb.length()<16)
			sb.insert(0,'0');
		return sb.toString();
	}

	/**
	 * Create a temporary directory.
	 * @param parent The parent directory for the temporary
	 *	directory. If this is null, the system property
	 * 	"java.io.tmpdir" is used.
	 * @param prefix The prefix for the directory's name. If this
	 * 	is null, the full class name of this class is used.
	 * @param suffix The suffix for the directory's name. If this
	 * 	is null, ".tmp" is used.
	 * @return The newly created temporary directory.
	 * @throws IOException if directory creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public static File createTmpDirectory(File parent, String prefix, String suffix) throws IOException
	{
		String name;
		File dir;

		if(prefix == null)
			prefix = DEFAULT_PREFIX;
		else if(!prefix.endsWith("."))
			prefix += '.';
		if(suffix == null)
			suffix = DEFAULT_SUFFIX;
		else if(!suffix.startsWith("."))
			suffix = "." + suffix;
		if(parent == null)
			parent = new File(System.getProperty("java.io.tmpdir"));
		name = prefix + toHexString(rand.nextLong()) + suffix;
		dir = new File(parent, name);
		if(!dir.mkdir())
			throw new IOException("Unable to create temporary directory " + dir);
		return dir.getCanonicalFile();
	}

	/**
	 * Create a temporary directory. This calls
	 * {@link #createTmpDirectory(File, String, String)} with null
	 * for parent and suffix.
	 * @param prefix The prefix for the directory's name. If this
	 * 	is null, the full class name of this class is used.
	 * @return The newly created temporary directory.
	 * @throws IOException if directory creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public static File createTmpDirectory(String prefix) throws IOException
	{
		return createTmpDirectory(null, prefix, null);
	}

	/**
	 * Create a temporary directory. This calls
	 * {@link #createTmpDirectory(File, String, String)} with null
	 * for all arguments.
	 * @return The newly created temporary directory.
	 * @throws IOException if directory creation fails
	 * @throws SecurityException if {@link File#mkdir()} throws one.
	 */
	public static File createTmpDirectory() throws IOException
	{
	 	return createTmpDirectory(null,null,null);
	}

	/**
	 * Checks that child is a directory and really a child of
	 * parent. This verifies that the {@link File#getCanonicalFile()
	 * canonical} child is actually a child of parent. This should
	 * fail if the child is a symbolic link to another directory and
	 * therefore should not be traversed in a recursive traversal of
	 * a directory.
	 * @param parent The supposed parent of the child
	 * @param child The child to check
	 * @return true if child is a directory and a direct decendant
	 * 	of parent.
	 * @throws IOException if {@link File#getCanonicalFile()} does
	 * @throws NullPointerException if either parent or child
	 * 	are null.
	 */
	public static boolean isChildSubDirectory(File parent, File child) throws IOException
	{
		File childsParent;

		if(child==null)
			throw new NullPointerException("child argument is null");
		if(!child.isDirectory())
			return false;
		if(parent==null)
			throw new NullPointerException("parent argument is null");
		parent = parent.getCanonicalFile();
		child = child.getCanonicalFile();
		childsParent = child.getParentFile();
		if(childsParent == null)
			return false;	// sym link to /?
		childsParent = childsParent.getCanonicalFile();	// just in case...
		if(!parent.equals(childsParent))
			return false;
		return true;
	}

	/**
	 * Delete a file. Unlink {@link File#delete()}, this throws an
	 * exception if deletion fails.
	 * @param file The file to delete
	 * @throws DeletionFailedException if file is not null, exists
	 * 	but delete fails.
	 * @throws SecurityException if {@link File#delete()} does.
	 */
	public static void deleteWithException(File file) throws IOException
	{
		if(!delete(file))
			throw new DeletionFailedException(file);
	}

	/**
	 * Delete a file. Deletion is not attempted if file is null or
	 * does not exist.
	 * @param file The file to delete.
	 * @return true if file is null, does not exist or deletion
	 * 	succeeds. false otherwise.
	 * @throws SecurityException if {@link File#delete()} does.
	 */
	public static boolean delete(File file)
	{
		if(file==null || !file.exists())
			return true;
		return file.delete();
	}

	/**
	 * Delete files. Deletion is not attempted if a file is null or
	 * does not exist.
	 * @param files Files to be deleted
	 * @return An array of those files for which deletion failed. null
	 * 	is returned on when no file deletion fails.
	 * @throws SecurityException if {@link File#delete()} does.
	 */
	public static File[] delete(File...files)
	{
		List<File> fails;

		if(files == null || files.length <= 0)
			return null;
 		fails = new ArrayList<File>(files.length);
		for(File file : files)
			if(!delete(file))
				fails.add(file);
		if(fails.size() == 0)
			return null;
		return fails.toArray(EMPTY_FILE_ARRAY);
	}

	/**
	 * Delete files. Deletion is not attempted if a file is null or
	 * does not exist.
	 * @param files Files to be deleted
	 * @return An array of those files for which deletion failed. A
	 * 	empty array is returned on when no file deletion fails (ie:
	 * 	not null).
	 * @throws SecurityException if {@link File#delete()} does.
	 */
	public static Collection<File> delete(Collection<File> files)
	{
		List<File> fails;

		if(files == null || files.size() <= 0)
			return null;
 		fails = new ArrayList<File>(files.size());
		for(File file : files)
			if(!delete(file))
				fails.add(file);
		if(fails.size() == 0)
			return null;
		return fails;
	}

	/**
	 * Delete files. Deletion is not attempted if a file is null or
	 * does not exist.
	 * @param files Files to be deleted
	 * @return An array of those files for which deletion failed. A
	 * 	empty array is returned on when no file deletion fails (ie:
	 * 	not null).
	 * @throws SecurityException if {@link File#delete()} does.
	 */
	public static void deleteWithException(File...files) throws DeletionFailedException
	{
		File[] fails;

		fails = delete(files);
		if(fails != null || fails.length > 0)
			throw new DeletionFailedException(fails);
	}

	/**
	 * Delete files. Deletion is not attempted if a file is null or
	 * does not exist.
	 * @param files Files to be deleted
	 * @return An array of those files for which deletion failed. A
	 * 	empty array is returned on when no file deletion fails (ie:
	 * 	not null).
	 * @throws SecurityException if {@link File#delete()} does.
	 */
	public static void deleteWithException(Collection<File> files) throws DeletionFailedException
	{
		Collection<File> fails;

		fails = delete(files);
		if(fails != null || fails.size() > 0)
			throw new DeletionFailedException(fails);
	}

	/**
	 * Recursively delete a file. If file is a directory,
	 * subdirectories and files are also deleted. Care is taken to
	 * not traverse symbolic links in this process. A null file or
	 * a file that does not exist is considered to already been
	 * deleted.
	 * @param file The file or directory to be deleted
	 * @return false if any deletion failed. true otherwise.
	 * @throws SecurityException if {@link File#delete()} does.
	 * @throws IOException listing or checking files does.
	 */
	public static boolean deleteRecursively(File file) throws IOException
	{
		File[] children;
		File child;
		File childsParent;
		boolean success = true;

		if(file == null || !file.exists())
			return true;	// already deleted?
		if(file.isDirectory())
		{
			children = file.listFiles();
			for(int i=0;i<children.length;i++)
			{
				child = children[i];
				if(isChildSubDirectory(file,child))
					success &= deleteRecursively(child);
				else
					success &= file.delete();
			}
		}

		// finally
		success &= delete(file);
		return success;
	}

	/**
	 * Recursively delete a file. If file is a directory,
	 * subdirectories and files are also deleted. Care is taken to
	 * not traverse symbolic links in this process. A null file or
	 * a file that does not exist is considered to already been
	 * deleted.
	 * @param file The file or directory to be deleted
	 * @throws DeletionFailedException if the file, or a descendant,
	 *	cannot be deleted. This is only thrown after all file
	 *	deletions have been attempted (IE: it is not thrown when
	 * 	the first one fails).
	 * @throws IOException listing or checking files does.
	 * @throws SecurityException if {@link File#delete()} does.
	 */
	public static void deleteRecursivelyWithException(File file) throws IOException
	{
		if(!deleteRecursively(file))
			throw new DeletionFailedException(file);
	}

	/**
	 * Recursively delete files. If a file is a directory,
	 * subdirectories and files are also deleted. Care is taken to
	 * not traverse symbolic links in this process. A null file or
	 * a file that does not exist is considered to already been
	 * deleted.
	 * @param files The files or directories to be deleted
	 * @return Array of files that could not be deleted. A empty
	 * 	array is returned if all deletes succeeded.
	 * @throws SecurityException if {@link File#delete()} does.
	 * @throws IOException listing or checking files does.
	 */
	public static File[] deleteRecursively(File...files) throws IOException
	{
		List<File> fails;
		
		if(files==null || files.length <= 0)
			return EMPTY_FILE_ARRAY;
		fails = new ArrayList<File>(files.length);
		for(File file : files)
			if(!deleteRecursively(file))
				fails.add(file);
		if(fails.size() > 0)
			return fails.toArray(new File[fails.size()]);
		return EMPTY_FILE_ARRAY;
	}

	/**
	 * Recursively delete files. If a file is a directory,
	 * subdirectories and files are also deleted. Care is taken to
	 * not traverse symbolic links in this process. A null file or
	 * a file that does not exist is considered to already been
	 * deleted.
	 * @param files The files or directories to be deleted
	 * @return A collection of files that could not be deleted. null
	 * 	is returned if all deletes succeeded.
	 * @throws SecurityException if {@link File#delete()} does.
	 * @throws IOException listing or checking files does.
	 */
	public static Collection<File> deleteRecursively(Collection<File> files) throws IOException
	{
		List<File> fails;
		
		if(files==null || files.size() <= 0)
			return null;
		fails = new ArrayList<File>(files.size());
		for(File file : files)
			if(!deleteRecursively(file))
				fails.add(file);
		if(fails.size() > 0)
			return fails;
		return null;
	}

	/**
	 * Recursively delete files. If a file is a directory,
	 * subdirectories and files are also deleted. Care is taken to
	 * not traverse symbolic links in this process. A null file or
	 * a file that does not exist is considered to already been
	 * deleted.
	 * @param files The files or directories to be deleted
	 * @return A collection of files that could not be deleted. null
	 * 	is returned if all deletes succeeded.
	 * @throws SecurityException if {@link File#delete()} does.
	 * @throws IOException listing or checking files does.
	 * @throws DeletionFailedException if any files could not
	 * 	be deleted.
	 */
	public static void deleteRecursivelyWithException(File...files) throws IOException
	{
		File[] fails;

		fails = deleteRecursively(files);
		if(fails != null && fails.length > 0)
			throw new DeletionFailedException(fails);
	}

	/**
	 * Recursively delete files. If a file is a directory,
	 * subdirectories and files are also deleted. Care is taken to
	 * not traverse symbolic links in this process. A null file or
	 * a file that does not exist is considered to already been
	 * deleted.
	 * @param files The files or directories to be deleted
	 * @throws SecurityException if {@link File#delete()} does.
	 * @throws IOException listing or checking files does.
	 * @throws DeletionFailedException if any files could not
	 * 	be deleted.
	 */
	public static void deleteRecursivelyWithException(Collection<File> files) throws IOException
	{
		Collection<File> fails;

		fails = deleteRecursively(files);
		if(fails != null && fails.size() > 0)
			throw new DeletionFailedException(fails);
	}
}
