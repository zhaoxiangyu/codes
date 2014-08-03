package org.sharpx.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.sharpx.utils.beans.DirSnap;
import org.sharpx.utils.beans.FileInfo;
import org.sharpx.utils.jdkex.Utils;

public class FsUtils {

	public static Object saveJson(Object obj, String file) {
		try {
			if (file == null)
				return null;
			File f = new File(file);
			if (!f.exists())
				f.getParentFile().mkdirs();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(f, obj);
		} catch (Exception e) {
			Utils.log.error("", e);
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	public static Object loadJson(String file, Class clazz, Object def) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			File f = new File(file);
			if (!f.exists())
				return def;
			Object conf = mapper.readValue(f, clazz);
			if (conf == null)
				conf = def;
			return conf;
		} catch (Exception e) {
			Utils.log.error("", e);
		}
		return null;
	}

	public static Object saveCastor(Object obj, String file) {
		FileWriter fileOut;
		try {
			fileOut = new FileWriter(file);
			Marshaller.marshal(obj, fileOut);
		} catch (Exception e) {
			Utils.log.error("", e);
		}
		return obj;
	}

	public static Object loadCastor(String file, Class clazz) {
		FileReader in;
		try {
			in = new FileReader(file);
			Object conf = Unmarshaller.unmarshal(clazz, in);
			return conf;
		} catch (Exception e) {
			Utils.log.error("", e);
		}
		return null;
	}

	public static File[] findNamedFilesUseRegEx(String dir, final String regEx) {
		final List fl = new ArrayList();
		HashMap hm = new HashMap();
		BaseFileHandler fh = null;
		new FsUtils().travDir(dir, fh = new BaseFileHandler(fl) {
			public void handle(File file) {
				fc++;
				// TODO
				if (FilenameUtils.equals(file.getName(), regEx))
					fl.add(file);
			}
		});
		fh.summary(hm);
		Utils.log.info("total " + hm.get("dc") + " dir, " + hm.get("fc")
				+ " files parsed.");
		return (File[]) fl.toArray(new File[0]);
	}

	public static File[] findNamedFiles(String dir, final String filename) {
		final List fl = new ArrayList();
		HashMap hm = new HashMap();
		BaseFileHandler fh = null;
		new FsUtils().travDir(dir, fh = new BaseFileHandler(fl) {
			public void handle(File file) {
				fc++;
				if (FilenameUtils.equals(file.getName(), filename))
					fl.add(file);
			}
		});
		fh.summary(hm);
		Utils.log.info("total " + hm.get("dc") + " dir, " + hm.get("fc")
				+ " files parsed.");
		return (File[]) fl.toArray(new File[0]);
	}

	public static File[] findMp3WaveFiles(String dir) {
		Utils.log.info("finding mp3 file under folder " + dir);
		final List fl = new ArrayList();
		HashMap hm = new HashMap();
		BaseFileHandler fh = null;
		new FsUtils().travDir(dir, fh = new BaseFileHandler(fl) {
			public void handle(File file) {
				fc++;
				if (FilenameUtils.isExtension(file.getName(), new String[] {
						"mp3", "MP3", "mP3", "Mp3", "wav", "WAV" }))
					fl.add(file);
			}
		});
		fh.summary(hm);
		Utils.log.info("total " + hm.get("dc") + " dir, " + hm.get("fc")
				+ " files parsed.");
		return (File[]) fl.toArray(new File[0]);
	}

	public static File[] findWavFile(String dir, final String word) {
		Utils.log.info("finding wav file for word " + word + " under folder "
				+ dir);
		final List fl = new ArrayList();
		HashMap hm = new HashMap();
		BaseFileHandler fh = null;
		new FsUtils().travDir(dir, fh = new BaseFileHandler(fl) {
			public void handle(File file) {
				fc++;
				if ((word + ".mp3").equalsIgnoreCase(file.getName())
						|| (word + ".wav").equalsIgnoreCase(file.getName()))
					fl.add(file);
			}
		});
		fh.summary(hm);
		Utils.log.info("total " + hm.get("dc") + " dir, " + hm.get("fc")
				+ " files parsed.");
		return (File[]) fl.toArray(new File[0]);
	}

