package com.ericsson.li.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class FileUtil {
   
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");
                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }
   
   
    public static boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }
   
   
    public static String createTempFile(String prefix, String suffix, String dirName) {
        File tempFile = null;
        if (dirName == null) {
            try{
                //在默认文件夹下创建临时文件
                tempFile = File.createTempFile(prefix, suffix);
                //返回临时文件的路径
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        } else {
            File dir = new File(dirName);
            //如果临时文件所在目录不存在，首先创建
            if (!dir.exists()) {
                if (!FileUtil.createDir(dirName)) {
                    System.out.println("创建临时文件失败，不能创建临时文件所在的目录！");
                    return null;
                }
            }
            try {
                //在指定目录下创建临时文件
                tempFile = File.createTempFile(prefix, suffix, dir);
                return tempFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("创建临时文件失败！" + e.getMessage());
                return null;
            }
        }
    }
    
    public static void test() {
        //创建目录
        String dirName = "D:/111work/temp/temp0/temp1";
        FileUtil.createDir(dirName);
        //创建文件
        //String fileName = dirName + "/temp2/tempFile.txt";
        String fileName = "D:/ooootemp2/333/tempFile.txt";
        FileUtil.createFile(fileName);
        //创建临时文件
/*        String prefix = "temp";
        String suffix = ".txt";
        for (int i = 0; i < 10; i++) {
            System.out.println("创建了临时文件："
                    + FileUtil.createTempFile(prefix, suffix, dirName));
        }
        //在默认目录下创建临时文件
        for (int i = 0; i < 10; i++) {
            System.out.println("在默认目录下创建了临时文件："
                    + FileUtil.createTempFile(prefix, suffix, null));
        }*/
    }
    
    public static void showList(String inputDir) {
		File dir = new File(inputDir);
		File[] files = dir.listFiles();
		for (File file : files) {    

		     if (file.isDirectory()) {
		    	 String dirName = file.getPath();
		    	 //System.out.println("dirName:" + dirName);
		    	 showList(dirName);
		     } else {
		    	 System.out.println(file.getPath() + File.separator + file.getName());
		    	 System.out.println(file.getName());
		    	 System.out.println(file.getPath());
		    	 System.out.println(file.getAbsolutePath());
		     }
		}	
    }

	public static void renameTest() {
		try {
			String filename = "D:/aaa.txt";
			//String dest = "D:/111work/bbb.txt";
			String dest = "e:/bbb.txt";
			File file1 = new File(filename);
			File file2 = new File(dest);
			if (file2.exists()) {
				System.out.println("file[" + file2.getAbsolutePath() + "] had exist");
			} else {
				file1.renameTo(file2);
			}

			
	    	 System.out.println(file1.getPath() + File.separator + file1.getName());
	    	 System.out.println(file1.getName());
	    	 System.out.println(file1.getPath());
	    	 System.out.println(file1.getAbsolutePath());
	    	 
	    	 filename = "D:\\logs";
	    	 file1 = new File(filename);
	    	 System.out.println(file1.getPath() + File.separator + file1.getName());
	    	 System.out.println(file1.getName());
	    	 System.out.println(file1.getPath());
	    	 System.out.println(file1.getAbsolutePath());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeTest(String fileName, String data) {
		if (!createFile(fileName)) {
			System.out.println("create file[" + fileName + "] error!");
			return;
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(fileName, true));
			bw.write(data);
			bw.newLine();
		} catch (IOException ie) {
			System.out.println("object2File error:" + ie.getMessage());
		} finally {
			try {
				if (bw != null) {
					bw.flush();
					bw.close();
				}
			} catch (IOException ie) {
				System.out.println("object2File bw close error:" + ie.getMessage());
			}
		}
	}
	
	private static void testLastModified() {
        // 定义list，用于存储数据文件的全路径
        List<String> filelist = new ArrayList<String>();
        String dataFileTempDir = "D:\\workspace\\test\\mediationgw\\testmodify";
        // 得到返回文件全路径的list集合
        List<String> list = getFiles(dataFileTempDir, filelist);
        String dataFileTempPath = null;
        for (int i = 0; i < list.size(); i++) {
            // 数据文件在临时区的路径
            dataFileTempPath = list.get(i);
            System.out.println(i+"dataFileTempPath:"+dataFileTempPath);
        }
	}
	
	public static List<String> getFiles(String filePath, List<String> filelist) {

        File root = new File(filePath);
        if (!root.exists()) {
        	System.out.println(filePath + " not exist!");
        } else {
            File[] files = root.listFiles();
            Arrays.sort(files, new FileUtil.CompratorByLastModified());  
            for (File file : files) {
                if (file.isDirectory()) {
                    getFiles(file.getAbsolutePath(), filelist);
                } else {
                    //logger.info("目录:" + filePath + "文件全路径:" + file.getAbsolutePath());
                    filelist.add(file.getAbsolutePath());
                }
            }
        }
        return filelist;
    }
     
    //根据文件修改时间进行比较的内部类
    static class CompratorByLastModified implements Comparator<File> {  
        
        public int compare(File f1, File f2) {  
            long diff = f1.lastModified() - f2.lastModified();  
            if (diff > 0) {  
                   return 1;  
            } else if (diff == 0) {  
                   return 0;  
            } else {  
                  return -1;  
            }  
        }  
    } 
    
    public static  void sortFiles() {
    	 String dataFileTempDir = "D:\\workspace\\test\\mediationgw\\testmodify";
    	 File root = new File(dataFileTempDir);
    	 File[] fileList = root.listFiles();
    	 for (File file : fileList) {
    		 System.out.println(file.getAbsolutePath());
    	 }
    	 System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
    	 Arrays.sort(fileList, new FileUtil.CompratorByLastModified()); 
    	 for (File file : fileList) {
    		 System.out.println(file.getAbsolutePath());
    	 }
    	 
    }
    
    public static void testModifyTime() {
    	String oldName = "D:/TSG-R4.04004.04004000000001.1004.20170207.1";
    	String newName = "D:/TSG-R4.04004.04004000000001.1004.20170207.2";
    	File oldFile = new File(oldName);
    	System.out.println("oldFile.lastModified:" + oldFile.lastModified());
    	oldFile.setLastModified(System.currentTimeMillis());
    	System.out.println("oldFile.lastModified:" + oldFile.lastModified());
    	try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	File newFile = new File(newName);
    	oldFile.renameTo(newFile);
    	System.out.println("newFile.lastModified:" + newFile.lastModified());
    	
    	System.out.println("oldKey = " + oldFile.getName().substring(oldFile.getName().indexOf('.') + 1));
    	System.out.println("newKey = " + newFile.getName().substring(newFile.getName().indexOf('.') + 1));
    }
   
    public static void main(String[] args) {
    	//test();
    	renameTest();
    	//showList("./");
    	//String data = "oooooooooooo\npppppppppppp";
    	
    	//writeTest("D:/oooopppp.txt", data);
    	//sortFiles();
    	System.out.println("====================");
    	//testModifyTime();
    }

}


