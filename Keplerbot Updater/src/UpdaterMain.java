import java.awt.Font;
import java.awt.SystemColor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class UpdaterMain {
	private JFrame frmUpdater;
	private JProgressBar progressBar;

	public UpdaterMain(final String downloadLink, final String fileName) {
		try {
			initialize();
			
			Thread.sleep(1000L);

			new Thread(new Runnable() {
	            public void run() {
	    			download(downloadLink, fileName);
	    			deleteOldFile(fileName);
	    			close(fileName);
	            }
	        }).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) throws Exception {
		if (args.length != 2) {
			throw new Exception(args.length + " is not the correct amount of arguments");
		}
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		new UpdaterMain(args[0], args[1]);
            }
        });
	}

	private void initialize() {
		frmUpdater = new JFrame();
		frmUpdater.setAlwaysOnTop(true);
		frmUpdater.setResizable(false);
		frmUpdater.setTitle("Keplerbot - Updating");
		frmUpdater.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(true);
		
		JTextPane txtpnUpdating = new JTextPane();
		txtpnUpdating.setBackground(SystemColor.menu);
		txtpnUpdating.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
		txtpnUpdating.setEditable(false);
		txtpnUpdating.setText("Updating...");
		GroupLayout groupLayout = new GroupLayout(frmUpdater.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(txtpnUpdating, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(126))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtpnUpdating, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		frmUpdater.getContentPane().setLayout(groupLayout);
		frmUpdater.pack();
		frmUpdater.setVisible(true);
	}

	private void download(String link, String filename) {
		try {
			URL url = new URL(link);
			URLConnection conn = url.openConnection();
			int size = conn.getContentLength();
			progressBar.setMaximum(size);
			progressBar.setValue(0);
			progressBar.setIndeterminate(false);
			int total = 0;
			if (size < 0) {
				throw new Exception("Unable to find remote file");
			} else {
				try {

					byte abyte0[] = new byte[4096];
					DataInputStream datainputstream = new DataInputStream(conn.getInputStream());
					DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(new File("./" + filename + "_new")));

					for (int i = 0; (i = datainputstream.read(abyte0)) >= 0;) {
						total += i;
						progressBar.setValue(total);
						progressBar.repaint();
						dataoutputstream.write(abyte0, 0, i);
					}

					datainputstream.close();
					dataoutputstream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			conn.getInputStream().close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
		try {
			File newFile = new File(filename + "_new");

			URL url = new URL(link);
			URLConnection conection = url.openConnection();
			conection.connect();
			progressBar.setMaximum(conection.getContentLength());

			FileOutputStream output = new FileOutputStream(newFile);
			InputStream input = url.openStream();

			byte[] buffer = new byte[1024];
			int bytesRead = input.read(buffer);

			while (bytesRead != -1) {
				progressBar.setValue(progressBar.getValue() + bytesRead);
				output.write(buffer, 0, bytesRead);
				bytesRead = input.read(buffer);
			}

			output.close();
			input.close();

		} catch (Exception e) {
			e.printStackTrace();
			close(filename);
		}
	}

	private void deleteOldFile(String filename) {
		try {
			File oldFile = new File(filename);
			File newFile = new File(filename + "_new");

			if (oldFile.delete()) {
				newFile.renameTo(oldFile);
			} else {
				System.out.println("Delete operation is failed.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close(String filename) {
		try {
			Runtime.getRuntime().exec("java -jar " + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.exit(3);
	}
}