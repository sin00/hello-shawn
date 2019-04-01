package com.ericsson.li.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FilesTest {

    public static void main(String[] args) throws Exception {  
        normalOpt();  
    }

	private static void normalOpt() throws IOException, FileNotFoundException {
		// 复制文件  
        Files.copy(Paths.get("Youth.txt"), new FileOutputStream("a2.txt"));  
        // 判断FilesTest.java文件是否为隐藏文件  
        System.out.println("FilesTest.java是否为隐藏文件：" + Files.isHidden(Paths.get("Youth.txt")));  
        // 一次性读取FilesTest.java文件的所有行  
        List<String> lines = Files.readAllLines(Paths.get("Youth.txt"), Charset.forName("UTF-8"));  
        System.out.println(lines);  
        // 判断指定文件的大小  
        System.out.println("FilesTest.java的大小为：" + Files.size(Paths.get("Youth.txt")));  
        List<String> poem = new ArrayList<>();  
        poem.add("水晶潭底银鱼跃");  
        poem.add("清徐风中碧竿横");  
        // 直接将多个字符串内容写入指定文件中  
        Files.write(Paths.get("pome.txt"), poem, Charset.forName("gbk"));  
        // 使用Java 8新增的Stream API列出当前目录下所有文件和子目录  
        Files.list(Paths.get(".")).forEach(path -> System.out.println(path));  
        // 使用Java 8新增的Stream API读取文件内容  
        Files.lines(Paths.get("Youth.txt"), Charset.forName("UTF-8")).forEach(line -> System.out.println(line));  
        FileStore cStore = Files.getFileStore(Paths.get("C:"));  
        // 判断C盘的总空间，可用空间  
        System.out.println("C:共有空间：" + cStore.getTotalSpace());  
        System.out.println("C:可用空间：" + cStore.getUsableSpace());
	}  
	
	public static void visitOpt() throws IOException {
		Files.walkFileTree(Paths.get("d:", "VM"), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println("正在访问" + file + "文件");
				// 找到了FileInputStreamTest.java文件
				if (file.endsWith("FileInputStreamTest.java")) {
					System.out.println("--已经找到目标文件--");
					return FileVisitResult.TERMINATE;
				}
				return FileVisitResult.CONTINUE;
			}

			// 开始访问目录时触发该方法
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println("正在访问：" + dir + " 路径");
				return FileVisitResult.CONTINUE;
			}
		});
	} 
	
    public static void watchServiceTest() throws IOException, InterruptedException{  
        // 获取文件系统的WatchService对象  
        WatchService watchService = FileSystems.getDefault().newWatchService();  
        // 为C:盘根路径注册监听  
        Paths.get("C:/").register(watchService, StandardWatchEventKinds.ENTRY_CREATE,  
                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);  
        while (true) {  
            // 获取下一个文件改动事件  
            WatchKey key = watchService.take(); // ①  
            for (WatchEvent<?> event : key.pollEvents()) {  
                System.out.println(event.context() + " 文件发生了 " + event.kind() + "事件！");  
            }  
            // 重设WatchKey  
            boolean valid = key.reset();  
            // 如果重设失败，退出监听  
            if (!valid) {  
                break;  
            }  
        }  
    }
}
