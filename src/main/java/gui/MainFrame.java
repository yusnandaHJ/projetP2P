package gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;

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
    private JLabel infoMessage;
    private JFileChooser folderChooser;

    public MainFrame() {
        setTitle(APP_TITLE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setVisible(true);
    }

    public void openFileChooser() {
        folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.showOpenDialog(selectFolderButton.getParent());
        try {
            selectedFolder.setText(folderChooser.getSelectedFile().getAbsolutePath());
        } catch (NullPointerException e) {
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

    public JList getPeerList() {
        return peerList;
    }

    public void setPeerList(JList peerList) {
        this.peerList = peerList;
    }

    public JButton getDeletePeerButton() {
        return deletePeerButton;
    }

    public JButton getAddPeerButton() {
        return addPeerButton;
    }

    public JButton getDownloadFileButton() {
        return downloadFileButton;
    }

    public JButton getDeleteFileButton() {
        return deleteFileButton;
    }

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

    public JLabel getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(JLabel infoMessage) {
        this.infoMessage = infoMessage;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBackground(new Color(-2039584));
        Font mainPanelFont = this.$$$getFont$$$("Roboto", -1, 20, mainPanel.getFont());
        if (mainPanelFont != null) mainPanel.setFont(mainPanelFont);
        menuTabbedPane = new JTabbedPane();
        menuTabbedPane.setBackground(new Color(-2039584));
        Font menuTabbedPaneFont = this.$$$getFont$$$("Roboto", Font.PLAIN, 16, menuTabbedPane.getFont());
        if (menuTabbedPaneFont != null) menuTabbedPane.setFont(menuTabbedPaneFont);
        menuTabbedPane.setForeground(new Color(-13421773));
        menuTabbedPane.setInheritsPopupMenu(false);
        menuTabbedPane.setName("");
        menuTabbedPane.setTabPlacement(2);
        mainPanel.add(menuTabbedPane, new GridConstraints(1, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        downloadPanel = new JPanel();
        downloadPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        menuTabbedPane.addTab("Téléchargements", downloadPanel);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        downloadPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        fileListLabel = new JLabel();
        Font fileListLabelFont = this.$$$getFont$$$(null, -1, -1, fileListLabel.getFont());
        if (fileListLabelFont != null) fileListLabel.setFont(fileListLabelFont);
        fileListLabel.setText("Liste des fichiers");
        panel1.add(fileListLabel);
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2);
        refreshFileListButton = new JButton();
        refreshFileListButton.setContentAreaFilled(false);
        Font refreshFileListButtonFont = this.$$$getFont$$$(null, -1, -1, refreshFileListButton.getFont());
        if (refreshFileListButtonFont != null) refreshFileListButton.setFont(refreshFileListButtonFont);
        refreshFileListButton.setHorizontalAlignment(0);
        refreshFileListButton.setHorizontalTextPosition(11);
        refreshFileListButton.setIconTextGap(0);
        refreshFileListButton.setMargin(new Insets(0, 0, 0, 0));
        refreshFileListButton.setOpaque(false);
        refreshFileListButton.setText("Rafraichir");
        refreshFileListButton.setVerifyInputWhenFocusTarget(false);
        refreshFileListButton.setVerticalTextPosition(0);
        panel2.add(refreshFileListButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 200, 0, 0), -1, -1));
        panel1.add(panel3);
        downloadFileButton = new JButton();
        downloadFileButton.setContentAreaFilled(false);
        downloadFileButton.setEnabled(false);
        downloadFileButton.setText("Télécharger");
        panel3.add(downloadFileButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteFileButton = new JButton();
        deleteFileButton.setContentAreaFilled(false);
        deleteFileButton.setEnabled(false);
        deleteFileButton.setText("Supprimer");
        panel3.add(deleteFileButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        downloadPanel.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        fileList = new JList();
        fileList.setBackground(new Color(-1));
        Font fileListFont = this.$$$getFont$$$("Roboto Lt", Font.PLAIN, 14, fileList.getFont());
        if (fileListFont != null) fileList.setFont(fileListFont);
        fileList.setForeground(new Color(-16777216));
        final DefaultListModel defaultListModel1 = new DefaultListModel();
        fileList.setModel(defaultListModel1);
        fileList.setVisible(true);
        fileList.putClientProperty("List.isFileList", Boolean.FALSE);
        fileList.putClientProperty("html.disable", Boolean.FALSE);
        scrollPane1.setViewportView(fileList);
        sharePanel = new JPanel();
        sharePanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        sharePanel.setName("");
        menuTabbedPane.addTab("Partage", sharePanel);
        final JScrollPane scrollPane2 = new JScrollPane();
        sharePanel.add(scrollPane2, new GridConstraints(1, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        localFilesList = new JList();
        scrollPane2.setViewportView(localFilesList);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        sharePanel.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Mes fichiers");
        panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel4.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, 20));
        sharePanel.add(panel5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        deleteLocalFileButton = new JButton();
        deleteLocalFileButton.setContentAreaFilled(false);
        deleteLocalFileButton.setEnabled(false);
        deleteLocalFileButton.setText("Supprimer");
        panel5.add(deleteLocalFileButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Envoyer à");
        panel5.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        sharePanel.add(scrollPane3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        recipientsList = new JList();
        recipientsList.setEnabled(false);
        scrollPane3.setViewportView(recipientsList);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 1, new Insets(15, 0, 0, 0), -1, -1));
        sharePanel.add(panel6, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        sharePanel.add(panel7, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        sendLocalFileButton = new JButton();
        sendLocalFileButton.setContentAreaFilled(false);
        sendLocalFileButton.setEnabled(false);
        sendLocalFileButton.setText("OK");
        panel7.add(sendLocalFileButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        parametersPanel = new JPanel();
        parametersPanel.setLayout(new GridLayoutManager(5, 3, new Insets(5, 0, 0, 0), -1, -1));
        menuTabbedPane.addTab("Paramètres", parametersPanel);
        final Spacer spacer4 = new Spacer();
        parametersPanel.add(spacer4, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        portParamLabel = new JLabel();
        portParamLabel.setText("Port de connexion");
        parametersPanel.add(portParamLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        selectedFolderParamLabel = new JLabel();
        selectedFolderParamLabel.setText("Répertoire à partager");
        parametersPanel.add(selectedFolderParamLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel8.setEnabled(true);
        panel8.setOpaque(false);
        panel8.setRequestFocusEnabled(false);
        panel8.setVerifyInputWhenFocusTarget(false);
        panel8.setVisible(true);
        parametersPanel.add(panel8, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        selectFolderButton = new JButton();
        selectFolderButton.setBackground(new Color(-10921639));
        selectFolderButton.setBorderPainted(true);
        selectFolderButton.setContentAreaFilled(false);
        selectFolderButton.setForeground(new Color(-11579569));
        selectFolderButton.setHorizontalAlignment(0);
        selectFolderButton.setText("Choisir un dossier");
        panel8.add(selectFolderButton);
        selectedFolder = new JLabel();
        selectedFolder.setForeground(new Color(-7562821));
        selectedFolder.setText("Label");
        panel8.add(selectedFolder);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel9.setFocusable(false);
        parametersPanel.add(panel9, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        portParamField = new JTextField();
        portParamField.setBackground(new Color(-1));
        portParamField.setForeground(new Color(-16777216));
        portParamField.setHorizontalAlignment(0);
        portParamField.setInheritsPopupMenu(true);
        portParamField.setMargin(new Insets(2, 5, 2, 5));
        portParamField.setMinimumSize(new Dimension(100, 30));
        portParamField.setPreferredSize(new Dimension(100, 30));
        portParamField.putClientProperty("caretAspectRatio", new Double(0.0));
        portParamField.putClientProperty("caretWidth", new Integer(0));
        portParamField.putClientProperty("html.disable", Boolean.TRUE);
        panel9.add(portParamField);
        savePortParamButton = new JButton();
        savePortParamButton.setContentAreaFilled(false);
        savePortParamButton.setHorizontalAlignment(0);
        savePortParamButton.setHorizontalTextPosition(0);
        savePortParamButton.setIconTextGap(4);
        savePortParamButton.setText("OK");
        panel9.add(savePortParamButton);
        final JLabel label3 = new JLabel();
        label3.setForeground(new Color(-7562821));
        label3.setText("Par défaut : ");
        panel9.add(label3);
        defaultPortParam = new JLabel();
        defaultPortParam.setForeground(new Color(-7562821));
        defaultPortParam.setText("Label");
        panel9.add(defaultPortParam);
        peerParamLabel = new JLabel();
        peerParamLabel.setText("Ajouter un pair");
        parametersPanel.add(peerParamLabel, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JPanel panel10 = new JPanel();
        panel10.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 5));
        panel10.setFocusable(false);
        panel10.setVisible(true);
        parametersPanel.add(panel10, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        peerParamField = new JTextField();
        peerParamField.setBackground(new Color(-1));
        peerParamField.setDisabledTextColor(new Color(-1));
        peerParamField.setForeground(new Color(-16777216));
        peerParamField.setHorizontalAlignment(0);
        peerParamField.setInheritsPopupMenu(false);
        peerParamField.setMargin(new Insets(2, 5, 2, 5));
        peerParamField.setMinimumSize(new Dimension(128, 30));
        peerParamField.setOpaque(true);
        peerParamField.setPreferredSize(new Dimension(128, 30));
        peerParamField.setText("");
        peerParamField.putClientProperty("caretAspectRatio", new Double(0.0));
        peerParamField.putClientProperty("caretWidth", new Integer(0));
        peerParamField.putClientProperty("html.disable", Boolean.TRUE);
        panel10.add(peerParamField);
        addPeerButton = new JButton();
        addPeerButton.setContentAreaFilled(false);
        addPeerButton.setText("+");
        panel10.add(addPeerButton);
        final Spacer spacer5 = new Spacer();
        panel10.add(spacer5);
        final Spacer spacer6 = new Spacer();
        panel10.add(spacer6);
        final Spacer spacer7 = new Spacer();
        panel10.add(spacer7);
        final Spacer spacer8 = new Spacer();
        panel10.add(spacer8);
        final Spacer spacer9 = new Spacer();
        panel10.add(spacer9);
        final Spacer spacer10 = new Spacer();
        panel10.add(spacer10);
        final Spacer spacer11 = new Spacer();
        panel10.add(spacer11);
        final Spacer spacer12 = new Spacer();
        panel10.add(spacer12);
        final Spacer spacer13 = new Spacer();
        panel10.add(spacer13);
        final Spacer spacer14 = new Spacer();
        panel10.add(spacer14);
        final Spacer spacer15 = new Spacer();
        panel10.add(spacer15);
        final Spacer spacer16 = new Spacer();
        panel10.add(spacer16);
        final Spacer spacer17 = new Spacer();
        panel10.add(spacer17);
        final Spacer spacer18 = new Spacer();
        panel10.add(spacer18);
        deletePeerButton = new JButton();
        deletePeerButton.setBorderPainted(true);
        deletePeerButton.setContentAreaFilled(false);
        deletePeerButton.setEnabled(false);
        deletePeerButton.setHorizontalAlignment(0);
        deletePeerButton.setText("Supprimer");
        panel10.add(deletePeerButton);
        final JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayoutManager(1, 1, new Insets(5, 10, 5, 35), -1, -1));
        parametersPanel.add(panel11, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane4 = new JScrollPane();
        panel11.add(scrollPane4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        peerList = new JList();
        scrollPane4.setViewportView(peerList);
        final JPanel panel12 = new JPanel();
        panel12.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
        infoMessage = new JLabel();
        infoMessage.setForeground(new Color(-28902));
        infoMessage.setHorizontalAlignment(0);
        infoMessage.setHorizontalTextPosition(10);
        infoMessage.setText("");
        panel12.add(infoMessage, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
