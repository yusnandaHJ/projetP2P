package gui;

import client.FileClient;
import client.PeerClient;
import manager.FilesListManager;
import manager.PeersListManager;
import representation.File;
import representation.Peer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class MainFrameController {
    public static final int DEFAULT_PORT = 0;
    public static final String DEFAULT_FOLDER_PATH = "/shared_folder";

    private int connectionPort;
    private String selectedFolderPath = DEFAULT_FOLDER_PATH;
    private List<File> fileList;
    private List<File> localFileList;
    private HashMap<File,List<Peer>> fileMap;
    private List<Peer> peerList;
    private File fileToDownload;
    private File fileToDelete;
    private File localFileToSend;
    private List<Peer> fileRecipients;
    private File localFileToDelete;
    private MainFrame mainframe;

    public MainFrameController() {
        initMainFrame();
        refreshFileList();
        refreshLocalFileList();
        refreshParametersLabels();
        refreshPeerList();
        runButtonListeners();
    }

    private void initMainFrame() {
        connectionPort = DEFAULT_PORT;
        selectedFolderPath = DEFAULT_FOLDER_PATH;
        //peerList = new ArrayList<>();
        peerList = PeersListManager.readPeers();
        fileList = new ArrayList<>();
        fileToDownload = null;
        mainframe = new MainFrame();
        mainframe.getDefaultPortParam().setText(Integer.toString(DEFAULT_PORT)); // default port, can't change during execution
    }

    public void refreshParametersLabels() {
        mainframe.getSelectedFolder().setText(selectedFolderPath);
        mainframe.getPortParamField().setText(Integer.toString(connectionPort));
    }

    public void refreshFileList() {
        mainframe.getDownloadFileButton().setEnabled(false);
        mainframe.getDeleteFileButton().setEnabled(false);

        DefaultListModel<File> model = new DefaultListModel<>();
        mainframe.getFileList().setModel(model);

        fileMap = FilesListManager.getAvailableFiles();
        fileList = FilesListManager.getFileListFromMap(fileMap);

        if(fileList != null) {
            for (File f : fileList) {
                model.addElement(f);
            }
        }
    }

    public void refreshLocalFileList() {
        mainframe.getDeleteLocalFileButton().setEnabled(false);
        mainframe.getRecipientsList().setEnabled(false);
        mainframe.getSendLocalFileButton().setEnabled(false);

        DefaultListModel<File> model = new DefaultListModel<>();
        mainframe.getLocalFilesList().setModel(model);

        localFileList = FilesListManager.readFiles();

        if(localFileList != null) {
            for (File f : localFileList) {
                model.addElement(f);
            }
        }
    }

    public void refreshPeerList() {
        mainframe.getDeletePeerButton().setEnabled(false);
        mainframe.getDeleteLocalFileButton().setEnabled(false);
        mainframe.getSendLocalFileButton().setEnabled(false);

        DefaultListModel<Peer> model = new DefaultListModel<>();
        mainframe.getPeerList().setModel(model);
        mainframe.getRecipientsList().setModel(model);

        if(peerList != null) {
            for (Peer p : peerList) {
                model.addElement(p);
            }
        }
    }

    public void runButtonListeners() {
        /*------------------ Download Tab Listeners --------------------*/

        /* Listener for file list refresh button */
        mainframe.getRefreshFileListButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshFileList();
            }
        });

        /* Listener for file list selection */
        mainframe.getFileList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getFileList().getSelectedValue() != null) {
                    mainframe.getDownloadFileButton().setEnabled(true);
                    mainframe.getDeleteFileButton().setEnabled(true);
                }
            }
        });

        /* Listener for download file button */
        mainframe.getDownloadFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileToDelete = null;
                fileToDownload = (File) mainframe.getFileList().getSelectedValue();
                FileClient.getFile(fileMap.get(fileToDownload).get(0).getUrl(),fileToDownload.getFileId());
                refreshLocalFileList();
                refreshFileList();
                displayFileActionConsole();
            }
        });

        /* Listener for delete file button */
        mainframe.getDeleteFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileToDownload = null;
                fileToDelete = (File) mainframe.getFileList().getSelectedValue();

                List<Peer> peers = fileMap.get(fileToDelete);
                for (Peer p: peers){
                    //SSystem.out.println(fileToDelete);
                    FileClient.deleteFile(p.getUrl(),fileToDelete.getFileId());
                }

                refreshFileList();
                refreshLocalFileList();

                displayFileActionConsole();
            }
        });

        /*------------------ Share Tab Listeners --------------------*/

        /* Listener for local file list selection */
        mainframe.getLocalFilesList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getLocalFilesList().getSelectedValue() != null){
                    mainframe.getDeleteLocalFileButton().setEnabled(true);
                    mainframe.getRecipientsList().setEnabled(true);
                }
            }
        });

        /* Listener for delete local file button */
        mainframe.getDeleteLocalFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localFileToSend = null;
                fileRecipients = null;
                localFileToDelete = (File) mainframe.getLocalFilesList().getSelectedValue();
                displayLocalFileActionConsole();
            }
        });

        /* Listener for recipient list selection */
        mainframe.getRecipientsList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getRecipientsList().getSelectedValue() != null) {
                    mainframe.getSendLocalFileButton().setEnabled(true);
                }
            }
        });

        /* Listener for send local file confirm button */
        mainframe.getSendLocalFileButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localFileToDelete = null;
                localFileToSend = (File) mainframe.getLocalFilesList().getSelectedValue();
                fileRecipients = (List<Peer>) mainframe.getRecipientsList().getSelectedValuesList();
                for (Peer p: fileRecipients
                     ) {
                    System.out.println("send file "+localFileList.toString()+ " to "+p.getUrl()); //à remplacer par un vrai upload client
                }
                displayLocalFileActionConsole();
            }
        });


        /*----------------- Parameters Tab Listeners --------------------*/

        /* Listener for folder button selection */
        mainframe.getSelectFolderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainframe.openFileChooser();
                if(mainframe.getSelectedFolder().getText() != null) {
                    selectedFolderPath = mainframe.getSelectedFolder().getText();
                }
                refreshParametersLabels();
                displayParamsConsole();
            }
        });

        /* Listener for peer list selection */
        mainframe.getPeerList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(mainframe.getPeerList().getSelectedValue() != null) {
                    mainframe.getDeletePeerButton().setEnabled(true);
                }
            }
        });

        /* Listener for peer add button */
        mainframe.getAddPeerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String peerUrl = mainframe.getPeerParamField().getText();
                if(!mainframe.getPeerParamField().getText().isEmpty()) {
                    peerList.add(new Peer(peerUrl));
                    mainframe.getPeerParamField().setText("");
                }
                PeersListManager.savePeers(peerList);
                refreshPeerList();
                displayParamsConsole();
            }
        });

        /* Listener for peer delete button */
        mainframe.getDeletePeerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JList pl = mainframe.getPeerList();
                peerList.remove(pl.getSelectedValue());
                ((DefaultListModel) pl.getModel()).remove(pl.getSelectedIndex());
                PeersListManager.savePeers(peerList);
                refreshPeerList();
                displayParamsConsole();
            }
        });

        /* Listener for save parameters button */
        mainframe.getSavePortParamButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!mainframe.getPortParamField().getText().isEmpty()) {
                    try {
                        connectionPort = Integer.parseInt(mainframe.getPortParamField().getText());
                    } catch (NumberFormatException ex) {
                        connectionPort = DEFAULT_PORT;
                    }
                }

                refreshParametersLabels();
                refreshPeerList();
                displayParamsConsole();
            }
        });
    }

    public void displayFileActionConsole() {
        System.out.println("--- File action detected ---");
        System.out.println("File to download : " + fileToDownload);
        System.out.println("File to delete : " + fileToDelete);
    }

    public void displayLocalFileActionConsole() {
        System.out.println("--- Local file action detected ---");
        System.out.println("Local file to send : " + localFileToSend);
        System.out.println("Local file recipient(s) : " + fileRecipients);
        System.out.println("Local file to delete : " + localFileToDelete);
    }

    public void displayParamsConsole() {
        System.out.println("--- Parameters changes save ---");
        System.out.println("Connection port : " + connectionPort);
        System.out.println("Selected folder : " + selectedFolderPath);
        System.out.println("List of known peers : " + peerList.toString());
    }

    public List<File> getFileList() { return fileList; }

    public void setFileList(List<File> fileList) { this.fileList = fileList; }

    public List<File> getLocalFileList() { return localFileList; }

    public void setLocalFileList(List<File> localFileList) { this.localFileList = localFileList; }

    public List<Peer> getPeerList() { return peerList; }

    public void setPeerList(List<Peer> peerList) { this.peerList = peerList; }

    public File getFileToDownload() { return fileToDownload; }

    public void setFileToDownload(File fileToDownload) { this.fileToDownload = fileToDownload; }

    public File getFileToDelete() { return fileToDelete; }

    public void setFileToDelete(File fileToDelete) { this.fileToDelete = fileToDelete; }

    public File getLocalFileToSend() { return localFileToSend; }

    public void setLocalFileToSend(File localFileToSend) { this.localFileToSend = localFileToSend; }

    public File getLocalFileToDelete() { return localFileToDelete; }

    public void setLocalFileToDelete(File localFileToDelete) { this.localFileToDelete = localFileToDelete; }

    public List<Peer> getFileRecipients() { return fileRecipients; }

    public void setFileRecipients(List<Peer> fileRecipients) { this.fileRecipients = fileRecipients; }

    public HashMap<File, List<Peer>> getFileMap() {
        return fileMap;
    }

    public void setFileMap(HashMap<File, List<Peer>> fileMap) {
        this.fileMap = fileMap;
    }
}