	private void travDir(String s, FileHandler fh) {
		File f = new File(s);
		if (!f.exists()) {
			Utils.log.debug(s + " does not exist");
			return;
		}
		if (f.isFile())
			fh.handle(f);
		else if (f.isDirectory()) {
			/* Console.log.debug("d " + f.getPath()); */
			fh.handleDir(f);
			String objects[] = f.list();
			if (objects == null)
				return;
			for (int i = 0; i < objects.length; i++)
				travDir(s + f.separator + objects[i], fh);
		} else
			Utils.log.error("Unknown: " + s);
	}

	public static void unzip(File file, File destination) throws IOException {
		if (!destination.exists())
			destination.mkdirs();
		if (!destination.isDirectory())
			throw new IllegalArgumentException();
		ZipInputStream in = null;
		OutputStream out = null;
		try {
			// Open the ZIP file
			in = new ZipInputStream(new FileInputStream(file));

			// Get the first entry
			ZipEntry entry = null;

			while ((entry = in.getNextEntry()) != null) {
				String outFilename = entry.getName();

				// Open the output file
				if (entry.isDirectory()) {
					System.out.println("dir entry:" + outFilename);
					new File(destination, outFilename).mkdirs();
				} else {
					System.out.println("file entry:" + outFilename);
					File f = new File(destination, outFilename);
					if (!f.getParentFile().exists()) {
						f.getParentFile().mkdirs();
					}
					out = new FileOutputStream(f);

					// Transfer bytes from the ZIP file to the output file
					byte[] buf = new byte[1024];
					int len;

					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}

					// Close the stream
					out.close();
				}
			}
		} finally {
			// Close the stream
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	public static void zip(final File base, String wildcard, final File todir)
			throws IOException {
		/*
		 * File[] dirs =
		 * base.listFiles((FilenameFilter)FileFilterUtils.andFileFilter(
		 * FileFilterUtils.directoryFileFilter(), new WildcardFileFilter("
		 *//*
			 * "))); for (int i = 0; i < dirs.length; i++) {
			 * System.out.println("path:"+dirs[i].getPath());
			 * zip(base,dirs[i],new File(FilenameUtils.concat(todir.getPath(),
			 * dirs[i].getName()+".zip"))); }
			 */
		String nw = FilenameUtils.concat(base.getPath(), wildcard);
		System.out.println("wildcard:" + nw);
		new DirectoryWalker(new WildcardFileFilter(nw),
				FileFilterUtils.falseFileFilter(), -1) {

			@Override
			protected boolean handleDirectory(File directory, int depth,
					Collection results) throws IOException {
				System.out.println("path:" + directory.getPath());
				zip(base,
						directory,
						new File(FilenameUtils.concat(todir.getPath(),
								directory.getName() + ".zip")));
				return false;
			}

			public void travel() throws IOException {
				walk(base, null);
			}
		}.travel();
	}

	public static void packJpword(String jpwordDir, String todir, int dep,
			String password) throws Exception {
		File targetdir = new File(todir);
		if (!targetdir.exists())
			targetdir.mkdir();
		zip(new File(jpwordDir), dep, targetdir);
		File[] zips = targetdir.listFiles((FilenameFilter) FileFilterUtils
				.fileFileFilter());
		if (password == null)
			return;
		for (int i = 0; i < zips.length; i++) {
			AesCypher.encrypt(zips[i], new File(targetdir, zips[i].getName()
					+ ".aes"), password);
		}
	}

	public static void unpackJpword(String jpwordDir, String todir,
			String password) throws Exception {
		File targetdir = new File(todir);
		if (!targetdir.exists())
			targetdir.mkdirs();

		File[] aes = new File(jpwordDir)
				.listFiles((FilenameFilter) new WildcardFileFilter("*.aes"));
		for (int i = 0; i < aes.length; i++) {
			File decryped = new File(targetdir, aes[i].getName() + ".zip");
			if (password != null)
				AesCypher.decrypt(aes[i], decryped, password);
			unzip(decryped, targetdir);
		}
	}

	public static void zip(final File base, final int dep, final File todir)
			throws IOException {
		/*
		 * File[] dirs =
		 * base.listFiles((FilenameFilter)FileFilterUtils.andFileFilter(
		 * FileFilterUtils.directoryFileFilter(), new WildcardFileFilter("
		 *//*
			 * "))); for (int i = 0; i < dirs.length; i++) {
			 * System.out.println("path:"+dirs[i].getPath());
			 * zip(base,dirs[i],new File(FilenameUtils.concat(todir.getPath(),
			 * dirs[i].getName()+".zip"))); }
			 */
		new DirectoryWalker() {

			@Override
			protected boolean handleDirectory(File directory, int depth,
					Collection results) throws IOException {
				System.out.println("path:" + directory.getPath());
				if (depth == dep) {
					zip(base,
							directory,
							new File(FilenameUtils.concat(todir.getPath(),
									directory.getName() + ".zip")));
					return false;
				}
				return true;
			}

			public void travel() throws IOException {
				walk(base, null);
			}
		}.travel();
	}

	public static void zip(final File base, final File file, File zip)
			throws IOException {
		final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zip));
		try {
			new FsUtils().travDir(file.getPath(), new FileHandler() {
				String basePath = base.getPath() + File.separator;

				public void handle(File file) {
					try {
						byte[] readBuffer = new byte[2156];
						int bytesIn = 0;

						FileInputStream fis = new FileInputStream(file);
						String entryName = StringUtils.removeStart(
								file.getPath(), basePath);
						System.out.println("entryname:" + entryName);
						ZipEntry anEntry = new ZipEntry(entryName);
						out.putNextEntry(anEntry);
						while ((bytesIn = fis.read(readBuffer)) != -1) {
							out.write(readBuffer, 0, bytesIn);
						}
						fis.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				public void handleDir(File dir) {
				}
			});

		} finally {
			out.close();
		}
	}

	public static void xmlEncode(String filePath, Object obj) {
		XMLEncoder xe;
		try {
			xe = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(
					filePath)));
			xe.writeObject(obj);
			xe.close();
		} catch (FileNotFoundException e) {
			Utils.log.error("", e);
		}
	}

	public static File copyFileToDirectory(File srcFile, String destDir,
			File def) {
		try {
			FileUtils.copyFileToDirectory(srcFile, new File(destDir));
			return new File(FilenameUtils.concat(destDir, srcFile.getName()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return def;
	}

	public static Object xmlDecode(String filePath, Object def) {
		XMLDecoder xd = null;
		Object ret = null;
		try {
			if (filePath != null) {
				xd = new XMLDecoder(new BufferedInputStream(
						new FileInputStream(filePath)));
				ret = xd.readObject();
			}
		} catch (FileNotFoundException e) {
			Utils.log.error("", e);
		}
		return ret == null ? def : ret;
	}

	public static class DirSnaps {
		public static interface CacheSupport {
			String cacheFile(String dir);

			String cacheDir();
		}

		CacheSupport supporter;
		static HashMap<String, DirSnap> map = new HashMap<String, DirSnap>();

		public DirSnaps(CacheSupport s) {
			supporter = s;
		}

		private static void toFile(DirSnap ds, String file) {
			FsUtils.saveJson(ds, file);
		}

		private static DirSnap fromFile(String file) {
			DirSnap ds = (DirSnap) FsUtils.loadJson(file, DirSnap.class, null);
			return ds;
		}
	}

	public static String toCanonicalPath(String fp) {
		String cPath = fp;
		try {
			cPath = new File(fp).getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cPath;
	}

}

class BaseFileHandler implements FileHandler {

	List fl;
	int fc = 0;
	int dc = 0;

	public BaseFileHandler(List fl) {
		this.fl = fl;
	}

	public void handle(File file) {
		fc++;
	}

	public void handleDir(File dir) {
		dc++;
	}

	public FileHandler summary(Map map) {
		map.put("fc", new Integer(fc));
		map.put("dc", new Integer(dc));
		return this;
	}

}

interface FileHandler {
	void handle(File file);

	void handleDir(File dir);
}
