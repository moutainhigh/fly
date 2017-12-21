package com.xinfang.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.xinfang.log.ApiLog;
import com.xinfang.utils.Base64Utils;

public class FtpClientHelper {
	FTPClient fp = new FTPClient();
	FileOutputStream os = null;
	FileInputStream is = null;

	private static final String REMOTE_FILE_PATH = "/usr/local/tiyujia/team";

	public void connect() {
		try {
			fp.connect("112.74.94.53", 21);
			if (!FTPReply.isPositiveCompletion(fp.getReplyCode())) { // コネクトできたか？
				System.out.println("connection failed");
				System.exit(1); // 異常終了
				fp.setConnectTimeout(1000);
				// fp.setDefaultTimeout(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login() {
		try {
			if (fp.login("root", "Han.1234") == false) { // ログインできたか？
				System.out.println("login failed");
				System.exit(1); // 異常終了
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void download() {
		try {
			os = new FileOutputStream("c:/aaa.txt");// クライアント側
			fp.retrieveFile("ftp_setting/aaa.txt", os);// サーバー側
			os.close();
			System.out.println("FTP GET COMPLETED");
			fp.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upload() {
		try {
			is = new FileInputStream("d:/abad.txt");// クライアント側
			boolean flag = fp.storeFile("111.txt", is);// サーバー側
			is.close();
			System.out.println("FTP PUT COMPLETED :" + flag);
			fp.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过base64码上按指定文件名字上传至FTP
	 * 
	 * @param localFilePath
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public String uploadHeadSrc(String localFilePath, String fileName) throws IOException {

		// String strRemovePrefix = "";
		// 将base64编码字符串转换为图片并返回图片路径-具体到文件
//		String localFilePath2 = Base64Utils.GetImageStr(localFilePath);
		String filePath = Base64Utils.GenerateSpecifiedImage(localFilePath, fileName);

		File file = new File(filePath);
		try {
			is = new FileInputStream(file);
			// 此处是重点要不就服务器的图像就被损坏
			fp.setFileType(FTP.BINARY_FILE_TYPE);
			// 改变服务器地址
			fp.changeWorkingDirectory(REMOTE_FILE_PATH);
			fp.storeFile(file.getName(), is);
			is.close();
			fp.logout();
		} catch (Exception e) {
			ApiLog.chargeLog1(e.getMessage());
		} finally {
			// 保存后删除本地上传的图片
			File headfile = new File(filePath);
			// 路径为文件且不为空则进行删除
			if (headfile.isFile() && file.exists()) {
				headfile.delete();
			}
		}

		return "http://112.74.94.53:8080/team/" + fileName + ".jpg";
		// if (localFilePath.contains("data:image/png;base64,") ||
		// localFilePath.contains("data:image/gif;base64,")
		// || localFilePath.contains("data:image/gif;base64,")) {
		// strRemovePrefix = localFilePath.substring(22,
		// localFilePath.length());
		// // 将base64编码字符串转换为图片并返回图片路径-具体到文件
		// filePath = Base64Utils.GenerateSpecifiedImage(localFilePath,
		// fileName);
		//
		// File file = new File(filePath);
		// try {
		// is = new FileInputStream(file);
		// // 改变服务器地址
		// fp.changeWorkingDirectory(REMOTE_FILE_PATH);
		// fp.storeFile(file.getName(), is);
		// } catch (Exception e) {
		// ApiLog.chargeLog1(e.getMessage());
		// } finally {
		// // 保存后删除本地上传的图片
		// File headfile = new File(filePath);
		// // 路径为文件且不为空则进行删除
		// if (headfile.isFile() && file.exists()) {
		// headfile.delete();
		// }
		//
		// is.close();
		// fp.logout();
		// }
		// }
		// if (localFilePath.contains("data:image/jpeg;base64,")) {
		// strRemovePrefix = localFilePath.substring(23,
		// localFilePath.length());
		// // 将base64编码字符串转换为图片并返回图片路径-具体到文件
		// filePath = Base64Utils.GenerateSpecifiedImage(localFilePath,
		// fileName);
		//
		// File file = new File(filePath);
		// try {
		// is = new FileInputStream(file);
		// // 改变服务器地址
		// fp.changeWorkingDirectory(REMOTE_FILE_PATH);
		// fp.storeFile(file.getName(), is);
		// } catch (Exception e) {
		// ApiLog.chargeLog1(e.getMessage());
		// } finally {
		// // 保存后删除本地上传的图片
		// File headfile = new File(filePath);
		// // 路径为文件且不为空则进行删除
		// if (headfile.isFile() && file.exists()) {
		// headfile.delete();
		// }
		//
		// is.close();
		// fp.logout();
		// }
		// }

	}

	public void fileList() {
		List<String> list = new ArrayList<String>();
		try {
			FTPFile[] files = fp.listFiles("/ftp_setting");
			for (int i = 0; i < files.length; i++) {
				FTPFile file = files[i];
				System.out.print(file.getName() + "\n");
				fp.changeWorkingDirectory("ftp_setting");

				// String[] files = fp.listNames("ftp_setting");
				// for(int i = 0; i < files.length; i++){
				// String file = files[i];
				// System.out.print(file + "\n");
			}

			// DataInputStream dis = new DataInputStream(is);
			// String filename = "";
			// while((filename=dis.readLine())!=null)
			// {
			// list.add(filename);
			// }
			fp.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFile() {
		try {
			if (fp != null) {
				fp.deleteFile("ftp_setting/abab.txt");
				System.out.print("delete " + "ftp_setting/abab.txt" + " successful!");
			}
			fp.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteDirectory() {
		try {
			fp.removeDirectory("ftp_setting/abc");
			fp.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		FtpClientHelper Ftp = new FtpClientHelper();
		Ftp.connect();
		Ftp.login();
		// Ftp.download();
		Ftp.upload();
		/*
		 * Ftp.fileList(); Ftp.deleteFile(); Ftp.deleteDirectory();
		 */
	}
}