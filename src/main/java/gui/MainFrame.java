package gui;

import javax.swing.*;

/**
 * Created by Paul on 03/12/2018.
 */
public class MainFrame extends JFrame {
    public static final String APP_TITLE = "Peer 2 Peer Application";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;

    private JTabbedPane menuTabbedPane;
    private JPanel downloadPanel;
    private JList fileList;
    private JLabel fileListLabel;
    private JPanel parametersPanel;
    private JTextField portParamField;
    private JPanel mainPanel;
    private JLabel portParamLabel;
    private JLabel selectedFolderParamLabel;
    private JButton selectFolderButton;
    private JLabel selectedFolder;
    private JButton refreshFileListButton;
    private JLabel defaultPortParam;
    private JButton savePortParamButton;
    private JLabel peerParamLabel;
    private JTextField peerParamField;
    private JList peerList;
    private JButton deletePeerButton;
    private JButton addPeerButton;
    private JButton downloadFileButton;
    private JButton deleteFileButton;
    private JPanel sharePanel;
    private JList localFilesList;
    private JButton deleteLocalFileButton;
    private JList recipientsList;
    private JButton sendLocalFileButton;
    private JFileChooser folderChooser;

    public MainFrame() {
        setTitle(APP_TITLE);
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setVisible(true);
    }

    public void openFileChooser() {
        folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.showOpenDialog(selectFolderButton.getParent());
        try
        {
            selectedFolder.setText(folderChooser.getSelectedFile().getAbsolutePath());
        }
        catch (NullPointerException e) {
            //error handling code
        }
    }

    public void setFileList(JList fileList) {
        this.fileList = fileList;
    }

    public JTabbedPane getMenuTabbedPane() {
        return menuTabbedPane;
    }

    public JPanel getDownloadPanel() {
        return downloadPanel;
    }

    public JList getFileList() {
        return fileList;
    }

    public JLabel getFileListLabel() {
        return fileListLabel;
    }

    public JPanel getParametersPanel() {
        return parametersPanel;
    }

    public JTextField getPortParamField() {
        return portParamField;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JLabel getPortParamLabel() {
        return portParamLabel;
    }

    public JLabel getSelectedFolderParamLabel() {
        return selectedFolderParamLabel;
    }

    public JButton getSelectFolderButton() {
        return selectFolderButton;
    }

    public JLabel getSelectedFolder() {
        return selectedFolder;
    }

    public JButton getRefreshFileListButton() {
        return refreshFileListButton;
    }

    public JLabel getDefaultPortParam() {
        return defaultPortParam;
    }

    public JButton getSavePortParamButton() {
        return savePortParamButton;
    }

    public JLabel getPeerParamLabel() {
        return peerParamLabel;
    }

    public JTextField getPeerParamField() {
        return peerParamField;
    }

    public JFileChooser getFolderChooser() {
        return folderChooser;
    }

    public JList getPeerList() { return peerList; }

    public void setPeerList(JList peerList) {
        this.peerList = peerList;
    }

    public JButton getDeletePeerButton() {
        return deletePeerButton;
    }

    public JButton getAddPeerButton() { return addPeerButton; }

    public JButton getDownloadFileButton() { return downloadFileButton; }

    public JButton getDeleteFileButton() { return deleteFileButton; }

    public JList getLocalFilesList() {
        return localFilesList;
    }

    public void setLocalFilesList(JList localFilesList) {
        this.localFilesList = localFilesList;
    }

    public JButton getDeleteLocalFileButton() {
        return deleteLocalFileButton;
    }

    public void setDeleteLocalFileButton(JButton deleteLocalFileButton) {
        this.deleteLocalFileButton = deleteLocalFileButton;
    }

    public JList getRecipientsList() {
        return recipientsList;
    }

    public void setRecipientsList(JList recipientsList) {
        this.recipientsList = recipientsList;
    }


    public JButton getSendLocalFileButton() {
        return sendLocalFileButton;
    }

    public void setSendLocalFileButton(JButton sendLocalFileButton) {
        this.sendLocalFileButton = sendLocalFileButton;
    }


}
